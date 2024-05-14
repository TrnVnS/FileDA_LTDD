package com.example.stsfoods.Fragment;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.stsfoods.Activity.MainActivity;
import com.example.stsfoods.DAO.DAO_NhanVien;
import com.example.stsfoods.DTO.DTO_NhanVien;
import com.example.stsfoods.R;

import java.util.Calendar;

public class CapNhatTTFragment extends Fragment {

    EditText edtHoTen, edtNgaySinh, edtSDT, edtEmail;
    RadioButton rdNam, rdNu;
    Button btnChon, btnHuy, btnLuu;
    View view;
    MainActivity m;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_capnhantt, container, false);

        edtHoTen = (EditText) view.findViewById(R.id.edt_saHoTen);
        edtNgaySinh = (EditText) view.findViewById(R.id.edt_saNgaySinh);
        edtSDT = (EditText) view.findViewById(R.id.edt_saSDT);
        edtEmail = (EditText) view.findViewById(R.id.edt_saEmail);
        rdNam = (RadioButton) view.findViewById(R.id.rd_saNam);
        rdNu = (RadioButton) view.findViewById(R.id.rd_saNu);
        btnLuu = (Button) view.findViewById(R.id.btn_saLuu);
        btnHuy = (Button) view.findViewById(R.id.btn_saHuy);
        btnChon = (Button) view.findViewById(R.id.btn_saChonNS);

        m = (MainActivity) getActivity();

        DTO_NhanVien nvDTO = new DTO_NhanVien();
        DAO_NhanVien nvDAO = new DAO_NhanVien(getActivity());
        Cursor cursor = nvDAO.getNhanVien(m.getsTendn());
        cursor.moveToFirst();

        int manv = cursor.getInt(0);
        edtHoTen.setText(cursor.getString(1));
        edtNgaySinh.setText(cursor.getString(2));
        edtSDT.setText(cursor.getString(4));
        edtEmail.setText(cursor.getString(5));
        if(cursor.getString(3).equals("Nam")){
            rdNam.setChecked(true);
        } else {
            rdNu.setChecked(true);
        }

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hoten = edtHoTen.getText().toString();
                String ngaysinh = edtNgaySinh.getText().toString();
                String sdt = edtSDT.getText().toString();
                String email = edtEmail.getText().toString();
                String gioitinh;
                if (rdNam.isChecked() == true)
                    gioitinh = rdNam.getText().toString();
                else
                    gioitinh = rdNu.getText().toString();

                if (hoten.isEmpty() || ngaysinh.isEmpty() || sdt.isEmpty() || email.isEmpty()) {
                    Toast.makeText(getActivity(), "Không để trống các thông tin.", Toast.LENGTH_LONG).show();
                } else {
                    if (!Patterns.PHONE.matcher(sdt).matches()) {
                        Toast.makeText(getActivity(), "Số điện thoại không hợp lệ.", Toast.LENGTH_LONG).show();
                    } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        Toast.makeText(getActivity(), "Email không hợp lệ.", Toast.LENGTH_LONG).show();
                    } else {

                        nvDTO.setMaNV(manv);
                        nvDTO.setHoTen(hoten);
                        nvDTO.setNgaySinh(ngaysinh);
                        nvDTO.setGioiTinh(gioitinh);
                        nvDTO.setsDT(sdt);
                        nvDTO.setEmail(email);

                        boolean kt = nvDAO.updateTTTK(nvDTO);
                        if(kt){
                            Toast.makeText(getActivity(), "Cập nhật người dùng thành công.", Toast.LENGTH_LONG).show();
                            m.showInfoOnNavHeader(m.getsTendn());
                        } else {
                            Toast.makeText(getActivity(), "Cập nhật người dùng không thành công.", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DAO_NhanVien nvDAO = new DAO_NhanVien(getActivity());
                Cursor cursor = nvDAO.getNhanVien(m.getsTendn());
                cursor.moveToFirst();

                edtHoTen.setText(cursor.getString(1));
                edtNgaySinh.setText(cursor.getString(2));
                edtSDT.setText(cursor.getString(4));
                edtEmail.setText(cursor.getString(5));
                if(cursor.getString(3).equals("Nam")){
                    rdNam.setChecked(true);
                } else {
                    rdNu.setChecked(true);
                }
                Toast.makeText(getActivity(), "Đã hủy.", Toast.LENGTH_LONG).show();
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
                                edtNgaySinh.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        },
                        year, month, day);
                datePickerDialog.show();
            }
        });

        return view;
    }
}