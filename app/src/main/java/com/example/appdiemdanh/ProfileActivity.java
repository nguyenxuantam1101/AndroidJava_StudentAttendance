package com.example.appdiemdanh;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appdiemdanh.data.Database;

public class ProfileActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_PROFILE = 10;
    Button btnBack, btnEditProfile;
    ImageView img;
    Database database;
    private static final int pic_id = 123;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        database = new Database(this);

        //Quay về trang Account
        btnBack = (Button) findViewById(R.id.button_Quaylai);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = GetId();
                Intent returnIntent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putInt("Key_Profile", id);
                returnIntent.putExtras(bundle);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });

        //Sửa thông tin
        btnEditProfile = findViewById(R.id.btnEditProfile);
        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = GetId();
                Intent intent = getIntent();
                Bundle bundle = intent.getExtras();
                String gmail = bundle.getString("Key_Profile5", "");
                String phone = bundle.getString("Key_Profile6", "");
                String role = bundle.getString("Key_Profile7", "");
                ButtonEdit(id, gmail, phone, role);
            }
        });
        //Cập nhật ảnh
        img = (ImageView) findViewById(R.id.imageView11);
        Button btnImg = (Button) findViewById(R.id.buttonCapNhatAnh);
        btnImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, pic_id);
            }
        });
        GetDataProfile();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == pic_id) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            img.setImageBitmap(photo);
        }
        if (REQUEST_CODE_PROFILE == requestCode && Activity.RESULT_OK == resultCode) {
            int id = data.getIntExtra("Key_Edit", 0);
            String newPhone = data.getStringExtra("Key_EditPhone");
            TextView txtNewPhone = findViewById(R.id.textView13);
            txtNewPhone.setText(newPhone);
        }
    }


    private int GetId() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int id = bundle.getInt("Key_Profile", 0);
        return id;
    }

    private void ButtonEdit(int id, String gmail, String phone, String role) {
        Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("Key_Edit", id);
        bundle.putString("Key_Edit1", gmail);
        bundle.putString("Key_Edit2", phone);
        bundle.putString("Key_Edit3", role);
        intent.putExtras(bundle);
        startActivityForResult(intent, REQUEST_CODE_PROFILE);
    }

    public void GetDataProfile() {
        TextView txtName = findViewById(R.id.textView6);
        TextView txtCode = findViewById(R.id.editTextTextMSSV);
        TextView txtBirthday = findViewById(R.id.textView10);
        TextView txtClass = findViewById(R.id.textView11);
        TextView txtGmail = findViewById(R.id.textView12);
        TextView txtPhone = findViewById(R.id.textView13);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        txtName.setText(bundle.getString("Key_Profile1", ""));
        txtCode.setText(bundle.getString("Key_Profile2", ""));
        txtBirthday.setText(bundle.getString("Key_Profile3", ""));
        txtClass.setText(bundle.getString("Key_Profile4", ""));
        txtGmail.setText(bundle.getString("Key_Profile5", ""));
        txtPhone.setText(bundle.getString("Key_Profile6", ""));
    }
}