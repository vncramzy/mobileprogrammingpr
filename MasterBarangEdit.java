package com.example.v.cobacobautsmobile;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.v.cobacobautsmobile.Adapter.BarangAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MasterBarangEdit extends AppCompatActivity implements ListView.OnItemClickListener {

    //    private static String DBNAME = "UTS";
//    SQLiteDatabase db;
    ArrayList<String[]> dataBarang = new ArrayList<String[]>();
    ListView lv_barang;
    Button btnsave, btnsaveedit, btndelete, btnexit;
    EditText rama_editTextKode, rama_editTextNama, rama_editTextSatuan, rama_editTextHargajual, rama_editTextHargabeli, rama_editTextJumlah;
    private String JSON_STRING;
    private String Kode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.masterbarang);
        rama_editTextKode = (EditText) findViewById(R.id.txtkode);
        rama_editTextNama = (EditText) findViewById(R.id.txtnama);
        rama_editTextSatuan = (EditText) findViewById(R.id.txtsatuan);
        rama_editTextJumlah = (EditText) findViewById(R.id.txtstok);
        rama_editTextHargabeli = (EditText) findViewById(R.id.txtbeli);
        rama_editTextHargajual = (EditText) findViewById(R.id.txtjual);
        btnsave = findViewById(R.id.btnsave);
        btnsaveedit = findViewById(R.id.btnsaveedit);
        btndelete = findViewById(R.id.btndelete);
        btnexit = findViewById(R.id.btnexit);
        lv_barang = findViewById(R.id.lv_barang);
        lv_barang.setOnItemClickListener(this);

        getJSON();
        Intent intent1 = getIntent();
        Kode = intent1.getStringExtra("kodebarang");
        getBarang();

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                simpan();
//                createDB();
                addEmployee();
                finish();
                refreshpage();
                clearField();
            }
        });
        btnsave.setEnabled(false);
        rama_editTextKode.setEnabled(false);

        btnsaveedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateBarang();
                Intent pindah = new Intent(MasterBarangEdit.this,MasterBarang.class);
                startActivity(pindah);
                finish();
            }
        });

        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               confirmDeleteBarang();
            }
        });
        btnexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                refreshpage();
            }
        });
    }

    private void showBarang() {
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray("result");

            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                String rama_kode = jo.getString("kode");
                String rama_nama = jo.getString("nama");
                String rama_satuan = jo.getString("satuan");
                String rama_hargajual = jo.getString("hargajual");
                String rama_hargabeli = jo.getString("hargabeli");
                String rama_jumlah = jo.getString("jumlah");

                HashMap<String, String> barang = new HashMap<>();
                barang.put("kode", rama_kode);
                barang.put("nama", rama_nama);
                barang.put("satuan", rama_satuan);
                barang.put("hargajual", rama_hargajual);
                barang.put("hargabeli", rama_hargabeli);
                barang.put("jumlah", rama_jumlah);
                list.add(barang);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                MasterBarangEdit.this, list, R.layout.masterbaranglistview,
                new String[]{"kode", "nama", "satuan", "stok", "hargajual", "hargabeli", "jumlah"},
                new int[]{R.id.lblkode, R.id.lblnama, R.id.lblsatuan, R.id.lblstok, R.id.lbljual, R.id.lblbeli, R.id.lblstok});
        lv_barang.setAdapter(adapter);
    }

    private void getJSON() {
        class GetJSON extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                JSON_STRING = s;
                showBarang();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(konfigurasi.RAMA_URL_TAMPILBARANG);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    private void addEmployee(){
        final String rama_kode = rama_editTextKode.getText().toString().trim();
        final String rama_nama = rama_editTextNama.getText().toString().trim();
        final String rama_satuan = rama_editTextSatuan.getText().toString().trim();
        final String rama_hargajual = rama_editTextHargajual.getText().toString().trim();
        final String rama_hargabeli = rama_editTextHargabeli.getText().toString().trim();
        final String rama_jumlah = rama_editTextJumlah.getText().toString().trim();

        class AddEmployee extends AsyncTask<Void,Void,String>{

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Toast.makeText(MasterBarangEdit.this,s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> parameter = new HashMap<>();
                parameter.put("kode",rama_kode);
                parameter.put("nama",rama_nama);
                parameter.put("satuan",rama_satuan);
                parameter.put("hargajual",rama_hargajual);
                parameter.put("hargabeli",rama_hargabeli);
                parameter.put("jumlah",rama_jumlah);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(konfigurasi.RAMA_URL_TAMBAHBARANG, parameter);
                return res;
            }
        }

        AddEmployee ae = new AddEmployee();
        ae.execute();
    }

    private void clearField() {
        rama_editTextKode.setText("");
        rama_editTextNama.setText("");
        rama_editTextSatuan.setText("");
        rama_editTextJumlah.setText("");
        rama_editTextHargabeli.setText("");
        rama_editTextHargajual.setText("");
    }

    private void refreshpage() {
        overridePendingTransition(0, 0);
    }

    private void getBarang() {
        class GetBarang extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                showBarang(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(konfigurasi.RAMA_URL_TARIKDATABARANG, Kode);
                return s;
            }
        }
        GetBarang gb = new GetBarang();
        gb.execute();
    }

    private void showBarang(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray("result");
            JSONObject c = result.getJSONObject(0);
            String rama_kode = c.getString("kode");
            String rama_nama = c.getString("nama");
            String rama_satuan = c.getString("satuan");
            String rama_hargajual = c.getString("hargajual");
            String rama_hargabeli = c.getString("hargabeli");
            String rama_jumlah = c.getString("jumlah");

            rama_editTextKode.setText(rama_kode);
            rama_editTextNama.setText(rama_nama);
            rama_editTextSatuan.setText(rama_satuan);
            rama_editTextHargajual.setText(rama_hargajual);
            rama_editTextHargabeli.setText(rama_hargabeli);
            rama_editTextJumlah.setText(rama_jumlah);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void updateBarang() {
        final String rama_kode = rama_editTextKode.getText().toString().trim();
        final String rama_nama = rama_editTextNama.getText().toString().trim();
        final String rama_satuan = rama_editTextSatuan.getText().toString().trim();
        final String rama_hargajual = rama_editTextHargajual.getText().toString().trim();
        final String rama_hargabeli = rama_editTextHargabeli.getText().toString().trim();
        final String rama_jumlah = rama_editTextJumlah.getText().toString().trim();

        class UpdateBarang extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Toast.makeText(MasterBarangEdit.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("kode", rama_kode);
                hashMap.put("nama", rama_nama);
                hashMap.put("satuan", rama_satuan);
                hashMap.put("hargajual", rama_hargajual);
                hashMap.put("hargabeli", rama_hargabeli);
                hashMap.put("jumlah", rama_jumlah);

                RequestHandler rh = new RequestHandler();

                String s = rh.sendPostRequest(konfigurasi.RAMA_URL_UPDATEBARANG, hashMap);

                return s;
            }
        }

        UpdateBarang ub = new UpdateBarang();
        ub.execute();
    }

    private void deleteBarang() {
        class DeleteBarang extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Toast.makeText(MasterBarangEdit.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(konfigurasi.RAMA_URL_DELETEBARANG, Kode);
                return s;
            }
        }

        DeleteBarang db = new DeleteBarang();
        db.execute();
    }

    private void confirmDeleteBarang() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Apakah Kamu Yakin Ingin Menghapus Pegawai ini?");

        alertDialogBuilder.setPositiveButton("Ya",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        deleteBarang();
                        startActivity(new Intent(MasterBarangEdit.this, MasterBarang.class));
                        finish();
                    }
                });

        alertDialogBuilder.setNegativeButton("Tidak",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this,MasterBarang.class);
        HashMap<String, String> map = (HashMap) parent.getItemAtPosition(position);
        String Kode = map.get("kode").toString();
        intent.putExtra("kodebarang", Kode.toString());
        startActivity(intent);
    }
}



//    @Override
//    public void onStart() {
//        super.onStart();
//        createDB();
//        get_data();
//        btnsaveedit.setClickable(false);
//
//        BarangAdapter ka = new BarangAdapter(getApplicationContext(), dataBarang);
//        lv_barang.setAdapter(ka);
//        lv_barang.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
//                Intent intent = new Intent(getBaseContext(), MasterBarangEdit.class);
//                intent.putExtra("detail_barang", dataBarang.get(i));
//                startActivity(intent);
//                refreshpage();
//
//            }
//        });
//    }
