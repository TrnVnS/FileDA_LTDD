package com.example.stsfoods.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.stsfoods.Activity.MainActivity;
import com.example.stsfoods.DAO.DAO_NhanVien;
import com.example.stsfoods.Database.CreateDatabase;
import com.example.stsfoods.R;

public class ThayDoiMKFragment extends Fragment {

    EditText edtMKcu, edtMKmoi, edtXNMK;
    Button btnThayDoi;

    View view;
    MainActivity m;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_thaydoimk, container, false);

        edtMKcu = (EditText) view.findViewById(R.id.edt_MKCu);
        edtMKmoi = (EditText) view.findViewById(R.id.edt_MKMoi);
        edtXNMK = (EditText) view.findViewById(R.id.edt_XNMK);
        btnThayDoi = (Button) view.findViewById(R.id.btn_ThayDoi);

        m = (MainActivity) getActivity();

        DAO_NhanVien nv = new DAO_NhanVien(getActivity());

        btnThayDoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mkcu = edtMKcu.getText().toString();
                String mkmoi = edtMKmoi.getText().toString();
                String xnmk = edtXNMK.getText().toString();

                if(mkcu.isEmpty()||mkmoi.isEmpty()||xnmk.isEmpty()){
                    Toast.makeText(getActivity(), "Chưa nhập đủ thông tin.", Toast.LENGTH_SHORT).show();
                } else if(!nv.KiemTraDN(m.getsTendn(), mkcu)){
                    Toast.makeText(getActivity(), "Mật khẩu cũ không chính xác.", Toast.LENGTH_SHORT).show();
                } else if (!mkmoi.equals(xnmk)){
                    Toast.makeText(getActivity(), "Mật khẩu xác nhận không trùng khớp.", Toast.LENGTH_SHORT).show();
                } else {
                    boolean kt = nv.updateMatKhau(m.getsTendn(), mkmoi);
                    if(kt)
                        Toast.makeText(getActivity(), "Thay đổi mật khẩu thành công.", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getActivity(), "Thay đổi mật khẩu không thành công.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        // Inflate the layout for this fragment
        return view;
    }
}