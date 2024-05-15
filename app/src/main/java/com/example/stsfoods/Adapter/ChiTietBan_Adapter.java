package com.example.stsfoods.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.stsfoods.DAO.Mon_DAO;
import com.example.stsfoods.DTO.ChiTietBan_DTO;
import com.example.stsfoods.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ChiTietBan_Adapter extends BaseAdapter{

    Context context;
    int layout;
    List<ChiTietBan_DTO> lst;
    ViewHolderChiTietBan viewHolderChiTietBan;

    public ChiTietBan_Adapter(Context context, int layout, List<ChiTietBan_DTO> lst)
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

    public class ViewHolderChiTietBan {
        TextView txtTenMon, txtDonGia, txtSoLuong;
        ImageView imgHinhMon;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            viewHolderChiTietBan = new ViewHolderChiTietBan();
            v = inflater.inflate(R.layout.item_qlchitietban, parent, false);
            viewHolderChiTietBan.txtTenMon = v.findViewById(R.id.txtTenMonHD);
            viewHolderChiTietBan.txtSoLuong = v.findViewById(R.id.txtSoLuongHD);
            viewHolderChiTietBan.txtDonGia = v.findViewById(R.id.txtDonGiaHD);
            viewHolderChiTietBan.imgHinhMon = v.findViewById(R.id.imgHinhMonCT);
            v.setTag(viewHolderChiTietBan);
        } else {
            viewHolderChiTietBan = (ViewHolderChiTietBan) v.getTag();
        }

        ChiTietBan_DTO chiTietHD = lst.get(position);
        viewHolderChiTietBan.txtSoLuong.setText("Số lượng: " + chiTietHD.getSoluong());
        viewHolderChiTietBan.txtTenMon.setText("Tên món: " + LayTenMonTuMaMon(chiTietHD.getMamon()));
        viewHolderChiTietBan.txtDonGia.setText("Đơn giá: " + LayDonGiaTuMaMon(chiTietHD.getMamon()));

        Picasso.get().load("file://" + LayHinhTuMaMon(chiTietHD.getMamon()) ).into(viewHolderChiTietBan.imgHinhMon);


        return v;
    }

    private String LayHinhTuMaMon(int mamon)
    {
        Mon_DAO mDAO = new Mon_DAO(context);
        String duongdan = mDAO.LayDuongDanUngVoiMaMon(mamon);
        if (duongdan != null) {
            return duongdan;
        }
        return null; // Trả về null nếu không tìm thấy
    }

    private String LayTenMonTuMaMon(int mamon)
    {
        Mon_DAO mDAO = new Mon_DAO(context);
        String tenmon = mDAO.LayTenMonUngVoiMaMon(mamon);
        if (tenmon != null) {
            return tenmon;
        }
        return null; // Trả về null nếu không tìm thấy
    }

    private String LayDonGiaTuMaMon(int mamon)
    {
        Mon_DAO mDAO = new Mon_DAO(context);
        String dongia = mDAO.LayDonGiaUngVoiMaMon(mamon);
        return dongia;
    }
}
