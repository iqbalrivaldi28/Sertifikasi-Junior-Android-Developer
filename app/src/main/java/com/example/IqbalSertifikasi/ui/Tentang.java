package com.example.IqbalSertifikasi.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.IqbalSertifikasi.R;

public class Tentang extends AppCompatActivity {
    Button bt1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tentang);

        bt1 = (Button) findViewById(R.id.kembaliButton);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Tentang");

        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = "6282373231266"; // Ganti dengan nomor WhatsApp Anda
                String message = "Hai Iqbal, kamu lagi sertifikasi Junior Mobile Programmer Yaa!"; // Pesan yang ingin Anda kirimkan

                bukaWa(phoneNumber, message);
            }
        });
    }


    private void bukaWa(String phoneNumber, String message) {
        try {
            String url = "https://api.whatsapp.com/send?phone=" + phoneNumber + "&text=" + Uri.encode(message);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}