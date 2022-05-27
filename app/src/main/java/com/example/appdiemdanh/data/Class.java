package com.example.appdiemdanh.data;

public class Class {
    private int ClassID;
    private String ClassName;
    private String Subject;
    private String ClassRoom;
    private String StartDay;
    private String EndDay;
    private float ScorePercent;
    private String CodeJoin;
    private String CodeAttendance;
    private Integer Status;
    private String TeacherName;

    public Class(int classID, String className, String teacherName) {
        ClassID = classID;
        ClassName = className;
        TeacherName = teacherName;
    }

    public int getClassID() {
        return ClassID;
    }

    public void setClassID(int classID) {
        ClassID = classID;
    }

    public String getClassName() {
        return ClassName;
    }

    public void setClassName(String className) {
        ClassName = className;
    }

    public String getTeacherName() {
        return TeacherName;
    }

    public void setTeacherName(String teacherName) {
        TeacherName = teacherName;
    }

    public String getCodeAttendance() {
        return CodeAttendance;
    }

    public void setCodeAttendance(String codeAttendance) {
        CodeAttendance = codeAttendance;
    }

    public String getCodeJoin() {
        return CodeJoin;
    }

    public void setCodeJoin(String codeJoin) {
        CodeJoin = codeJoin;
    }

    public String getClassRoom() {
        return ClassRoom;
    }

    public void setClassRoom(String classRoom) {
        ClassRoom = classRoom;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public float getScorePercent() {
        return ScorePercent;
    }

    public void setScorePercent(float scorePercent) {
        ScorePercent = scorePercent;
    }

    public String getEndDay() {
        return EndDay;
    }

    public void setEndDay(String endDay) {
        EndDay = endDay;
    }

    public String getStartDay() {
        return StartDay;
    }

    public void setStartDay(String startDay) {
        StartDay = startDay;
    }

    public Integer getStatus() {
        return Status;
    }

    public void setStatus(Integer status) {
        Status = status;
    }
}
