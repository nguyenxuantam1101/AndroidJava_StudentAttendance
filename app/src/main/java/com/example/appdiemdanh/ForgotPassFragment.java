package com.example.appdiemdanh;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.appdiemdanh.data.Database;

public class ForgotPassFragment extends Fragment {
    EditText txtEmail;
    Database database;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_forgot_pass, container, false);
        final Button btnForgotPass = (Button) root.findViewById(R.id.btnForgotPass);
        txtEmail = (EditText) root.findViewById(R.id.txtMail);
        btnForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = txtEmail.getText().toString();
          //      int IdStudent = database.GetIdEmail(email);
                if (email.equals(""))
                    Toast.makeText(getActivity().getApplicationContext(), "Vui Lòng Không Để Trống email", Toast.LENGTH_SHORT).show();
                else {
                    //Sv
//                    Toast.makeText(getActivity().getApplicationContext(), IdStudent, Toast.LENGTH_SHORT).show();
////                    int IdAcc = database.GetIdStudent(IdStudent);
////                    String User = database.GetUser(IdAcc);
////                    //gv
////                    int IdTeacher = database.GetIdEmailGV(email);
////                    int IdAccGV = database.GetIdTeacher(IdTeacher);
////                    String Teacher = database.GetUser(IdAccGV);
////
////                    if (IdStudent == -1) {
////                        if(IdTeacher == -1){
////                            Toast.makeText(getActivity().getApplicationContext(), "Email Bạn Nhập Không Tồn Tại", Toast.LENGTH_SHORT).show();
////                        }
////                        else {
////                            database.QueryData("UPDATE AccountTable SET Password = '" + Teacher + "' WHERE AccountId = '" + IdAccGV + "'");
////                            Toast.makeText(getActivity().getApplicationContext(), "Cập Nhập Mật Khẩu Thành Công", Toast.LENGTH_SHORT).show();
////                        }
////                    } else {
////                        database.QueryData("UPDATE AccountTable SET Password = '" + User + "' WHERE AccountId = '" + IdAcc + "'");
////
                        Toast.makeText(getActivity().getApplicationContext(), "Chức Năng Đang Được Phát Triển", Toast.LENGTH_SHORT).show();
//                    }
                }
            }
        });
        return root;
    }
}