package com.example.stsfoods.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.stsfoods.Adapter.ChiTietBan_Adapter;
import com.example.stsfoods.Adapter.Mon_Adapter;
import com.example.stsfoods.Adapter.spinnerMonAdapter;
import com.example.stsfoods.DAO.BanAnDAO;
import com.example.stsfoods.DAO.ChiTietBan_DAO;
import com.example.stsfoods.DAO.MonDAO;
import com.example.stsfoods.DTO.ChiTietBan_DTO;
import com.example.stsfoods.DTO.MonDTO;
import com.example.stsfoods.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ChiTietBan_Activity extends AppCompatActivity {

    ListView lstQLMon;
    EditText edtNgayLap, edtSoLuongCT, edtTenBanCT;
    Spinner spnTenMonCT;
    Button btnLuuCT, btnHuyCT, btnThemMonCT, btnThoatCT;
    int maBan;

    BanAnDAO bDAO;

    MonDAO mDAO;
    spinnerMonAdapter mAdapter;

    ChiTietBan_DTO ctDTO;
    List<ChiTietBan_DTO> lstCT;
    ChiTietBan_DAO ctDAO;
    ChiTietBan_Adapter ctAdapter;

    List<MonDTO> lstMon;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitietban);

        lstQLMon = (ListView) findViewById(R.id.lvDSMonHD);
        edtNgayLap = (EditText) findViewById(R.id.edtNgayLapHD);
        edtSoLuongCT = (EditText) findViewById(R.id.edtSoLuongHD);
        edtTenBanCT = (EditText) findViewById(R.id.edtTenBanHD);
        spnTenMonCT = (Spinner) findViewById(R.id.spinMonHD);
        btnLuuCT = (Button) findViewById(R.id.btnLuuHD);
        btnHuyCT = (Button) findViewById(R.id.btnHuyHD);
        btnThemMonCT = (Button) findViewById(R.id.btnThemMonHD);
        btnThoatCT = (Button) findViewById(R.id.btnThoatHD);

        bDAO = new BanAnDAO(this);
        mDAO = new MonDAO(this);
        ctDAO = new ChiTietBan_DAO(this);

        // Lấy mã bàn từ Intent
        maBan = getIntent().getIntExtra("maBan", -1);

        HienThiTenBan();
        HienThiDSMon();
        HienThiDSHoaDon();

        String date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        edtNgayLap.setText(date);

        btnThemMonCT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int vitri = spnTenMonCT.getSelectedItemPosition();
                int mamon = lstMon.get(vitri).getMamon();
                String soluong = edtSoLuongCT.getText().toString();
                String ngaylap = edtNgayLap.getText().toString();

                if (soluong == null || soluong.trim().equals(""))
                {
                    Toast.makeText(getApplication(), "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    ChiTietBan_DTO ct = new ChiTietBan_DTO();
                    ct.setMaban(maBan);
                    ct.setMamon(mamon);
                    ct.setSoluong(Integer.parseInt(soluong));

                    boolean kt = ctDAO.ThemChiTietBan(ct);
                    if (kt)
                    {
                        Toast.makeText(getApplication(), "Bạn vừa thêm một món vào bàn", Toast.LENGTH_SHORT).show();
                        HienThiDSHoaDon();
                    }
                    else {
                        Toast.makeText(getApplication(), "Không thêm được", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        btnLuuCT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String soluong = edtSoLuongCT.getText().toString();
                MonDTO mDTO = (MonDTO) spnTenMonCT.getSelectedItem();

                // Kiểm tra
                if (soluong.equals("")) {
                    Toast.makeText(getApplication(), "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                ChiTietBan_DTO ctMoi = new ChiTietBan_DTO();
                ctMoi.setMaban(maBan);
                ctMoi.setMamon(mDTO.getMamon());
                ctMoi.setSoluong(Integer.parseInt(soluong));

                boolean kt = ctDAO.CapNhatChiTietBan(ctMoi);

                if (kt) {
                    // Cập nhật lại danh sách món và giao diện
                    HienThiDSHoaDon();
                    Toast.makeText(getApplication(), "Cập nhật món thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplication(), "Cập nhật món thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Đồng bộ lên edtText
        lstQLMon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ChiTietBan_DTO selectedMon = lstCT.get(position);

                edtSoLuongCT.setText(String.valueOf(selectedMon.getSoluong()));

                //Lấy tên loại
                int spinnerPositon = -1;
                for (int i = 0; i < lstMon.size(); i++)
                {
                    if (lstMon.get(i).getMamon() == selectedMon.getMamon());
                    {
                        spinnerPositon = i;
                        break;
                    }
                }
                if (spinnerPositon != -1)
                {
                    spnTenMonCT.setSelection(spinnerPositon);
                }

            }
        });

        lstQLMon.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ChiTietBan_Activity.this);
                builder.setTitle("Xác nhận");
                builder.setMessage("Bạn có chắc muốn xóa?");
                builder.setCancelable(true);
                builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Lấy mã món
                        ChiTietBan_DTO selectedCT = lstCT.get(position);
                        int mamon = selectedCT.getMamon();

                        // Thực hiện xoá theo mã
                        boolean ktmon = ctDAO.XoaCTBanThemMaMon(mamon);

                        if (ktmon) {
                            HienThiDSHoaDon();
                            Toast.makeText(getApplication(), "Đã xóa món ", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplication(), "Xóa món không thành công.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                return true;
            }
        });

        btnHuyCT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtSoLuongCT.setText("");

            }
        });

        btnThoatCT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void HienThiTenBan(){
        String ten = bDAO.LayTenUngVoiMaBan(maBan);
        edtTenBanCT.setText(ten);
    }

    public void HienThiDSHoaDon(){
        lstCT = ctDAO.DSBan(maBan);

        ctAdapter =  new ChiTietBan_Adapter(ChiTietBan_Activity.this, R.layout.item_qlhoadon, lstCT);
        lstQLMon.setAdapter(ctAdapter);
        ctAdapter.notifyDataSetChanged();
    }

    private void HienThiDSMon(){
        // Truy vấn danh sách món ăn từ cơ sở dữ liệu
        lstMon = mDAO.DSMon();

        mAdapter = new spinnerMonAdapter(ChiTietBan_Activity.this, R.layout.spinner_mon, lstMon);

        // Thiết lập adapter cho spinner
        spnTenMonCT.setAdapter(mAdapter);
    }

}