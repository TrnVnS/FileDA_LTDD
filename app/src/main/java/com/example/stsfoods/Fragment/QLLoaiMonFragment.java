package com.example.stsfoods.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.stsfoods.Activity.ThemBanAn_Activity;
import com.example.stsfoods.Activity.ThemLoaiMonActivity;
import com.example.stsfoods.Activity.ThemLoaiThucDon_Activity;
import com.example.stsfoods.Adapter.PhanLoaiAdapter;
import com.example.stsfoods.Adapter.lstPhanLoaiAdapter;
import com.example.stsfoods.DAO.MonDAO;
import com.example.stsfoods.DAO.PhanLoaiDAO;
import com.example.stsfoods.DTO.PhanLoaiDTO;
import com.example.stsfoods.R;

import java.util.List;

public class QLLoaiMonFragment extends Fragment {

    Button btnLuu, btnHuy;
    EditText edtLoaiMon;

    ListView listView;
    List<PhanLoaiDTO> lstPhanLoai;
    lstPhanLoaiAdapter adtPhanLoai;
    PhanLoaiDAO plDAO;

    MonDAO mDAO;

    int maLoai;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_qlloaimon, container, false);
        setHasOptionsMenu(true);

        listView = (ListView) view.findViewById(R.id.lst_LM);
        edtLoaiMon = (EditText) view.findViewById(R.id.edt_LoaiMon);
        btnLuu = (Button) view.findViewById(R.id.btn_LuuLoaiMon);
        btnHuy = (Button) view.findViewById(R.id.btn_HuyLoaiMon);

        mDAO = new MonDAO(getContext());
        plDAO = new PhanLoaiDAO(getContext());
        showLoaiMon();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PhanLoaiDTO pl = lstPhanLoai.get(position);
                edtLoaiMon.setText(pl.getTen());

                maLoai = pl.getMa();
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Xác nhận");
                builder.setMessage("Bạn có chắc muốn xóa?");
                builder.setCancelable(true);
                builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                        PhanLoaiDTO pl = lstPhanLoai.get(position);
                        int maloai = pl.getMa();
                        boolean kt = plDAO.XoaLoai(maloai);
                        if(kt){
                            showLoaiMon();
                            Toast.makeText(getActivity(), "Đã xóa loại món.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "Xóa loại món không thành công.", Toast.LENGTH_SHORT).show();
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
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenloai = edtLoaiMon.getText().toString();

                if(tenloai.isEmpty()){
                    Toast.makeText(getActivity(), "Chưa nhập tên loại món.", Toast.LENGTH_SHORT).show();
                } else {
                    PhanLoaiDTO pl = new PhanLoaiDTO();
                    pl.setMa(maLoai);
                    pl.setTen(tenloai);

                    boolean kt = plDAO.CapNhatLoai(pl);
                    if(kt){
                        showLoaiMon();
                        Toast.makeText(getActivity(), "Đã cập nhật loại món.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "Cập nhật loại món không thành công.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtLoaiMon.setText("");
                Toast.makeText(getActivity(), "Đã huỷ.", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem iThemBan = menu.add(1, R.id.itThemLoaiMon, 1, "Thêm loại món");
        iThemBan.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id)
        {
            case R.id.itThemLoaiMon:
                Intent intent = new Intent(getActivity(), ThemLoaiMonActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showLoaiMon(){
        lstPhanLoai = plDAO.DSPhanLoai();

        adtPhanLoai = new lstPhanLoaiAdapter(getActivity(), R.layout.listitem_loaimon, lstPhanLoai);
        listView.setAdapter(adtPhanLoai);
        adtPhanLoai.notifyDataSetChanged();

    }
}