package com.example.doan_food.User;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan_food.Home_Fragment;
import com.example.doan_food.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class GioHang_Fragment extends Fragment {
    RecyclerView lst_Giohang;
    Button btnThanhToan,btnMuaTiep;
    String thongTin;
    SQL sql;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    public GioHang_Fragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gio_hang, container, false);
        sql = new SQL(view.getContext(),"data_Food.db");
        btnThanhToan = view.findViewById(R.id.btnThanhToan);
        btnMuaTiep = view.findViewById(R.id.btnMuaTiep);
        lst_Giohang = view.findViewById(R.id.listGioHang);

        btnMuaTiep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                Fragment fragment = new Home_Fragment();
                transaction.replace(R.id.noiDung, fragment);
                transaction.commit();
            }
        });

        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getContext(),ThanhToanActivity.class),1);
            }
        });

        ArrayList<HoaDon> lst = sql.getHoaDon(mAuth.getCurrentUser().getUid());
        GioHangAdapter adapter = new GioHangAdapter(getContext(),lst);
        lst_Giohang.setAdapter(adapter);
        lst_Giohang.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    public int TongTien(ArrayList<HoaDon> lst)
    {
        int tong = 0;
        for (int i = 0; i < lst.size(); i++) {
            tong += Integer.parseInt(lst.get(i).SL) * Integer.parseInt(lst.get(i).getMonAn().getGiaTien());
        }
        return tong;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK)
        {
            if (requestCode == 1 && data != null)
            {
                thongTin = data.getStringExtra("ThongTin");
                if(thongTin.equals("Đã thanh toan"))
                {
                    sql.deleteAllHD(mAuth.getCurrentUser().getUid());
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    Fragment fragment = new Home_Fragment();
                    transaction.replace(R.id.noiDung, fragment).commit();
                }
            }
        }
    }
}