package com.example.stsfoods.Fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.stsfoods.Activity.ThemNhanVien_Activity;
import com.example.stsfoods.Adapter.BanAdapter;
import com.example.stsfoods.Adapter.NhanVienAdapter;
import com.example.stsfoods.DAO.BanAnDAO;
import com.example.stsfoods.DAO.DAO_NhanVien;
import com.example.stsfoods.DTO.BanAnDTO;
import com.example.stsfoods.DTO.DTO_NhanVien;
import com.example.stsfoods.R;

import java.util.Calendar;
import java.util.List;

public class QLNhanVienFragment extends Fragment {

    ListView lst;
    List<DTO_NhanVien> lst_nv;
    DAO_NhanVien nvDAO;
    DTO_NhanVien nvDTO;
    NhanVienAdapter nvAdapter;

    EditText edtHoten, edtNgaysinh, edtSDT, edtEmail;
    RadioButton rdNam, rdNu, rdNV, rdQL;
    Button btnLuu, btnHuy, btnChon;
    int manv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_qlnhanvien, container, false);

        lst = (ListView) view.findViewById(R.id.lst_NV);

        btnLuu =  (Button) view.findViewById(R.id.btn_sLuu);
        btnHuy = (Button) view.findViewById(R.id.btn_sHuy);
        btnChon = (Button) view.findViewById(R.id.btn_sChonNS);
        rdNam = (RadioButton) view.findViewById(R.id.rd_sNam);
        rdNu = (RadioButton) view.findViewById(R.id.rd_sNu);
        rdNV = (RadioButton) view.findViewById(R.id.rd_sNhanVien);
        rdQL = (RadioButton) view.findViewById(R.id.rd_sQuanLy);
        edtHoten = (EditText) view.findViewById(R.id.edt_sHoTen);
        edtNgaysinh = (EditText) view.findViewById(R.id.edt_sNgaySinh);
        edtSDT = (EditText) view.findViewById(R.id.edt_sSDT);
        edtEmail = (EditText) view.findViewById(R.id.edt_sEmail);

        nvDAO = new DAO_NhanVien(getActivity());
        HienThiNhanVienAdapter();

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DTO_NhanVien nv = lst_nv.get(position);
                edtHoten.setText(nv.getHoTen());
                edtNgaysinh.setText(nv.getNgaySinh());
                edtEmail.setText(nv.getEmail());
                edtSDT.setText(nv.getsDT());
                if(nv.getGioiTinh().equals("Nam")){
                    rdNam.setChecked(true);
                }else{
                    rdNu.setChecked(true);
                }
                if(nv.getChucVu().equals("Nhân viên")){
                    rdNV.setChecked(true);
                }else{
                    rdQL.setChecked(true);
                }
                manv = nv.getMaNV();
            }
        });

        lst.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                DTO_NhanVien nv = lst_nv.get(position);
                int manv = nv.getMaNV();
                boolean kt = nvDAO.deleteNhanVien(manv);
                if(kt){
                    HienThiNhanVienAdapter();
                    Toast.makeText(getActivity(), "Đã xóa nhân viên.", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getActivity(), "Không thể xóa nhân viên.", Toast.LENGTH_LONG).show();
                }
                return true;
            }
        });
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hoten = edtHoten.getText().toString();
                String ngaysinh = edtNgaysinh.getText().toString();
                String gioitinh;
                if (rdNam.isChecked() == true)
                    gioitinh = rdNam.getText().toString();
                else
                    gioitinh = rdNu.getText().toString();
                String sdt = edtSDT.getText().toString();
                String email = edtEmail.getText().toString();
                String chucvu;
                if (rdNV.isChecked() == true)
                    chucvu = rdNV.getText().toString();
                else
                    chucvu = rdQL.getText().toString();

                if (hoten.isEmpty() || ngaysinh.isEmpty() || sdt.isEmpty() || email.isEmpty()) {
                    Toast.makeText(getActivity(), "Không để trống các thông tin.", Toast.LENGTH_LONG).show();
                } else {
                    if (!Patterns.PHONE.matcher(sdt).matches()) {
                        Toast.makeText(getActivity(), "Số điện thoại không hợp lệ.", Toast.LENGTH_LONG).show();
                    } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        Toast.makeText(getActivity(), "Email không hợp lệ.", Toast.LENGTH_LONG).show();
                    } else {
                        DTO_NhanVien nv = new DTO_NhanVien();
                        nv.setMaNV(manv);
                        nv.setHoTen(hoten);
                        nv.setNgaySinh(ngaysinh);
                        nv.setGioiTinh(gioitinh);
                        nv.setsDT(sdt);
                        nv.setEmail(email);
                        nv.setChucVu(chucvu);

                        boolean kt = nvDAO.updateNhanVien(nv);
                        if(kt){
                            HienThiNhanVienAdapter();
                            Toast.makeText(getActivity(), "Cập nhật nhân viên thành công.", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getActivity(), "Cập nhật nhân viên không thành công.", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtHoten.setText("");
                edtNgaysinh.setText("");
                edtSDT.setText("");
                edtEmail.setText("");
                rdNam.setChecked(true);
                rdNV.setChecked(true);
            }
        });
        btnChon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();

                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                edtNgaysinh.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        },
                        year, month, day);
                datePickerDialog.show();
            }
        });
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem iThemNV = menu.add(1, R.id.itThemNhanVien, 1, "Thêm nhân viên");
        iThemNV.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.itThemNhanVien:
                Intent intent = new Intent(getActivity(), ThemNhanVien_Activity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void HienThiNhanVienAdapter(){
        lst_nv = nvDAO.getAllNhanVien();

        nvAdapter = new NhanVienAdapter(getActivity(), R.layout.listitem_nv, lst_nv);
        lst.setAdapter(nvAdapter);
        nvAdapter.notifyDataSetChanged();

    }
}