package com.example.v.cobacobautsmobile;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button btnLogin;
    EditText edtEmail,edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnLogin = findViewById(R.id.loginBtn);
        edtEmail = findViewById(R.id.login_emailid);
        edtPassword = findViewById(R.id.login_password);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(MainActivity.this,Home.class);
                startActivity(pindah);
//                String username = edtEmail.getText().toString();
//                String password = edtPassword.getText().toString();
//
//                if(username.equals("admin")&& password.equals("admin")){
//                    //loginberhasil
//                    Toast.makeText(getApplicationContext(),"Login Sukses",Toast.LENGTH_SHORT).show();
//                    Intent pindah = new Intent(MainActivity.this,Home.class);
//                    startActivity(pindah);
//                }
//                else if(username.equals("")&& password.equals("")){
//                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//                    builder.setMessage("Username Atau Password Belum Diisi!")
//                            .setNegativeButton("ok",null).create().show();
//                }
//                else {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//                    builder.setMessage("Username Atau Password Salah!")
//                            .setNegativeButton("ok", null).create().show();
//                }
            }
        });
    }
}
