package com.example.stsfoods.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.stsfoods.DAO.PhanLoaiDAO;
import com.example.stsfoods.DTO.BanAnDTO;
import com.example.stsfoods.DTO.MonDTO;
import com.example.stsfoods.DTO.PhanLoaiDTO;
import com.example.stsfoods.R;

import java.util.List;

public class Mon_Adapter extends BaseAdapter {

    Context context;
    int layout;
    List<MonDTO> lst;

    public Mon_Adapter (Context context, int layout, List<MonDTO> lst)
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

        txtTenMon.setText(lst.get(position).getTenmon());
        txtDonGia.setText("Đơn giá: "+lst.get(position).getDongia());

        return view;
    }

    // Phương thức để lấy tên loại từ mã loại
    private String LayTenTuMaLoai(int maloai) {
        PhanLoaiDAO plDAO = new PhanLoaiDAO(context);
        String tenloai = plDAO.LayTenUngVoiMaLoai(maloai);
        if (tenloai != null) {
            return tenloai;
        }
        return null; // Trả về null nếu không tìm thấy
    }
}