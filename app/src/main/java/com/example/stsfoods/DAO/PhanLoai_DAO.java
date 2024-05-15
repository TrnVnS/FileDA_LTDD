package com.example.stsfoods.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.stsfoods.DTO.PhanLoai_DTO;
import com.example.stsfoods.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

public class PhanLoai_DAO {
    SQLiteDatabase db;
    public PhanLoai_DAO(Context context)
    {
        CreateDatabase createDatabase = new CreateDatabase(context);
        db = createDatabase.getWritableDatabase();
    }

    public boolean ThemLoai(String tenloai)
    {
        ContentValues values = new ContentValues();
        values.put(CreateDatabase.PhanLoai_Ten, tenloai);

        long kt = db.insert(CreateDatabase.TBPhanLoai, null,values);
        if (kt != 0)
            return true;
        else
            return false;
    }
    public boolean CapNhatLoai(PhanLoai_DTO pl)
    {
        ContentValues values = new ContentValues();
        values.put(CreateDatabase.PhanLoai_Ten, pl.getTen());

        int kt = db.update(CreateDatabase.TBPhanLoai, values, CreateDatabase.PhanLoai_Ma + " = ?", new String[]{String.valueOf(pl.getMa())});

        if(kt != 0)
        {
            return true;
        }
        return false;
    }
    public boolean XoaLoai(int maloai)
    {
        String sTruyVan = "DELETE FROM " + CreateDatabase.TBPhanLoai + " WHERE " + CreateDatabase.PhanLoai_Ma + " = " + maloai;

        try {
            db.execSQL(sTruyVan);
            return true; // Trả về true nếu xóa thành công
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Trả về false nếu xóa thất bại
        }
    }

    public List<PhanLoai_DTO> DSPhanLoai()
    {
        List<PhanLoai_DTO> lst = new ArrayList<PhanLoai_DTO>();

        String sTruyVan = "SELECT * FROM " + CreateDatabase.TBPhanLoai;
        Cursor c = db.rawQuery(sTruyVan, null);
        c.moveToFirst();
        while (!c.isAfterLast())
        {
            PhanLoai_DTO pl = new PhanLoai_DTO();
            pl.setMa(c.getInt(c.getColumnIndex(CreateDatabase.PhanLoai_Ma)));
            pl.setTen(c.getString(c.getColumnIndex(CreateDatabase.PhanLoai_Ten)));

            lst.add(pl);

            c.moveToNext();
        }
        return lst;
    }
    public String LayTenUngVoiMaLoai(int maloai) {
        String tenloai = "";
        String sTruyVan = "SELECT * FROM " + CreateDatabase.TBPhanLoai + " WHERE " + CreateDatabase.PhanLoai_Ma + " = " + maloai;
        Cursor c = db.rawQuery(sTruyVan, null);
        if (c.moveToFirst()) { // Di chuyển con trỏ đến dòng đầu tiên
            tenloai = c.getString(c.getColumnIndex(CreateDatabase.PhanLoai_Ten));
        }
        c.close(); // Đóng Cursor sau khi sử dụng xong
        return tenloai;
    }
}