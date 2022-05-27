package com.example.appdiemdanh.data;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;

import com.example.appdiemdanh.R;

import java.util.ArrayList;
import java.util.List;

public class ClassAdapter extends BaseAdapter {

    private MainClass context;
    private int layout;
    private List<Class> ClassList;

    public ClassAdapter(MainClass context, int layout, List<Class> classList) {
        this.context = context;
        this.layout = layout;
        this.ClassList = classList;
    }

    @Override
    public int getCount() {
        return ClassList.size();
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
        TextView txtClassName, txtTeacherName;
        Button btnClassDetails;
        RelativeLayout layoutClass;
        ImageView imgMenu;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder.layoutClass = view.findViewById(R.id.idClass);
            holder.txtClassName = (TextView) view.findViewById(R.id.txtClassName);
            holder.txtTeacherName = (TextView) view.findViewById(R.id.txtTeacherName);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        final Class lophoc = ClassList.get(i);
        holder.txtClassName.setText(lophoc.getClassName());
        holder.txtTeacherName.setText(lophoc.getTeacherName());
        holder.btnClassDetails = (Button) view.findViewById(R.id.btnClassDetails);
        holder.imgMenu = (ImageView) view.findViewById(R.id.imgMenu);
        //thông tin chi tiết lớp học
        holder.btnClassDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.ClassDetails(lophoc.getClassID());
            }
        });

        holder.imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(context, view);
                popupMenu.inflate(R.menu.menu_popup_class);

                //context.onCreateOptionsMenu();
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.menuEdit:
                                context.EditClassDialog(Gravity.BOTTOM, lophoc.getClassID());
                                break;
                            case R.id.menuDelete:
                                context.DeleteClass(lophoc.getClassID());
                                break;
                        }
                        return false;
                    }
                });
            }
        });
        return view;
    }
}
