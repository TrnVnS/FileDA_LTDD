package com.example.stsfoods.Activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Selection;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.stsfoods.DAO.DAO_NhanVien;
import com.example.stsfoods.DTO.DTO_NhanVien;
import com.example.stsfoods.Database.CreateDatabase;
import com.example.stsfoods.R;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SignIn extends AppCompatActivity {

    Button btnChon, btnDK, btnDN;
    EditText edtHoTen, edtNgaySinh, edtSDT, edtEmail, edtTenDN, edtMK, edtXNMK;
    RadioButton rdNam, rdNu, rdNhanVien, rdQuanLy;

    private DAO_NhanVien dao_nv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signin);

        btnChon = (Button) findViewById(R.id.btn_ChonNS);
        btnDK = (Button) findViewById(R.id.btn_DangKy);
        btnDN = (Button) findViewById(R.id.btn_DangNhap);
        edtHoTen = (EditText) findViewById(R.id.edt_HoTen);
        edtNgaySinh = (EditText) findViewById(R.id.edt_NgaySinh);
        edtSDT = (EditText) findViewById(R.id.edt_SDT);
        edtEmail = (EditText) findViewById(R.id.edt_Email);
        edtTenDN = (EditText) findViewById(R.id.edt_TenDN);
        edtMK = (EditText) findViewById(R.id.edt_MatKhau);
        edtXNMK = (EditText) findViewById(R.id.edt_XacNhanMatKhau);
        rdNam = (RadioButton) findViewById(R.id.rd_Nam);
        rdNu = (RadioButton) findViewById(R.id.rd_Nu);
        rdNhanVien = (RadioButton) findViewById(R.id.rd_NhanVien);
        rdQuanLy = (RadioButton) findViewById(R.id.rd_QuanLy);

        dao_nv = new DAO_NhanVien(this);

        btnChon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();

                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        SignIn.this,
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

        btnDK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hoten = edtHoTen.getText().toString();
                String ngaysinh = edtNgaySinh.getText().toString();
                String sdt = edtSDT.getText().toString().trim();
                String email = edtEmail.getText().toString();
                String tendn = edtTenDN.getText().toString();
                String mk = edtMK.getText().toString();
                String xnmk = edtXNMK.getText().toString();
                String gioitinh;
                if(rdNam.isChecked())
                    gioitinh = rdNam.getText().toString();
                else
                    gioitinh = rdNu.getText().toString();
                String chucvu;
                if(rdNhanVien.isChecked())
                    chucvu = rdNhanVien.getText().toString();
                else
                    chucvu = rdQuanLy.getText().toString();

                if(hoten.isEmpty()||ngaysinh.isEmpty()||sdt.isEmpty()||email.isEmpty()||tendn.isEmpty()||mk.isEmpty()||xnmk.isEmpty()){
                    Toast.makeText(SignIn.this, "Không để trống các thông tin.", Toast.LENGTH_LONG).show();
                } else {
                    if(!Patterns.PHONE.matcher(sdt).matches()){
                        Toast.makeText(SignIn.this, "Số điện thoại không hợp lệ.", Toast.LENGTH_LONG).show();
                    } else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                        Toast.makeText(SignIn.this, "Email không hợp lệ.", Toast.LENGTH_LONG).show();
                    } else if(!mk.equals(xnmk)){
                        Toast.makeText(SignIn.this, "Mật khẩu xác nhận không trùng khớp.", Toast.LENGTH_LONG).show();
                    }  else if(dao_nv.ktraNguoiDung(tendn)){
                        Toast.makeText(SignIn.this, "Tên người dùng đã tồn tại.", Toast.LENGTH_LONG).show();
                    } else {
                        DTO_NhanVien nv = new DTO_NhanVien();
                        nv.setHoTen(hoten);
                        nv.setNgaySinh(ngaysinh);
                        nv.setGioiTinh(gioitinh);
                        nv.setsDT(sdt);
                        nv.setEmail(email);
                        nv.setTenDangNhap(tendn);
                        nv.setMatKhau(mk);
                        nv.setChucVu(chucvu);
                        boolean kt = dao_nv.addNhanVien(nv);
                        if(kt){
                            Toast.makeText(SignIn.this, "Đăng kí thành công.", Toast.LENGTH_LONG).show();
                            finish();
                        } else {
                            Toast.makeText(SignIn.this, "Đăng kí thất bại.", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });

        //Click btn đăng nhập
        btnDN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}