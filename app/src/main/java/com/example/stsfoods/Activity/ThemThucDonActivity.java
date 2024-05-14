package com.example.stsfoods.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.stsfoods.Adapter.PhanLoaiAdapter;
import com.example.stsfoods.DAO.MonDAO;
import com.example.stsfoods.DAO.PhanLoaiDAO;
import com.example.stsfoods.DTO.MonDTO;
import com.example.stsfoods.DTO.PhanLoaiDTO;
import com.example.stsfoods.Fragment.QLMonFragment;
import com.example.stsfoods.R;

import java.util.ArrayList;
import java.util.List;

public class ThemThucDonActivity extends AppCompatActivity {

    public static int Request_code_themloaitd = 112;
    public static int Request_code_themhinh = 113;
    ImageButton ibtnThemLTD;
    Spinner spnLoai;

    MonDAO mDAO;
    PhanLoaiDAO plDAO;

    List<PhanLoaiDTO> lst;
    PhanLoaiAdapter plAdapter;
    ImageView imgPicture;
    Button btnDongYThemMon, btnThoatTMon;
    String sDuongDanHinh;
    EditText edtTenMon, edtGia;
    QLMonFragment qlmon;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_themthucdon);

        imgPicture = (ImageView) findViewById(R.id.imgHinh);
        ibtnThemLTD = (ImageButton) findViewById(R.id.ibtnThemLoai);
        spnLoai = (Spinner) findViewById(R.id.spnLoai);
        edtTenMon = (EditText) findViewById(R.id.edtTenMonThem);
        edtGia = (EditText) findViewById(R.id.edtDonGiaThem);
        btnDongYThemMon = (Button) findViewById(R.id.btnDongYThemMon);
        btnThoatTMon = (Button) findViewById(R.id.btnThoatThemMon);

        plDAO = new PhanLoaiDAO(this);
        mDAO = new MonDAO(this);

        HienThiDSLoai();

        imgPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iMoHinh = new Intent();
                iMoHinh.setType("image/*");//Mở những ứng dụng có thể mở được file hình ảnh
                iMoHinh.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(iMoHinh, "Chọn hình món"), Request_code_themhinh);
            }
        });

        ibtnThemLTD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThemThucDonActivity.this, ThemLoaiThucDon_Activity.class);
                startActivityForResult(intent, Request_code_themloaitd);

            }
        });

        btnDongYThemMon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int vitri = spnLoai.getSelectedItemPosition();
                int maloai = lst.get(vitri).getMa(); //Lay ma loai tu vi tri duoc chon trong spinner
                String tenmon = edtTenMon.getText().toString();
                String gia = edtGia.getText().toString();

                if (tenmon != null && gia != null && !tenmon.trim().equals("") && !gia.equals("")) {
                    MonDTO m = new MonDTO();
                    m.setTenmon(tenmon);
                    m.setDongia(gia);
                    m.setMaloai(maloai);
                    m.setHinhanh(sDuongDanHinh);

                    boolean kt = mDAO.ThemMon(m);
                    if (kt)
                    {
                        Toast.makeText(getApplication(), "Đã thêm món ăn.", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getApplication(), "Không thể thêm món.", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(getApplication(), "Tên món và giá tiền không được để trống.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnThoatTMon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void HienThiDSLoai(){
        lst = plDAO.DSPhanLoai();
        plAdapter = new PhanLoaiAdapter(ThemThucDonActivity.this, R.layout.spinner_loaithucdon, lst);
        spnLoai.setAdapter(plAdapter);
        plAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Request_code_themloaitd)
        {
            if (resultCode == Activity.RESULT_OK)
            {
                Intent intent = data;
                boolean kt = intent.getBooleanExtra("kiemtraloai", false);
                if (kt)
                {
                    Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    HienThiDSLoai();
                }
                else
                {
                    Toast.makeText(this, "Thêm thất bại", Toast.LENGTH_SHORT).show();

                }
            }
        }
        else if (requestCode == Request_code_themhinh)
        {
            if (resultCode == Activity.RESULT_OK) //Mặc định chọn 1 tấm hình thì sẽ là result ok
            {
                //Lay duong dan hinh
                sDuongDanHinh = data.getData().toString();
                imgPicture.setImageURI(data.getData());

            }
        }
    }
}