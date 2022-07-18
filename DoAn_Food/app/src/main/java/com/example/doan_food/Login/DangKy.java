package com.example.doan_food.Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.doan_food.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DangKy extends AppCompatActivity {
    Button btnDangKy;
    EditText txtTen,txtPass,txtrePass,txtEmail;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("User");

        btnDangKy = findViewById(R.id.btnRegister);
        txtEmail = findViewById(R.id.inputEmail);
        txtTen = findViewById(R.id.inputUsername);
        txtPass = findViewById(R.id.inputPassword);
        txtrePass = findViewById(R.id.inputConformPassword);

        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtTen.getText().toString().equals("") && txtEmail.getText().toString().equals("") &&txtPass.getText().toString().equals("") && txtrePass.getText().toString().equals("") )
                {
                    txtEmail.requestFocus();
                    Toast.makeText(DangKy.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_LONG).show();
                    return;
                }
                if(!txtPass.getText().toString().equals(txtrePass.getText().toString()))
                {
                    txtrePass.requestFocus();
                    txtrePass.setError("Mật khẩu phải giống nhau");
                    return;
                }
                if(txtPass.getText().toString().length() < 6)
                {
                    txtPass.setError("Mật khẩu phải có ít nhất 6 kí tự");
                    txtPass.requestFocus();
                    return;
                }
                else{
                    mAuth.createUserWithEmailAndPassword(txtEmail.getText().toString(), txtPass.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task){
                                    if(task.isSuccessful()){
                                        User user = new User(txtTen.getText().toString(), txtEmail.getText().toString());
                                        FirebaseDatabase.getInstance().getReference("Users")
                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful())
                                                {
                                                    Toast.makeText(DangKy.this,"Đăng ký thành công", Toast.LENGTH_LONG).show();
                                                    dangky();
                                                }else{
                                                    Toast.makeText(DangKy.this, "Thử Lại", Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        });
                                    }
                                    else{
                                        Toast.makeText(DangKy.this,"Đăng ký thất bại",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            }
        });

    }
    public void dacotk(View view) {
        finish();
    }
    public void dangky(){
        Intent dk = new Intent(DangKy.this, com.example.doan_food.Login.DangNhap.class);
        startActivity(dk);
    }
}