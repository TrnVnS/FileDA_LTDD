package com.example.stsfoods.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.stsfoods.DTO.MonDTO;
import com.example.stsfoods.DTO.PhanLoaiDTO;
import com.example.stsfoods.R;

import java.util.List;

public class spinnerMonAdapter extends BaseAdapter {
    Context context;
    int layout;
    List<MonDTO> lst;

    public spinnerMonAdapter (Context context, int layout, List<MonDTO> lst) {
        this.context = context;
        this.layout = layout;
        this.lst = lst;
    }

    @Override
    public int getCount() {
        return lst.size();
    }

    @Override
    public Object getItem(int position) {
        return lst.get(position);
    }

    @Override
    public long getItemId(int position) {
        return lst.get(position).getMamon();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.spinner_mon, parent, false);

        TextView txtMon = (TextView) view.findViewById(R.id.spinTenMon);

        txtMon.setText(lst.get(position).getTenmon());

        return view;
    }
}
