package com.example.doan_food;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.doan_food.Admin.QL_DatHang_Fragment;
import com.example.doan_food.Admin.QL_MonAn_Fragment;
import com.example.doan_food.Admin.QL_ThongKe_Fragment;
import com.example.doan_food.Login.DangNhap;
import com.example.doan_food.Login.User;
import com.example.doan_food.User.GioHang_Fragment;
import com.example.doan_food.User.YeuThich_Fragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    FragmentTransaction transaction;
    User tk = null;
    NavigationView navigationView;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        navigationView = (NavigationView) findViewById(R.id.navi);
        SetNaviGation();
        Intent intent=getIntent();
        tk = (User) intent.getSerializableExtra("TK");
        if(tk!=null)
        {
            if(tk.gettenND().equals("admin"))
            {
                navigationView.getMenu().clear();
                navigationView.removeHeaderView(navigationView.getHeaderView(0));
                navigationView.inflateHeaderView(R.layout.nav_header_main_admin);
                navigationView.inflateMenu(R.menu.menu_admin);
            }
            else
            {
                MenuItem item = navigationView.getMenu().getItem(4);
                item.setTitle("Đăng xuất");
                SetName(tk.gettenND());
            }
        }

        transaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment_home = new Home_Fragment();
        transaction.replace(R.id.noiDung, fragment_home);
        transaction.commit();
    }

    public void SetName(String name)
    {
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.textName);
        navUsername.setText(name);
    }

    public void SetNaviGation()
    {
        final Toolbar toolbar=findViewById(R.id.toolbar_drawer);
        setSupportActionBar(toolbar);
        drawerLayout=findViewById(R.id.layoutChinh);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this, drawerLayout,toolbar,R.string.mo,R.string.dong);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                if(id==R.id.nav_DangNhap) {
                    if(tk==null) startActivity(new Intent(getApplicationContext(), DangNhap.class));
                    else
                    {
                        Intent dn = new Intent(MainActivity.this, MainActivity.class);
                        dn.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        Toast.makeText(MainActivity.this, "Đã đăng xuất", Toast.LENGTH_SHORT).show();
                        startActivity(dn);
                    }
                    drawerLayout.closeDrawer(GravityCompat.START);
                    return true;
                }

                if(id==R.id.nav_DangXuat_admin) {
                    Intent dn = new Intent(MainActivity.this, MainActivity.class);
                    dn.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    Toast.makeText(MainActivity.this, "Đã đăng xuất", Toast.LENGTH_SHORT).show();
                    startActivity(dn);
                    drawerLayout.closeDrawer(GravityCompat.START);
                    return true;
                }

                if(id == R.id.nav_home || id==R.id.nav_home_admin)
                {
                    Fragment fragment = new Home_Fragment();
                    transaction.replace(R.id.noiDung, fragment);
                }
                if(id == R.id.nav_quanLyDH)
                {
                    Fragment fragment= new QL_DatHang_Fragment(tk);
                    transaction.replace(R.id.noiDung, fragment);
                }
                if(id == R.id.nav_quanLyMA)
                {
                    Fragment fragment= new QL_MonAn_Fragment();
                    transaction.replace(R.id.noiDung, fragment);
                }
                if(id == R.id.nav_thongKe)
                {
                    Fragment fragment= new QL_ThongKe_Fragment();
                    transaction.replace(R.id.noiDung, fragment);
                }

                if(id == R.id.nav_gioHang)
                {
                    if(tk==null) startActivity(new Intent(getApplicationContext(), DangNhap.class));
                    else {
                        Fragment fragment = new GioHang_Fragment();
                        transaction.replace(R.id.noiDung, fragment);
                    }
                }
                    if(id == R.id.nav_yeuThich)
                {
                    if(tk==null) startActivity(new Intent(getApplicationContext(), DangNhap.class));
                    else {
                    Fragment fragment = new YeuThich_Fragment();
                    transaction.replace(R.id.noiDung, fragment);
                    }
                }
                    if(id == R.id.nav_DonHang)
                {
                    if(tk==null) startActivity(new Intent(getApplicationContext(), DangNhap.class));
                    else {
                        Fragment fragment = new QL_DatHang_Fragment(tk);
                        transaction.replace(R.id.noiDung, fragment);
                    }
                }
                transaction.commit();
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }
}