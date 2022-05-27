package com.example.appdiemdanh;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appdiemdanh.data.Database;
import com.example.appdiemdanh.data.MainClass;
import com.example.appdiemdanh.data.MainDetails;

public class GV_InfoClassActivity extends AppCompatActivity {

    Database database;
    private static final int REQUEST_CODE_DETAILS_CLASS_GV = 10;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gv_info_class);

        ImageView imgBack = findViewById(R.id.imgBack1);
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
        database = new Database(this);
        GetDataInfoClass();

        Button btnEveryone = (Button) findViewById(R.id.btnEveryone);
        btnEveryone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = GetId();
                ButtonEveryone(id);
            }
        });
    }

    private void GetDataInfoClass(){
        TextView txtSubject = findViewById(R.id.txt_tenMonHoc);
        TextView txtRoom = findViewById(R.id.txt_Phong);
        TextView txtStartDay = findViewById(R.id.txt_ngayBatDau);
        TextView txtEndDay = findViewById(R.id.txt_ngayKetThuc);
        TextView txtTeacherName = findViewById(R.id.txt_tenGiangVien);
        TextView txtCountStudent = findViewById(R.id.txt_TongSoSV);

        int idClass = GetId();
        Cursor cursor = database.GetData("SELECT Subject, ClassRoom, " +
                "StartDay, EndDay FROM ClassTable Where ClassId = '" + idClass + "'");
        cursor.moveToFirst();
        txtSubject.setText(cursor.getString(0));
        txtRoom.setText(cursor.getString(1));
        txtStartDay.setText(cursor.getString(2));
        txtEndDay.setText(cursor.getString(3));

        Cursor cursor1 = database.GetData("SELECT TeacherName FROM ((AccountTable " +
                "INNER JOIN TeacherTable ON AccountTable.TeacherId = TeacherTable.TeacherId) " +
                "INNER JOIN ClassTable ON AccountTable.AccountId = ClassTable.AccountId) WHERE ClassId = '" + idClass + "'");
        cursor1.moveToFirst();
        txtTeacherName.setText(cursor1.getString(0));

        Cursor cursor2 = database.GetData("SELECT count(AccountId) FROM ClassDetailsTable Where ClassId = '" + idClass + "' And Status = 1");
        cursor2.moveToFirst();
        txtCountStudent.setText("" + cursor2.getInt(0));
    }

    private int GetId() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int idClass = bundle.getInt("Key_ClassBtnDetails", 0);
        return idClass;
    }

    private void ButtonEveryone(int idClass) {
        Intent intent = new Intent(GV_InfoClassActivity.this, MainDetails.class);
        Bundle bundle = new Bundle();
        bundle.putInt("Key_Everyone", idClass);
        intent.putExtras(bundle);
        startActivityForResult(intent, REQUEST_CODE_DETAILS_CLASS_GV);
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (REQUEST_CODE_DETAILS_CLASS_GV == requestCode && Activity.RESULT_OK == resultCode) {
            int id = data.getIntExtra("Key_Everyone", 0);
            GetDataInfoClass();
        }
    }
}