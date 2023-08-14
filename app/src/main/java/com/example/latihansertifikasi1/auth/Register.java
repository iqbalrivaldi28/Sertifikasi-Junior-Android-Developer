package com.example.latihansertifikasi1.auth;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.latihansertifikasi1.R;
import com.example.latihansertifikasi1.data.DataHelper;

public class Register extends AppCompatActivity {

    private DataHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText username = findViewById(R.id.etUsername);
        EditText password = findViewById(R.id.etPassword);
        EditText repassword = findViewById(R.id.etRepeatPassword);
        Button daftar = findViewById(R.id.btnRegister);

        db = new DataHelper(this);

        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inUsername = username.getText().toString();
                String inPassword = password.getText().toString();
                String inrePassword = repassword.getText().toString();

                if (!(inrePassword.equals(inPassword))) {
                    repassword.setError("Password Tidak Sama");
                } else {
                    Boolean daftar = db.simpanUser(inUsername, inPassword);
                    if (daftar) {
                        Toast.makeText(Register.this, "Daftar Berhasil", Toast.LENGTH_LONG).show();
                        finish(); // Menutup activity setelah pendaftaran berhasil
                    } else {
                        Toast.makeText(Register.this, "Daftar Gagal", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }


}