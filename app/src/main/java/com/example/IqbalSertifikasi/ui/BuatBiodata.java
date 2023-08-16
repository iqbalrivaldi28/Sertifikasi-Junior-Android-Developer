package com.example.IqbalSertifikasi.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.IqbalSertifikasi.MainActivity;
import com.example.IqbalSertifikasi.R;
import com.example.IqbalSertifikasi.data.DataHelper;

import java.util.Calendar;
import java.util.Locale;

public class BuatBiodata extends AppCompatActivity {
    protected Cursor cursor;
    DataHelper dbHelper;
    Button bt1, bt2;
    EditText text1, text2, text3, text4, text5;
    DatePicker datePicker;

    private TextView textViewTanggal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buat_biodata);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Tambah Data Mahasiswa");

        // Inisialisasi DatePicker
        datePicker = findViewById(R.id.datePicker);

        dbHelper = new DataHelper(this);
        text1 = (EditText) findViewById(R.id.editText1);
        text2 = (EditText) findViewById(R.id.editText2);

        //text3 = (EditText) findViewById(R.id.editText3);
        datePicker = (DatePicker) findViewById(R.id.datePicker);

        textViewTanggal = findViewById(R.id.textViewTanggal);


        text4 = (EditText) findViewById(R.id.editText4);
        text5 = (EditText) findViewById(R.id.editText5);
        bt1 = (Button) findViewById(R.id.button1);
        bt2 = (Button) findViewById(R.id.button2);

//        bt1.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view) {
//                SQLiteDatabase db = dbHelper.getWritableDatabase();
//                db.execSQL("INSERT INTO biodata(no, nama, tgl, jk, alamat) VALUES ('" +
//                        text1.getText().toString() + "', '" +
//                        text2.getText().toString() + "', '" +
//                        text3.getText().toString() + "', '" +
//                        text4.getText().toString() + "', '" +
//                        text5.getText().toString() + "')");
//                Toast.makeText(getApplicationContext(), "Berhasil Tambah Data", Toast.LENGTH_LONG).show();
//                MainActivity.ma.RefreshList();
//                finish();
//            }
//        });

        bt1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                // Mengambil tanggal dari DatePicker
                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth() + 1; // Month is 0-based
                int year = datePicker.getYear();

                String selectedDate = String.format(Locale.getDefault(), "%02d-%02d-%d",
                        day, month, year);

                db.execSQL("INSERT INTO biodata(no, nama, tgl, jk, alamat) VALUES ('" +
                        text1.getText().toString() + "', '" +
                        text2.getText().toString() + "', '" +
                        selectedDate + "', '" +
                        text4.getText().toString() + "', '" +
                        text5.getText().toString() + "')");

                Toast.makeText(getApplicationContext(), "Berhasil Tambah Data", Toast.LENGTH_LONG).show();
                MainActivity.ma.RefreshList();
                finish();
            }
        });


        bt2.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                finish();
            }
        });

        TextView textViewTanggalLahir = findViewById(R.id.textView3);
        textViewTanggalLahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(BuatBiodata.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String selectedDate = String.format(Locale.getDefault(), "%02d-%02d-%d",
                                dayOfMonth, monthOfYear + 1, year); // Month is 0-based

                        // Set teks pada TextView
                        textViewTanggal.setText(selectedDate);

                        // Set the selected date to the DatePicker
                        datePicker.updateDate(year, monthOfYear, dayOfMonth);
                    }
                }, year, month, day);

        datePickerDialog.show();
    }
}
