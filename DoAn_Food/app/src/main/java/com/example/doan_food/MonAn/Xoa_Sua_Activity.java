package com.example.doan_food.MonAn;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doan_food.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class Xoa_Sua_Activity extends AppCompatActivity {
    TextView txtName,txtChiTiet,txtGia;
    String thongTin;
    Button btnSua,btnXoa;
    ImageView image;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Food");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xoa_sua);

        txtName = findViewById(R.id.name);
        txtGia = findViewById(R.id.price);
        txtChiTiet = findViewById(R.id.txtDetail);
        image = findViewById(R.id.imageView5);
        btnSua = findViewById(R.id.btnSuaMonAn);
        btnXoa = findViewById(R.id.btnXoaMonAn);

        Intent intent = getIntent();
        MonAn monAn = (MonAn) intent.getSerializableExtra("food");

        txtName.setText(monAn.getTenMonAn());
        txtGia.setText(monAn.getGiaTien());
        txtChiTiet.setText(monAn.getChuThich());
        Picasso.get().load(monAn.getLink_image()).into(image);

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context,SuaMonAn.class);
                intent.putExtra("food",monAn);
                startActivityForResult(intent,1);
            }
        });

        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myRef.child(monAn.getTenMonAn()).removeValue();
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK)
        {
            if (requestCode == 1)
            {
                if (data != null)
                thongTin = data.getStringExtra("ThongTin");
                if(thongTin.equals("Đã sửa")) finish();
            }
        }
    }


    public void back(View view) {
        finish();
    }
}