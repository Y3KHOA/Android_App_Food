package com.example.doan_food.Firebase;

import androidx.annotation.NonNull;

import com.example.doan_food.MonAn.MonAn;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class List_Firebase {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    DatabaseReference myRef = database.getReference("Like");
    public List_Firebase(){};

    public ArrayList<MonAn> lst_yeuThich()
    {
        ArrayList<MonAn> lst = new ArrayList<MonAn>();
        myRef.child(mAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                lst.clear();
                for(DataSnapshot ds : snapshot.getChildren()) {
                    String tenMA = ds.child("tenMonAn").getValue(String.class);
                    String giaTien = ds.child("giaTien").getValue(String.class);
                    String chuThich = ds.child("chuThich").getValue(String.class);
                    String link = ds.child("link_image").getValue(String.class);
                    MonAn _newMonAn = new MonAn(tenMA, giaTien, chuThich,link);
                    lst.add(_newMonAn);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        return lst;
    }
}
