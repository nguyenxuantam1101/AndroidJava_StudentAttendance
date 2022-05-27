package com.example.appdiemdanh;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appdiemdanh.data.Database;

public class SV_InfoClassActivity extends AppCompatActivity {
    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sv_info_class);

        database = new Database(this);
        TextView txtTeacherName = findViewById(R.id.txt_tenGiangVien);
        TextView txtClassName = findViewById(R.id.txt_nameClass);
        TextView txtSubject = findViewById(R.id.txt_tenMonHoc);
        TextView txtRoom = findViewById(R.id.txt_Phong);
        TextView txtStartDay = findViewById(R.id.txt_ngayBatDau);
        TextView txtEndDay = findViewById(R.id.txt_ngayKetThuc);
        int id = GetId();
        Cursor cursor = database.GetData("SELECT DISTINCT * FROM ((AccountTable " +
                "INNER JOIN TeacherTable ON AccountTable.TeacherId = TeacherTable.TeacherId) " +
                "INNER JOIN ClassTable ON AccountTable.AccountId = ClassTable.AccountId) " +
                "WHERE ClassId = '"+id+"'");
        cursor.moveToFirst();
        txtTeacherName.setText(cursor.getString(8));
        txtClassName.setText(cursor.getString(15));
        txtSubject.setText(cursor.getString(16));
        txtRoom.setText(cursor.getString(17));
        txtStartDay.setText(cursor.getString(18));
        txtEndDay.setText(cursor.getString(19));

        ImageView imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = GetId();
                Intent returnIntent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putInt("Key_ClassBtnDetails", id);
                returnIntent.putExtras(bundle);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });
    }

    private int GetId() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int idClass = bundle.getInt("Key_ClassBtnDetails", 0);
        return idClass;
    }
}