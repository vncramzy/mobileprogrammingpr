package com.example.v.cobacobautsmobile;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import com.example.v.cobacobautsmobile.Adapter.LaporanBeliAdapter;

public class LaporanPembelian extends AppCompatActivity {

    private static String DBNAME = "UTS";
    SQLiteDatabase db;
    ArrayList<String[]> dataLaporan = new ArrayList<String[]>();

    EditText tgl_awal, tgl_akhir;
    Button btn_cari;
    TextView txt_total_lap;
    ListView list_laporan;

    Double stot = 0.0;
    Locale localeID;
    NumberFormat formatRupiah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.laporanpembelian);
        tgl_awal = (EditText)findViewById(R.id.tgl_awal);
        tgl_akhir = (EditText)findViewById(R.id.tgl_akhir);
        btn_cari = (Button)findViewById(R.id.btn_cari);
        txt_total_lap = (TextView)findViewById(R.id.txt_total_lap);
        list_laporan = (ListView)findViewById(R.id.list_laporan);

        String myDate =  new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        tgl_awal.setText(myDate);
        tgl_akhir.setText(myDate);
        Log.e("TGL", myDate);
        createDB();

        localeID = new Locale("in", "ID");
        formatRupiah = NumberFormat.getCurrencyInstance(localeID);

        btn_cari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tgl_awal.getText().toString().equals("") || tgl_akhir.getText().toString().equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(LaporanPembelian.this);
                    builder.setMessage("Silahkan isikan tanggal")
                            .setNegativeButton("Retry", null).create().show();
                }else{
                    get_data(tgl_awal.getText().toString(), tgl_akhir.getText().toString());
                    LaporanBeliAdapter ca = new LaporanBeliAdapter(getApplicationContext(), dataLaporan);
                    list_laporan.setAdapter(ca);
                    list_laporan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                            Intent intent = new Intent(getBaseContext(), LaporanDetailPembelian.class);
                            intent.putExtra("detail_laporan", dataLaporan.get(i));
                            startActivity(intent);
                        }
                    });
                    Log.e("TGL", tgl_awal.getText().toString().equals("")+" "+tgl_akhir.getText().toString().equals(""));
                }
            }
        });
    }


    private void createDB(){
        try {
            db = this.openOrCreateDatabase(DBNAME, MODE_PRIVATE, null);
        }catch (Exception e){
            Log.e("Error", "Can't Create Database");
        }
    }

    private void get_data(String tgl1, String tgl2){
        String sql = "SELECT * FROM pembelian a JOIN MasterSupplier b ON a.ID = b.ID";
        if(!tgl1.equals("") || !tgl2.equals("")){
            sql += " WHERE TGL_TRANSAKSI BETWEEN '"+tgl1+"' AND '"+tgl2+"'";
        }
        Cursor c = db.rawQuery(sql, null);
        stot = 0.0;
        dataLaporan.clear();
        while (c.moveToNext()){
            String[] ret = new String[5];
            ret[0] = c.getString(c.getColumnIndex("ID_TRANSAKSI"));
            ret[1] = c.getString(c.getColumnIndex("TGL_TRANSAKSI"));
            ret[2] = c.getString(c.getColumnIndex("ID"));
            ret[3] = c.getString(c.getColumnIndex("GRANDTOTAL"));
            ret[4] = c.getString(c.getColumnIndex("NAMA"));
            dataLaporan.add(ret);
            stot += Double.parseDouble(ret[3]);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        createDB();
        get_data("", "");
        txt_total_lap.setText(formatRupiah.format((double)stot));

        LaporanBeliAdapter ca = new LaporanBeliAdapter(getApplicationContext(), dataLaporan);
        list_laporan.setAdapter(ca);
        list_laporan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                Intent intent = new Intent(getBaseContext(), LaporanDetailPembelian.class);
                intent.putExtra("detail_laporan", dataLaporan.get(i));
                startActivity(intent);
            }
        });
    }
}
