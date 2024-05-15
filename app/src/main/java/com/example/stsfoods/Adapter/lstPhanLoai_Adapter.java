package com.example.stsfoods.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.stsfoods.DTO.PhanLoai_DTO;
import com.example.stsfoods.R;

import java.util.List;

public class lstPhanLoai_Adapter extends BaseAdapter {
    Context context;
    int layout;
    List<PhanLoai_DTO> lst;

    public lstPhanLoai_Adapter(Context context, int layout, List<PhanLoai_DTO> lst) {
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
        return lst.get(position).getMa();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.listitem_loaimon, parent, false);

        TextView txtLoaiMon = (TextView) view.findViewById(R.id.txt_tenLoaiMon);

        txtLoaiMon.setText(lst.get(position).getTen());

        return view;
    }
}
