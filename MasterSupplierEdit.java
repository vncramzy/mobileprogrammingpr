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

import com.example.v.cobacobautsmobile.Adapter.SupplierAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MasterSupplierEdit extends AppCompatActivity implements ListView.OnItemClickListener{

    ArrayList<String[]> dataSupplier = new ArrayList<String[]>();
    ListView lv_supplier;
    Button btnsave,btnsaveedit,btndelete,btnexit;
    EditText rama_txtkode,rama_txtnama,rama_txttelp,rama_txtalamat;
    private String JSON_STRING;
    private String Kode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mastersupplier);
        rama_txtkode = (EditText)findViewById(R.id.txtkode);
        rama_txtnama = (EditText)findViewById(R.id.txtnama);
        rama_txttelp = (EditText)findViewById(R.id.txttelp);
        rama_txtalamat = (EditText)findViewById(R.id.txtalamat);

        btnsave = findViewById(R.id.btnsave);
        btnsaveedit = findViewById(R.id.btnsaveedit);
        btndelete = findViewById(R.id.btndelete);
        btnexit = findViewById(R.id.btnexit);
        lv_supplier = findViewById(R.id.lv_supplier);
        lv_supplier.setOnItemClickListener(this);

        getJSON();
        Intent intent1 = getIntent();
        Kode = intent1.getStringExtra("kodesupplier");
        getSupplier();

        btnsave.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                addSupplier();
                finish();
                refreshpage();
                clearField();
            }
        });
        btnsave.setEnabled(false);
        rama_txtkode.setEnabled(false);

        btnsaveedit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                updateSupplier();
                Intent pindah = new Intent(MasterSupplierEdit.this,MasterKaryawan.class);
                startActivity(pindah);
                finish();
            }
        });
        btndelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                confirmDeleteSupplier();
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

    private void showSupplier() {
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray("result");

            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                String rama_kode = jo.getString("kode");
                String rama_nama = jo.getString("nama");
                String rama_telp = jo.getString("telp");
                String rama_alamat = jo.getString("alamat");

                HashMap<String, String> supplier = new HashMap<>();
                supplier.put("kode", rama_kode);
                supplier.put("nama", rama_nama);
                supplier.put("telp", rama_telp);
                supplier.put("alamat", rama_alamat);
                list.add(supplier);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                MasterSupplierEdit.this, list, R.layout.mastersupplierlistview,
                new String[]{"kode", "nama", "telp", "alamat"},
                new int[]{R.id.lblkode, R.id.lblnama, R.id.lbltelp, R.id.lblalamat});
        lv_supplier.setAdapter(adapter);
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
                showSupplier();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(konfigurasi.RAMA_URL_TAMPILSUPPLIER);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    private void addSupplier(){
        final String rama_kode = rama_txtkode.getText().toString().trim();
        final String rama_nama = rama_txtnama.getText().toString().trim();
        final String rama_telp = rama_txttelp.getText().toString().trim();
        final String rama_alamat = rama_txtalamat.getText().toString().trim();

        class AddSupplier extends AsyncTask<Void,Void,String>{

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Toast.makeText(MasterSupplierEdit.this,s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> parameter = new HashMap<>();
                parameter.put("kode",rama_kode);
                parameter.put("nama",rama_nama);
                parameter.put("telp",rama_telp);
                parameter.put("alamat",rama_alamat);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(konfigurasi.RAMA_URL_TAMBAHSUPPLIER, parameter);
                return res;
            }
        }

        AddSupplier as = new AddSupplier();
        as.execute();
    }

    private void clearField() {
        rama_txtkode.setText("");
        rama_txtnama.setText("");
        rama_txttelp.setText("");
        rama_txtalamat.setText("");
    }

    private void refreshpage() {
        overridePendingTransition(0, 0);
    }

    private void getSupplier() {
        class GetSupplier extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                showSupplier(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(konfigurasi.RAMA_URL_TARIKDATASUPPLIER, Kode);
                return s;
            }
        }
        GetSupplier gs = new GetSupplier();
        gs.execute();
    }

    private void showSupplier(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray("result");
            JSONObject c = result.getJSONObject(0);
            String rama_kode = c.getString("kode");
            String rama_nama = c.getString("nama");
            String rama_telp = c.getString("telp");
            String rama_alamat = c.getString("alamat");

            rama_txtkode.setText(rama_kode);
            rama_txtnama.setText(rama_nama);
            rama_txttelp.setText(rama_telp);
            rama_txtalamat.setText(rama_alamat);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void updateSupplier() {
        final String rama_kode = rama_txtkode.getText().toString().trim();
        final String rama_nama = rama_txtnama.getText().toString().trim();
        final String rama_telp = rama_txttelp.getText().toString().trim();
        final String rama_alamat = rama_txtalamat.getText().toString().trim();

        class UpdateSupplier extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Toast.makeText(MasterSupplierEdit.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("kode", rama_kode);
                hashMap.put("nama", rama_nama);
                hashMap.put("telp", rama_telp);
                hashMap.put("alamat", rama_alamat);

                RequestHandler rh = new RequestHandler();

                String s = rh.sendPostRequest(konfigurasi.RAMA_URL_UPDATESUPPLIER, hashMap);

                return s;
            }
        }

        UpdateSupplier us = new UpdateSupplier();
        us.execute();
    }

    private void deleteSupplier() {
        class DeleteKaryawan extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Toast.makeText(MasterSupplierEdit.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(konfigurasi.RAMA_URL_DELETESUPPLIER, Kode);
                return s;
            }
        }

        DeleteKaryawan dk = new DeleteKaryawan();
        dk.execute();
    }

    private void confirmDeleteSupplier() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Apakah Kamu Yakin Ingin Menghapus Pegawai ini?");

        alertDialogBuilder.setPositiveButton("Ya",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        deleteSupplier();
                        startActivity(new Intent(MasterSupplierEdit.this, MasterKaryawan.class));
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
        Intent intent = new Intent(this,MasterSupplierEdit.class);
        HashMap<String, String> map = (HashMap) parent.getItemAtPosition(position);
        String Kode = map.get("kode").toString();
        intent.putExtra("kodesupplier", Kode.toString());
        startActivity(intent);
    }

}
