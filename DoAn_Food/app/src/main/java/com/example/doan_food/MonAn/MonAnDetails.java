package com.example.doan_food.MonAn;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doan_food.Firebase.List_Firebase;
import com.example.doan_food.R;
import com.example.doan_food.User.HoaDon;
import com.example.doan_food.User.SQL;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MonAnDetails extends AppCompatActivity {
    TextView txtName,txtChiTiet,txtGia;
    Button btnThemGioHang;
    ImageView image,imageLike;
    FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Like");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);
        ArrayList<MonAn> lst_yeuThich = new List_Firebase().lst_yeuThich();

        mAuth = FirebaseAuth.getInstance();
        Intent intent = getIntent();
        MonAn monAn = (MonAn) intent.getSerializableExtra("food");

        btnThemGioHang = findViewById(R.id.buttonThemGioHang);
        imageLike = findViewById(R.id.image_Like);
        txtName = findViewById(R.id.name_detail);
        txtGia = findViewById(R.id.price_detail);
        txtChiTiet = findViewById(R.id.detail);
        image = findViewById(R.id.imageViewDetail);

        txtName.setText(monAn.getTenMonAn());
        txtGia.setText(monAn.getGiaTien());
        txtChiTiet.setText(monAn.getChuThich());
        Picasso.get().load(monAn.getLink_image()).into(image);

        SQL sql = new SQL(MonAnDetails.this,"data_Food.db");
        btnThemGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sql.addHD(new HoaDon(monAn,"1"),mAuth.getCurrentUser().getUid()))
                    Toast.makeText(MonAnDetails.this, "Đã thêm giỏ hàng", Toast.LENGTH_SHORT).show();
                else Toast.makeText(MonAnDetails.this, "Đã có trong giỏ hàng", Toast.LENGTH_SHORT).show();
            }
        });

        imageLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myRef.child(mAuth.getCurrentUser().getUid()).push().setValue(monAn).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(MonAnDetails.this, "Đã thêm 1 món ăn yêu thích", Toast.LENGTH_SHORT).show();
                        imageLike.setImageResource(R.drawable.img_liked);
                    }
                });
            }
        });
    }

    public void back(View view) {
        finish();
    }
}