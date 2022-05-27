package com.example.appdiemdanh.data;

public class Details {
    private int ClassDetailsId;
    private int AccountId;
    private int ClassId;
    private float Score;
    private String StudentName;
    private int SoBuoiDaDiemDanh;
    private String StudentCode;


    public Details(int classDetailsId, String studentName, String studentCode, int soBuoiDaDiemDanh, float score) {
        ClassDetailsId = classDetailsId;
        Score = score;
        StudentName = studentName;
        StudentCode = studentCode;
        SoBuoiDaDiemDanh = soBuoiDaDiemDanh;
    }

    public int getClassDetailsId() {
        return ClassDetailsId;
    }

    public void setClassDetailsId(int classDetailsId) {
        ClassDetailsId = classDetailsId;
    }

    public int getAccountId() {
        return AccountId;
    }

    public void setAccountId(int accountId) {
        AccountId = accountId;
    }

    public int getClassId() {
        return ClassId;
    }

    public void setClassId(int classId) {
        ClassId = classId;
    }

    public float getScore() {
        return Score;
    }

    public void setScore(float score) {
        Score = score;
    }

    public int getSoBuoiDaDiemDanh() {
        return SoBuoiDaDiemDanh;
    }

    public void setSoBuoiDaDiemDanh(int soBuoiDaDiemDanh) {
        SoBuoiDaDiemDanh = soBuoiDaDiemDanh;
    }

    public String getStudentName() {
        return StudentName;
    }

    public void setStudentName(String studentName) {
        StudentName = studentName;
    }

    public String getStudentCode() {
        return StudentCode;
    }

    public void setStudentCode(String studentCode) {
        StudentCode = studentCode;
    }
}
