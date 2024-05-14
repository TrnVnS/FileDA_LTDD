package com.example.stsfoods.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.stsfoods.Fragment.ThayDoiMKFragment;

public class CreateDatabase extends SQLiteOpenHelper {

    public static String TBNhanVien = "NhanVien";
    public static String TBMon = "Mon";
    public static String TBPhanLoai = "PhanLoai";
    public static String TBBan = "Ban";
    public static String TBHoaDon = "HoaDon";
    public static String TBChiTietBan = "ChiTietBan";

    //Table NhanVien
    public static String NhanVien_Ma = "manv";
    public static String NhanVien_HoTen = "hoten";
    public static String NhanVien_NgaySinh = "ngaysinh";
    public static String NhanVien_GioiTinh = "gioitinh";
    public static String NhanVien_Sdt = "sdt";
    public static String NhanVien_Email = "email";
    public static String NhanVien_TenDN = "tendn";
    public static String NhanVien_MatKhau = "matkhau";
    public static String NhanVien_ChucVu = "chucvu";

    //Table Mon
    public static String Mon_Ma = "mamon";
    public static String Mon_Ten = "ten";
    public static String Mon_DonGia = "dongia";
    public static String Mon_HinhAnh = "hinhanh";
    public static String Mon_MaLoai = "maloai";

    //Table PhanLoai
    public static String PhanLoai_Ma = "maloai";
    public static String PhanLoai_Ten = "ten";

    //Table Ban
    public static String Ban_Ma = "maban";
    public static String Ban_Ten = "ten";
    public static String Ban_TinhTrang = "tinhtrang";

    //Table HoaDon
    public static String HoaDon_Ma = "mahoadon";
    public static String HoaDon_Ngay = "ngaylap";
    public static String HoaDon_TinhTrang = "tinhtrang";
    public static String HoaDon_MaBan = "maban";

    //Table ChiTietBan
    public static String ChiTietBan_MaBan = "maban";
    public static String ChiTietBan_MaMon = "mamon";
    public static String ChiTietBan_SoLuong = "soluong";

    public CreateDatabase(@Nullable Context context) {
        super(context, "Ql_DAV", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // Table NhanVien
        String NhanVien = "CREATE TABLE " + TBNhanVien + "(" + NhanVien_Ma + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NhanVien_HoTen + " TEXT, "
                + NhanVien_NgaySinh + " TEXT, "
                + NhanVien_GioiTinh + " TEXT, "
                + NhanVien_Sdt + " TEXT, "
                + NhanVien_Email + " TEXT, "
                + NhanVien_TenDN + " TEXT, "
                + NhanVien_MatKhau + " TEXT, "
                + NhanVien_ChucVu + " TEXT )";

        // Table Mon
        String Mon = "CREATE TABLE " + TBMon + "(" + Mon_Ma + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Mon_Ten + " TEXT, "
                + Mon_DonGia + " TEXT, "
                + Mon_HinhAnh + " TEXT, "
                + Mon_MaLoai + " INTEGER )";

        // Table PhanLoai
        String PhanLoai = "CREATE TABLE " + TBPhanLoai + "(" + PhanLoai_Ma + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PhanLoai_Ten + " TEXT )";

        // Table Ban
        String Ban = "CREATE TABLE " + TBBan + "(" + Ban_Ma + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Ban_Ten + " TEXT, "
                + Ban_TinhTrang + " TEXT )";

        // Table HoaDon
        String HoaDon = "CREATE TABLE " + TBHoaDon + "(" + HoaDon_Ma + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + HoaDon_Ngay + " TEXT, "
                + HoaDon_TinhTrang + " TEXT, "
                + HoaDon_MaBan + " INTEGER )";

        // Table ChiTietHoaDonMon
        String ChiTietBan = "CREATE TABLE " + TBChiTietBan + "(" + ChiTietBan_MaBan + " INTEGER, "
                + ChiTietBan_MaMon + " INTEGER, "
                + ChiTietBan_SoLuong + " INTEGER, "
                + " PRIMARY KEY (" + ChiTietBan_MaMon + ", " + ChiTietBan_MaBan + "))";

        db.execSQL(NhanVien);
        db.execSQL(Mon);
        db.execSQL(PhanLoai);
        db.execSQL(Ban);
        db.execSQL(HoaDon);
        db.execSQL(ChiTietBan);

        //Insert table NhanVien
        String insertNhanVien = "INSERT INTO " + TBNhanVien + " ("
                + NhanVien_HoTen + ", "
                + NhanVien_NgaySinh + ", "
                + NhanVien_GioiTinh + ", "
                + NhanVien_Sdt + ", "
                + NhanVien_Email + ", "
                + NhanVien_TenDN + ", "
                + NhanVien_MatKhau + ", "
                + NhanVien_ChucVu + ") VALUES " +
                "('Trần Văn Sĩ', '12/11/2003', 'Nam', '0384294495', 'sitrnvn@gmail.com', 'tvs', '123', 'Quản lý')," +
                "('Nguyễn Tấn Tài', '23/05/2003', 'Nam', '1234567890', 'tai@gmail.com', 'ntt', '123', 'Quản lý');";
        db.execSQL(insertNhanVien);

        //Insert table PhanLoai
        String insertPhanLoai = "INSERT INTO " + TBPhanLoai + " ("
                + PhanLoai_Ten + ") VALUES " +
                "('Xào'), ('Chiên'), ('Nước'), ('Hâp');";
        db.execSQL(insertPhanLoai);

        //Insert table Ban
        String insertBan = "INSERT INTO " + TBBan + " ("
                + Ban_Ten + ", "
                + Ban_TinhTrang + ") VALUES " +
                "('Bàn 1', 'Trống'), ('Bàn 2', 'Trống');";
        db.execSQL(insertBan);

        //Insert table Mon
        String insertMon = "INSERT INTO " + TBMon + " ("
                + Mon_Ten + ", "
                + Mon_DonGia + ", "
                + Mon_HinhAnh + ", "
                + Mon_MaLoai + ") VALUES " +
                "('Phở', '30000', '', 2), ('Cà phê', '20000', '', 2);";
        db.execSQL(insertMon);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String NhanVien = "DROP TABLE IF EXISTS "+TBNhanVien;
        String Mon = "DROP TABLE IF EXISTS "+TBMon;
        String PhanLoai = "DROP TABLE IF EXISTS "+TBPhanLoai;
        String Ban = "DROP TABLE IF EXISTS "+TBBan;
        String HoaDon = "DROP TABLE IF EXISTS "+TBHoaDon;
        String ChiTietBan = "DROP TABLE IF EXISTS "+TBChiTietBan;

        db.execSQL(NhanVien);
        db.execSQL(Mon);
        db.execSQL(PhanLoai);
        db.execSQL(Ban);
        db.execSQL(HoaDon);
        db.execSQL(ChiTietBan);

        onCreate(db);
    }

    public SQLiteDatabase openWriteable(){
        return this.getWritableDatabase();
    }
}
