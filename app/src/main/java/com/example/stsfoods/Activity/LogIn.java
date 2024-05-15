package com.example.stsfoods.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.stsfoods.DAO.DAO_NhanVien;
import com.example.stsfoods.Database.CreateDatabase;
import com.example.stsfoods.R;

public class LogIn extends AppCompatActivity {

    EditText edtTK, edtMK;
    Button btnDN, btnDK;
    DAO_NhanVien nvDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);


        edtTK = (EditText) findViewById(R.id.edt_TenDN);
        edtMK = (EditText) findViewById(R.id.edt_MatKhau);
        btnDN = (Button) findViewById(R.id.btn_DangNhap);
        btnDK = (Button) findViewById(R.id.btn_DangKy);

        nvDAO = new DAO_NhanVien(this);


        //Click btn đăng nhập
        btnDN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tendn = edtTK.getText().toString();
                String mk = edtMK.getText().toString();

                boolean kt = nvDAO.KiemTraDN(tendn, mk);

                if(tendn.isEmpty()||mk.isEmpty()){
                    Toast.makeText(LogIn.this, "Chưa nhập tên đăng nhập hoặc mật khẩu.", Toast.LENGTH_SHORT).show();
                } else {
                    if (kt){
                        Toast.makeText(LogIn.this, "Đăng nhập thành công.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LogIn.this, MainActivity.class);
                        intent.putExtra("TenDN", tendn);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LogIn.this, "Tên đăng nhập hoặc mật khẩu không chính xác.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        //Click btn đăng ký
        btnDK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogIn.this, SignIn.class);
                startActivity(intent);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}