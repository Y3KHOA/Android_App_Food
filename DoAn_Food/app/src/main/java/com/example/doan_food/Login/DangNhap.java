package com.example.doan_food.Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.doan_food.MainActivity;
import com.example.doan_food.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class DangNhap extends AppCompatActivity {
    Button btnDN,btnDNGG;
    EditText txtEmail,txtPass;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("User");

        btnDNGG = findViewById(R.id.btnFacebook);
        btnDN = findViewById(R.id.btnlogin);
        txtEmail = findViewById(R.id.txtEmail);
        txtPass = findViewById(R.id.txtPassword);

        btnDN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtEmail.getText().toString().equals("")  &&txtPass.getText().toString().equals(""))
                {
                    Toast.makeText(DangNhap.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_LONG).show();
                }
                else {
                    if(txtEmail.getText().toString().equals("admin") && txtPass.getText().toString().equals("admin"))
                    {
                      dangnhap(new User("admin",""));
                    }
                    else {
                        mAuth.signInWithEmailAndPassword(txtEmail.getText().toString(),txtPass.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful())
                                {
                                    Toast.makeText(DangNhap.this,"Đăng nhập thành công",Toast.LENGTH_LONG).show();
                                    GetUserFireBase(txtEmail.getText().toString());
                                }else{
                                    Toast.makeText(DangNhap.this,"Đăng nhập lỗi",Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                }
            }
        });

    }

    public void GetUserFireBase(String email)
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users");
        Query _query = myRef.orderByChild("email").equalTo(email);
        final User[] tk = {new User()};
        _query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data:snapshot.getChildren())
                {
                    if(data!=null){
                        tk[0] = new User(data.child("tenND").getValue().toString(), data.child("email").getValue().toString());
                        dangnhap(tk[0]);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }

    public void dangky(View view) {startActivity(new Intent(DangNhap.this,DangKy.class));
    }
   public  void dangnhap(User value)
   {
       Intent dn = new Intent(DangNhap.this, MainActivity.class);
       dn.putExtra("TK",value);
       startActivity(dn);
   }

    public void forgot(View view)
    {
       Intent forgot = new Intent(DangNhap.this,ForgotPass.class);
       startActivity(forgot);
    }
}