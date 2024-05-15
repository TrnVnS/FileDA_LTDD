package com.example.stsfoods.Activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.stsfoods.Adapter.PhanLoai_Adapter;
import com.example.stsfoods.DAO.Mon_DAO;
import com.example.stsfoods.DAO.PhanLoai_DAO;
import com.example.stsfoods.DTO.Mon_DTO;
import com.example.stsfoods.DTO.PhanLoai_DTO;
import com.example.stsfoods.Fragment.QLMonFragment;
import com.example.stsfoods.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class ThemMon_Activity extends AppCompatActivity {

    public static int Request_code_themloaimon = 1;
    public static int Request_code_themhinh = 2;
    ImageButton ibtnThemLoaiMon;
    Spinner spnLoai;

    Mon_DAO mDAO;
    PhanLoai_DAO plDAO;

    List<PhanLoai_DTO> lst;
    PhanLoai_Adapter plAdapter;
    ImageView imgPicture;
    Button btnDongYThemMon, btnThoatTMon;
    String sDuongDanHinh;
    EditText edtTenMon, edtGia;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_themmon);

        imgPicture = (ImageView) findViewById(R.id.imgHinh);
        ibtnThemLoaiMon = (ImageButton) findViewById(R.id.ibtnThemLoai);
        spnLoai = (Spinner) findViewById(R.id.spnLoai);
        edtTenMon = (EditText) findViewById(R.id.edtTenMonThem);
        edtGia = (EditText) findViewById(R.id.edtDonGiaThem);
        btnDongYThemMon = (Button) findViewById(R.id.btnDongYThemMon);
        btnThoatTMon = (Button) findViewById(R.id.btnThoatThemMon);

        plDAO = new PhanLoai_DAO(this);
        mDAO = new Mon_DAO(this);

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

        ibtnThemLoaiMon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThemMon_Activity.this, ThemLoaiMon_Activity.class);
                startActivityForResult(intent, Request_code_themloaimon);

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
                    Mon_DTO m = new Mon_DTO();
                    m.setTenmon(tenmon);
                    m.setDongia(gia);
                    m.setMaloai(maloai);
                    m.setHinhanh(sDuongDanHinh);

                    boolean kt = mDAO.ThemMon(m);

                    Intent intent = new Intent();
                    intent.putExtra("ketquathemmon", kt);
                    setResult(Activity.RESULT_OK, intent);
                    if (kt)
                    {
                        Toast.makeText(getApplication(), "Đã thêm món ăn.", Toast.LENGTH_SHORT).show();
                        edtTenMon.setText("");
                        edtGia.setText("");
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
        plAdapter = new PhanLoai_Adapter(ThemMon_Activity.this, R.layout.spinner_loaimon, lst);
        spnLoai.setAdapter(plAdapter);
        plAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Request_code_themloaimon)
        {
            if (resultCode == Activity.RESULT_OK)
            {
                Intent intent = data;
                boolean kt = intent.getBooleanExtra("ktloaimon", false);
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
            if (resultCode == Activity.RESULT_OK && data != null) //Mặc định chọn 1 tấm hình thì sẽ là result ok
            {
                // Lấy đường dẫn hình ảnh đã chọn
                Uri selectedImageUri = data.getData();

                // Sao chép hình ảnh vào thư mục 'image' của ứng dụng
                String copiedImagePath = copyImageToAppDirectory(selectedImageUri);

                // Nếu sao chép thành công, sử dụng đường dẫn mới
                if (copiedImagePath != null) {
                    sDuongDanHinh = copiedImagePath;
                    imgPicture.setImageURI(selectedImageUri); // Hiển thị hình ảnh đã chọn
                } else {
                    Toast.makeText(this, "Không thể sao chép hình ảnh", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private String copyImageToAppDirectory(Uri selectedImageUri) {
        try {
            // Mở luồng vào hình ảnh đã chọn
            InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);

            // Tạo file đích trong thư mục 'image' của ứng dụng
            File appDir = new File(getFilesDir(), "image");
            if (!appDir.exists()) {
                appDir.mkdir();
            }
            String imageName = "image_" + System.currentTimeMillis() + ".jpg";
            File destFile = new File(appDir, imageName);

            // Sao chép dữ liệu từ luồng vào file đích
            OutputStream outputStream = new FileOutputStream(destFile);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }

            // Đóng luồng và trả về đường dẫn của file đích
            inputStream.close();
            outputStream.close();
            return destFile.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}