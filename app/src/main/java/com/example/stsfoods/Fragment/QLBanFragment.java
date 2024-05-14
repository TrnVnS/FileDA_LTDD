package com.example.stsfoods.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stsfoods.Activity.ChiTietBan_Activity;
import com.example.stsfoods.Activity.ThemBanAn_Activity;
import com.example.stsfoods.Adapter.BanAdapter;
import com.example.stsfoods.DAO.BanAnDAO;
import com.example.stsfoods.DAO.ChiTietBan_DAO;
import com.example.stsfoods.DTO.BanAnDTO;
import com.example.stsfoods.DTO.MonDTO;

import com.example.stsfoods.R;

import java.util.List;

public class QLBanFragment extends Fragment {

    Button btnLuuBan, btnHuyBan, btnXemCTBan;
    EditText edtTenBan;
    Spinner spinTinhTrangBan;
    public static int Request_code_them = 111;
    GridView gvBan;
    List<BanAnDTO> lst;
    BanAnDAO bDAO;
    BanAdapter bAdapter;
    int mabanchon=-1;

    ChiTietBan_DAO ctDAO;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_qlban, container, false);
        setHasOptionsMenu(true);

        gvBan = (GridView) view.findViewById(R.id.gvBan);
        btnLuuBan = (Button) view.findViewById(R.id.btnLuuBan);
        btnHuyBan = (Button) view.findViewById(R.id.btnHuyBan);
        btnXemCTBan = (Button) view.findViewById(R.id.btnXemCTBan);
        edtTenBan = (EditText) view.findViewById(R.id.edtTenBan);
        spinTinhTrangBan = (Spinner) view.findViewById(R.id.spnTinhTrangBan);

        ctDAO = new ChiTietBan_DAO(getActivity());
        bDAO = new BanAnDAO(getActivity());
        HienThiBanAdapter();


        gvBan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BanAnDTO selectedBan = lst.get(position);
                edtTenBan.setText(selectedBan.getTenBan());

                //Lấy mã bàn
                mabanchon = selectedBan.getMaBan();

                // Lấy tình trạng của bàn được chọn
                String tinhTrang = selectedBan.getTinhTrang();

                // Lấy danh sách tình trạng từ Spinner
                String[] tinhTrangArray = getResources().getStringArray(R.array.choices);

                // Tìm vị trí của tình trạng trong Spinner
                int spinnerPosition = -1;
                for (int i = 0; i < tinhTrangArray.length; i++) {
                    if (tinhTrangArray[i].equals(tinhTrang)) {
                        spinnerPosition = i;
                        break;
                    }
                }

                // Nếu tìm thấy tình trạng trong Spinner, thiết lập lại giá trị cho Spinner
                if (spinnerPosition != -1) {
                    spinTinhTrangBan.setSelection(spinnerPosition);
                }

            }
        });

        gvBan.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Xác nhận");
                builder.setMessage("Bạn có chắc muốn xóa?");
                builder.setCancelable(true);
                builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Lấy đối tượng BanTDO tương ứng với vị trí trong danh sách
                        BanAnDTO selectedBan = lst.get(position);
                        //Lấy mã bàn
                        int maban = selectedBan.getMaBan();
                        // Gọi phương thức xóa món từ lớp DAO
                        boolean delete = bDAO.XoaBan(maban);
                        // Kiểm tra kết quả xóa và thông báo cho người dùng
                        if (delete) {
                            // Cập nhật lại danh sách món và giao diện
                            HienThiBanAdapter();
                            Toast.makeText(getActivity(), "Đã xóa bàn.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "Xóa bàn không thành công.", Toast.LENGTH_SHORT).show();
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

        btnLuuBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtTenBan = edtTenBan.getText().toString();
                int selectedPosition = spinTinhTrangBan.getSelectedItemPosition();

                // Lấy giá trị tình trạng bàn từ mảng
                String[] tinhTrangArray = getResources().getStringArray(R.array.choices);
                String selectedTinhTrang = tinhTrangArray[selectedPosition];

                //Kiểm tra
                if(txtTenBan.equals(""))
                {
                    Toast.makeText(getActivity(), "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Tạo đối tượng MonDTO mới với thông tin đã cập nhật
                BanAnDTO banMoi = new BanAnDTO();
                banMoi.setMaBan(mabanchon);
                banMoi.setTenBan(txtTenBan);
                banMoi.setTinhTrang(selectedTinhTrang);

                //Gọi phương thức cập nhật
                boolean updateBan = bDAO.CapNhatBan(banMoi);

                // Kiểm tra kết quả cập nhật
                if (updateBan) {
                    // Cập nhật lại bàn và giao diện
                    HienThiBanAdapter();
                    Toast.makeText(getActivity(), "Cập nhật bàn thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Cập nhật món thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnHuyBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtTenBan.setText("");
                Toast.makeText(getActivity(), "Đã huỷ", Toast.LENGTH_SHORT).show();
            }
        });
        btnXemCTBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mabanchon == - 1)
                {
                    Toast.makeText(getActivity(), "Vui lòng chọn bàn", Toast.LENGTH_SHORT).show();
                }
                else {
                    // Tạo Intent để chuyển sang HoaDon_Activity
                    Intent intent = new Intent(getActivity(), ChiTietBan_Activity.class);

                    // Gửi mã bàn qua Intent
                    intent.putExtra("maBan", mabanchon);

                    // Khởi chạy HoaDon_Activity
                    startActivity(intent);
                }
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem iThemBan = menu.add(1, R.id.itThemBan, 1, "Thêm bàn ăn");
        iThemBan.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id)
        {
            case R.id.itThemBan:
                Intent intent = new Intent(getActivity(), ThemBanAn_Activity.class);
                startActivityForResult(intent, Request_code_them);
                break;
        }

        return true;
    }

    private void HienThiBanAdapter()
    {
        lst = bDAO.LayDSBan();

        bAdapter = new BanAdapter(getActivity(), R.layout.gridview_banan, lst);
        gvBan.setAdapter(bAdapter);
        bAdapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Request_code_them) {
            if (resultCode == Activity.RESULT_OK && data != null) { // Kiểm tra data khác null trước khi sử dụng
                boolean kt = data.getBooleanExtra("ketquathem", false);
                if (kt) {
                    HienThiBanAdapter();
                    Toast.makeText(getActivity(), "Thêm bàn thành công", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Toast.makeText(getActivity(), "Thêm bàn không thành công", Toast.LENGTH_SHORT).show();
            }
        }
    }
}