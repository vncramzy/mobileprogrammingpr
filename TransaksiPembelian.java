package com.example.v.cobacobautsmobile;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import com.example.v.cobacobautsmobile.Adapter.BarangAdapter;
import com.example.v.cobacobautsmobile.Adapter.SupplierAdapter;

public class TransaksiPembelian extends AppCompatActivity {

    private static String DBNAME = "UTS";
    SQLiteDatabase db;

    ArrayList<String[]> dataSupplier = new ArrayList<String[]>();
    ArrayList<String[]> dataBarang = new ArrayList<String[]>();
    String[] tmpBarang;
    ArrayList<String[]> dataTransaksi = new ArrayList<String[]>();
    ArrayList<String[]> dataDetailBarang = new ArrayList<String[]>();
    Double grandTotal = 0.0;
    int nm_trans = 0;
    Boolean cek = true;
    int min_jml = 0;


    EditText txtid,txtsupplier, txtidsupplier, date, time, txtBarang, txtIdBarang, txtjumlah;
    Button   btn_cari_barang, btn_trans_reset, btn_trans_simpan, btn_tambah_barang;
    TextView txttotal;
    TableLayout tbl_det_barang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transaksipembelian);
        txtid = (EditText)findViewById(R.id.txtid);
        txtsupplier = (EditText)findViewById(R.id.txtsupplier);
        txtidsupplier = (EditText)findViewById(R.id.txtidsupplier);
        txtBarang = (EditText)findViewById(R.id.txtBarang);
        txtIdBarang = (EditText)findViewById(R.id.txtIdBarang);
        txtjumlah = (EditText)findViewById(R.id.txtjumlah);

        btn_trans_simpan = (Button)findViewById(R.id.btn_trans_simpan);
        btn_tambah_barang = (Button)findViewById(R.id.btn_tambah_barang);

        txttotal = (TextView)findViewById(R.id.txttotal);

        tbl_det_barang = (TableLayout)findViewById(R.id.tbl_det_barang);

        createDB();
        get_data();
        gen_id_trans();

        String myDate =  new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        date = (EditText)findViewById(R.id.date);
        date.setText(myDate);

        txtsupplier.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                show_supplier();
            }
        });
        txtBarang.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                show_barang();
            }
        });
        btn_tambah_barang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_barang();
            }
        });

        btn_trans_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpan();
            }
        });


    }

    private void clearField(){
        txtid.setText("");
        txtsupplier.setText("");
        txtidsupplier.setText("");
        txtBarang.setText("");
        txttotal.setText("0");
    }

    private void gen_id_trans(){
        String num = "00"+ String.valueOf(nm_trans);
        String nt = "TB"+num.substring(num.length()-2, num.length());
        txtid.setText(nt);
        Log.e("GEN - ", num);
        Log.e("GEN - ", nt);
    }

    private void show_supplier(){
        get_data_supplier();
        SupplierAdapter ca = new SupplierAdapter(getApplicationContext(), dataSupplier);
        AlertDialog.Builder builder = new AlertDialog.Builder(TransaksiPembelian.this);
        builder.setTitle("Pilih Supplier")
                .setAdapter(ca, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        txtsupplier.setText(dataSupplier.get(i)[1]);
                        txtidsupplier.setText(dataSupplier.get(i)[0]);
                    }
                });
        builder.create().show();
    }
    private void show_barang(){
        get_data_barang();
        BarangAdapter ca = new BarangAdapter(getApplicationContext(), dataBarang);
        AlertDialog.Builder builder = new AlertDialog.Builder(TransaksiPembelian.this);
        builder.setTitle("Pilih Barang")
                .setAdapter(ca, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                            min_jml = Integer.parseInt(dataBarang.get(i)[2]);
                            txtBarang.setText(dataBarang.get(i)[1]);
                            txtIdBarang.setText(dataBarang.get(i)[0]);
                            tmpBarang = dataBarang.get(i);
                    }
                });
        builder.create().show();
    }

    private void add_barang(){
        String tbarang = txtIdBarang.getText().toString();
        String tjml = txtjumlah.getText().toString();

        if(tbarang.equals("") || tjml.equals("") || tjml.equals("0")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(TransaksiPembelian.this);
            builder.setMessage("Silahkan pilih barang atau isikan jumlah")
                    .setNegativeButton("Retry", null).create().show();
        }else{
            Double harga = Double.parseDouble(tmpBarang[4]);
            int jumlah = Integer.parseInt(String.valueOf(txtjumlah.getText()));
            Double subtotal = harga*jumlah;

            String[] brg = new String[5];
            brg[0] = tmpBarang[0];
            brg[1] = tmpBarang[1];
            brg[2] = String.valueOf(jumlah);
            brg[3] = tmpBarang[4];
            brg[4] = String.valueOf(subtotal);
            dataDetailBarang.add(brg);
            refresh_list();
            txtIdBarang.setText("");
            txtBarang.setText("");
            txtjumlah.setText("");
        }
    }

    private void refresh_list(){
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

        int co = tbl_det_barang.getChildCount();
        for(int i = 1; i<co; i++){
            View child = tbl_det_barang.getChildAt(i);
            if (child instanceof TableRow) ((ViewGroup) child).removeAllViews();
        }
        grandTotal = 0.0;
        for(int i=0;i<dataDetailBarang.size();i++){
            TableRow row = new TableRow(TransaksiPembelian.this);
            TextView idbarang = new TextView(TransaksiPembelian.this);
            TextView nomer = new TextView(TransaksiPembelian.this);
            TextView namabarang = new TextView(TransaksiPembelian.this);
            TextView jumlah = new TextView(TransaksiPembelian.this);
            TextView harga = new TextView(TransaksiPembelian.this);
            TextView subtot = new TextView(TransaksiPembelian.this);

            Double hrg = Double.parseDouble(dataDetailBarang.get(i)[3]);
            Double st = Double.parseDouble(dataDetailBarang.get(i)[4]);
            grandTotal += st;


            idbarang.setText(dataDetailBarang.get(i)[0]);
            nomer.setText(String.valueOf(i+1));
            namabarang.setText(dataDetailBarang.get(i)[1]);
            jumlah.setText(dataDetailBarang.get(i)[2]);
            harga.setText(formatRupiah.format((double)hrg));
            subtot.setText(formatRupiah.format((double)st));

            row.addView(idbarang);
            row.addView(nomer);
            row.addView(namabarang);
            row.addView(jumlah);
            row.addView(harga);
            row.addView(subtot);
            tbl_det_barang.addView(row);
        }
        txttotal.setText(formatRupiah.format((double)grandTotal));
    }

    private void createDB(){
        try {
            db = this.openOrCreateDatabase(DBNAME, MODE_PRIVATE, null);
            String sql_trans = "CREATE TABLE IF NOT EXISTS Pembelian (" +
                    "ID_TRANSAKSI VARCHAR PRIMARY KEY, " +
                    "TGL_TRANSAKSI DATE, " +
                    "ID VARCHAR, " +
                    "SUPPLIER VARCHAR, " +
                    "GRANDTOTAL DOUBLE);";
            String sql_det_trans = "CREATE TABLE IF NOT EXISTS DetailPembelian (" +
                    "ID_DET_TRANSAKSI INT PRIMARY KEY, " +
                    "ID_TRANSAKSI VARCHAR, " +
                    "ID_BARANG VARCHAR, " +
                    "JUMLAH INT, " +
                    "HARGA DOUBLE, " +
                    "SUBTOTAL DOUBLE);";
//            String sql_trigger = "CREATE TRIGGER IF NOT EXISTS t_pembelian AFTER INSERT ON DetailPembelian " +
//                    "BEGIN " +
//                    "UPDATE Barang SET JUMLAH = JUMLAH - new.JUMLAH WHERE ID_BARANG = new.ID_BARANG; " +
//                    "END;";
            db.execSQL(sql_trans);
            db.execSQL(sql_det_trans);
//            db.execSQL(sql_trigger);
        }catch (Exception e){
            Log.e("SQL ERROR", "TERJADI KELASALAHAN");

        }
    }

    private void get_data_barang(){
        String sql = "SELECT * FROM MasterBarang";
        Cursor c = db.rawQuery(sql, null);
        String a = "";
        dataBarang.clear();
        while (c.moveToNext()){
            String[] ret = new String[6];
            ret[0] = c.getString(c.getColumnIndex("ID"));
            ret[1] = c.getString(c.getColumnIndex("NAMA"));
            ret[2] = c.getString(c.getColumnIndex("STOK"));
            ret[3] = c.getString(c.getColumnIndex("SATUAN"));
            ret[4] = c.getString(c.getColumnIndex("BELI"));
            ret[5] = c.getString(c.getColumnIndex("JUAL"));
            dataBarang.add(ret);
        }
    }

    private void get_data_supplier(){
        Cursor c = db.rawQuery("SELECT * FROM MasterSupplier", null);
        String a = "";
        dataSupplier.clear();
        while (c.moveToNext()){
            String[] ret = new String[4];
            ret[0] = c.getString(c.getColumnIndex("ID"));
            ret[1] = c.getString(c.getColumnIndex("NAMA"));
            ret[2] = c.getString(c.getColumnIndex("TELP"));
            ret[3] = c.getString(c.getColumnIndex("ALAMAT"));
            dataSupplier.add(ret);
        }
    }

    private void get_data(){
        Cursor c = db.rawQuery("SELECT * FROM pembelian", null);
        nm_trans = c.getCount()+1;
    }

    private void simpan(){
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

        if(txtid.getText().toString().equals("") ||
                date.getText().toString().equals("") ||
                tbl_det_barang.getChildCount() == 1
                ){
            AlertDialog.Builder builder = new AlertDialog.Builder(TransaksiPembelian.this);
            builder.setMessage("Silahkan Lengkapi data!")
                    .setNegativeButton("ok", null).create().show();
        }else{
            String sql = "INSERT INTO pembelian VALUES(" +
                    "'"+txtid.getText()+"',"+
                    "'"+date.getText()+"',"+
                    "'"+txtidsupplier.getText()+"',"+
                    "'"+txtsupplier.getText()+"',"+
                    ""+grandTotal+""+
                    ");";
            for(int i=0;i<dataDetailBarang.size();i++){
                String sql_det = "INSERT INTO DetailPembelian VALUES("+
                        "'"+txtid.getText()+dataDetailBarang.get(i)[0]+"', "+
                        "'"+txtid.getText()+"', "+
                        "'"+dataDetailBarang.get(i)[0]+"', "+
                        ""+dataDetailBarang.get(i)[2]+", "+
                        ""+dataDetailBarang.get(i)[3]+", "+
                        ""+dataDetailBarang.get(i)[4]+"); ";
                    try{
                        db.execSQL(sql);
                        db.execSQL(sql_det);
                        AlertDialog.Builder builder = new AlertDialog.Builder(TransaksiPembelian.this);
                        builder.setMessage("DATA BERHASIL DISIMPAN")
                                .setPositiveButton("ok", null).create().show();
                    }catch (Exception e){
                        AlertDialog.Builder builder = new AlertDialog.Builder(TransaksiPembelian.this);
                        builder.setMessage("Terjadi Kesalahan, Data Transaksi Gagal Disimpan!")
                                .setNegativeButton("Retry", null).create().show();
                    }
                }
        }

    }
}

