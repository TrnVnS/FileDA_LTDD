package com.example.stsfoods.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.stsfoods.DTO.NhanVien_DTO;
import com.example.stsfoods.R;

import java.util.List;

public class NhanVien_Adapter extends BaseAdapter {
    Context context;
    int layout;
    List<NhanVien_DTO> lst;

    public NhanVien_Adapter(Context context, int layout, List<NhanVien_DTO> lst) {
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
        return lst.get(position).getMaNV();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.listitem_nv, parent, false);

//        TextView txtMa = (TextView) v.findViewById(R.id.txt_MaNV);
        TextView txtHoten = (TextView) v.findViewById(R.id.txt_TenNV);
        TextView txtNgaysinh = (TextView) v.findViewById(R.id.txt_NgaySinh);
        TextView txtGioitinh = (TextView) v.findViewById(R.id.txt_GioiTinh);
        TextView txtSdt = (TextView) v.findViewById(R.id.txt_SDT);
        TextView txtEmail = (TextView) v.findViewById(R.id.txt_Email);
        TextView txtTendn = (TextView) v.findViewById(R.id.txt_TenDN);
        TextView txtChucvu = (TextView) v.findViewById(R.id.txt_ChucVu);

//        txtMa.setText(lst.get(position).getMaNV());
        txtHoten.setText(lst.get(position).getHoTen());
        txtNgaysinh.setText("Ngày sinh: " + lst.get(position).getNgaySinh());
        txtGioitinh.setText("Giới tính: " + lst.get(position).getGioiTinh());
        txtSdt.setText("SDT: " + lst.get(position).getsDT());
        txtEmail.setText("Email: " + lst.get(position).getEmail());
        txtTendn.setText("Tên đăng nhập: " + lst.get(position).getTenDangNhap());
        txtChucvu.setText("Chức vụ: " + lst.get(position).getChucVu());

        return v;
    }
}
