package com.example.doan_food.User;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan_food.Firebase.List_Firebase;
import com.example.doan_food.MonAn.MonAn;
import com.example.doan_food.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class YeuThich_Fragment extends Fragment
{
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Like");
    RecyclerView lst_YeuThich;
    public YeuThich_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_yeu_thich, container, false);
        ArrayList<MonAn> lst = new List_Firebase().lst_yeuThich();

        lst_YeuThich = view.findViewById(R.id.lst_yeuThich);
        MonAnAdapter_Like adapter = new MonAnAdapter_Like(getContext(),lst);
        lst_YeuThich.setAdapter(adapter);
        lst_YeuThich.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }
}