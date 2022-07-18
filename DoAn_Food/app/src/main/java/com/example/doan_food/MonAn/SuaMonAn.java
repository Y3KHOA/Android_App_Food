package com.example.doan_food.MonAn;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doan_food.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Arrays;

public class SuaMonAn extends AppCompatActivity {
    Button btnSua,btnUpSua;
    TextView txtTenMon;
    EditText txtGia,txtChuThich;
    ImageView image;
    Uri imageUri;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Food");
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference();
    MonAn monAn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_mon_an);

        Intent intent = getIntent();
        monAn = (MonAn) intent.getSerializableExtra("food");

        btnSua = findViewById(R.id.btnSuaMonAnMoi);
        btnUpSua = findViewById(R.id.btnUpImageSua);
        image = findViewById(R.id.imageViewSua);
        txtTenMon = findViewById(R.id.txt_suaTen);
        txtGia = findViewById(R.id.txt_suaGia);
        txtChuThich = findViewById(R.id.txt_suachiTiet);

        txtTenMon.setText(monAn.getTenMonAn());
        txtGia.setText(monAn.getGiaTien());
        txtChuThich.setText(monAn.getChuThich());
        Picasso.get().load(monAn.getLink_image()).into(image);

        btnUpSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePicture();
            }
        });

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StorageReference mountainsRef = storageReference.child("Images/"+txtTenMon.getText().toString()+".jpg");
                ProgressDialog progressDialog= new ProgressDialog(SuaMonAn.this);
                progressDialog.setTitle("Uploading...");
                progressDialog.show();
                if(image!=null)
                {
                    mountainsRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            mountainsRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    monAn = new MonAn(txtTenMon.getText().toString(),txtGia.getText().toString(),txtChuThich.getText().toString(),uri.toString());
                                    myRef.child(monAn.getTenMonAn()).removeValue();
                                    myRef.child(monAn.getTenMonAn()).setValue(monAn);
                                    Toast.makeText(SuaMonAn.this, "Đã sửa món ăn", Toast.LENGTH_SHORT).show();
                                    Intent intent2 = new Intent();
                                    intent2.putExtra("ThongTin", "Đã sửa");
                                    setResult(RESULT_OK, intent2);
                                    finish();
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SuaMonAn.this, "Failed to Upload Image", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnProgressListener(
                        new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot)
                            {
                                double progress= (100.0 * taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                                progressDialog.setMessage("Uploaded "+ (int)progress + "%");
                                if(progress>=100) progressDialog.cancel();
                            }
                        });
                }
                else
                {
                    monAn = new MonAn(txtTenMon.getText().toString(),txtGia.getText().toString(),txtChuThich.getText().toString(),monAn.getLink_image());
                    myRef.child(monAn.getTenMonAn()).removeValue();
                    myRef.child(monAn.getTenMonAn()).setValue(monAn);
                    Toast.makeText(SuaMonAn.this, "Đã sửa món ăn", Toast.LENGTH_SHORT).show();
                    Intent intent2 = new Intent();
                    intent2.putExtra("ThongTin", "Đã sửa");
                    setResult(RESULT_OK, intent2);
                    finish();
                }
            }
        });
    }

    public void back(View view) {finish();}

    public void choosePicture()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK && data!=null && data.getData()!=null)
        {
            imageUri = data.getData();
            Toast.makeText(this, imageUri+"", Toast.LENGTH_SHORT).show();
            image.setImageURI(imageUri);
        }
    }
}