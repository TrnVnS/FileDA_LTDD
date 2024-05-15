package com.example.stsfoods.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.stsfoods.DAO.PhanLoai_DAO;
import com.example.stsfoods.DTO.Mon_DTO;
import com.example.stsfoods.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Mon_Adapter extends BaseAdapter {

    Context context;
    int layout;
    List<Mon_DTO> lst;

    public Mon_Adapter (Context context, int layout, List<Mon_DTO> lst)
    {
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
        view = inflater.inflate(R.layout.item_qlmon, parent, false);

        TextView txtTenMon = (TextView) view.findViewById(R.id.txtTenMon);
        TextView txtDonGia = (TextView) view.findViewById(R.id.txtDonGia);
        ImageView imgHinhMon = (ImageView) view.findViewById(R.id.imgHinhMon);

        Mon_DTO monDTO = lst.get(position);

        txtTenMon.setText(monDTO.getTenmon());
        txtDonGia.setText("Đơn giá: "+monDTO.getDongia());

        if (monDTO.getHinhanh() != null && !monDTO.getHinhanh().isEmpty()) {
            // Nếu có hình ảnh, tải hình ảnh bằng Picasso
            Picasso.get().load("file://" + monDTO.getHinhanh()).into(imgHinhMon);
        } else {
            // Nếu không có hình ảnh, đặt hình ảnh mặc định của máy vào ImageView
            imgHinhMon.setImageResource(R.drawable.logo); // hoặc hình ảnh khác tùy chọn
        }
        return view;
    }

}