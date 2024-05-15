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

public class PhanLoai_Adapter extends BaseAdapter {

    Context context;
    int layout;
    List<PhanLoai_DTO> lst;
    ViewHolderPhanLoai viewHolderPhanLoai;

    public PhanLoai_Adapter(Context context, int layout, List<PhanLoai_DTO> lst) {
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

    public class ViewHolderPhanLoai {
        TextView txtTenLoai;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            viewHolderPhanLoai = new ViewHolderPhanLoai();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.spinner_loaimon, parent, false);

            viewHolderPhanLoai.txtTenLoai = (TextView) view.findViewById(R.id.spinTenLoai);

            view.setTag(viewHolderPhanLoai);
        } else {
            viewHolderPhanLoai = (ViewHolderPhanLoai) view.getTag();
        }

        PhanLoai_DTO plDTO = lst.get(position);
        viewHolderPhanLoai.txtTenLoai.setText(plDTO.getTen());
        viewHolderPhanLoai.txtTenLoai.setTag(plDTO.getMa());

        return view;
    }
}