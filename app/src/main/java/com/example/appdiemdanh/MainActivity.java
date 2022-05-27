package com.example.appdiemdanh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.appdiemdanh.data.Database;
public class MainActivity extends AppCompatActivity {
    Database database;

    int SPLASH_TIME_OUT = 1000;//set thời gian chờ 1 giây

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIME_OUT);

        database = new Database(this);
//        //data "TeacherTable"
//        database.QueryData("INSERT INTO TeacherTable VALUES(null, 'GV01', 'Bùi Phú Khuyên 1', '1/1/2001', '0123456789', 'giangvien@gmail.com', 'Lập trình C', '1')");
//        database.QueryData("INSERT INTO TeacherTable VALUES(null, 'GV02', 'Bùi Phú Khuyên 2', '6/5/2001', '0123456789', 'giangvien1@gmail.com', 'Cấu trúc dữ liệu', '1')");
//        database.QueryData("INSERT INTO TeacherTable VALUES(null, 'GV03', 'Bùi Phú Khuyên 3', '1/4/2001', '0123456789', 'giangvien2@gmail.com', 'Lập trình Windows', '1')");
//        database.QueryData("INSERT INTO TeacherTable VALUES(null, 'Admin', 'Admin Tesst', '1/4/2001', '0123456789', 'giangvien4@gmail.com', 'Lập trình Windows', '1')");
//        database.QueryData("INSERT INTO TeacherTable VALUES(null, 'Admin1', 'Admin Test 1', '1/4/2001', '0123456789', 'giangvien5@gmail.com', 'Lập trình Windows', '1')");
//        database.QueryData("INSERT INTO TeacherTable VALUES(null, 'Admin2', 'Admin Test 2', '1/4/2001', '0123456789', 'giangvien6@gmail.com', 'Lập trình Windows', '1')");
//        //data "studenttable"
//        database.QueryData("INSERT INTO StudentTable VALUES(null, 'SV01', 'Sinh Viên 1', '1/1/2001', '0123456789', 'sinhvien1@gmail.com', '19DTHD2', '1')");
//        database.QueryData("INSERT INTO StudentTable VALUES(null, 'SV02', 'Sinh Viên 2', '6/5/2001', '0123456789', 'sinhvien2@gmail.com', '19DTHD2', '1')");
//        database.QueryData("INSERT INTO StudentTable VALUES(null, 'SV03', 'Sinh Viên 3', '1/4/2001', '0123456789', 'sinhvien3@gmail.com', '19DTHD2', '1')");
//        database.QueryData("INSERT INTO StudentTable VALUES(null, 'SV04', 'Sinh Viên 4', '1/1/2001', '0123456789', 'sinhvien4@gmail.com', '19DTHD2', '1')");
//        database.QueryData("INSERT INTO StudentTable VALUES(null, 'SV05', 'Sinh Viên 5', '6/5/2001', '0123456789', 'sinhvien5@gmail.com', '19DTHD2', '1')");
//        database.QueryData("INSERT INTO StudentTable VALUES(null, 'SV06', 'Sinh Viên 6', '1/4/2001', '0123456789', 'sinhvien6@gmail.com', '19DTHD2', '1')");
//        database.QueryData("INSERT INTO StudentTable VALUES(null, 'SV07', 'Sinh Viên 7', '1/1/2001', '0123456789', 'sinhvien7@gmail.com', '19DTHD2', '1')");
//        database.QueryData("INSERT INTO StudentTable VALUES(null, 'SV08', 'Sinh Viên 8', '6/5/2001', '0123456789', 'sinhvien8@gmail.com', '19DTHD2', '1')");
//        database.QueryData("INSERT INTO StudentTable VALUES(null, 'SV09', 'Sinh Viên 9', '1/4/2001', '0123456789', 'sinhvien9@gmail.com', '19DTHD2', '1')");
//        database.QueryData("INSERT INTO StudentTable VALUES(null, 'SV010', 'Sinh Viên 10', '1/4/2001', '0123456789', 'sinhvien10@gmail.com', '19DTHD2', '1')");
//        database.QueryData("INSERT INTO StudentTable VALUES(null, 'SV011', 'Sinh Viên 0', '1/4/2001', '0123456789', 'sinhvien11@gmail.com', '19DTHD2', '1')");
//        database.QueryData("INSERT INTO StudentTable VALUES(null, 'user', 'User Test', '1/4/2001', '0123456789', 'sinhvien12@gmail.com', '19DTHD2', '1')");
//        database.QueryData("INSERT INTO StudentTable VALUES(null, 'user1', 'User Test 1', '1/4/2001', '0123456789', 'sinhvien13@gmail.com', '19DTHD2', '1')");
//        database.QueryData("INSERT INTO StudentTable VALUES(null, 'user2', 'User Test 2', '1/4/2001', '0123456789', 'sinhvien14@gmail.com', '19DTHD2', '1')");
////        //data "AccountTable"
//        database.QueryData("INSERT INTO AccountTable VALUES(null,'gv','gv','1',null,'1')");
//        database.QueryData("INSERT INTO AccountTable VALUES(null,'gv1','gv','1',null,'2')");
//        database.QueryData("INSERT INTO AccountTable VALUES(null,'gv2','gv','1',null,'3')");
//        database.QueryData("INSERT INTO AccountTable VALUES(null,'sv1','sv','0','1',null)");
//        database.QueryData("INSERT INTO AccountTable VALUES(null,'sv2','sv','0','2',null)");
//        database.QueryData("INSERT INTO AccountTable VALUES(null,'sv3','sv','0','3',null)");
//        database.QueryData("INSERT INTO AccountTable VALUES(null,'sv4','sv','0','4',null)");
//        database.QueryData("INSERT INTO AccountTable VALUES(null,'sv5','sv','0','5',null)");
//        database.QueryData("INSERT INTO AccountTable VALUES(null,'sv6','sv','0','6',null)");
//        database.QueryData("INSERT INTO AccountTable VALUES(null,'sv7','sv','0','7',null)");
//        database.QueryData("INSERT INTO AccountTable VALUES(null,'sv8','sv','0','8',null)");
//        database.QueryData("INSERT INTO AccountTable VALUES(null,'sv9','sv','0','9',null)");
//        database.QueryData("INSERT INTO AccountTable VALUES(null,'sv0','sv','0','10',null)");
//        database.QueryData("INSERT INTO AccountTable VALUES(null,'sv','sv','0','11',null)");
//        database.QueryData("INSERT INTO AccountTable VALUES(null,'user','user','0','12',null)");
//        database.QueryData("INSERT INTO AccountTable VALUES(null,'user1','user','0','13',null)");
//        database.QueryData("INSERT INTO AccountTable VALUES(null,'user2','user','0','14',null)");
//        database.QueryData("INSERT INTO AccountTable VALUES(null,'admin','admin','1',null,'4')");
//        database.QueryData("INSERT INTO AccountTable VALUES(null,'admin1','admin','1',null,'5')");
//        database.QueryData("INSERT INTO AccountTable VALUES(null,'admin2','admin','1',null,'6')");
//
////        //data "ClassTable"
//        database.QueryData("INSERT INTO ClassTable VALUES(null,'LTW Chiều Thứ 6','Lập Trình Web','E1.05.11','20/5/2022','20/8/2022','25','ABC111',null,'1','1','5',null)");
//        database.QueryData("INSERT INTO ClassTable VALUES(null,'Đồ Án Java 19DTHD2','Đồ Án Java','E1.05.11','20/5/2022','20/8/2022','20','ABC112',null,'1','1','5',null)");
//        database.QueryData("INSERT INTO ClassTable VALUES(null,'Đồ Án Cở Sở 19DTHD2','Đồ Án Cơ Sở','E1.05.11','20/5/2022','20/8/2022','35','ABC113',null,'1','1','5',null)");
//        database.QueryData("INSERT INTO ClassTable VALUES(null,'Bóng Chuyền 3 19DTHD2','Bóng Chuyền','E1.05.11','20/5/2022','20/8/2022','30','ABC114',null,'1','1','5',null)");
//        database.QueryData("INSERT INTO ClassTable VALUES(null,'Tiếng Anh 6','Tiếng Anh 6','E1.05.11','20/5/2022','20/8/2022','25','ABC115',null,'1','1','5',null)");
//        database.QueryData("INSERT INTO ClassTable VALUES(null,'Kinh Tế Chính Trị Chiều T6','KTCT','E1.05.11','20/5/2022','20/8/2022','20','ABC116',null,'1','1','5',null)");
//        //data của acc gv 2
//        database.QueryData("INSERT INTO ClassTable VALUES(null,'LTW Chiều Thứ 6','Lập Trình Web','E1.05.11','20/5/2022','20/8/2022','20','ABC117',null,'1','2','5',null)");
//        database.QueryData("INSERT INTO ClassTable VALUES(null,'Đồ Án Java 19DTHD2','Đồ Án Java','E1.05.11','20/5/2022','20/8/2022','30','ABC118',null,'1','2','5',null)");
//        database.QueryData("INSERT INTO ClassTable VALUES(null,'Đồ Án Cở Sở 19DTHD2','Đồ Án Cơ Sở','E1.05.11','20/5/2022','20/8/2022','25','ABC118',null,'1','2','5',null)");
//        database.QueryData("INSERT INTO ClassTable VALUES(null,'Bóng Chuyền 3 19DTHD2','Bóng Chuyền','E1.05.11','20/5/2022','20/8/2022','25','ABC120',null,'1','2','5',null)");
//        database.QueryData("INSERT INTO ClassTable VALUES(null,'Tiếng Anh 6','Tiếng Anh 6','E1.05.11','20/5/2022','20/8/2022','30','ABC121',null,'1','2','5',null)");
//        database.QueryData("INSERT INTO ClassTable VALUES(null,'Kinh Tế Chính Trị Chiều T6','KTCT','E1.05.11','20/5/2022','20/8/2022','25','ABC122',null,'1','2','5',null)");
//        //data của acc gv3
//        database.QueryData("INSERT INTO ClassTable VALUES(null,'LTW Chiều Thứ 6','Lập Trình Web','E1.05.11','20/5/2022','20/8/2022','20','ABC123',null,'1','3','5',null)");
//        database.QueryData("INSERT INTO ClassTable VALUES(null,'Đồ Án Java 19DTHD2','Đồ Án Java','E1.05.11','20/5/2022','20/8/2022','35','ABC124',null,'1','3','5',null)");
//        database.QueryData("INSERT INTO ClassTable VALUES(null,'Đồ Án Cở Sở 19DTHD2','Đồ Án Cơ Sở','E1.05.11','20/5/2022','20/8/2022','25','ABC125',null,'1','3','5',null)");
//        database.QueryData("INSERT INTO ClassTable VALUES(null,'Bóng Chuyền 3 19DTHD2','Bóng Chuyền','E1.05.11','20/5/2022','20/8/2022','30','ABC126',null,'1','3','5',null)");
//        database.QueryData("INSERT INTO ClassTable VALUES(null,'Tiếng Anh 6','Tiếng Anh 6','E1.05.11','20/5/2022','20/8/2022','25','ABC127',null,'1','3','5',null)");
//        database.QueryData("INSERT INTO ClassTable VALUES(null,'Kinh Tế Chính Trị Chiều T6','KTCT','E1.05.11','20/5/2022','20/8/2022','10','ABC128',null,'1','3','5',null)");
//
//        //Data " ClassDetailsTable"
//        //data lớp thứ 1
//        database.QueryData("INSERT INTO ClassDetailsTable VALUES(null,'4','1','10.0','5','1')");
//        database.QueryData("INSERT INTO ClassDetailsTable VALUES(null,'5','1','9.0','4','1')");
//        database.QueryData("INSERT INTO ClassDetailsTable VALUES(null,'6','1','8.0','5','1')");
//        database.QueryData("INSERT INTO ClassDetailsTable VALUES(null,'7','1','9.0','4','1')");
//        database.QueryData("INSERT INTO ClassDetailsTable VALUES(null,'8','1','4.0','3','1')");
//        database.QueryData("INSERT INTO ClassDetailsTable VALUES(null,'9','1','6.0','2','1')");
//        database.QueryData("INSERT INTO ClassDetailsTable VALUES(null,'10','1','3.0','5','1')");
//        database.QueryData("INSERT INTO ClassDetailsTable VALUES(null,'11','1','10.0','5','1')");
//        database.QueryData("INSERT INTO ClassDetailsTable VALUES(null,'12','1','2.0','3','1')");
//        database.QueryData("INSERT INTO ClassDetailsTable VALUES(null,'13','1','1.0','5','1')");
//        database.QueryData("INSERT INTO ClassDetailsTable VALUES(null,'14','1','6.0','5','1')");
//        //data lớp thứ 2
//        database.QueryData("INSERT INTO ClassDetailsTable VALUES(null,'4','2','4.0','5','1')");
//        database.QueryData("INSERT INTO ClassDetailsTable VALUES(null,'5','2','5.0','5','1')");
//        database.QueryData("INSERT INTO ClassDetailsTable VALUES(null,'6','2','6.0','5','1')");
//        database.QueryData("INSERT INTO ClassDetailsTable VALUES(null,'7','2','7.0','5','1')");
//        database.QueryData("INSERT INTO ClassDetailsTable VALUES(null,'8','2','8.0','5','1')");
//        database.QueryData("INSERT INTO ClassDetailsTable VALUES(null,'9','2','9.0','5','1')");
//        database.QueryData("INSERT INTO ClassDetailsTable VALUES(null,'10','2','2.0','5','1')");
//        database.QueryData("INSERT INTO ClassDetailsTable VALUES(null,'11','2','4.0','5','1')");
//        database.QueryData("INSERT INTO ClassDetailsTable VALUES(null,'12','2','5.0','5','1')");
//        database.QueryData("INSERT INTO ClassDetailsTable VALUES(null,'13','2','6.0','5','1')");
//        database.QueryData("INSERT INTO ClassDetailsTable VALUES(null,'14','2','7.0','5','1')");
//        //data lớp thứ 3
//        database.QueryData("INSERT INTO ClassDetailsTable VALUES(null,'4','3','4.0','5','1')");
//        database.QueryData("INSERT INTO ClassDetailsTable VALUES(null,'5','3','5.0','5','1')");
//        database.QueryData("INSERT INTO ClassDetailsTable VALUES(null,'6','3','6.0','5','1')");
//        database.QueryData("INSERT INTO ClassDetailsTable VALUES(null,'7','3','7.0',0,'1')");
//        database.QueryData("INSERT INTO ClassDetailsTable VALUES(null,'8','3','8.0','5','1')");
//        database.QueryData("INSERT INTO ClassDetailsTable VALUES(null,'9','3','6.0',0,'1')");
//        database.QueryData("INSERT INTO ClassDetailsTable VALUES(null,'10','3','7.0','5','1')");
//        database.QueryData("INSERT INTO ClassDetailsTable VALUES(null,'11','3','8.0',0,'1')");
//        database.QueryData("INSERT INTO ClassDetailsTable VALUES(null,'12','3','8.0','5','1')");
//        database.QueryData("INSERT INTO ClassDetailsTable VALUES(null,'13','3','7.0',0,'1')");
//        database.QueryData("INSERT INTO ClassDetailsTable VALUES(null,'14','3','8.0',0,'1')");
//        //data lớp thứ 3
//        database.QueryData("INSERT INTO ClassDetailsTable VALUES(null,'4','4','0.0',0,'1')");
//        database.QueryData("INSERT INTO ClassDetailsTable VALUES(null,'5','4','0.0',0,'1')");
//        database.QueryData("INSERT INTO ClassDetailsTable VALUES(null,'6','4','0.0',0,'1')");
//        database.QueryData("INSERT INTO ClassDetailsTable VALUES(null,'7','4','0.0','5','1')");
//        database.QueryData("INSERT INTO ClassDetailsTable VALUES(null,'8','4','0.0',0,'1')");
//        database.QueryData("INSERT INTO ClassDetailsTable VALUES(null,'9','4','0.0',0,'1')");
//        database.QueryData("INSERT INTO ClassDetailsTable VALUES(null,'10','4','0.0',0,'1')");
//        database.QueryData("INSERT INTO ClassDetailsTable VALUES(null,'11','4','0.0',0,'1')");
//        database.QueryData("INSERT INTO ClassDetailsTable VALUES(null,'12','4','0.0',0,'1')");
//        database.QueryData("INSERT INTO ClassDetailsTable VALUES(null,'13','4','0.0',0,'1')");
//        database.QueryData("INSERT INTO ClassDetailsTable VALUES(null,'14','4','0.0',0,'1')");
//        //data lớp thứ 3
//        database.QueryData("INSERT INTO ClassDetailsTable VALUES(null,'4','5','0.0',0,'1')");
//        database.QueryData("INSERT INTO ClassDetailsTable VALUES(null,'5','5','0.0',0,'1')");
//        database.QueryData("INSERT INTO ClassDetailsTable VALUES(null,'6','5','0.0',0,'1')");
//        database.QueryData("INSERT INTO ClassDetailsTable VALUES(null,'7','5','0.0',0,'1')");
//        database.QueryData("INSERT INTO ClassDetailsTable VALUES(null,'8','5','0.0',0,'1')");
//        database.QueryData("INSERT INTO ClassDetailsTable VALUES(null,'9','5','0.0',0,'1')");
//        database.QueryData("INSERT INTO ClassDetailsTable VALUES(null,'10','5','0.0',0,'1')");
//        database.QueryData("INSERT INTO ClassDetailsTable VALUES(null,'11','5','0.0',0,'1')");
//        database.QueryData("INSERT INTO ClassDetailsTable VALUES(null,'12','5','0.0',0,'1')");
//        database.QueryData("INSERT INTO ClassDetailsTable VALUES(null,'13','5','0.0',0,'1')");
//        database.QueryData("INSERT INTO ClassDetailsTable VALUES(null,'14','5','0.0',0,'1')");
//
//        database.QueryData("INSERT INTO AttendanceTable VALUES(null,'1','123123',null)");
//        database.QueryData("INSERT INTO AttendanceTable VALUES(null,'1','123124',null)");
//        database.QueryData("INSERT INTO AttendanceTable VALUES(null,'1','123125',null)");
//        database.QueryData("INSERT INTO AttendanceTable VALUES(null,'1','123126',null)");
//        database.QueryData("INSERT INTO AttendanceTable VALUES(null,'1','123127',null)");
//        //
//        database.QueryData("INSERT INTO AttendanceTable VALUES(null,'2','123123',null)");
//        database.QueryData("INSERT INTO AttendanceTable VALUES(null,'2','123124',null)");
//        database.QueryData("INSERT INTO AttendanceTable VALUES(null,'2','123125',null)");
//        database.QueryData("INSERT INTO AttendanceTable VALUES(null,'2','123126',null)");
//        database.QueryData("INSERT INTO AttendanceTable VALUES(null,'2','123127',null)");
//        //
//        database.QueryData("INSERT INTO AttendanceTable VALUES(null,'3','123123',null)");
//        database.QueryData("INSERT INTO AttendanceTable VALUES(null,'3','123124',null)");
//        database.QueryData("INSERT INTO AttendanceTable VALUES(null,'3','123125',null)");
//        database.QueryData("INSERT INTO AttendanceTable VALUES(null,'3','123126',null)");
//        database.QueryData("INSERT INTO AttendanceTable VALUES(null,'3','123127',null)");
//        //
//        database.QueryData("INSERT INTO AttendanceTable VALUES(null,'4','123123',null)");
//        database.QueryData("INSERT INTO AttendanceTable VALUES(null,'4','123124',null)");
//        database.QueryData("INSERT INTO AttendanceTable VALUES(null,'4','123125',null)");
//        database.QueryData("INSERT INTO AttendanceTable VALUES(null,'4','123126',null)");
//        database.QueryData("INSERT INTO AttendanceTable VALUES(null,'4','123127',null)");
//        //
//        database.QueryData("INSERT INTO AttendanceTable VALUES(null,'5','123123',null)");
//        database.QueryData("INSERT INTO AttendanceTable VALUES(null,'5','123124',null)");
//        database.QueryData("INSERT INTO AttendanceTable VALUES(null,'5','123125',null)");
//        database.QueryData("INSERT INTO AttendanceTable VALUES(null,'5','123126',null)");
//        database.QueryData("INSERT INTO AttendanceTable VALUES(null,'5','123127',null)");
//        //
//        database.QueryData("INSERT INTO AttendanceTable VALUES(null,'6','123123',null)");
//        database.QueryData("INSERT INTO AttendanceTable VALUES(null,'6','123124',null)");
//        database.QueryData("INSERT INTO AttendanceTable VALUES(null,'6','123125',null)");
//        database.QueryData("INSERT INTO AttendanceTable VALUES(null,'6','123126',null)");
//        database.QueryData("INSERT INTO AttendanceTable VALUES(null,'6','123127',null)");
//        //
//        database.QueryData("INSERT INTO AttendanceTable VALUES(null,'7','123123',null)");
//        database.QueryData("INSERT INTO AttendanceTable VALUES(null,'7','123124',null)");
//        database.QueryData("INSERT INTO AttendanceTable VALUES(null,'7','123125',null)");
//        database.QueryData("INSERT INTO AttendanceTable VALUES(null,'7','123126',null)");
//        database.QueryData("INSERT INTO AttendanceTable VALUES(null,'7','123127',null)");
//        //
//        database.QueryData("INSERT INTO AttendanceTable VALUES(null,'8','123123',null)");
//        database.QueryData("INSERT INTO AttendanceTable VALUES(null,'8','123124',null)");
//        database.QueryData("INSERT INTO AttendanceTable VALUES(null,'8','123125',null)");
//        database.QueryData("INSERT INTO AttendanceTable VALUES(null,'8','123126',null)");
//        database.QueryData("INSERT INTO AttendanceTable VALUES(null,'8','123127',null)");
//        //
//        database.QueryData("INSERT INTO AttendanceTable VALUES(null,'9','123123',null)");
//        database.QueryData("INSERT INTO AttendanceTable VALUES(null,'9','123124',null)");
//        database.QueryData("INSERT INTO AttendanceTable VALUES(null,'9','123125',null)");
//        database.QueryData("INSERT INTO AttendanceTable VALUES(null,'9','123126',null)");
//        database.QueryData("INSERT INTO AttendanceTable VALUES(null,'9','123127',null)");
//        //
//        database.QueryData("INSERT INTO AttendanceTable VALUES(null,'10','123123',null)");
//        database.QueryData("INSERT INTO AttendanceTable VALUES(null,'10','123124',null)");
//        database.QueryData("INSERT INTO AttendanceTable VALUES(null,'10','123125',null)");
//        database.QueryData("INSERT INTO AttendanceTable VALUES(null,'10','123126',null)");
//        database.QueryData("INSERT INTO AttendanceTable VALUES(null,'10','123127',null)");
//        //
//        database.QueryData("INSERT INTO AttendanceTable VALUES(null,'11','123123',null)");
//        database.QueryData("INSERT INTO AttendanceTable VALUES(null,'11','123124',null)");
//        database.QueryData("INSERT INTO AttendanceTable VALUES(null,'11','123125',null)");
//        database.QueryData("INSERT INTO AttendanceTable VALUES(null,'11','123126',null)");
//        database.QueryData("INSERT INTO AttendanceTable VALUES(null,'11','123127',null)");
//        //
//        database.QueryData("INSERT INTO AttendanceTable VALUES(null,'12','123123',null)");
//        database.QueryData("INSERT INTO AttendanceTable VALUES(null,'12','123124',null)");
//        database.QueryData("INSERT INTO AttendanceTable VALUES(null,'12','123125',null)");
//        database.QueryData("INSERT INTO AttendanceTable VALUES(null,'12','123126',null)");
//        database.QueryData("INSERT INTO AttendanceTable VALUES(null,'12','123127',null)");
//        //
//        database.QueryData("INSERT INTO AttendanceTable VALUES(null,'13','123123',null)");
//        database.QueryData("INSERT INTO AttendanceTable VALUES(null,'13','123124',null)");
//        database.QueryData("INSERT INTO AttendanceTable VALUES(null,'13','123125',null)");
//        database.QueryData("INSERT INTO AttendanceTable VALUES(null,'13','123126',null)");
//        database.QueryData("INSERT INTO AttendanceTable VALUES(null,'13','123127',null)");
//        //
//        database.QueryData("INSERT INTO AttendanceTable VALUES(null,'13','123123',null)");
//        database.QueryData("INSERT INTO AttendanceTable VALUES(null,'13','123124',null)");
//        database.QueryData("INSERT INTO AttendanceTable VALUES(null,'13','123125',null)");
//        database.QueryData("INSERT INTO AttendanceTable VALUES(null,'13','123126',null)");
//        database.QueryData("INSERT INTO AttendanceTable VALUES(null,'13','123127',null)");
//        //
//        database.QueryData("INSERT INTO AttendanceTable VALUES(null,'14','123123',null)");
//        database.QueryData("INSERT INTO AttendanceTable VALUES(null,'14','123124',null)");
//        database.QueryData("INSERT INTO AttendanceTable VALUES(null,'14','123125',null)");
//        database.QueryData("INSERT INTO AttendanceTable VALUES(null,'14','123126',null)");
//        database.QueryData("INSERT INTO AttendanceTable VALUES(null,'14','123127',null)");
//        //
//        database.QueryData("INSERT INTO AttendanceTable VALUES(null,'15','123123',null)");
//        database.QueryData("INSERT INTO AttendanceTable VALUES(null,'15','123124',null)");
//        database.QueryData("INSERT INTO AttendanceTable VALUES(null,'15','123125',null)");
//        database.QueryData("INSERT INTO AttendanceTable VALUES(null,'15','123126',null)");
//        database.QueryData("INSERT INTO AttendanceTable VALUES(null,'15','123127',null)");
//        //
//        database.QueryData("INSERT INTO AttendanceTable VALUES(null,'16','123123',null)");
//        database.QueryData("INSERT INTO AttendanceTable VALUES(null,'16','123124',null)");
//        database.QueryData("INSERT INTO AttendanceTable VALUES(null,'16','123125',null)");
//        database.QueryData("INSERT INTO AttendanceTable VALUES(null,'16','123126',null)");
//        database.QueryData("INSERT INTO AttendanceTable VALUES(null,'16','123127',null)");
//        //
//        database.QueryData("INSERT INTO AttendanceTable VALUES(null,'17','123123',null)");
//        database.QueryData("INSERT INTO AttendanceTable VALUES(null,'17','123124',null)");
//        database.QueryData("INSERT INTO AttendanceTable VALUES(null,'17','123125',null)");
//        database.QueryData("INSERT INTO AttendanceTable VALUES(null,'17','123126',null)");
//        database.QueryData("INSERT INTO AttendanceTable VALUES(null,'17','123127',null)");
//        //
//        database.QueryData("INSERT INTO AttendanceTable VALUES(null,'18','123123',null)");
//        database.QueryData("INSERT INTO AttendanceTable VALUES(null,'18','123124',null)");
//        database.QueryData("INSERT INTO AttendanceTable VALUES(null,'18','123125',null)");
//        database.QueryData("INSERT INTO AttendanceTable VALUES(null,'18','123126',null)");
//        database.QueryData("INSERT INTO AttendanceTable VALUES(null,'18','123127',null)");
//        //data AttendanceDeatlsTable
//        database.QueryData("INSERT INTO AttendanceDetailsTable VALUES(null,'4','1',null,null)");
//        database.QueryData("INSERT INTO AttendanceDetailsTable VALUES(null,'4','1',null,null)");
//        database.QueryData("INSERT INTO AttendanceDetailsTable VALUES(null,'4','1',null,null)");
//        database.QueryData("INSERT INTO AttendanceDetailsTable VALUES(null,'4','1',null,null)");
//        database.QueryData("INSERT INTO AttendanceDetailsTable VALUES(null,'4','1',null,null)");
//        database.QueryData("INSERT INTO AttendanceDetailsTable VALUES(null,'4','2',null,null)");
//        database.QueryData("INSERT INTO AttendanceDetailsTable VALUES(null,'4','2',null,null)");
//        database.QueryData("INSERT INTO AttendanceDetailsTable VALUES(null,'4','2',null,null)");
//        database.QueryData("INSERT INTO AttendanceDetailsTable VALUES(null,'4','2',null,null)");
//        database.QueryData("INSERT INTO AttendanceDetailsTable VALUES(null,'4','2',null,null)");
//
//        //
//        database.QueryData("INSERT INTO AttendanceDetailsTable VALUES(null,'5','1',null,null)");
//        database.QueryData("INSERT INTO AttendanceDetailsTable VALUES(null,'5','1',null,null)");
//        database.QueryData("INSERT INTO AttendanceDetailsTable VALUES(null,'5','1',null,null)");
//        database.QueryData("INSERT INTO AttendanceDetailsTable VALUES(null,'5','1',null,null)");
//        database.QueryData("INSERT INTO AttendanceDetailsTable VALUES(null,'5','1',null,null)");
//        //
//        database.QueryData("INSERT INTO AttendanceDetailsTable VALUES(null,'6','1',null,null)");
//        database.QueryData("INSERT INTO AttendanceDetailsTable VALUES(null,'6','1',null,null)");
//        database.QueryData("INSERT INTO AttendanceDetailsTable VALUES(null,'6','1',null,null)");
//        database.QueryData("INSERT INTO AttendanceDetailsTable VALUES(null,'6','1',null,null)");
//        database.QueryData("INSERT INTO AttendanceDetailsTable VALUES(null,'6','1',null,null)");
//        //
//        database.QueryData("INSERT INTO AttendanceDetailsTable VALUES(null,'7','1',null,null)");
//        database.QueryData("INSERT INTO AttendanceDetailsTable VALUES(null,'7','1',null,null)");
//        database.QueryData("INSERT INTO AttendanceDetailsTable VALUES(null,'7','1',null,null)");
//        database.QueryData("INSERT INTO AttendanceDetailsTable VALUES(null,'7','1',null,null)");
//        database.QueryData("INSERT INTO AttendanceDetailsTable VALUES(null,'7','1',null,null)");
//        //
//        database.QueryData("INSERT INTO AttendanceDetailsTable VALUES(null,'8','1',null,null)");
//        database.QueryData("INSERT INTO AttendanceDetailsTable VALUES(null,'8','1',null,null)");
//        database.QueryData("INSERT INTO AttendanceDetailsTable VALUES(null,'8','1',null,null)");
//        database.QueryData("INSERT INTO AttendanceDetailsTable VALUES(null,'8','1',null,null)");
//        database.QueryData("INSERT INTO AttendanceDetailsTable VALUES(null,'8','1',null,null)");
//        //
//        database.QueryData("INSERT INTO AttendanceDetailsTable VALUES(null,'9','1',null,null)");
//        database.QueryData("INSERT INTO AttendanceDetailsTable VALUES(null,'9','1',null,null)");
//        database.QueryData("INSERT INTO AttendanceDetailsTable VALUES(null,'9','1',null,null)");
//        database.QueryData("INSERT INTO AttendanceDetailsTable VALUES(null,'9','1',null,null)");
//        database.QueryData("INSERT INTO AttendanceDetailsTable VALUES(null,'9','1',null,null)");
//        //
//        database.QueryData("INSERT INTO AttendanceDetailsTable VALUES(null,'10','1',null,null)");
//        database.QueryData("INSERT INTO AttendanceDetailsTable VALUES(null,'10','1',null,null)");
//        database.QueryData("INSERT INTO AttendanceDetailsTable VALUES(null,'10','1',null,null)");
//        database.QueryData("INSERT INTO AttendanceDetailsTable VALUES(null,'10','1',null,null)");
//        database.QueryData("INSERT INTO AttendanceDetailsTable VALUES(null,'10','1',null,null)");
//        //
//        database.QueryData("INSERT INTO AttendanceDetailsTable VALUES(null,'11','1',null,null)");
//        database.QueryData("INSERT INTO AttendanceDetailsTable VALUES(null,'11','1',null,null)");
//        database.QueryData("INSERT INTO AttendanceDetailsTable VALUES(null,'11','1',null,null)");
//        database.QueryData("INSERT INTO AttendanceDetailsTable VALUES(null,'11','1',null,null)");
//        database.QueryData("INSERT INTO AttendanceDetailsTable VALUES(null,'11','1',null,null)");
//        //
//        database.QueryData("INSERT INTO AttendanceDetailsTable VALUES(null,'12','1',null,null)");
//        database.QueryData("INSERT INTO AttendanceDetailsTable VALUES(null,'12','1',null,null)");
//        database.QueryData("INSERT INTO AttendanceDetailsTable VALUES(null,'12','1',null,null)");
//        database.QueryData("INSERT INTO AttendanceDetailsTable VALUES(null,'12','1',null,null)");
//        database.QueryData("INSERT INTO AttendanceDetailsTable VALUES(null,'12','1',null,null)");
//        //
//        database.QueryData("INSERT INTO AttendanceDetailsTable VALUES(null,'13','1',null,null)");
//        database.QueryData("INSERT INTO AttendanceDetailsTable VALUES(null,'13','1',null,null)");
//        database.QueryData("INSERT INTO AttendanceDetailsTable VALUES(null,'13','1',null,null)");
//        database.QueryData("INSERT INTO AttendanceDetailsTable VALUES(null,'13','1',null,null)");
//        database.QueryData("INSERT INTO AttendanceDetailsTable VALUES(null,'13','1',null,null)");
//        //
//        database.QueryData("INSERT INTO AttendanceDetailsTable VALUES(null,'14','1',null,null)");
//        database.QueryData("INSERT INTO AttendanceDetailsTable VALUES(null,'14','1',null,null)");
//        database.QueryData("INSERT INTO AttendanceDetailsTable VALUES(null,'14','1',null,null)");
//        database.QueryData("INSERT INTO AttendanceDetailsTable VALUES(null,'14','1',null,null)");
//        database.QueryData("INSERT INTO AttendanceDetailsTable VALUES(null,'14','1',null,null)");
    }
}