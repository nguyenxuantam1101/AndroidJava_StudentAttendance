package com.example.appdiemdanh.data;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.Transliterator;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.example.appdiemdanh.AccountActivity;
import com.example.appdiemdanh.ClassView;
import com.example.appdiemdanh.GV_ClassView;
import com.example.appdiemdanh.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.util.ArrayList;
import java.util.Calendar;

import java.util.Random;

public class MainClass extends AppCompatActivity {
    private static final int REQUEST_CODE_HOME = 10;
    BottomNavigationView bottomNavigationView;

    EditText editStartDay, editEndDay, editClassName, editScore, editSubject, editClassRoom;
    Button btnStartDay, btnEndDay, btnAcpCreateClass, btnCancelCreateClass, btnCancelEdit, btnAcpEdit;

    Database database;
    ListView lvClass;
    ArrayList<Class> arrClass;
    ClassAdapter adapter;
    //hàm tạo random mã lớp học
    private static final String random_characters = "0123456789QWERTYUIOPASDFGHJKLZXCVBNM";

    private String code() {
        String code;
        final Random random = new Random();
        final StringBuilder sb = new StringBuilder(6);
        for (int i = 0; i < 6; i++)
            sb.append(random_characters.charAt(random.nextInt(random_characters.length())));
        code = String.valueOf(sb);
        return code;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_class);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        //Thanh Navigation
        //Lựa chọn Home
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        return true;
                    case R.id.account:
                        if (Authorization() == true) {
                            int idAccount = IdAccountLogin();
                            Cursor cursor = database.GetData("Select * From StudentTable Inner Join AccountTable ON AccountTable.StudentId = StudentTable.StudentId Where AccountId = '" + idAccount + "'");
                            cursor.moveToFirst();
                            String NameAccount = cursor.getString(2);
                            String CodeAccount = cursor.getString(1);
                            String Birthday = cursor.getString(3);
                            String Class = cursor.getString(6);
                            String Gmail = cursor.getString(5);
                            String Phone = cursor.getString(4);
                            SwapAccount(idAccount, NameAccount, "Sinh Viên", CodeAccount, Birthday, Class, Gmail, Phone);
                        } else {
                            int idAccount = IdAccountLogin();
                            Cursor cursor = database.GetData("Select * From TeacherTable Inner Join AccountTable ON AccountTable.TeacherId = TeacherTable.TeacherId Where AccountId = '" + idAccount + "'");
                            cursor.moveToFirst();
                            String NameAccount = cursor.getString(2);
                            String CodeAccount = cursor.getString(1);
                            String Birthday = cursor.getString(3);
                            String Class = "";
                            String Gmail = cursor.getString(5);
                            String Phone = cursor.getString(4);
                            SwapAccount(idAccount, NameAccount, "Giảng Viên", CodeAccount, Birthday, Class, Gmail, Phone);
                        }
                        return true;
                }
                return false;
            }
        });
        bottomNavigationView.setBackground(null);

        //tìm kiếm
        SearchView searchView = findViewById(R.id.search);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                ArrayList<Class> filterClass = new ArrayList<Class>();
                for (Class lopHoc : arrClass) {
                    if (lopHoc.getClassName().toLowerCase().contains(query.toLowerCase())
                            || lopHoc.getTeacherName().toLowerCase().contains(query.toLowerCase())) {
                        filterClass.add(lopHoc);
                    }
                }
                if (filterClass.isEmpty()) {
                    Toast.makeText(MainClass.this, "Không tìm thấy", Toast.LENGTH_SHORT).show();
                }
                adapter = new ClassAdapter(MainClass.this, R.layout.activity_class, filterClass);
                lvClass.setAdapter(adapter);
                return false;
            }
        });
        searchView.setVisibility(View.GONE);
        ImageView imgSearch = findViewById(R.id.imgSearch);
        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imgSearch.getVisibility() == View.VISIBLE) {
                    imgSearch.setVisibility(View.INVISIBLE);
                    searchView.setVisibility(View.VISIBLE);
                }
            }
        });

        //xuất lớp học vào listview
        lvClass = (ListView) findViewById(R.id.listviewClass);
        arrClass = new ArrayList<>();
        adapter = new ClassAdapter(this, R.layout.activity_class, arrClass);
        lvClass.setAdapter(adapter);

        lvClass.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState) {
                switch (scrollState) {
                    case SCROLL_STATE_IDLE:
                        imgSearch.setVisibility(View.VISIBLE);
                        break;
                    case SCROLL_STATE_TOUCH_SCROLL:
                        searchView.setVisibility(View.GONE);
                        imgSearch.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

            }
        });
        //gọi database
        database = new Database(this);
        //select data
        GetDataClass();

        //mở bottom sheet
        FloatingActionButton fabShowBottom = (FloatingActionButton) findViewById(R.id.fabShowBottom);
        fabShowBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheet(Gravity.BOTTOM);
            }
        });
    }

    private void SwapAccount(int idAccount, String nameAccount, String role, String codeAccount, String birthday, String Class, String gmail, String phone) {
        Intent intent = new Intent(MainClass.this, AccountActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("Key_Swap", idAccount);
        bundle.putString("Key_Swap1", nameAccount);
        bundle.putString("Key_Swap2", role);
        bundle.putString("Key_Swap3", codeAccount);
        bundle.putString("Key_Swap4", birthday);
        bundle.putString("Key_Swap5", Class);
        bundle.putString("Key_Swap6", gmail);
        bundle.putString("Key_Swap7", phone);
        intent.putExtras(bundle);
        startActivityForResult(intent, REQUEST_CODE_HOME);
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (REQUEST_CODE_HOME == requestCode && Activity.RESULT_OK == resultCode) {
            int id = data.getIntExtra("Key_Swap", 0);
            int idClass = data.getIntExtra("Key_Class", 0);
            bottomNavigationView.setSelectedItemId(R.id.home);
        }
    }

    //select data -> list lớp
    private void GetDataClass() {
        int accountId = IdAccountLogin();
        if (Authorization() == true) {
            Cursor dataClass = database.GetData("SELECT ClassTable.ClassId, ClassTable.ClassName, TeacherName,ClassDetailsTable.Status FROM (((ClassTable " +
                    "INNER JOIN AccountTable ON AccountTable.AccountId = ClassTable.AccountId) " +
                    "INNER JOIN ClassDetailsTable ON ClassDetailsTable.ClassId = ClassTable.ClassId) " +
                    "INNER JOIN TeacherTable ON AccountTable.TeacherId = TeacherTable.TeacherId) " +
                    "WHERE ClassDetailsTable.AccountId = '" + accountId + "'");
            arrClass.clear();
            while (dataClass.moveToNext()) {
                if (dataClass.getInt(3) == 1) {
                    String classname = dataClass.getString(1);
                    String teachername = dataClass.getString(2);
                    int id = dataClass.getInt(0);
                    arrClass.add(new Class(id, classname, teachername));
                }
            }
            adapter.notifyDataSetChanged();
        } else {
            Cursor dataClass = database.GetData("SELECT ClassId, ClassName, TeacherName, ClassTable.Status FROM ((AccountTable " +
                    "INNER JOIN TeacherTable ON AccountTable.TeacherId = TeacherTable.TeacherId) " +
                    "INNER JOIN ClassTable ON AccountTable.AccountId = ClassTable.AccountId) " +
                    "WHERE AccountTable.AccountId = '" + accountId + "'");
            arrClass.clear();
            while (dataClass.moveToNext()) {
                if (dataClass.getInt(3) == 1) {
                    String classname = dataClass.getString(1);
                    String teachername = dataClass.getString(2);
                    int id = dataClass.getInt(0);
                    arrClass.add(new Class(id, classname, teachername));
                }
            }
            adapter.notifyDataSetChanged();
        }
    }

    public void DeleteClass(int idClass) {
        AlertDialog.Builder dialogDelete = new AlertDialog.Builder(this);
        if (Authorization() == true) {
            dialogDelete.setMessage("Khi thực hiện thao tác này bạn sẽ rời khỏi lớp học và không thể tham gia lại, bạn có muốn thực hiện điều này không?");
            dialogDelete.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    int Status = 0;
                    database.QueryData("UPDATE ClassDetailsTable SET Status = '" + Status + "' WHERE ClassId = '" + idClass + "'AND AccountId ='" + IdAccountLogin() + "'");
                    Toast.makeText(MainClass.this, "Bạn Đã Rời Khỏi Lớp Học Thành Công", Toast.LENGTH_SHORT).show();
                    GetDataClass();
                }
            });
            dialogDelete.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            dialogDelete.show();
        } else {
            dialogDelete.setMessage("Bạn có thật sự muốn xoá lớp này không? Sau khi xoá sẽ không thể khôi phục lại lớp học!");
            dialogDelete.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    int Status = 0;
                    database.QueryData("UPDATE ClassDetailsTable SET Status = '" + Status + "' WHERE ClassId = '" + idClass + "'");
                    database.QueryData("UPDATE ClassTable SET Status = '" + Status + "' WHERE ClassId = '" + idClass + "'");
                    Toast.makeText(MainClass.this, "Xoá Thành Công Lớp Học", Toast.LENGTH_SHORT).show();
                    GetDataClass();
                }
            });
            dialogDelete.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            dialogDelete.show();
        }
    }

    public void ClassDetails(int idClass) {
        if (Authorization() == true) {
            //user
            int idAccountLogin = IdAccountLogin();
            Intent intent = new Intent(MainClass.this, ClassView.class);
            Bundle bundle = new Bundle();
            bundle.putInt("Key_Class", idClass);
            bundle.putInt("Key_AccountLogin", idAccountLogin);
            intent.putExtras(bundle);
            startActivityForResult(intent, REQUEST_CODE_HOME);
        } else {
            //admin
            Intent intent = new Intent(MainClass.this, GV_ClassView.class);
            Bundle bundle = new Bundle();
            bundle.putInt("Key_Class", idClass);
            intent.putExtras(bundle);
            startActivityForResult(intent, REQUEST_CODE_HOME);
        }
    }

    //lấy quyền (0: user; 1: admin)
    public boolean Authorization() {
        int idAccountLogin = IdAccountLogin();
        int role = database.GetRole(idAccountLogin);
        if (role == 0) {
            return true;
        } else {
            return false;
        }
    }

    public int IdAccountLogin() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int idAccountLogin = bundle.getInt("Key_User", 0);
        return idAccountLogin;
    }

    private void BottomSheet(int gravity) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_bottom_sheet);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }

        //set thuộc tính
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);

        //tắt dialog khi click ra ngoài
        if (Gravity.BOTTOM == gravity) {
            dialog.setCancelable(true);
        } else {
            dialog.setCancelable(false);
        }

        Button btnTaoLop = dialog.findViewById(R.id.btnTaoLop);
        Button btnThamGiaLopHoc = dialog.findViewById(R.id.btnThamGiaLopHoc);

        if (Authorization() == true) {
            btnTaoLop.setVisibility(View.GONE);
            btnThamGiaLopHoc.setVisibility(View.VISIBLE);
        } else {
            //btnThamGiaLopHoc.setVisibility(View.GONE);
            btnTaoLop.setVisibility(View.VISIBLE);
        }

        btnThamGiaLopHoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                JoinClassDialog(Gravity.CENTER);

            }
        });
        btnTaoLop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                CreateClassDialog(Gravity.BOTTOM);
            }
        });
        dialog.show();
    }

    //tham gia lớp
    private void JoinClassDialog(int gravity) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_join_class);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }

        //set thuộc tính
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);

        //tắt dialog khi click ra ngoài
        if (Gravity.CENTER == gravity) {
            dialog.setCancelable(true);
        } else {
            dialog.setCancelable(false);
        }

        EditText editCodeJoinClass = dialog.findViewById(R.id.txtCodeJoinClass);
        Button btnJoinClass = dialog.findViewById(R.id.btnAcceptJoinClass);
        btnJoinClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String codeJoinClass = editCodeJoinClass.getText().toString();
                if (codeJoinClass.equals("")) {
                    Toast.makeText(MainClass.this, "Nhập mã lớp học mà giáo viên cung cấp", Toast.LENGTH_SHORT).show();
                } else {
                    Boolean checkCodeJoin = database.CheckCodeJoin(codeJoinClass);
                    int IdClass = database.GetIdClass(codeJoinClass);
                    int AccountId = IdAccountLogin();
                    int checkStatus = database.GetStatusClass(codeJoinClass);
                    float score = 0;
                    int soBuoiDaDiemDanh = 0;
                    int Status = 1;
                    Boolean checkUserInClass = database.CheckUserInClass(String.valueOf(AccountId), String.valueOf(IdClass));
                    int checkUserRemovedClass = database.CheckUserRemovedClass(AccountId, IdClass);
                    if (checkStatus == 0) {
                        Toast.makeText(MainClass.this, "Mã lớp học không tồn tại", Toast.LENGTH_SHORT).show();
                    } else {
                        if (checkUserRemovedClass == 0) {
                            AlertDialog.Builder dialog = new AlertDialog.Builder(MainClass.this);
                            dialog.setMessage("Bạn đã bị giáo viên xoá hoặc đã tự rời khỏi lớp học nên không thể tham gia lại lớp học này.\nVui lòng liên hệ bộ phận kỹ thuật để được hỗ trợ");
                            dialog.setNegativeButton("Quay Lại", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                            dialog.show();
                        } else {
                            if (checkCodeJoin == true) {
                                if (checkUserInClass == true) {
                                    Toast.makeText(MainClass.this, "Bạn đã tham gia lớp học này rồi", Toast.LENGTH_SHORT).show();
                                } else {
                                    dialog.dismiss();
                                    //add data vaof
                                    database.QueryData("INSERT INTO ClassDetailsTable VALUES(null, '" + AccountId + "','" + IdClass + "'," +
                                            "'" + score + "', '" + soBuoiDaDiemDanh + "' , '" + Status + "')");
                                    GetDataClass();
                                    Toast.makeText(MainClass.this, "Tham gia lớp học thành công", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(MainClass.this, "Mã lớp học không tồn tại", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }

                }
            }
        });
        dialog.show();
    }

    //tạo lớp
    private void CreateClassDialog(int gravity) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_create_class);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        } else {
            //set thuộc tính
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            WindowManager.LayoutParams windowAttributes = window.getAttributes();
            windowAttributes.gravity = gravity;
            window.setAttributes(windowAttributes);
        }

        //xử lý các thuộc tính tại layout create class
        btnAcpCreateClass = dialog.findViewById(R.id.btnAcpCreateClass);
        editClassName = dialog.findViewById(R.id.txtClassName);
        editSubject = dialog.findViewById(R.id.txtSubject);
        editScore = dialog.findViewById(R.id.txtScore);
        editClassRoom = dialog.findViewById(R.id.txtClassRoom);
        btnStartDay = dialog.findViewById(R.id.btnStartDay);
        btnEndDay = dialog.findViewById(R.id.btnEndDay);
        editStartDay = dialog.findViewById(R.id.txtStartDay);
        editEndDay = dialog.findViewById(R.id.txtEndDay);
        btnCancelCreateClass = dialog.findViewById(R.id.btnCancelCreateClass);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(calendar.YEAR);
        final int month = calendar.get(calendar.MONTH);
        final int day = calendar.get(calendar.DAY_OF_MONTH);
        //chọn ngày
        btnStartDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(MainClass.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month + 1;
                        String date = day + "/" + month + "/" + year;
                        editStartDay.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();

            }
        });
        btnEndDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(MainClass.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month + 1;
                        String date = day + "/" + month + "/" + year;
                        editEndDay.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
        //thao tác quay về giao diện chính
        btnCancelCreateClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        //Đồng ý tạo lớp và lưu trữ vào database
        btnAcpCreateClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String classname = editClassName.getText().toString().trim();
                String subject = editSubject.getText().toString().trim();
                String classroom = editClassRoom.getText().toString().trim();
                String scorePercent = editScore.getText().toString().trim();
                String startDay = editStartDay.getText().toString().trim();
                String endDay = editEndDay.getText().toString().trim();
                String codeJoinClass = code().trim();
                String codeAttendance = "".trim();
                int status = 1;
                int accountId = IdAccountLogin();
                int numOfAttendance = 0;
                int successAttendance = 0;
                if (classname.equals("")) {
                    Toast.makeText(MainClass.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    database.QueryData("INSERT INTO ClassTable VALUES(null, '" + classname + "', '" + subject + "', '" + classroom + "', " +
                            "'" + startDay + "', '" + endDay + "', '" + scorePercent + "', '" + codeJoinClass + "','" + codeAttendance + "', " +
                            "'" + status + "' , '" + accountId + "', '" + numOfAttendance + "', '" + successAttendance + "')");

                    Toast.makeText(MainClass.this, "Tạo lớp học thành công", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    GetDataClass();
                }
            }
        });
        dialog.show();
    }

    //sửa thông tin lớp (admin)
    public void EditClassDialog(int gravity, int classId) {
        AlertDialog.Builder dialogEdit = new AlertDialog.Builder(this);
        if (Authorization() == true) {
            dialogEdit.setMessage("Bạn không có quyền để thực hiện điều này vui lòng quay trở lại.");
            dialogEdit.setNegativeButton("Quay Lại", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            dialogEdit.show();
        } else {
            final Dialog dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.layout_edit_class);
            Window window = dialog.getWindow();
            if (window == null) {
                return;
            } else {
                //set thuộc tính
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                WindowManager.LayoutParams windowAttributes = window.getAttributes();
                windowAttributes.gravity = gravity;
                window.setAttributes(windowAttributes);
            }
            //xử lý các thuộc tính tại layout edit class
            btnAcpEdit = dialog.findViewById(R.id.btnAcpEdit);
            btnCancelEdit = dialog.findViewById(R.id.btnCancelEdit);
            Button btnEditStartDay = dialog.findViewById(R.id.btnEditStartDay);
            Button btnEditEndDay = dialog.findViewById(R.id.btnEditEndDay);
            EditText classNameEdit = dialog.findViewById(R.id.editClassName);
            EditText startDayEdit = dialog.findViewById(R.id.editStartDay);
            EditText endDayEdit = dialog.findViewById(R.id.editEndDay);
            EditText scorePercentEdit = dialog.findViewById(R.id.editScorePercent);
            EditText subjectEdit = dialog.findViewById(R.id.editSubject);
            EditText classRoomEdit = dialog.findViewById(R.id.editClassRoom);

            int id = IdAccountLogin();
            Cursor cursor = database.GetData("SELECT * FROM ClassTable WHERE AccountId = '" + id + "' AND ClassId = '" + classId + "'");
            cursor.moveToFirst();
            classNameEdit.setText(cursor.getString(1));
            startDayEdit.setText(cursor.getString(4));
            endDayEdit.setText(cursor.getString(5));
            scorePercentEdit.setText(String.valueOf(cursor.getDouble(6)));
            subjectEdit.setText(cursor.getString(2));
            classRoomEdit.setText(cursor.getString(3));

            Calendar calendar = Calendar.getInstance();
            final int year = calendar.get(calendar.YEAR);
            final int month = calendar.get(calendar.MONTH);
            final int day = calendar.get(calendar.DAY_OF_MONTH);
            //chọn ngày
            btnEditStartDay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(MainClass.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                            month = month + 1;
                            String date = day + "/" + month + "/" + year;
                            startDayEdit.setText(date);
                        }
                    }, year, month, day);
                    datePickerDialog.show();

                }
            });
            btnEditEndDay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(MainClass.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                            month = month + 1;
                            String date = day + "/" + month + "/" + year;
                            endDayEdit.setText(date);
                        }
                    }, year, month, day);
                    datePickerDialog.show();
                }
            });
            //thao tác quay về giao diện chính
            btnCancelEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            //Đồng ý sửa lớp và lưu trữ vào database
            btnAcpEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String newClassname = classNameEdit.getText().toString().trim();
                    String newSubject = subjectEdit.getText().toString().trim();
                    String newClassroom = classRoomEdit.getText().toString().trim();
                    String newScorePercent = scorePercentEdit.getText().toString().trim();
                    String newStartDay = startDayEdit.getText().toString().trim();
                    String newEndDay = endDayEdit.getText().toString().trim();
                    database.QueryData("UPDATE ClassTable SET ClassName = '" + newClassname + "', Subject = '" + newSubject + "', " +
                            "ClassRoom = '" + newClassroom + "', StartDay = '" + newStartDay + "', EndDay = '" + newEndDay + "', " +
                            "ScorePercent = '" + newScorePercent + "' WHERE ClassId = '" + classId + "'");
                    Toast.makeText(MainClass.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    GetDataClass();

                }
            });
            dialog.show();
        }
    }
}
