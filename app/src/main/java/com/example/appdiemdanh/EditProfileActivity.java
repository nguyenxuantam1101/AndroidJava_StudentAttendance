package com.example.appdiemdanh;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.appdiemdanh.data.Database;
import com.example.appdiemdanh.data.MainClass;

public class EditProfileActivity extends AppCompatActivity {
    EditText editGmail, editPhoneNumber;
    Button btnEdit;
    ImageView imgBack;
    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                Bundle bundle = intent.getExtras();
                int id = bundle.getInt("Key_Edit", 0);
                Intent returnIntent = new Intent();
                Bundle bundle1 = new Bundle();
                bundle1.putInt("Key_Edit", id);
                returnIntent.putExtras(bundle1);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });
        database = new Database(this);
        editGmail = findViewById(R.id.editGmail);
        editPhoneNumber = findViewById(R.id.editPhoneNumber);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        editGmail.setText(bundle.getString("Key_Edit1", ""));
        editPhoneNumber.setText(bundle.getString("Key_Edit2", ""));
        btnEdit = findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                Bundle bundle = intent.getExtras();
                String role = bundle.getString("Key_Edit3", "");
                int id = bundle.getInt("Key_Edit", 0);
                String teacher = "Giảng Viên";
                String student = "Sinh Viên";
                String newGmail = editGmail.getText().toString().trim();
                String newPhoneNum = editPhoneNumber.getText().toString().trim();

                if (role.equals(teacher)) {
                    database.QueryData("UPDATE TeacherTable SET TeacherPhone = '" + newPhoneNum + "', TeacherGmail = '" + newGmail + "' " +
                            "WHERE TeacherId = (SELECT TeacherId FROM AccountTable WHERE AccountId = '" + id + "')");
                    Toast.makeText(EditProfileActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    Intent returnIntent = new Intent();
                    Bundle bundle2 = new Bundle();
                    bundle2.putInt("Key_Edit", id);
                    bundle2.putString("Key_EditPhone", newPhoneNum);
                    bundle2.putString("Key_EditGmail", newGmail);
                    returnIntent.putExtras(bundle2);
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                } else if (role.equals(student)) {
                    database.QueryData("UPDATE StudentTable SET StudentPhone = '" + newPhoneNum + "', StudentGmail = '" + newGmail + "' " +
                            "WHERE StudentId = (SELECT StudentId FROM AccountTable WHERE AccountId = '" + id + "')");
                    Toast.makeText(EditProfileActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    Intent returnIntent = new Intent();
                    Bundle bundle2 = new Bundle();
                    bundle2.putInt("Key_Edit", id);
                    bundle2.putString("Key_EditPhone", newPhoneNum);
                    bundle2.putString("Key_EditGmail", newGmail);
                    returnIntent.putExtras(bundle2);
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                } else {
                    Toast.makeText(EditProfileActivity.this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}