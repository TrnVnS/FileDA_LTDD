package com.example.stsfoods.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.ContextMenu;

import com.example.stsfoods.DTO.MonDTO;
import com.example.stsfoods.DTO.PhanLoaiDTO;
import com.example.stsfoods.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

public class MonDAO {

    SQLiteDatabase db;
    public MonDAO (Context context)
    {
        CreateDatabase createDatabase = new CreateDatabase(context);
        db = createDatabase.getWritableDatabase();
    }

    public boolean ThemMon(MonDTO mon)
    {
        ContentValues values = new ContentValues();
        values.put(CreateDatabase.Mon_Ten, mon.getTenmon());
        values.put(CreateDatabase.Mon_DonGia, mon.getDongia());
        values.put(CreateDatabase.Mon_MaLoai, mon.getMaloai());
        values.put(CreateDatabase.Mon_HinhAnh, mon.getHinhanh());

        long kt = db.insert(CreateDatabase.TBMon, null, values);

        if(kt != 0)
        {
            return true;
        }

        return false;
    }

    public boolean CapNhatMon(MonDTO mon)
    {

        ContentValues values = new ContentValues();
        values.put(CreateDatabase.Mon_Ten, mon.getTenmon());
        values.put(CreateDatabase.Mon_DonGia, mon.getDongia());
        values.put(CreateDatabase.Mon_MaLoai, mon.getMaloai());

        int kt = db.update(CreateDatabase.TBMon, values, CreateDatabase.Mon_Ma + " = ?", new String[]{String.valueOf(mon.getMamon())});

        if(kt != 0)
        {
            return true;
        }
        return false;
    }

    public boolean XoaMon(int mamon)
    {
        String sTruyVan = "DELETE FROM " + CreateDatabase.TBMon + " WHERE " + CreateDatabase.Mon_Ma + " = " + mamon;

        try {
            db.execSQL(sTruyVan);
            return true; // Trả về true nếu xóa thành công
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Trả về false nếu xóa thất bại
        }
    }

    public boolean XoaMonTheoMaLoai(int maloai)
    {
        String sTruyVan = "DELETE FROM " + CreateDatabase.TBMon + " WHERE " + CreateDatabase.Mon_MaLoai + " = " + maloai;

        try {
            db.execSQL(sTruyVan);
            return true; // Trả về true nếu xóa thành công
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Trả về false nếu xóa thất bại
        }
    }

    public List<MonDTO> DSMon()
    {
        List<MonDTO> lst = new ArrayList<MonDTO>();

        String sTruyVan = "SELECT * FROM " + CreateDatabase.TBMon;
        Cursor c = db.rawQuery(sTruyVan, null);
        c.moveToFirst();
        while (!c.isAfterLast())
        {
            MonDTO m = new MonDTO();
            m.setMamon(c.getInt(c.getColumnIndex(CreateDatabase.Mon_Ma)));
            m.setTenmon(c.getString(c.getColumnIndex(CreateDatabase.Mon_Ten)));
            m.setDongia(c.getString(c.getColumnIndex(CreateDatabase.Mon_DonGia)));
            m.setMaloai(c.getInt(c.getColumnIndex(CreateDatabase.Mon_MaLoai)));
            m.setHinhanh(c.getString(c.getColumnIndex(CreateDatabase.Mon_HinhAnh)));

            lst.add(m);

            c.moveToNext();
        }
        return lst;
    }

    public List<MonDTO> DSTenMon() {
        List<MonDTO> lstMon = new ArrayList<>();

        String sTruyVan = "SELECT * FROM " + CreateDatabase.TBMon;
        Cursor c = db.rawQuery(sTruyVan, null);
        if (c.moveToFirst()) {
            do {
                MonDTO mon = new MonDTO();
                mon.setMamon(c.getInt(c.getColumnIndex(CreateDatabase.Mon_Ma)));
                mon.setTenmon(c.getString(c.getColumnIndex(CreateDatabase.Mon_Ten)));
                lstMon.add(mon);
            } while (c.moveToNext());
        }
        c.close(); // Đóng Cursor sau khi sử dụng xong

        return lstMon;
    }

    public String LayDonGiaUngVoiMaMon(int mamon)
    {
        String dongia = "0";

        String sTruyVan = "SELECT * FROM " + CreateDatabase.TBMon + " WHERE " + CreateDatabase.Mon_Ma + " = " + mamon;
        Cursor c = db.rawQuery(sTruyVan, null);
        if (c.moveToFirst()) { // Di chuyển con trỏ đến dòng đầu tiên
            dongia = c.getString(c.getColumnIndex(CreateDatabase.Mon_DonGia));
        }
        c.close(); // Đóng Cursor sau khi sử dụng xong;

        return dongia;
    }

    public String LayDuongDanUngVoiMaMon(int mamon)
    {
        String duongdan = "";

        String sTruyVan = "SELECT * FROM " + CreateDatabase.TBMon + " WHERE " + CreateDatabase.Mon_Ma + " = " + mamon;
        Cursor c = db.rawQuery(sTruyVan, null);
        if (c.moveToFirst()) { // Di chuyển con trỏ đến dòng đầu tiên
            duongdan = c.getString(c.getColumnIndex(CreateDatabase.Mon_HinhAnh));
        }
        c.close(); // Đóng Cursor sau khi sử dụng xong;

        return duongdan;
    }

    public String LayTenMonUngVoiMaMon(int mamon)
    {
        String tenmon = "";

        String sTruyVan = "SELECT * FROM " + CreateDatabase.TBMon + " WHERE " + CreateDatabase.Mon_Ma + " = " + mamon;
        Cursor c = db.rawQuery(sTruyVan, null);
        if (c.moveToFirst()) { // Di chuyển con trỏ đến dòng đầu tiên
            tenmon = c.getString(c.getColumnIndex(CreateDatabase.Mon_Ten));
        }
        c.close(); // Đóng Cursor sau khi sử dụng xong;

        return tenmon;
    }

}