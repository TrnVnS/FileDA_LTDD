package com.example.stsfoods.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.stsfoods.DTO.ChiTietBan_DTO;
import com.example.stsfoods.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

public class ChiTietBan_DAO {

    SQLiteDatabase db;
    public ChiTietBan_DAO(Context context)
    {
        CreateDatabase createDatabase = new CreateDatabase(context);
        db = createDatabase.getWritableDatabase();
    }

    public boolean ThemChiTietBan(ChiTietBan_DTO ct)
    {
        ContentValues values = new ContentValues();
        values.put(CreateDatabase.ChiTietBan_MaBan, ct.getMaban());
        values.put(CreateDatabase.ChiTietBan_MaMon, ct.getMamon());
        values.put(CreateDatabase.ChiTietBan_SoLuong, ct.getSoluong());

        long kt = db.insert(CreateDatabase.TBChiTietBan, null, values);

        if(kt != 0)
        {
            return true;
        }

        return false;
    }

    public boolean CapNhatChiTietBan(ChiTietBan_DTO ct)
    {

        ContentValues values = new ContentValues();

        values.put(CreateDatabase.ChiTietBan_MaMon, ct.getMamon());
        values.put(CreateDatabase.ChiTietBan_SoLuong, ct.getSoluong());

        int kt = db.update(CreateDatabase.TBChiTietBan, values, CreateDatabase.ChiTietBan_MaBan + " = ?", new String[]{String.valueOf(ct.getMaban())});

        if(kt != 0)
        {
            return true;
        }
        return false;
    }

    public boolean XoaCTBanThemMaMon(int mamon)
    {
        String sTruyVan = "DELETE FROM " + CreateDatabase.TBChiTietBan + " WHERE " + CreateDatabase.ChiTietBan_MaMon + " = " + mamon;

        try {
            db.execSQL(sTruyVan);
            return true; // Trả về true nếu xóa thành công
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Trả về false nếu xóa thất bại
        }
    }

    public boolean XoaCTBanTheoMaBan(int maban)
    {
        String sTruyVan = "DELETE FROM " + CreateDatabase.TBChiTietBan + " WHERE " + CreateDatabase.ChiTietBan_MaBan + " = " + maban;

        try {
            db.execSQL(sTruyVan);
            return true; // Trả về true nếu xóa thành công
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Trả về false nếu xóa thất bại
        }
    }

    public List<ChiTietBan_DTO> DSBan(int maban)
    {
        List<ChiTietBan_DTO> lst = new ArrayList<ChiTietBan_DTO>();

        String sTruyVan = "SELECT * FROM " + CreateDatabase.TBChiTietBan + " WHERE " + CreateDatabase.ChiTietBan_MaBan + " = " + maban;
        Cursor c = db.rawQuery(sTruyVan, null);
        c.moveToFirst();
        while (!c.isAfterLast())
        {
            ChiTietBan_DTO ct = new ChiTietBan_DTO();
            ct.setMamon(c.getInt(c.getColumnIndex(CreateDatabase.ChiTietBan_MaBan)));
            ct.setMamon(c.getInt(c.getColumnIndex(CreateDatabase.ChiTietBan_MaMon)));
            ct.setSoluong(c.getInt(c.getColumnIndex(CreateDatabase.ChiTietBan_SoLuong)));

            lst.add(ct);

            c.moveToNext();
        }
        return lst;
    }
}
