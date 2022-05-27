package com.example.appdiemdanh;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appdiemdanh.data.Class;
import com.example.appdiemdanh.data.ClassAdapter;
import com.example.appdiemdanh.data.Database;
import com.example.appdiemdanh.data.MainClass;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ClassView extends AppCompatActivity {

    Database database;
    private static final int REQUEST_CODE_CLASS_VIEW = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_view);

        database = new Database(this);
        GetDataClassView();

        Button btnChiTiet = findViewById(R.id.btnChiTiet);
        btnChiTiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = GetIdClass();
                ButtonDetails(id);
            }
        });
        ImageView imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int idClass = GetIdClass();
                Intent returnIntent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putInt("Key_Class", idClass);
                returnIntent.putExtras(bundle);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });
        Button btnAttendance = findViewById(R.id.btnAttendance);
        btnAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = GetIdClass();
                AttendanceDialog(Gravity.CENTER, id);
            }
        });
    }

    private void GetDataClassView(){
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int idAccountLogin = bundle.getInt("Key_AccountLogin", 0);
        int id = GetIdClass();
        TextView txtCodeClass = findViewById(R.id.txtCodeClass);
        TextView txt_diemQuaTrinh = findViewById(R.id.txt_diemQuaTrinh);
        TextView txt_soBuoiDiemDanh = findViewById(R.id.txt_soBuoiDiemDanh);
        Cursor cursor = database.GetData("SELECT ClassTable.CodeJoin, ClassDetailsTable.Score, ClassDetailsTable.SoBuoiDaDiemDanh " +
                "FROM ClassDetailsTable " +
                "INNER JOIN ClassTable ON ClassDetailsTable.ClassId = ClassTable.ClassId " +
                "WHERE ClassDetailsTable.ClassId = '" + id + "' AND ClassDetailsTable.AccountId = '" + idAccountLogin + "'");
        cursor.moveToFirst();
        txtCodeClass.setText(cursor.getString(0));
        txt_diemQuaTrinh.setText(String.valueOf(cursor.getDouble(1)));
        txt_soBuoiDiemDanh.setText(String.valueOf(cursor.getInt(2)));
    }

    //điểm danh
    private void AttendanceDialog(int gravity, int id) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_attendance);
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
        EditText editCodeAttendance = dialog.findViewById(R.id.editCodeAttendance);
        Button btnAcceptAttendance = dialog.findViewById(R.id.btnAcceptAtetendance);
        btnAcceptAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String codeAttendance = editCodeAttendance.getText().toString();
                Intent intent = getIntent();
                Bundle bundle = intent.getExtras();
                int idAccountLogin = bundle.getInt("Key_AccountLogin", 0);
                if (codeAttendance.equals("")) {
                    Toast.makeText(ClassView.this, "Nhập mã điểm danh mà giáo viên cung cấp", Toast.LENGTH_SHORT).show();
                } else {
                    int idClassAtten = database.IdClassAttendance(codeAttendance);
                    Boolean checkCode = database.CheckCodeAttendance(codeAttendance);
                    int IdAttendance = database.GetIdDetail(codeAttendance);
                    int AccountId = idAccountLogin;
                    DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy");
                    String DateTime = df.format(Calendar.getInstance().getTime());
                    Boolean checkUserAttendance = database.CheckUserAttendance(String.valueOf(AccountId), String.valueOf(IdAttendance));
                    if (checkCode == true) {
                        if (checkUserAttendance == true) {
                            Toast.makeText(ClassView.this, "Bạn đã điểm danh rồi!!!", Toast.LENGTH_SHORT).show();
                        } else {
                            dialog.dismiss();
                            // tính điểm điểm danh
                            int IdClassCodeAttendance = database.GetIdClassCodeAttendance(codeAttendance);
                            Cursor cursorDiem = database.GetData("SELECT * FROM ClassTable WHERE ClassId = '" + IdClassCodeAttendance + "'");
                            cursorDiem.moveToFirst();
                            float LayBuoiDiemDanh = cursorDiem.getInt(11);
                            float LayDiemQuaTrinh = cursorDiem.getInt(6);
                            float TongSoDiemQuaTrinh = (10 * LayDiemQuaTrinh) / 100;
                            float DiemTheoTungBuoi = TongSoDiemQuaTrinh / LayBuoiDiemDanh;
                            // tính số buổi đã điểm danh
                            int ClassDetailsId = database.GetIdClassDetail(AccountId, idClassAtten);
                            Cursor cursor = database.GetData("SELECT * FROM ClassDetailsTable WHERE ClassDetailsId = '" + ClassDetailsId + "'");
                            cursor.moveToFirst();
                            int SoBuoiDaDiemDanh = cursor.getInt(4) + 1;
                            float SoBuoiTinhDiem = cursor.getInt(4) + 1;
                            float DiemTongSV = DiemTheoTungBuoi * SoBuoiTinhDiem;
                            database.QueryData("UPDATE ClassDetailsTable SET SoBuoiDaDiemDanh = '" + SoBuoiDaDiemDanh + "', Score = '" + DiemTongSV + "' " +
                                    "WHERE ClassDetailsId = '" + ClassDetailsId + "'");
                            //add data vao
                            database.QueryData("INSERT INTO AttendanceDetailsTable VALUES(null, '" + AccountId + "','" + IdAttendance + "'," +
                                    "'" + codeAttendance + "', '" + DateTime + "')");
                            Toast.makeText(ClassView.this, "Điểm danh thành công", Toast.LENGTH_SHORT).show();
                            GetDataClassView();
                        }
                    } else {
                        Toast.makeText(ClassView.this, "Mã điểm danh không tồn tại", Toast.LENGTH_SHORT).show();
                    }
                }
            }

        });
        dialog.show();
    }

    private int GetIdClass() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int idClass = bundle.getInt("Key_Class", 0);
        return idClass;
    }

    private void ButtonDetails(int idClass) {
        Intent intent = new Intent(ClassView.this, SV_InfoClassActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("Key_ClassBtnDetails", idClass);
        intent.putExtras(bundle);
        startActivityForResult(intent, REQUEST_CODE_CLASS_VIEW);
    }

}