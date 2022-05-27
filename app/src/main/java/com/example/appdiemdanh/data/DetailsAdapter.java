package com.example.appdiemdanh.data;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.appdiemdanh.GV_InfoClassActivity;
import com.example.appdiemdanh.R;

import java.util.ArrayList;
import java.util.List;

public class DetailsAdapter extends BaseAdapter {
    private MainDetails context;
    private int layout;
    private List<Details> detailsList;

    public DetailsAdapter(MainDetails mainDetails, int layout_details, List<Details> detailsArrayList) {
        this.context = mainDetails;
        this.layout = layout_details;
        this.detailsList = detailsArrayList;
    }


    @Override
    public int getCount() {
        return detailsList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder {
        TextView txtStudentName, txtNumOfAttendance, txtScore;
        Button btnDeleteStudent;
        RelativeLayout layoutListStudent;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder.layoutListStudent = (RelativeLayout) view.findViewById(R.id.idStudent);
            holder.txtStudentName = (TextView) view.findViewById(R.id.txtStudentName);
            holder.txtNumOfAttendance = (TextView) view.findViewById(R.id.txtNumOfAttendance);
            holder.txtScore = (TextView) view.findViewById(R.id.txtScore);
            holder.btnDeleteStudent = (Button) view.findViewById(R.id.btnDeleteStudent);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        final Details details = detailsList.get(i);
        holder.txtStudentName.setText(details.getStudentName());
        holder.txtNumOfAttendance.setText(String.valueOf(details.getSoBuoiDaDiemDanh()));
        holder.txtScore.setText(String.valueOf(details.getScore()));
        holder.btnDeleteStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.DialogDeleteStudent(details.getClassDetailsId());
            }
        });
        return view;
    }
}
