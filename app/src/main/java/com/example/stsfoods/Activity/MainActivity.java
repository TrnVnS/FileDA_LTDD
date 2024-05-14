package com.example.stsfoods.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import com.example.stsfoods.DAO.DAO_NhanVien;
import com.example.stsfoods.Database.CreateDatabase;
import com.example.stsfoods.Fragment.CapNhatTTFragment;
import com.example.stsfoods.Fragment.LapHoaDonFragment;
import com.example.stsfoods.Fragment.QLBanFragment;
import com.example.stsfoods.Fragment.QLLoaiMonFragment;
import com.example.stsfoods.Fragment.QLMonFragment;
import com.example.stsfoods.Fragment.QLNhanVienFragment;
import com.example.stsfoods.R;
import com.example.stsfoods.Fragment.ThayDoiMKFragment;
import com.example.stsfoods.Fragment.TrangChuFragment;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private TextView txtTen, txtEmail, txtQuyen;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private View header;
    private Menu menu;
    private String sTendn="";

    public String getsTendn() {
        return sTendn;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        String tendn = intent.getStringExtra("TenDN");
        sTendn = tendn;

        showInfoOnNavHeader(tendn);

        anMenuChucNang(tendn);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TrangChuFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_TrangChu);
        }



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.drawer_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_TrangChu:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TrangChuFragment()).commit();
                break;
            case R.id.nav_Thoat:
                this.finish();
                System.exit(0);
                break;
            case R.id.nav_QLMon:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new QLMonFragment()).commit();
                break;
            case R.id.nav_QLLoaiMon:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new QLLoaiMonFragment()).commit();
                break;
            case R.id.nav_QLBan:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new QLBanFragment()).commit();
                break;
            case R.id.nav_QLNhanVien:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new QLNhanVienFragment()).commit();
                break;
            case R.id.nav_ThongTin:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CapNhatTTFragment()).commit();
                break;
            case R.id.nav_ThayDoiMK:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ThayDoiMKFragment()).commit();
                break;
            case R.id.nav_DangXuat:
                Toast.makeText(this, "Đã đăng xuất", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, LogIn.class);
                startActivity(intent);
                finish();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void showInfoOnNavHeader(String tendn){
        DAO_NhanVien dao_nhanVien = new DAO_NhanVien(this);
        Cursor cursor = dao_nhanVien.getNhanVien(tendn);
        cursor.moveToFirst();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        header = navigationView.getHeaderView(0);
        txtTen = (TextView) header.findViewById(R.id.txt_tennd);
        txtEmail = (TextView) header.findViewById(R.id.txt_email);
        txtQuyen = (TextView) header.findViewById(R.id.txt_quyen);

        txtTen.setText(cursor.getString(1));
        txtEmail.setText(cursor.getString(5));
        txtQuyen.setText(cursor.getString(8));
    }

    public void anMenuChucNang(String tendn){
        DAO_NhanVien dao_nhanVien = new DAO_NhanVien(this);
        Cursor cursor = dao_nhanVien.getNhanVien(tendn);
        cursor.moveToFirst();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        menu = navigationView.getMenu();

        if(Objects.equals(cursor.getString(8), "Nhân viên")){
            menu.getItem(3).setVisible(false);
        } else if(Objects.equals(cursor.getString(8), "Quản lý")){
            menu.getItem(3).setVisible(true);
        }
    }
}