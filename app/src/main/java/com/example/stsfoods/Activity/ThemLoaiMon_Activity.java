package com.example.stsfoods.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.stsfoods.DAO.PhanLoai_DAO;
import com.example.stsfoods.R;

public class ThemLoaiMon_Activity extends AppCompatActivity {
    Button btnDongY;
    EditText edtTen;
    PhanLoai_DAO plDAO;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_themloaimon);
        edtTen = (EditText) findViewById(R.id.edtTenLoaiMon);
        btnDongY = (Button) findViewById(R.id.btnThemLoaiMon);
        plDAO = new PhanLoai_DAO(this);

        btnDongY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenloai = edtTen.getText().toString();
                if (tenloai != null && !tenloai.equals("")) {
                    boolean kt = plDAO.ThemLoai(tenloai);
                    Intent intent = new Intent();
                    intent.putExtra("ktloaimon", kt);
                    setResult(Activity.RESULT_OK, intent);
                    finish();

                } else {
                    Toast.makeText(ThemLoaiMon_Activity.this, "Vui lòng nhập tên loại món.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}