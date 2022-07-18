package com.example.doan_food.Admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan_food.HoaDonDatHang.AdapterDatHang;
import com.example.doan_food.Login.User;
import com.example.doan_food.R;
import com.example.doan_food.User.DatHang;
import com.example.doan_food.User.HoaDon;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class QL_DatHang_Fragment extends Fragment {
    public QL_DatHang_Fragment(User tk) {
        this.tk = tk;
    }
    User tk;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("datHang");
    RecyclerView viewDH;
    ArrayList<DatHang> lst;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_ql_dathang, container, false);
        GenericTypeIndicator<ArrayList<HoaDon>> t = new GenericTypeIndicator<ArrayList<HoaDon>>(){};
        lst = new ArrayList<DatHang>();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                lst.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    ArrayList<HoaDon> lstHD = ds.child("hd").getValue(t);
                    String ngDat = ds.child("ngDat").getValue(String.class);
                    String diaChi = ds.child("diaChi").getValue(String.class);
                    String ngayDat = ds.child("ngayDat").getValue(String.class);
                    String thanhToan = ds.child("thanhToan").getValue(String.class);
                    String tongTien = ds.child("tongTien").getValue(String.class);
                    DatHang _newDH = new DatHang(lstHD, ngayDat, ngDat, tongTien, thanhToan, diaChi);
                    if(tk.gettenND().equals("admin")) lst.add(_newDH);
                    else if(mAuth.getCurrentUser().getUid().equals(ngDat)) lst.add(_newDH);
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
            }
        });

        viewDH = view.findViewById(R.id.listdonhang);
        AdapterDatHang adapter = new AdapterDatHang(getContext(),lst);
        viewDH.setAdapter(adapter);
        viewDH.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }
}