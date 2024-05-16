    package com.example.stsfoods.Fragment;

    import android.app.Activity;
    import android.app.AlertDialog;
    import android.content.DialogInterface;
    import android.content.Intent;
    import android.net.Uri;
    import android.os.Bundle;

    import androidx.annotation.NonNull;
    import androidx.annotation.Nullable;
    import androidx.fragment.app.Fragment;

    import android.view.LayoutInflater;
    import android.view.Menu;
    import android.view.MenuInflater;
    import android.view.MenuItem;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.AdapterView;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.ImageView;
    import android.widget.ListView;
    import android.widget.Spinner;
    import android.widget.Toast;

    import com.example.stsfoods.Activity.ThemMon_Activity;
    import com.example.stsfoods.Adapter.Mon_Adapter;
    import com.example.stsfoods.Adapter.PhanLoai_Adapter;
    import com.example.stsfoods.DAO.ChiTietBan_DAO;
    import com.example.stsfoods.DAO.Mon_DAO;
    import com.example.stsfoods.DAO.PhanLoai_DAO;
    import com.example.stsfoods.DTO.Mon_DTO;
    import com.example.stsfoods.DTO.PhanLoai_DTO;
    import com.example.stsfoods.R;

    import java.io.File;
    import java.io.FileOutputStream;
    import java.io.IOException;
    import java.io.InputStream;
    import java.io.OutputStream;
    import java.util.List;

    public class QLMonFragment extends Fragment {

        ThemMon_Activity themmon;
        ImageView imgHinhQL;
        ListView lstQLMon;
        EditText edtTenMon, edtDonGia;
        Spinner spinLoai;
        Button btnLuu, btnHuy;

        Mon_DTO mDTO;
        List<Mon_DTO> lst;
        Mon_DAO mDAO;
        Mon_Adapter mAdapter;
        PhanLoai_DAO plDAO;
        PhanLoai_Adapter plAdapter;
        public static  int Request_code_mon = 4;
        ChiTietBan_DAO ctDAO;

        String sDuongDanHinh;
        int mamonchon;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            setHasOptionsMenu(true);
            View view = inflater.inflate(R.layout.fragment_qlmon, container, false);

            imgHinhQL = (ImageView) view.findViewById(R.id.imgHinhMonQL);
            lstQLMon = (ListView) view.findViewById(R.id.lvMon);
            edtTenMon = (EditText) view.findViewById(R.id.edtTenMon);
            edtDonGia = (EditText) view.findViewById(R.id.edtDonGia);
            spinLoai = (Spinner) view.findViewById(R.id.spnLoaiTD);
            btnLuu = (Button) view.findViewById(R.id.btnLuuMon);
            btnHuy = (Button) view.findViewById(R.id.btnHuyMon);



            mDAO = new Mon_DAO(getActivity());
            plDAO = new PhanLoai_DAO(getActivity());
            ctDAO = new ChiTietBan_DAO(getActivity());

            HienThiDSMon();
            HienThiDanhSachLoaiMon();

            imgHinhQL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent iMoHinh = new Intent();
                    iMoHinh.setType("image/*");//Mở những ứng dụng có thể mở được file hình ảnh
                    iMoHinh.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(iMoHinh, "Chọn hình món"), themmon.Request_code_themhinh);
                }
            });

            lstQLMon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Mon_DTO selectedMon = lst.get(position);
                    edtTenMon.setText(selectedMon.getTenmon());
                    edtDonGia.setText(selectedMon.getDongia());

                    if (selectedMon.getHinhanh() != null && !selectedMon.getHinhanh().isEmpty())
                        imgHinhQL.setImageURI(Uri.parse(selectedMon.getHinhanh()));
                    else
                        imgHinhQL.setImageResource(R.drawable.logo);

                    //Lấy mã
                    mamonchon = selectedMon.getMamon();

                    // Lấy danh sách loại món
                    List<PhanLoai_DTO> danhSachLoaiMon = plDAO.DSPhanLoai();

                    //Tìm địa chỉ của mã loại trong spiner
                    int spinnerPosition = -1;
                    for (int i = 0; i < danhSachLoaiMon.size(); i++) {
                        if (danhSachLoaiMon.get(i).getMa() == selectedMon.getMaloai()) {
                            spinnerPosition = i;
                            break;
                        }
                    }

                    if (spinnerPosition != -1)
                    {
                        spinLoai.setSelection(spinnerPosition);
                    }

                }
            });

            lstQLMon.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Xác nhận");
                    builder.setMessage("Bạn có chắc muốn xóa?");
                    builder.setCancelable(true);
                    builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Lấy đối tượng MonDTO tương ứng với vị trí trong danh sách
                            Mon_DTO selectedMon = lst.get(position);
                            //Lấy mã món
                            int mamon = selectedMon.getMamon();
                            // Gọi phương thức xóa món từ lớp DAO
                            boolean xoaThanhCong = mDAO.XoaMon(mamon);
                            boolean xoaCTBan = ctDAO.XoaCTBanTheoMaMon(mamon);
                            // Kiểm tra kết quả xóa và thông báo cho người dùng
                            if (xoaThanhCong && xoaCTBan) {
                                // Cập nhật lại danh sách món và giao diện
                                HienThiDSMon();
                                Toast.makeText(getActivity(), "Bạn vừa xoá một món", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), "Xóa món thất bại", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                    return true;
                }
            });

            btnLuu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String txtTenMon = edtTenMon.getText().toString();
                    String txtDonGia = edtDonGia.getText().toString();
                    PhanLoai_DTO plDTOmoi = (PhanLoai_DTO) spinLoai.getSelectedItem();

                    //Kiểm tra
                    if(txtTenMon.equals("") || txtDonGia.equals(""))
                    {
                        Toast.makeText(getActivity(), "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // Tạo đối tượng MonDTO mới với thông tin đã cập nhật
                    Mon_DTO monMoi = new Mon_DTO();
                    monMoi.setMamon(mamonchon);
                    monMoi.setTenmon(txtTenMon);
                    monMoi.setDongia(txtDonGia);
                    monMoi.setMaloai(plDTOmoi.getMa());
                    monMoi.setHinhanh(sDuongDanHinh);

                    // Gọi phương thức cập nhật món từ lớp DAO
                    boolean capNhatThanhCong = mDAO.CapNhatMon(monMoi);

                    // Kiểm tra kết quả cập nhật
                    if (capNhatThanhCong) {
                        // Cập nhật lại danh sách món và giao diện
                        HienThiDSMon();
                        Toast.makeText(getActivity(), "Cập nhật món thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "Cập nhật món thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            btnHuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    edtTenMon.setText("");
                    edtDonGia.setText("");
                    Toast.makeText(getActivity(), "Đã huỷ", Toast.LENGTH_SHORT).show();
                }
            });

            return view;
        }

        public void HienThiDSMon()
        {
            lst = mDAO.DSMon();

            mAdapter = new Mon_Adapter(getActivity(), R.layout.item_qlmon, lst);
            lstQLMon.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();

        }

        private void HienThiDanhSachLoaiMon() {
            List<PhanLoai_DTO> danhSachLoaiMon = plDAO.DSPhanLoai();
            plAdapter = new PhanLoai_Adapter(getActivity(), R.layout.spinner_loaimon, danhSachLoaiMon);
            spinLoai.setAdapter(plAdapter);
        }

        @Override
        public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
            super.onCreateOptionsMenu(menu, inflater);
            MenuItem iThemThucDon = menu.add(1, R.id.itThemThucDon, 1, "Thêm món");
            iThemThucDon.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        }

        @Override
        public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            int id  = item.getItemId();
            switch(id)
            {
                case R.id.itThemThucDon:
                    Intent intent = new Intent(getActivity(), ThemMon_Activity.class);
                    startActivityForResult(intent, Request_code_mon);
                    break;
            }

            return super.onOptionsItemSelected(item);
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == Request_code_mon)
            {
                if (resultCode == Activity.RESULT_OK)
                {
                    HienThiDSMon();
                }
            }
            else if(requestCode == themmon.Request_code_themhinh)
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
                        imgHinhQL.setImageURI(selectedImageUri); // Hiển thị hình ảnh đã chọn
                    } else {
                        Toast.makeText(getActivity(), "Không thể sao chép hình ảnh", Toast.LENGTH_SHORT).show();
                    }
                }
            }

        }
        private String copyImageToAppDirectory(Uri selectedImageUri) {
            try {
                // Mở luồng vào hình ảnh đã chọn
                InputStream inputStream = getActivity().getContentResolver().openInputStream(selectedImageUri);

                // Tạo file đích trong thư mục 'image' của ứng dụng
                File appDir = new File(getActivity().getFilesDir(), "image");
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