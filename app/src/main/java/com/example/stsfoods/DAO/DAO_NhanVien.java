package com.example.stsfoods.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.stsfoods.DTO.DTO_NhanVien;
import com.example.stsfoods.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

public class DAO_NhanVien {

    SQLiteDatabase db;

    public DAO_NhanVien(Context context) {
        CreateDatabase createDatabase = new CreateDatabase(context);
        db = createDatabase.openWriteable();
    }
    public Cursor getNhanVien(String tendn){
        String sTruyVan = "SELECT * FROM " + CreateDatabase.TBNhanVien
                + " WHERE " + CreateDatabase.NhanVien_TenDN + " = '" + tendn + "'";

        Cursor c = db.rawQuery(sTruyVan, null);
        return  c;
    }
    public List<DTO_NhanVien> getAllNhanVien(){

        List<DTO_NhanVien> lst_nv = new ArrayList<DTO_NhanVien>();
        String sQuery = "SELECT * FROM "+CreateDatabase.TBNhanVien;
        Cursor cursor = db.rawQuery(sQuery, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            DTO_NhanVien nv = new DTO_NhanVien();
            nv.setMaNV(cursor.getInt(cursor.getColumnIndex(CreateDatabase.NhanVien_Ma)));
            nv.setHoTen(cursor.getString(cursor.getColumnIndex(CreateDatabase.NhanVien_HoTen)));
            nv.setNgaySinh(cursor.getString(cursor.getColumnIndex(CreateDatabase.NhanVien_NgaySinh)));
            nv.setGioiTinh(cursor.getString(cursor.getColumnIndex(CreateDatabase.NhanVien_GioiTinh)));
            nv.setsDT(cursor.getString(cursor.getColumnIndex(CreateDatabase.NhanVien_Sdt)));
            nv.setEmail(cursor.getString(cursor.getColumnIndex(CreateDatabase.NhanVien_Email)));
            nv.setTenDangNhap(cursor.getString(cursor.getColumnIndex(CreateDatabase.NhanVien_TenDN)));
            nv.setChucVu(cursor.getString(cursor.getColumnIndex(CreateDatabase.NhanVien_ChucVu)));

            lst_nv.add(nv);
            cursor.moveToNext();
        }
        return lst_nv;
    }

    public boolean addNhanVien(DTO_NhanVien nv){
        ContentValues values = new ContentValues();
        values.put(CreateDatabase.NhanVien_HoTen, nv.getHoTen());
        values.put(CreateDatabase.NhanVien_NgaySinh, nv.getNgaySinh());
        values.put(CreateDatabase.NhanVien_GioiTinh, nv.getGioiTinh());
        values.put(CreateDatabase.NhanVien_Sdt, nv.getsDT());
        values.put(CreateDatabase.NhanVien_Email, nv.getEmail());
        values.put(CreateDatabase.NhanVien_TenDN, nv.getTenDangNhap());
        values.put(CreateDatabase.NhanVien_MatKhau, nv.getMatKhau());
        values.put(CreateDatabase.NhanVien_ChucVu, nv.getChucVu());

        long kt = db.insert(CreateDatabase.TBNhanVien, null, values);
        if(kt==-1)
            return false;
        else
            return true;
    }

    public boolean updateNhanVien(DTO_NhanVien nv){
        ContentValues values = new ContentValues();
        values.put(CreateDatabase.NhanVien_HoTen, nv.getHoTen());
        values.put(CreateDatabase.NhanVien_NgaySinh, nv.getNgaySinh());
        values.put(CreateDatabase.NhanVien_GioiTinh, nv.getGioiTinh());
        values.put(CreateDatabase.NhanVien_Sdt, nv.getsDT());
        values.put(CreateDatabase.NhanVien_Email, nv.getEmail());
        values.put(CreateDatabase.NhanVien_ChucVu, nv.getChucVu());

        long kt = db.update(CreateDatabase.TBNhanVien, values,CreateDatabase.NhanVien_Ma+"=?", new String[]{String.valueOf(nv.getMaNV())});
        if(kt==-1)
            return false;
        else
            return true;
    }

    public boolean deleteNhanVien(int manv){
        long kt = db.delete(CreateDatabase.TBNhanVien, CreateDatabase.NhanVien_Ma+"=?", new String[]{String.valueOf(manv)});
        if(kt==-1)
            return false;
        else
            return true;
    }
    public boolean updateTTTK(DTO_NhanVien nv){
        ContentValues values = new ContentValues();
        values.put(CreateDatabase.NhanVien_HoTen, nv.getHoTen());
        values.put(CreateDatabase.NhanVien_NgaySinh, nv.getNgaySinh());
        values.put(CreateDatabase.NhanVien_GioiTinh, nv.getGioiTinh());
        values.put(CreateDatabase.NhanVien_Sdt, nv.getsDT());
        values.put(CreateDatabase.NhanVien_Email, nv.getEmail());

        long kt = db.update(CreateDatabase.TBNhanVien, values,CreateDatabase.NhanVien_Ma+"=?", new String[]{String.valueOf(nv.getMaNV())});
        if(kt==-1)
            return false;
        else
            return true;
    }

    public boolean updateMatKhau(String tendn, String mk){
        ContentValues values = new ContentValues();
        values.put(CreateDatabase.NhanVien_MatKhau, mk);
        long kt = db.update(CreateDatabase.TBNhanVien, values,CreateDatabase.NhanVien_TenDN+"=?", new String[]{String.valueOf(tendn)});
        if(kt==-1)
            return false;
        else
            return true;
    }

    public boolean KiemTraDN(String tendn, String mk)
    {
        String sTruyVan = "SELECT * FROM " + CreateDatabase.TBNhanVien
                + " WHERE " + CreateDatabase.NhanVien_TenDN + " = '" + tendn
                + "' AND " + CreateDatabase.NhanVien_MatKhau + " = '" + mk + "'";

        Cursor c = db.rawQuery(sTruyVan, null);
        if (c.getCount() != 0)
            return true;
        else
            return false;
    }

    public boolean ktraNguoiDung(String tendn) {
        String sTruyVan = "SELECT * FROM " + CreateDatabase.TBNhanVien
                + " WHERE " + CreateDatabase.NhanVien_TenDN + " = '" + tendn + "'";

        Cursor c = db.rawQuery(sTruyVan, null);
        if (c.getCount() != 0)
            return true;
        else
            return false;
    }
}
