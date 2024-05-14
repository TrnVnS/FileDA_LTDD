package com.example.stsfoods.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.stsfoods.DAO.PhanLoaiDAO;
import com.example.stsfoods.Fragment.QLLoaiMonFragment;
import com.example.stsfoods.R;

public class ThemLoaiMonActivity extends AppCompatActivity {
    Button btnDongY;
    EditText edtTen;
    PhanLoaiDAO plDAO;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_themloaimon);
        edtTen = (EditText) findViewById(R.id.edtTenLoaiMon);
        btnDongY = (Button) findViewById(R.id.btnThemLoaiMon);
        plDAO = new PhanLoaiDAO(this);

        btnDongY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenloai = edtTen.getText().toString();
                if (tenloai != null && !tenloai.equals("")) {
                    boolean kt = plDAO.ThemLoai(tenloai);
                    if(kt){
                        Toast.makeText(getApplication(), "Đã thêm loại món.", Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(getApplication(), "Thêm loại món không thành công.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ThemLoaiMonActivity.this, "Vui lòng nhập tên loại món.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}