package com.example.doan_food.MonAn;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.doan_food.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class ThemMonAn extends AppCompatActivity {
    Button btnThem,btnUp;
    Uri imageUri;
    ImageView img;

    EditText txtTenMon,txtGia,txtChuThich;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Food");
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_mon_an);
        btnUp = findViewById(R.id.btnUpImage);
        btnThem = findViewById(R.id.btnThemMonAnMoi);
        img = findViewById(R.id.imageView);
        txtTenMon = findViewById(R.id.txtTenMonAnMoi);
        txtGia = findViewById(R.id.txtGiaMonAnMoi);
        txtChuThich = findViewById(R.id.txtChiTietMonAnMoi);

        btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePicture();
            }
        });

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(imageUri==null)
                {
                    Toast.makeText(ThemMonAn.this, "Chưa load ảnh", Toast.LENGTH_SHORT).show();
                    return;
                }

                StorageReference mountainsRef = storageReference.child("Images/"+txtTenMon.getText().toString()+".jpg");
                ProgressDialog progressDialog= new ProgressDialog(ThemMonAn.this);
                progressDialog.setTitle("Uploading...");
                progressDialog.show();

                mountainsRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        mountainsRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                MonAn _new = new MonAn(txtTenMon.getText().toString(),txtGia.getText().toString(),txtChuThich.getText().toString(),uri.toString());
                                myRef.child(_new.getTenMonAn()).setValue(_new).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(ThemMonAn.this, "Đã thêm 1 món ăn", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                finish();
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ThemMonAn.this, "Failed to Upload Image", Toast.LENGTH_SHORT).show();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot)
                    {
                        double progress= (100.0 * taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                        progressDialog.setMessage("Uploaded "+ (int)progress + "%");
                        if(progress>=100)
                            progressDialog.cancel();
                    }
                });
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
            img.setImageURI(imageUri);
        }
    }
}