package com.example.doan_food.Admin;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.doan_food.MonAn.MonAn;
import com.example.doan_food.MonAn.MonAnAdapter;
import com.example.doan_food.MonAn.ThemMonAn;
import com.example.doan_food.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class QL_MonAn_Fragment extends Fragment {
    Button btnThem;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Food");
    ArrayList<MonAn> lst;
    RecyclerView recyclerView;
    public QL_MonAn_Fragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ql_mon_an, container, false);

        btnThem = view.findViewById(R.id.btnThemMonAn);
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), ThemMonAn.class));
            }
        });

        recyclerView = view.findViewById(R.id.list_Monan);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                lst = new ArrayList<>();
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String tenMA = ds.child("tenMonAn").getValue(String.class);
                    String giaTien = ds.child("giaTien").getValue(String.class);
                    String chuThich = ds.child("chuThich").getValue(String.class);
                    String link = ds.child("link_image").getValue(String.class);
                    MonAn _newMonAn = new MonAn(tenMA, giaTien, chuThich,link);
                    lst.add(_newMonAn);
                }
                Toast.makeText(getContext(), lst.size()+"", Toast.LENGTH_SHORT).show();
                MonAnAdapter adapter = new MonAnAdapter(getContext(),lst);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            }
            @Override
            public void onCancelled(DatabaseError error) {
            }
        });

        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int itemPosition = recyclerView.getChildLayoutPosition(view);
                String item = lst.get(itemPosition).getTenMonAn();
                Toast.makeText(getContext(), item, Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }
}