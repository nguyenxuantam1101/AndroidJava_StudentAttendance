package com.example.appdiemdanh;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.appdiemdanh.data.Database;
import com.example.appdiemdanh.data.MainClass;

public class SignInFragment extends Fragment {
    EditText username, password;
    Database database;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_sign_in, container, false);
        final Button btnLogin = (Button) root.findViewById(R.id.login);
        database = new Database(getActivity());
        username = (EditText) root.findViewById(R.id.username1);
        password = (EditText) root.findViewById(R.id.password1);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                if (user.equals("") || pass.equals(""))
                    Toast.makeText(getActivity().getApplicationContext(), "Nhập tài khoản và mật khẩu", Toast.LENGTH_SHORT).show();
                else {
                    Boolean checkuserpass = database.CheckUsernamePassword(user, pass);
                    if (checkuserpass == true) {
                        Toast.makeText(getActivity().getApplicationContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        Cursor cursor = database.GetData("Select * from AccountTable");
                        if (cursor.moveToFirst()) {
                            do {
                                if (user.equals(cursor.getString(1))) {
                                    Intent intent = new Intent(getActivity(), MainClass.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putInt("Key_User", cursor.getInt(0));
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                    getActivity().finish();
                                }
                            } while (cursor.moveToNext());
                        }
                    } else {
                        Toast.makeText(getActivity().getApplicationContext(), "Tên đăng nhập hoặc mật khẩu sai", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        return root;

    }
}
