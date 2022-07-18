package com.example.doan_food;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.doan_food.MonAn.MonAnAdapter_IC;
import com.example.doan_food.MonAn.MonAnAdapter_Cart;
import com.example.doan_food.MonAn.MonAn;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
public class Home_Fragment extends Fragment {
    RecyclerView view_popular,view_recommend,view_allMenu;
    View view;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Food");
    public Home_Fragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = getView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        ArrayList<MonAn> lst = new ArrayList<MonAn>();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                lst.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String tenMA = ds.child("tenMonAn").getValue(String.class);
                    String giaTien = ds.child("giaTien").getValue(String.class);
                    String chuThich = ds.child("chuThich").getValue(String.class);
                    String link = ds.child("link_image").getValue(String.class);
                    MonAn _newMonAn = new MonAn(tenMA, giaTien, chuThich,link);
                    lst.add(_newMonAn);
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
            }
        });

        popular_View(lst);
        recommend_view(lst);
        allMenu_view(lst);
        return view;
    }

    public void popular_View(ArrayList<MonAn> lst)
    {
        view_popular = view.findViewById(R.id.popular_recycler);
        MonAnAdapter_IC adapter = new MonAnAdapter_IC(getContext(),lst);
        view_popular.setAdapter(adapter);
        view_popular.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL,false));
    }

    public void recommend_view(ArrayList<MonAn> lst)
    {
        view_recommend = view.findViewById(R.id.recommended_recycler);
        MonAnAdapter_IC adapter = new MonAnAdapter_IC(getContext(),lst);
        view_recommend.setAdapter(adapter);
        view_recommend.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL,false));
    }

    public void allMenu_view(ArrayList<MonAn> lst)
    {
        view_allMenu = view.findViewById(R.id.all_menu_recycler);
        MonAnAdapter_Cart adapter = new MonAnAdapter_Cart(getContext(),lst);
        view_allMenu.setAdapter(adapter);
        view_allMenu.setLayoutManager(new GridLayoutManager(getContext(), 2));
    }
}