package com.example.v.cobacobautsmobile;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;

import com.example.v.cobacobautsmobile.Adapter.AdapterStok;
import com.example.v.cobacobautsmobile.Adapter.BarangAdapter;
import com.example.v.cobacobautsmobile.Adapter.dataStok;

import java.util.ArrayList;

public class LaporanStok extends AppCompatActivity {

    public static String DBNAME = "UTS";
    private AdapterView listview;
    EditText tglawalStok, tglakhirStok;
    Button btn_cari;
    ArrayList<dataStok> listData = new ArrayList<dataStok>();
    ListView LVStock;
    int totalstokakhir = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.laporanstoklistview);
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        tglawalStok = (EditText) findViewById(R.id.tgl_awal);
        tglakhirStok = (EditText) findViewById(R.id.tgl_akhir);
        btn_cari = (Button) findViewById(R.id.btn_cari);
        LVStock = (ListView) findViewById(R.id.listlaporanstok);

        fetchdata();
    }
    public void fetchdata(){
        totalstokakhir = 0;
        SQLiteDatabase db = openOrCreateDatabase(DBNAME, MODE_PRIVATE, null);
        Cursor c = db.rawQuery("SELECT B.NAMA as nama, B.STOK as stok, DB.JUMLAH as stokbeli, DJ.JUMLAH as stokjual FROM DetailPembelian DB join MasterBarang B on (B.ID = DB.ID_BARANG) join DetailPenjualan DJ on (B.ID = DJ.ID_BARANG) order by B.NAMA ASC",null);
        listData.clear();
        while(c.moveToNext()){
            String namabarang = c.getString(0);
            int stokawal = c.getInt(1);
            int stokbeli = c.getInt(2);
            int stokjual = c.getInt(3);
            int stokakhir = stokawal+stokbeli-stokjual;

            totalstokakhir+=stokakhir;

            dataStok hasil = new dataStok(namabarang,stokawal,stokbeli,stokjual,stokakhir);
            listData.add(hasil);
        }
        AdapterStok adapter = new AdapterStok(this,listData);
        LVStock.setAdapter(adapter);
    }

}