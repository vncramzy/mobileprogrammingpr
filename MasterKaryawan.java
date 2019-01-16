package com.example.v.cobacobautsmobile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
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

import com.example.v.cobacobautsmobile.Adapter.KaryawanAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MasterKaryawan extends AppCompatActivity implements ListView.OnItemClickListener {

    private static String DBNAME = "UTS";
    SQLiteDatabase db;
    ArrayList<String[]> dataKaryawan = new ArrayList<String[]>();
    ListView lv_karyawan;
    Button btnsave,btnsaveedit,btndelete,btnexit;
    EditText rama_txtkode,rama_txtnama,rama_txttelp,rama_txtalamat,rama_txtpassword;
    private String JSON_STRING;
    private String Kode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.masterkaryawan);
        rama_txtkode = (EditText)findViewById(R.id.txtkode);
        rama_txtnama = (EditText)findViewById(R.id.txtnama);
        rama_txttelp = (EditText)findViewById(R.id.txttelp);
        rama_txtalamat = (EditText)findViewById(R.id.txtalamat);
        rama_txtpassword = (EditText)findViewById(R.id.txtpassword);
        btnsave = findViewById(R.id.btnsave);
        btnsaveedit = findViewById(R.id.btnsaveedit);
        btndelete = findViewById(R.id.btndelete);
        btnexit = findViewById(R.id.btnexit);
        lv_karyawan = findViewById(R.id.lv_karyawan);
        lv_karyawan.setOnItemClickListener(this);

        getJSON();
        btnsaveedit.setEnabled(false);
        btndelete.setEnabled(false);

        btnsave.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View v){
               addKaryawan();
               finish();
               refreshpage();
               startActivity(getIntent());
               clearField();
           }
        });
        btnsaveedit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
//                update();
            }
        });
        btndelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
//                hapus();
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

    private void showKaryawan() {
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
                String rama_password = jo.getString("password");

                HashMap<String, String> karyawan = new HashMap<>();
                karyawan.put("kode", rama_kode);
                karyawan.put("nama", rama_nama);
                karyawan.put("telp", rama_telp);
                karyawan.put("alamat", rama_alamat);
                karyawan.put("password", rama_password);
                list.add(karyawan);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                MasterKaryawan.this, list, R.layout.masterkaryawanlistview,
                new String[]{"kode", "nama", "telp", "alamat", "password"},
                new int[]{R.id.lblkode, R.id.lblnama, R.id.lbltelp, R.id.lblalamat, R.id.lblpassword});
        lv_karyawan.setAdapter(adapter);
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
                showKaryawan();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(konfigurasi.RAMA_URL_TAMPILKARYAWAN);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    private void addKaryawan(){
        final String rama_kode = rama_txtkode.getText().toString().trim();
        final String rama_nama = rama_txtnama.getText().toString().trim();
        final String rama_telp = rama_txttelp.getText().toString().trim();
        final String rama_alamat = rama_txtalamat.getText().toString().trim();
        final String rama_password = rama_txtpassword.getText().toString().trim();

        class AddEmployee extends AsyncTask<Void,Void,String>{

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Toast.makeText(MasterKaryawan.this,s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> parameter = new HashMap<>();
                parameter.put("kode",rama_kode);
                parameter.put("nama",rama_nama);
                parameter.put("telp",rama_telp);
                parameter.put("alamat",rama_alamat);
                parameter.put("password",rama_password);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(konfigurasi.RAMA_URL_TAMBAHKARYAWAN, parameter);
                return res;
            }
        }

        AddEmployee ae = new AddEmployee();
        ae.execute();
    }

    private void clearField() {
        rama_txtkode.setText("");
        rama_txtnama.setText("");
        rama_txttelp.setText("");
        rama_txtalamat.setText("");
        rama_txtpassword.setText("");
    }

    private void refreshpage() {
        overridePendingTransition(0, 0);
    }

    private void getKaryawan() {
        class GetKaryawan extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                showKaryawan(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(konfigurasi.RAMA_URL_TARIKDATAKARYAWAN, Kode);
                return s;
            }
        }
        GetKaryawan gk = new GetKaryawan();
        gk.execute();
    }

    private void showKaryawan(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray("result");
            JSONObject c = result.getJSONObject(0);
            String rama_kode = c.getString("kode");
            String rama_nama = c.getString("nama");
            String rama_telp = c.getString("telp");
            String rama_alamat = c.getString("alamat");
            String rama_password = c.getString("password");

            rama_txtkode.setText(rama_kode);
            rama_txtnama.setText(rama_nama);
            rama_txttelp.setText(rama_telp);
            rama_txtalamat.setText(rama_alamat);
            rama_txtpassword.setText(rama_password);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this,MasterKaryawanEdit.class);
        HashMap<String, String> map = (HashMap) parent.getItemAtPosition(position);
        String Kode = map.get("kode").toString();
        intent.putExtra("kodekaryawan", Kode.toString());
        startActivity(intent);
    }

}
