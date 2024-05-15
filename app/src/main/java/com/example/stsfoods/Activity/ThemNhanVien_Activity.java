package com.example.stsfoods.Activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.stsfoods.DAO.DAO_NhanVien;
import com.example.stsfoods.DTO.NhanVien_DTO;
import com.example.stsfoods.Fragment.QLNhanVienFragment;
import com.example.stsfoods.R;

import java.util.Calendar;

public class ThemNhanVien_Activity extends AppCompatActivity {

    EditText edtHoten, edtNgaysinh, edtSDT, edtEmail, edtTenDN, edtMK;
    RadioButton rdNam, rdNu, rdNV, rdQL;
    Button btnChon, btnThem, btnHuy;

    DAO_NhanVien nvDAO;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_themnhanvien);

        edtHoten = (EditText) findViewById(R.id.edt_themHoTen);
        edtNgaysinh = (EditText) findViewById(R.id.edt_themNgaySinh);
        edtSDT = (EditText) findViewById(R.id.edt_themSDT);
        edtEmail = (EditText) findViewById(R.id.edt_themEmail);
        edtTenDN = (EditText) findViewById(R.id.edt_themTenDN);
        edtMK = (EditText) findViewById(R.id.edt_themMatKhau);
        rdNam = (RadioButton) findViewById(R.id.rd_themNam);
        rdNu = (RadioButton) findViewById(R.id.rd_themNu);
        rdNV = (RadioButton) findViewById(R.id.rd_themNhanVien);
        rdQL = (RadioButton) findViewById(R.id.rd_themQuanLy);
        btnChon = (Button) findViewById(R.id.btn_themChonNS);
        btnThem = (Button) findViewById(R.id.btn_themThemNV);
        btnHuy = (Button) findViewById(R.id.btn_themHuy);

        nvDAO = new DAO_NhanVien(this);

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hoten = edtHoten.getText().toString();
                String ngaysinh = edtNgaysinh.getText().toString();
                String sdt = edtSDT.getText().toString().trim();
                String email = edtEmail.getText().toString();
                String tendn = edtTenDN.getText().toString();
                String mk = edtMK.getText().toString();
                String gioitinh;
                if(rdNam.isChecked())
                    gioitinh = rdNam.getText().toString();
                else
                    gioitinh = rdNu.getText().toString();
                String chucvu;
                if(rdNV.isChecked())
                    chucvu = rdNV.getText().toString();
                else
                    chucvu = rdQL.getText().toString();

                if(hoten.isEmpty()||ngaysinh.isEmpty()||sdt.isEmpty()||email.isEmpty()||tendn.isEmpty()||mk.isEmpty()){
                    Toast.makeText(ThemNhanVien_Activity.this, "Không để trống các thông tin.", Toast.LENGTH_LONG).show();
                } else {
                    if(!Patterns.PHONE.matcher(sdt).matches()){
                        Toast.makeText(ThemNhanVien_Activity.this, "Số điện thoại không hợp lệ.", Toast.LENGTH_LONG).show();
                    } else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                        Toast.makeText(ThemNhanVien_Activity.this, "Email không hợp lệ.", Toast.LENGTH_LONG).show();
                    } else if(nvDAO.ktraNguoiDung(tendn)){
                        Toast.makeText(ThemNhanVien_Activity.this, "Tên người dùng đã tồn tại.", Toast.LENGTH_LONG).show();
                    } else {
                        NhanVien_DTO nv = new NhanVien_DTO();
                        nv.setHoTen(hoten);
                        nv.setNgaySinh(ngaysinh);
                        nv.setGioiTinh(gioitinh);
                        nv.setsDT(sdt);
                        nv.setEmail(email);
                        nv.setTenDangNhap(tendn);
                        nv.setMatKhau(mk);
                        nv.setChucVu(chucvu);
                        boolean kt = nvDAO.addNhanVien(nv);
                        if(kt){
                            Toast.makeText(getApplication(), "Thêm nhân viên thành công.", Toast.LENGTH_SHORT).show();
                            QLNhanVienFragment qlnv = new QLNhanVienFragment();
                            qlnv.HienThiNhanVienAdapter();
                            finish();
                        } else {
                            Toast.makeText(getApplication(), "Thêm nhân viên không thành công.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
                        ThemNhanVien_Activity.this,
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
    }
}