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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appdiemdanh.data.Database;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

public class GV_ClassView extends AppCompatActivity {
    Button btnCreateAttendance, btnChiTiet;
    Database database;
    private static final int REQUEST_CODE_CLASS_VIEW_GV = 10;

    //hàm tạo random mã điểm danh
    private static final String random_characters = "0123456789";

    private String CodeAttendance() {
        String codeAttendance;
        final Random random = new Random();
        final StringBuilder sb = new StringBuilder(6);
        for (int i = 0; i < 6; i++)
            sb.append(random_characters.charAt(random.nextInt(random_characters.length())));
        codeAttendance = String.valueOf(sb);
        return codeAttendance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gv_class_view);

        database = new Database(this);
        btnCreateAttendance = findViewById(R.id.btnCreateAttendance);
        btnCreateAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int idClass = BundleIdClass();
                Cursor cursor = database.GetData("SELECT * FROM ClassTable WHERE ClassId = '" + idClass + "'");
                cursor.moveToFirst();
                String NewCodeAttendance = CodeAttendance().trim();
                int newNumOfAttendance = cursor.getInt(11) + 1;
                DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy");
                String DateTime = df.format(Calendar.getInstance().getTime());
                database.QueryData("INSERT INTO AttendanceTable VALUES(null, '" + idClass + "','" + NewCodeAttendance + "', '" + DateTime + "')");
                database.QueryData("UPDATE ClassTable SET CodeAttendance = '" + NewCodeAttendance + "', NumOfAttendance = '" + newNumOfAttendance + "' " +
                        "WHERE ClassId = '" + idClass + "'");
                Toast.makeText(GV_ClassView.this, "Tạo điểm danh thành công", Toast.LENGTH_SHORT).show();
                GetDataClassView();
                CreateAttendance(Gravity.CENTER);
            }

        });
        btnChiTiet = findViewById(R.id.btnChiTiet);
        btnChiTiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int idClass = BundleIdClass();
                ButtonDetails(idClass);
            }
        });
        ImageView imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int idClass = BundleIdClass();
                Intent returnIntent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putInt("Key_Class", idClass);
                returnIntent.putExtras(bundle);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });
        GetDataClassView();
    }

    private void GetDataClassView(){
        int idClass = BundleIdClass();
        TextView txtCodeClass = findViewById(R.id.txtCodeClass);
        TextView txtNumOfAttendance = findViewById(R.id.txt_soBuoiDiemDanh);
        Cursor cursor = database.GetData("SELECT CodeJoin, NumOfAttendance FROM ClassTable Where ClassId = '" + idClass + "'");
        cursor.moveToFirst();
        txtCodeClass.setText(cursor.getString(0));
        txtNumOfAttendance.setText(String.valueOf(cursor.getInt(1)));
    }

    private int BundleIdClass() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int idClass = bundle.getInt("Key_Class", 0);
        return idClass;
    }

    private void ButtonDetails(int idClass) {
        Intent intent = new Intent(GV_ClassView.this, GV_InfoClassActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("Key_ClassBtnDetails", idClass);
        intent.putExtras(bundle);
        startActivityForResult(intent, REQUEST_CODE_CLASS_VIEW_GV);
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (REQUEST_CODE_CLASS_VIEW_GV == requestCode && Activity.RESULT_OK == resultCode) {
            int id = data.getIntExtra("Key_ClassBtnDetails", 0);
        }
    }

    private void CreateAttendance(int gravity) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_attendance_code);

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
        //xử lý các tác vụ:
        TextView txtCodeAttendance = dialog.findViewById(R.id.txtCodeAttendance);
        int idClass = BundleIdClass();
        Cursor cursor = database.GetData("SELECT * FROM ClassTable WHERE ClassId = '" + idClass + "'");
        cursor.moveToFirst();
        txtCodeAttendance.setText(cursor.getString(8));

        //click "X"
        ImageView imgClose = dialog.findViewById(R.id.imgClose);
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        //đổi mã thủ công
        Button btnSwapCode = dialog.findViewById(R.id.btnSwapCode);
        btnSwapCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int idClass = BundleIdClass();
                String NewCodeAttendance = CodeAttendance().trim();
                database.QueryData("UPDATE ClassTable SET CodeAttendance = '" + NewCodeAttendance + "' WHERE ClassId = '" + idClass + "'");
                database.QueryData("UPDATE AttendanceTable SET CodeAttendance = '" + NewCodeAttendance + "' WHERE ClassId = '" + idClass + "'");
                Toast.makeText(GV_ClassView.this, "Đổi mã điểm danh thành công", Toast.LENGTH_SHORT).show();
                Cursor cursor = database.GetData("SELECT * FROM ClassTable WHERE ClassId = '" + idClass + "'");
                cursor.moveToFirst();
                txtCodeAttendance.setText(cursor.getString(8));
            }
        });
        //kết thúc điểm danh
        Button btnCancelAttendance = dialog.findViewById(R.id.btnCancelAttendance);
        btnCancelAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                int idClass = BundleIdClass();
                String NewCodeAttendance = "Đố Bạn Mò Được Code";
                database.QueryData("UPDATE ClassTable SET CodeAttendance = '" + NewCodeAttendance + "' WHERE ClassId = '" + idClass + "'");
                Toast.makeText(GV_ClassView.this, "Kết Thúc Điểm Danh", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
    }
}