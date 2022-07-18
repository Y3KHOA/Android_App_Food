package com.example.doan_food.Admin;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.doan_food.R;
import com.example.doan_food.User.HoaDon;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class QL_ThongKe_Fragment extends Fragment {
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("datHang");
    LineChart chart;
    LineChart lineChart;
    LineData lineData;
    LineDataSet lineDataSet;
    ArrayList<ThongKe> lst;
    public QL_ThongKe_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ql_thongke, container, false);
        chart = view.findViewById(R.id.lineChart);
        GenericTypeIndicator<ArrayList<HoaDon>> t = new GenericTypeIndicator<ArrayList<HoaDon>>(){};
        lst = new ArrayList<ThongKe>();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                lst.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String ngayDat = ds.child("ngayDat").getValue(String.class);
                    String tongTien = ds.child("tongTien").getValue(String.class);
                    ThongKe _newTK = new ThongKe(ngayDat,Integer.parseInt(tongTien));
                    lst.add(_newTK);
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
        ArrayList<Entry> entries = new ArrayList<>();
        ArrayList labels = new ArrayList();

        for (int i = 0; i < lst.size(); i++) {
            entries.add(new Entry(lst.get(i).getTongTien(), i));
            labels.add(lst.get(i).getDate());
        }
        lineDataSet = new LineDataSet(entries, "");
        lineData = new LineData(lineDataSet);
        lineChart.setData(lineData);
        lineDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        lineDataSet.setValueTextColor(Color.BLACK);
        lineDataSet.setValueTextSize(18f);
        Toast.makeText(getContext() ,lst.size()+"", Toast.LENGTH_SHORT).show();
        return view;
    }
}