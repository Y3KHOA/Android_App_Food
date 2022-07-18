package com.example.doan_food.HoaDonDatHang;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan_food.R;
import com.example.doan_food.User.HoaDon;

import java.util.ArrayList;

public class lstDatHangActivity extends AppCompatActivity {
RecyclerView viewLst;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lst_dat_hang);
        viewLst = findViewById(R.id.lst_hd);
        Intent intent = getIntent();
        ArrayList<HoaDon> lst = (ArrayList<HoaDon>) intent.getSerializableExtra("food");

        AdapterlstFoodDH adapter = new AdapterlstFoodDH(lstDatHangActivity.this,lst);
        viewLst.setAdapter(adapter);
        viewLst.setLayoutManager(new LinearLayoutManager(lstDatHangActivity.this));
    }
}