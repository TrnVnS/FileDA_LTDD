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

import com.example.stsfoods.DAO.BanAnDAO;
import com.example.stsfoods.R;

public class ThemBanAn_Activity extends AppCompatActivity {

    EditText edtThemBan;
    Button btnThemBan, btnThoat;
    BanAnDAO bDAO;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thembanan);

        edtThemBan = (EditText) findViewById(R.id.edtThemBan);
        btnThemBan = (Button) findViewById(R.id.btnThemBan);
        btnThoat = (Button) findViewById(R.id.btnThemBan);

        bDAO = new BanAnDAO(this);


        btnThemBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenban = edtThemBan.getText().toString();
                if (tenban != null && !tenban.equals("")) {
                    boolean kt = bDAO.ThemBanAn(tenban);
                    Intent intent = new Intent();
                    intent.putExtra("ketquathem", kt);
                    setResult(Activity.RESULT_OK, intent); // Truyền intent vào hàm setResult
                    finish();
                } else {
                    Toast.makeText(ThemBanAn_Activity.this, "Vui lòng nhập tên bàn", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}