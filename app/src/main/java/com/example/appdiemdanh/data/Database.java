package com.example.appdiemdanh.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;
import androidx.core.database.CursorWindowCompat;

public class Database extends SQLiteOpenHelper {
    Context context;

    public Database(@Nullable Context context) {
        super(context, "AttendanceApp.sqlite", null, 1);
        this.context = context;
    }

    //truy vấn không trả kết quả: CREATE, INSERT, UPDATE, DELETE,...
    public void QueryData(String sql) {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    //truy vấn có trả kết quả: SELECT
    public Cursor GetData(String sql) {
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //tạo bảng "lớp học":
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS ClassTable(ClassId INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "ClassName TEXT, Subject TEXT, ClassRoom TEXT, StartDay TEXT, EndDay TEXT, ScorePercent REAL, " +
                "CodeJoin TEXT, CodeAttendance TEXT, Status INTEGER, AccountId INTEGER," +
                "NumOfAttendance INTEGER, SuccessAttendance INTEGER," +
                "FOREIGN KEY(AccountId) REFERENCES AccountTable(AccountId) ON DELETE CASCADE)");

        //tạo bảng "sinh viên":
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS StudentTable(StudentId INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "StudentCode TEXT NOT NULL UNIQUE, StudentName TEXT, StudentBirthday TEXT, StudentPhone TEXT, " +
                "StudentGmail TEXT, StudentClass TEXT, StudentStatus INTEGER)");

        //tạo bảng "giảng viên":
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS TeacherTable(TeacherId INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "TeacherCode TEXT NOT NULL UNIQUE, TeacherName TEXT, TeacherBirthday TEXT, TeacherPhone TEXT, " +
                "TeacherGmail TEXT, TeachSubject TEXT, TeacherStatus INTEGER)");

        //tạo bảng "tài khoản":
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS AccountTable(AccountId INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Username TEXT NOT NULL UNIQUE, Password TEXT NOT NULL, Role INTEGER, " +
                "StudentId INTEGER, TeacherId INTEGER, " +
                "FOREIGN KEY(StudentId) REFERENCES StudentTable(StudentId) ON DELETE CASCADE, " +
                "FOREIGN KEY(TeacherId) REFERENCES TeacherTable(TeacherId) ON DELETE CASCADE)");

        //tạo bảng "chi tiết lớp học":
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS ClassDetailsTable(ClassDetailsId INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "AccountId INTEGER, ClassId INTEGER, Score REAL,SoBuoiDaDiemDanh INTEGER,Status INTEGER, " +
                "FOREIGN KEY(AccountId) REFERENCES AccountTable(AccountId) ON DELETE CASCADE, " +
                "FOREIGN KEY(ClassId) REFERENCES ClassTable(ClassId) ON DELETE CASCADE)");

        //tạo bảng điểm danh
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS AttendanceTable(AttendanceId INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "ClassId INTEGER,CodeAttendance TEXT, DateTime TEXT," +
                "FOREIGN KEY(ClassId) REFERENCES ClassTable(ClassId) ON DELETE CASCADE)");

        //chi chi tiết điểm danh:
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS AttendanceDetailsTable(AttendanceDetailsId INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "AccountId INTEGER, AttendanceId INTEGER, AttendanceCode TEXT,AttendanceTime TEXT, " +
                "FOREIGN KEY(AccountId) REFERENCES AccountTable(AccountId) ON DELETE CASCADE, " +
                "FOREIGN KEY(AttendanceId) REFERENCES AttendanceTable(AttendanceId) ON DELETE CASCADE)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + "'ClassTable'");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + "'StudentTable'");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + "'AccountTable'");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + "'TeacherTable'");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + "'ClassDetailsTable'");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + "'AttendanceTable'");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + "'AttendanceDetailsTable'");
        onCreate(sqLiteDatabase);
    }

    public Boolean CheckUsernamePassword(String username, String password) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from AccountTable where Username = ? and Password = ?", new String[]{username, password});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    //lấy quyền
    public int GetRole(int idAccountLogin) {
        int role = -1;
        SQLiteDatabase SQL = getReadableDatabase();
        Cursor cursor = SQL.rawQuery("Select * from AccountTable", null);
        {
            if (cursor.moveToFirst()) {
                do {
                    if (idAccountLogin == cursor.getInt(0)) {
                        role = cursor.getInt(3);
                    }
                } while (cursor.moveToNext());
            }
        }
        return role;
    }

    //lấy id lớp
    public int GetIdClass(String CodeJoin) {
        int ClassId = -1;
        SQLiteDatabase SQL = getReadableDatabase();
        Cursor cursor = SQL.rawQuery("Select * from ClassTable", null);
        {
            if (cursor.moveToFirst()) {
                do {
                    if (CodeJoin.equals(cursor.getString(7))) {
                        //if (CodeJoin == cursor.getString(7)) {
                        ClassId = cursor.getInt(0);
                    }
                } while (cursor.moveToNext());
            }
        }
        return ClassId;
    }

    //check đã tham gia lớp hay chưa
    public Boolean CheckUserInClass(String ClassId, String UserId) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from ClassDetailsTable where AccountId = ? and ClassId = ? ", new String[]{ClassId, UserId});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    //check sinh viên có bị xoá khỏi lớp không
    public int CheckUserRemovedClass(int AccountId, int ClassId) {
        int Status = -1;

        SQLiteDatabase SQL = getReadableDatabase();
        Cursor cursor = SQL.rawQuery("Select * from ClassDetailsTable", null);
        {
            if (cursor.moveToFirst()) {
                do {
                    if (AccountId == cursor.getInt(1) && ClassId == cursor.getInt(2) ) {
                        Status = cursor.getInt(5);
                    }
                } while (cursor.moveToNext());

            }
        }
        return Status;
    }


    //xét sinh viên trong bảng chi tiết để điểm danh
    public int GetIdDetail(String CodeAttendance) {
        int IdDetail = -1;
        SQLiteDatabase SQL = getReadableDatabase();
        Cursor cursor = SQL.rawQuery("Select * from AttendanceTable", null);
        {
            if (cursor.moveToFirst()) {
                do {
                    if (CodeAttendance.equals(cursor.getString(2))) {
                        IdDetail = cursor.getInt(0);
                    }
                } while (cursor.moveToNext());
            }
        }
        return IdDetail;
    }

    //check sinh viên điểm danh hay chưa
    public Boolean CheckUserAttendance(String AccountId, String AttendanceId) {//, String AttendanceCode -- and AttendanceCode  = ? -- ,AttendanceCode
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from AttendanceDetailsTable where AccountId = ? and AttendanceId = ?    ", new String[]{AccountId,AttendanceId});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    //check mã điểm danh
    public Boolean CheckCodeAttendance(String CodeAttendance) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from ClassTable where CodeAttendance = ? ", new String[]{CodeAttendance});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }
    //xét xem lớp học bị xoá hay chưa
    public int GetStatusClass(String CodeJoin) {
        int Status = -1;
        SQLiteDatabase SQL = getReadableDatabase();
        Cursor cursor = SQL.rawQuery("Select * from ClassTable", null);
        {
            if (cursor.moveToFirst()) {
                do {
                    if (CodeJoin.equals(cursor.getString(7))) {
                        Status = cursor.getInt(9);
                    }
                } while (cursor.moveToNext());
            }
        }
        return Status;
    }

    //xét xem code join có đúng hay không
    public Boolean CheckCodeJoin(String CodeJoin) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from ClassTable where CodeJoin = ? ", new String[]{CodeJoin});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    //lấy mã lớp bên bảng điểm danh
    public int IdClassAttendance(String CodeAttendance) {
        int ClassId = -1;
        SQLiteDatabase SQL = getReadableDatabase();
        Cursor cursor = SQL.rawQuery("Select * from AttendanceTable", null);
        {
            if (cursor.moveToFirst()) {
                do {
                    if (CodeAttendance.equals(cursor.getString(2))) {
                        //if (CodeJoin == cursor.getString(7)) {
                        ClassId = cursor.getInt(1);
                    }
                } while (cursor.moveToNext());
            }
        }
        return ClassId;
    }
    // lấy id bảng chi tiết
    public int GetIdClassDetail(int account , int IdClassDetails) {
        int IdDetail = -1;
        SQLiteDatabase SQL = getReadableDatabase();
        Cursor cursor = SQL.rawQuery("Select * from ClassDetailsTable", null);
        {
            if (cursor.moveToFirst()) {
                do {
                    if (account == cursor.getInt(1) && IdClassDetails == cursor.getInt(2)) {
                        IdDetail = cursor.getInt(0);
                    }
                } while (cursor.moveToNext());
            }
        }
        return IdDetail;
    }

    //lấy id lớp theo coded diem danh
    public int GetIdClassCodeAttendance(String CodeAttendance) {
        int ClassId = -1;
        SQLiteDatabase SQL = getReadableDatabase();
        Cursor cursor = SQL.rawQuery("Select * from ClassTable", null);
        {
            if (cursor.moveToFirst()) {
                do {
                    if (CodeAttendance.equals(cursor.getString(8))) {
                        //if (CodeJoin == cursor.getString(7)) {
                        ClassId = cursor.getInt(0);
                    }
                } while (cursor.moveToNext());
            }
        }
        return ClassId;
    }
}
