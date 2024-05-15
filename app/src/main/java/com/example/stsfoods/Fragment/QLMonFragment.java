    package com.example.stsfoods.Fragment;

    import android.app.Activity;
    import android.app.AlertDialog;
    import android.app.Application;
    import android.content.ContentResolver;
    import android.content.DialogInterface;
    import android.content.Intent;
    import android.graphics.Bitmap;
    import android.graphics.BitmapFactory;
    import android.net.Uri;
    import android.os.Bundle;

    import androidx.annotation.NonNull;
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
    import android.widget.GridView;
    import android.widget.ImageView;
    import android.widget.ListView;
    import android.widget.Spinner;
    import android.widget.Toast;

    import com.example.stsfoods.Adapter.Mon_Adapter;
    import com.example.stsfoods.Adapter.PhanLoaiAdapter;
    import com.example.stsfoods.DAO.ChiTietBan_DAO;
    import com.example.stsfoods.DAO.MonDAO;
    import com.example.stsfoods.DAO.PhanLoaiDAO;
    import com.example.stsfoods.DTO.ChiTietBan_DTO;
    import com.example.stsfoods.DTO.MonDTO;
    import com.example.stsfoods.DTO.PhanLoaiDTO;
    import com.example.stsfoods.R;

    import java.io.InputStream;
    import java.util.List;

    public class QLMonFragment extends Fragment {

        ImageView imgHinhQL;
        ListView lstQLMon;
        EditText edtTenMon, edtDonGia;
        Spinner spinLoai;
        Button btnLuu, btnHuy;

        MonDTO mDTO;
        List<MonDTO> lst;
        MonDAO mDAO;
        Mon_Adapter mAdapter;
        PhanLoaiDAO plDAO;
        PhanLoaiAdapter plAdapter;

        ChiTietBan_DAO ctDAO;

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



            mDAO = new MonDAO(getActivity());
            plDAO = new PhanLoaiDAO(getActivity());
            ctDAO = new ChiTietBan_DAO(getActivity());

            HienThiDSMon();
            HienThiDanhSachLoaiMon();

            lstQLMon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    MonDTO selectedMon = lst.get(position);
                    edtTenMon.setText(selectedMon.getTenmon());
                    edtDonGia.setText(selectedMon.getDongia());

                    imgHinhQL.setImageURI(Uri.parse(selectedMon.getHinhanh()));

                    //Lấy mã
                    mamonchon = selectedMon.getMamon();

                    // Lấy danh sách loại món
                    List<PhanLoaiDTO> danhSachLoaiMon = plDAO.DSPhanLoai();

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
                            MonDTO selectedMon = lst.get(position);
                            //Lấy mã món
                            int mamon = selectedMon.getMamon();
                            // Gọi phương thức xóa món từ lớp DAO
                            boolean xoaThanhCong = mDAO.XoaMon(mamon);
                            // Kiểm tra kết quả xóa và thông báo cho người dùng
                            if (xoaThanhCong) {
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
                    PhanLoaiDTO plDTOmoi = (PhanLoaiDTO) spinLoai.getSelectedItem();

                    //Kiểm tra
                    if(txtTenMon.equals("") || txtDonGia.equals(""))
                    {
                        Toast.makeText(getActivity(), "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // Tạo đối tượng MonDTO mới với thông tin đã cập nhật
                    MonDTO monMoi = new MonDTO();
                    monMoi.setMamon(mamonchon);
                    monMoi.setTenmon(txtTenMon);
                    monMoi.setDongia(txtDonGia);
                    monMoi.setMaloai(plDTOmoi.getMa());

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
            List<PhanLoaiDTO> danhSachLoaiMon = plDAO.DSPhanLoai();
            plAdapter = new PhanLoaiAdapter(getActivity(), R.layout.spinner_loaithucdon, danhSachLoaiMon);
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
                    Intent intent = new Intent(getActivity(), com.example.stsfoods.Activity.ThemThucDonActivity.class);
                    startActivity(intent);
                    break;
            }

            return super.onOptionsItemSelected(item);
        }


    }