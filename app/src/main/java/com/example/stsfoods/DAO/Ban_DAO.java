package com.example.stsfoods.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.stsfoods.DTO.Ban_DTO;
import com.example.stsfoods.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

public class Ban_DAO {
    SQLiteDatabase db;
    public Ban_DAO(Context context)
    {
        CreateDatabase createDatabase = new CreateDatabase(context);
        db = createDatabase.getWritableDatabase();
    }

    public boolean ThemBanAn(String tenban)
    {
        ContentValues values = new ContentValues();
        values.put(CreateDatabase.Ban_Ten, tenban);
        values.put(CreateDatabase.Ban_TinhTrang, "Trống");

        long kt = db.insert(CreateDatabase.TBBan, null,values);
        if (kt != 0)
            return true;
        else
            return false;
    }

    public boolean CapNhatBan(Ban_DTO ban)
    {
        ContentValues values = new ContentValues();
        values.put(CreateDatabase.Ban_Ma, ban.getMaBan());
        values.put(CreateDatabase.Ban_Ten, ban.getTenBan());
        values.put(CreateDatabase.Ban_TinhTrang, ban.getTinhTrang());

        int kt = db.update(CreateDatabase.TBBan, values, CreateDatabase.Ban_Ma + " = ?", new String[]{String.valueOf(ban.getMaBan())});

        if(kt != 0)
        {
            return true;
        }
        return false;
    }

    public boolean XoaBan(int maban)
    {
        String sTruyVan = "DELETE FROM " + CreateDatabase.TBBan + " WHERE " + CreateDatabase.Ban_Ma + " = " + maban;

        try {
            db.execSQL(sTruyVan);
            return true; // Trả về true nếu xóa thành công
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Trả về false nếu xóa thất bại
        }
    }

    public List<Ban_DTO> LayDSBan()
    {
        List<Ban_DTO> lst = new ArrayList<Ban_DTO>();
        String sTruyVan = "SELECT * FROM " + CreateDatabase.TBBan;
        Cursor c = db.rawQuery(sTruyVan, null);
        c.moveToFirst();
        while (!c.isAfterLast())
        {
            Ban_DTO b = new Ban_DTO();
            b.setMaBan(c.getInt(c.getColumnIndex(CreateDatabase.Ban_Ma)));
            b.setTenBan(c.getString(c.getColumnIndex(CreateDatabase.Ban_Ten)));
            b.setTinhTrang(c.getString(c.getColumnIndex(CreateDatabase.Ban_TinhTrang)));
            lst.add(b);
            c.moveToNext();
        }
        return lst;
    }

    public String LayTenUngVoiMaBan(int maban)
    {
        String tenban = "";

        String sTruyVan = "SELECT * FROM " + CreateDatabase.TBBan + " WHERE " + CreateDatabase.Ban_Ma + " = " + maban;
        Cursor c = db.rawQuery(sTruyVan, null);
        if (c.moveToFirst()) { // Di chuyển con trỏ đến dòng đầu tiên
            tenban = c.getString(c.getColumnIndex(CreateDatabase.Ban_Ten));
        }
        c.close(); // Đóng Cursor sau khi sử dụng xong;

        return tenban;
    }
}