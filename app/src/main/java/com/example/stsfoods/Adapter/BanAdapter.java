package com.example.stsfoods.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.stsfoods.DTO.BanAnDTO;
import com.example.stsfoods.R;

import java.util.List;

public class BanAdapter extends BaseAdapter {

    Context context;
    int layout;
    List<BanAnDTO> lst;
    ViewHolderBan viewHolderBan;

    public BanAdapter (Context context, int layout, List<BanAnDTO> lst)
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
        return lst.get(position).getMaBan();
    }

    public class ViewHolderBan
    {
        ImageView imgBan, imgTinhTrang;
        TextView txtTenBan;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) //Khởi tạo các view
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            viewHolderBan = new ViewHolderBan();
            v =  inflater.inflate(R.layout.gridview_banan, parent, false);
            viewHolderBan.imgBan = (ImageView) v.findViewById(R.id.img_Ban);
            viewHolderBan.imgTinhTrang = (ImageView) v.findViewById(R.id.img_TinhTrang);
            viewHolderBan.txtTenBan = (TextView) v.findViewById(R.id.txtTenBan);

            v.setTag(viewHolderBan);
        }
        else {
            viewHolderBan = (ViewHolderBan) v.getTag();
        }

        BanAnDTO b = lst.get(position);
        viewHolderBan.txtTenBan.setText(b.getTenBan());
        viewHolderBan.imgBan.setTag(position);

        // Kiểm tra tình trạng của bàn để hiển thị hoặc ẩn ảnh img_TinhTrang
        if (b.getTinhTrang().equals("Có người")) {
            viewHolderBan.imgTinhTrang.setVisibility(View.VISIBLE);
        } else {
            viewHolderBan.imgTinhTrang.setVisibility(View.INVISIBLE);
        }

        return v;
    }
}
