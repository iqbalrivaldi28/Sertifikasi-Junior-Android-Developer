package com.example.latihansertifikasi1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.latihansertifikasi1.auth.Login;
import com.example.latihansertifikasi1.data.DataHelper;
import com.example.latihansertifikasi1.ui.BuatBiodata;
import com.example.latihansertifikasi1.ui.LihatBiodata;
import com.example.latihansertifikasi1.ui.Tentang;
import com.example.latihansertifikasi1.ui.UpdateBiodata;

public class MainActivity extends AppCompatActivity {
    String [] daftar;
    ListView ListView01;
    Menu menu;
    protected Cursor cursor;
    DataHelper dbcenter;
    public static MainActivity ma;

    private Button btnKeluar;

    //shared pref
    public static final String SHARED_PREF_NAME = "myPref";

    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        Button btn =(Button) findViewById(R.id.button2);
        btnKeluar = findViewById(R.id.btnKeluar);

        // Menginisialisasi objek dbcenter
        dbcenter = new DataHelper(this);
        Boolean checksession = dbcenter.checkSession("ada");
        if (checksession == false){
            Intent login = new Intent(getApplicationContext(), Login.class);
            startActivity(login);
            finish();
        }

        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                Intent inte = new Intent(MainActivity.this, BuatBiodata.class);
                Log.d("11", "kudene ora error");
                startActivity(inte);
            }
        });
        
        ma = this;
        dbcenter = new DataHelper(this);
        RefreshList();
    }

    public void  RefreshList() {
        SQLiteDatabase db = dbcenter.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM biodata", null);
        daftar = new String[cursor.getCount()];
        cursor.moveToFirst();
        for (int cc = 0; cc < cursor.getCount(); cc++){
            cursor.moveToPosition(cc);
            daftar[cc] = cursor.getString(1).toString();
        }
        ListView01 = (ListView) findViewById(R.id.listview);
        ListView01.setAdapter(new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, daftar));
        ListView01.setSelected(true);
        ListView01.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {
                final String selection = daftar[arg2];
                final CharSequence[] dialogitem = {"Lihat Biodata", "Update Biodata", "Hapus Biodata"};

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Pilihan");
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        switch (item){
                            case 0:
                                Intent i = new Intent(getApplicationContext(), LihatBiodata.class);
                                i.putExtra("nama", selection);
                                startActivity(i);
                                break;

                            case 1:
                                Intent in = new Intent(getApplicationContext(), UpdateBiodata.class);
                                in.putExtra("nama", selection);
                                startActivity(in);
                                break;

                            case 2:
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                                alertDialogBuilder.setTitle("Konfirmasi");
                                alertDialogBuilder.setMessage("Apakah Anda yakin ingin menghapus data ini?");
                                alertDialogBuilder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        SQLiteDatabase db = dbcenter.getWritableDatabase();
                                        db.execSQL("DELETE FROM biodata WHERE nama = '" + selection + "'");
                                        RefreshList();
                                    }
                                });
                                alertDialogBuilder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss(); // Tutup dialog jika pengguna memilih "Tidak"
                                    }
                                });
                                AlertDialog alertDialog = alertDialogBuilder.create();
                                alertDialog.show();
                                break;
                        }
                    }
                });
                builder.create().show();
            }});
        ((ArrayAdapter)ListView01.getAdapter()).notifyDataSetInvalidated();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings){
            Intent i = new Intent(this, Tentang.class);
            startActivity(i);
            return true;
        } else if (id == R.id.btnKeluar){

            // Menginisialisasi dbcenter di dalam onClick()
            dbcenter = new DataHelper(getApplicationContext());

            Boolean updateSession = dbcenter.upgradeSession("kosong", 1);
            if (updateSession == true) {
                Toast.makeText(getApplicationContext(), "Berhasil Keluar", Toast.LENGTH_LONG).show();

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("masuk", false);
                editor.apply();

                Intent keluar = new Intent(getApplicationContext(), Login.class);
                startActivity(keluar);
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}