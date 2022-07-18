package com.example.doan_food.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doan_food.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ThanhToanActivity extends AppCompatActivity {
    SQL sql;
    TextView tongTien;
    EditText diachi;
    Button thanhToan;
    Switch sw1,sw2;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("datHang");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan);
        sql = new SQL(this,"data_Food.db");
        tongTien = findViewById(R.id.txtThanhToanTongTien);
        thanhToan = findViewById(R.id.btnDatHang);
        diachi = findViewById(R.id.txtDiaChiNhan);
        sw1 = findViewById(R.id.switch1);
        sw2 = findViewById(R.id.switch2);
        ArrayList<HoaDon> lst = sql.getHoaDon(mAuth.getCurrentUser().getUid());
        tongTien.setText(TongTien(lst)+"");

        thanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<HoaDon> lst = sql.getHoaDon(mAuth.getCurrentUser().getUid());
                Date date = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                String formattedDate = df.format(date);
                String tt;
                if(sw1.isChecked()) tt = "Trực tiếp";
                else tt = "Online";
                DatHang hd = new DatHang(lst,formattedDate,mAuth.getCurrentUser().getUid(),TongTien(lst)+"",tt,diachi.getText().toString());

                myRef.push().setValue(hd).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(ThanhToanActivity.this, "Đã đặt hàng", Toast.LENGTH_SHORT).show();
                        Intent intent2 = new Intent();
                        intent2.putExtra("ThongTin", "Đã thanh toan");
                        setResult(RESULT_OK, intent2);
                        finish();
                    }
                });
            }
        });

    }
    public int TongTien(ArrayList<HoaDon> lst)
    {
        int tong = 0;
        for (int i = 0; i < lst.size(); i++) {
            tong += Integer.parseInt(lst.get(i).SL) * Integer.parseInt(lst.get(i).getMonAn().getGiaTien());
        }
        return tong;
    }

    public void setCheck(View view) {
        sw2.setChecked(!sw2.isChecked());
        sw1.setChecked(!sw2.isChecked());
    }

    public void setCheck2(View view) {
        sw1.setChecked(!sw1.isChecked());
        sw2.setChecked(!sw1.isChecked());
    }
}