package com.example.appdiemdanh.data;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.example.appdiemdanh.GV_InfoClassActivity;
import com.example.appdiemdanh.R;

import java.util.ArrayList;

public class MainDetails extends AppCompatActivity {
    Database database;

    ListView lvDetails;
    ArrayList<Details> detailsArrayList;
    DetailsAdapter adapter;

    @Override
    protected void onCreate(Bundle saveInstanceStace) {
        super.onCreate(saveInstanceStace);
        setContentView(R.layout.activity_main_details);
        database = new Database(this);

        ImageView imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = GetId();
                Intent returnIntent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putInt("Key_Everyone", id);
                returnIntent.putExtras(bundle);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });

        //tìm kiếm
        int idClass = IdClass();
        Cursor data = database.GetData("SELECT StudentCode FROM ((AccountTable " +
                "INNER JOIN StudentTable ON AccountTable.StudentId = StudentTable.StudentId) " +
                "INNER JOIN ClassDetailsTable ON AccountTable.AccountId = ClassDetailsTable.AccountId) " +
                "WHERE ClassId = '" + idClass + "'");
        data.moveToFirst();
        String StudentCode = data.getString(0);

        SearchView searchView = findViewById(R.id.search);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                ArrayList<Details> filterStudent = new ArrayList<Details>();
                for (Details students : detailsArrayList) {
                    if (students.getStudentName().toLowerCase().contains(query.toLowerCase())
                            || students.getStudentCode().toLowerCase().contains(query.toLowerCase())) {
                        filterStudent.add(students);
                    }
                }
                if (filterStudent.isEmpty()) {
                    Toast.makeText(MainDetails.this, "Không tìm thấy", Toast.LENGTH_SHORT).show();
                }
                adapter = new DetailsAdapter(MainDetails.this, R.layout.activity_list_student, filterStudent);
                lvDetails.setAdapter(adapter);
                return false;
            }
        });
        searchView.setVisibility(View.GONE);
        ImageView imgSearch = findViewById(R.id.imgSearch);
        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imgSearch.getVisibility() == View.VISIBLE) {
                    imgSearch.setVisibility(View.INVISIBLE);
                    searchView.setVisibility(View.VISIBLE);
                }
            }
        });

        lvDetails = (ListView) findViewById(R.id.listviewStudent);
        detailsArrayList = new ArrayList<>();
        adapter = new DetailsAdapter(this, R.layout.activity_list_student, detailsArrayList);
        lvDetails.setAdapter(adapter);

        lvDetails.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState) {
                switch (scrollState) {
                    case SCROLL_STATE_IDLE:
                        imgSearch.setVisibility(View.VISIBLE);
                        break;
                    case SCROLL_STATE_TOUCH_SCROLL:
                        searchView.setVisibility(View.GONE);
                        imgSearch.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

            }
        });

        GetDataListStudent();

    }

    private int GetId() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int id = bundle.getInt("Key_Everyone", 0);
        return id;
    }

    public void DialogDeleteStudent(final int Id) {
        AlertDialog.Builder dialogDelete = new AlertDialog.Builder(this);
        dialogDelete.setMessage("Bạn có muốn xoá sinh viên này không? Sau khi xoá sinh viên sẽ không thể tham gia lại lớp học!");
        dialogDelete.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int Status = 0;
                database.QueryData("UPDATE ClassDetailsTable SET Status = '" + Status + "' WHERE ClassDetailsId = '" + Id + "'");
                //database.QueryData("DELETE FROM ClassDetailsTable WHERE ClassDetailsId = '"+ Id + "' ");
                Toast.makeText(MainDetails.this, "Bạn đã xoá thành công", Toast.LENGTH_SHORT).show();
                GetDataListStudent();
            }
        });

        dialogDelete.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialogDelete.show();
    }

    public int IdClass() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int idClass = bundle.getInt("Key_Everyone", 0);
        return idClass;
    }

    public void GetDataListStudent() {
        //hứng idclass
        int idClass = IdClass();
        Cursor dataDetails = database.GetData("SELECT ClassDetailsId, StudentName, SoBuoiDaDiemDanh, " +
                "Score, ClassDetailsTable.Status, StudentCode FROM ((AccountTable " +
                "INNER JOIN StudentTable ON AccountTable.StudentId = StudentTable.StudentId) " +
                "INNER JOIN ClassDetailsTable ON AccountTable.AccountId = ClassDetailsTable.AccountId) " +
                "WHERE ClassId = '" + idClass + "'");
        detailsArrayList.clear();
        while (dataDetails.moveToNext()) {
            if (dataDetails.getInt(4) == 1) {
                int id = dataDetails.getInt(0);
                String StudentName = dataDetails.getString(1);
                int NumSuccessAttendance = dataDetails.getInt(2);
                float Score = dataDetails.getFloat(3);
                String StudentCode = dataDetails.getString(5);
                String Student = StudentName + " - " + StudentCode;
                detailsArrayList.add(new Details(id, Student, StudentCode, NumSuccessAttendance, Score));
            }
        }
        adapter.notifyDataSetChanged();
    }
}
