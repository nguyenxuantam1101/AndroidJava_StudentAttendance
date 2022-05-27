package com.example.appdiemdanh;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appdiemdanh.data.AccountTable;
import com.example.appdiemdanh.data.Database;
import com.example.appdiemdanh.data.MainClass;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AccountActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_ACCOUNT = 10;
    BottomNavigationView bottomNavigationView;
    TextView txtFullNameAccount, txtOffice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        //Lựa chọn Tài Khoản
        bottomNavigationView.setSelectedItemId(R.id.account);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        SwapHome();
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.account:
                        return true;
                }
                return false;
            }
        });
        bottomNavigationView.setBackground(null);
        //lấy id account hiện hữu
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String name = bundle.getString("Key_Swap1", "");
        String role = bundle.getString("Key_Swap2", "");

        txtFullNameAccount = (TextView) findViewById(R.id.txtFullNameAccount);
        txtOffice = findViewById(R.id.txtOffice);
        txtFullNameAccount.setText(name);
        txtOffice.setText(role);

        //Xem thông tin cá nhân
        Button btnProfile = findViewById(R.id.button_ThongTinCaNhan);
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = IdAccountLogin();
                Intent intent = getIntent();
                Bundle bundle = intent.getExtras();
                String NameAccount = bundle.getString("Key_Swap1", "");
                String role = bundle.getString("Key_Swap2", "");
                String CodeAccount = bundle.getString("Key_Swap3", "");
                String Birthday = bundle.getString("Key_Swap4", "");
                String Class = bundle.getString("Key_Swap5", "");
                String Gmail = bundle.getString("Key_Swap6", "");
                String Phone = bundle.getString("Key_Swap7", "");
                ButtonProfile(id, NameAccount, CodeAccount, Birthday, Class, Gmail, Phone, role);
            }
        });
        //Phản hồi về app
        Button btnPhanHoi = (Button) findViewById(R.id.button_PhanHoi);
        btnPhanHoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmail();
            }
        });


        //Đăng xuất
        Button btnLogout = (Button) findViewById(R.id.button_DangXuat);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccountActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    protected void sendEmail() {
        Log.i("Send email", "");
        String[] TO = {"nguyenxuantam2611@gmail.com"};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Phản Hồi Về Ứng Dụng Điểm Danh HUTECH");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Nhập Phản Hồi Của Bạn Tại Đây");

        try {
            startActivity(Intent.createChooser(emailIntent, "Phản Hồi Về Ứng Dụng"));
            finish();
            Log.i("Hoàn Tất Phản Hồi", "");
            // Toast.makeText(MainClass.class, "Phản Hồi Thành Công.", Toast.LENGTH_SHORT).show();
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(AccountActivity.this, "Bạn Chưa Cài Đặt Ứng Dụng.", Toast.LENGTH_SHORT).show();
        }
    }


    private int IdAccountLogin() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            int id = bundle.getInt("Key_Swap", 0);
            return id;
        }
        return -1;
    }

    private void SwapHome() {
        int idAccount = IdAccountLogin();
        Intent returnIntent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putInt("Key_Swap", idAccount);
        returnIntent.putExtras(bundle);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    private void ButtonProfile(int id, String nameAccount, String codeAccount, String birthday, String Class, String gmail, String phone, String role) {
        Intent intent = new Intent(AccountActivity.this, ProfileActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("Key_Profile", id);
        bundle.putString("Key_Profile1", nameAccount);
        bundle.putString("Key_Profile2", codeAccount);
        bundle.putString("Key_Profile3", birthday);
        bundle.putString("Key_Profile4", Class);
        bundle.putString("Key_Profile5", gmail);
        bundle.putString("Key_Profile6", phone);
        bundle.putString("Key_Profile7", role);
        intent.putExtras(bundle);
        startActivityForResult(intent, REQUEST_CODE_ACCOUNT);
    }

}