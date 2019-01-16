package com.example.v.cobacobautsmobile;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class LaporanDetailPenjualan extends AppCompatActivity {

    private static String DBNAME = "UTS";
    SQLiteDatabase db;
    ArrayList<String[]> dataDetLaporan = new ArrayList<String[]>();

    TextView txt_lap_id,txt_lap_date, txt_lap_cust;
    TableLayout tbl_det_trans;
    TextView txt_grandtotal;

    String id_lap = "";
    Double grandTot = 0.0;
    Locale localeID;
    NumberFormat formatRupiah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.laporanpenjualandetail);

        txt_lap_id = (TextView)findViewById(R.id.txt_lap_id);
        txt_lap_date = (TextView)findViewById(R.id.txt_lap_date);
        txt_lap_cust = (TextView)findViewById(R.id.txt_lap_cust);

        tbl_det_trans = (TableLayout)findViewById(R.id.tbl_det_trans);
        txt_grandtotal = (TextView)findViewById(R.id.txt_grandtotal);


        Intent from_barang = getIntent();
        String[] detail_laporan = from_barang.getStringArrayExtra("detail_laporan");
        id_lap = detail_laporan[0];
        txt_lap_id.setText(detail_laporan[0]);
        txt_lap_date.setText(detail_laporan[1]);
        txt_lap_cust.setText(detail_laporan[2]);

        localeID = new Locale("in", "ID");
        formatRupiah = NumberFormat.getCurrencyInstance(localeID);

        Double gt = Double.parseDouble(detail_laporan[3]);
        txt_grandtotal.setText(formatRupiah.format((double)gt));
        createDB();
        get_data();
        refresh_list();
    }

    private void createDB(){
        try {
            db = this.openOrCreateDatabase(DBNAME, MODE_PRIVATE, null);
        }catch (Exception e){
            Log.e("Error", "Can't Create Database");
        }
    }

    private void get_data(){
        String sql = "SELECT a.ID_DET_TRANSAKSI, a.ID_TRANSAKSI, a.ID_BARANG, a.JUMLAH, a.HARGA, a.SUBTOTAL, b.NAMA FROM DetailPenjualan a JOIN MasterBarang b ON a.ID_BARANG = b.ID WHERE ID_TRANSAKSI = '"+id_lap+"'";
        Cursor c = db.rawQuery(sql, null);
        Log.e("SQL", sql);
        while (c.moveToNext()){
            String[] ret = new String[7];
            ret[0] = c.getString(c.getColumnIndex("ID_DET_TRANSAKSI"));
            ret[1] = c.getString(c.getColumnIndex("ID_TRANSAKSI"));
            ret[2] = c.getString(c.getColumnIndex("ID_BARANG"));
            ret[3] = c.getString(c.getColumnIndex("JUMLAH"));
            ret[4] = c.getString(c.getColumnIndex("HARGA"));
            ret[5] = c.getString(c.getColumnIndex("SUBTOTAL"));
            ret[6] = c.getString(c.getColumnIndex("NAMA"));
//            ret[6] = c.getString(6);
            dataDetLaporan.add(ret);
        }
    }

    private void refresh_list(){
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

        int co = tbl_det_trans.getChildCount();
        for(int i = 1; i<co; i++){
            View child = tbl_det_trans.getChildAt(i);
            if (child instanceof TableRow) ((ViewGroup) child).removeAllViews();
        }
        for(int i=0;i<dataDetLaporan.size();i++){
            TableRow row = new TableRow(LaporanDetailPenjualan.this);
            TextView tno = new TextView(LaporanDetailPenjualan.this);
            TextView tna = new TextView(LaporanDetailPenjualan.this);
            TextView tjm = new TextView(LaporanDetailPenjualan.this);
            TextView thg = new TextView(LaporanDetailPenjualan.this);
            TextView tot = new TextView(LaporanDetailPenjualan.this);

            Double hrg = Double.parseDouble(dataDetLaporan.get(i)[4]);
            Double st = Double.parseDouble(dataDetLaporan.get(i)[5]);

            tno.setText(String.valueOf(i+1));
            tna.setText(dataDetLaporan.get(i)[6]);
            tjm.setText(dataDetLaporan.get(i)[3]);
            thg.setText(formatRupiah.format((double)hrg));
            tot.setText(formatRupiah.format((double)st));

            row.addView(tno);
            row.addView(tna);
            row.addView(tjm);
            row.addView(thg);
            row.addView(tot);
            tbl_det_trans.addView(row);
        }
    }
}
