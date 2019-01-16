package com.example.v.cobacobautsmobile;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.v.cobacobautsmobile.Adapter.dataNotaPenjualan;
import com.example.v.cobacobautsmobile.MainActivity;
import com.example.v.cobacobautsmobile.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public class CetakTransaksiJual extends AppCompatActivity {
//   String _idTransaksiPenjualan, _tglTransaksiJual, _costumerTransaksijual;
//    int _totalHarga = 0;
//    int _totalJumlah    = 0;
//    private AdapterView listview;
//    ArrayList<dataNotaPenjualan> listData = new ArrayList<dataNotaPenjualan>();
//
//    private Button btnPrintNotaPenjualan;
//    private TextView txtTanggal, totalJumlahPenjualan, totalHargaPenjualan, txtCustomerPenjualan;
//
//    BluetoothAdapter mBluetoothAdapter;
//    BluetoothSocket mmSocket;
//    BluetoothDevice mmDevice;
//    OutputStream mmOutputStream;
//    InputStream mmInputStream;
//    Thread workerThread;
//
//    byte [] readBuffer;
//    int readBufferPosition;
//    volatile boolean stopWorker;
//    String msg;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.notapenjualan);
//
//        listview        = (ListView) findViewById(R.id.listDetailPenjualan);
//        totalJumlahPenjualan = (TextView)findViewById(R.id.notaTotalJumlahPenjualan);
//        totalHargaPenjualan  = (TextView)findViewById(R.id.notaTotalHargaPenjualan);
//        txtCustomerPenjualan = (TextView)findViewById(R.id.notaTxtCostumer);
//        txtTanggal           = (TextView)findViewById(R.id.notaTanggalPenjualan);
////        mylabelpenjualan  = (TextView)findViewById(R.id.mylabelpenjualan);
//
//        btnPrintNotaPenjualan = (Button) findViewById(R.id.btnPrintNotaPenjualan);
//
//        Intent intent = getIntent();
//        _noTransaksiPenjualan           = intent.getStringExtra("notransaksi");
//        _tglTransaksiPenjualan          = intent.getStringExtra("tgltransaksi");
//        _costumerTransaksiPenjualan     = intent.getStringExtra("costumerTransaksi");
//
//        setTitle("Nomor Transaksi : " + _noTransaksiPenjualan);
//        txtTanggal.setText(_tglTransaksiPenjualan);
//        txtCustomerPenjualan.setText(_costumerTransaksiPenjualan);
//        fetchData();
//
//        btnPrintNotaPenjualan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                try {
//                    findBT();
//                    openBT();
//                    sendData();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }
//    public void fetchData(){
//        _totalJumlah    = 0;
//        Cursor cursor = MainActivity.sqLiteHelper.getData("SELECT a.id, a.nomor_transaksi, a.jumlah, b.nama, b.harga_jual, b.kode_barang, b.id as id_barang FROM PENJUALAN_DETAIL a INNER JOIN BARANG b ON(a.id_barang=b.id) WHERE a.nomor_transaksi='"+ _noTransaksiPenjualan +"'");
//        listData.clear();
//        while (cursor.moveToNext()) {
//            int _id              = cursor.getInt(0);
//            String _notrans      = cursor.getString(1);
//            int _jumlah          = cursor.getInt(2);
//            String _barang       = cursor.getString(3);
//            int _harga           = cursor.getInt(4);
//            String _kodebarang       = cursor.getString(5);
//            int _idbarang          = cursor.getInt(6);
//            int _total           = _jumlah*_harga;
//
//            _totalJumlah += _jumlah;
//            _totalHarga  += _total;
//
//            DataModelPenjualanDetail dataModel =  new DataModelPenjualanDetail(_id, _notrans, _idbarang, _kodebarang, _barang, _harga, _jumlah, _total);
//            listData.add(dataModel);
//        }
//        ListPenjualanDetailAdapter adapter = new ListPenjualanDetailAdapter(this, listData);
//        listview.setAdapter(adapter);
//
//        totalJumlahPenjualan.setText(String.valueOf(_totalJumlah));
//        totalHargaPenjualan.setText(String.valueOf(_totalHarga));
//
//    }
//
//    //-------------Bluetooth-------------------------------------
//    void findBT(){
//        try {
//            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//
//            if (mBluetoothAdapter.isEnabled()) {
//                Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//                startActivityForResult(enableBluetooth, 0);
//            }
//
//            Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
//
//            if (pairedDevices.size() > 0) {
//                for (BluetoothDevice device : pairedDevices) {
//                    if (device.getName().equals("printer001")) {
//                        mmDevice = device;
//                        break;
//                    }
//                }
//            }
//
//            Toast.makeText(getApplicationContext(), "Bluetooth device found", Toast.LENGTH_LONG).show();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//
//    void beginListenForData(){
//        try{
//            final Handler handler = new Handler();
//            final byte delimiter = 10;
//
//            stopWorker = false;
//            readBufferPosition = 0;
//            readBuffer = new byte[1024];
//
//            workerThread = new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    while (!Thread.currentThread().isInterrupted() && !stopWorker) {
//                        try {
//                            int bytesAvailable = mmInputStream.available();
//
//                            if (bytesAvailable > 0) {
//                                byte[] packetBytes = new byte[bytesAvailable];
//                                mmInputStream.read(packetBytes);
//
//                                for (int i = 0; i < bytesAvailable; i++) {
//                                    byte b = packetBytes[i];
//                                    if (b == delimiter) {
//                                        byte[] encodedBytes = new byte[readBufferPosition];
//                                        System.arraycopy(
//                                                readBuffer, 0,
//                                                encodedBytes, 0,
//                                                encodedBytes.length
//                                        );
//
//                                        final String data = new String(encodedBytes, "US-ASCII");
//                                        readBufferPosition = 0;
//
//                                        handler.post(new Runnable() {
//                                            @Override
//                                            public void run() {
////                                                myLabel.setText(data);
//                                            }
//                                        });
//                                    } else {
//                                        readBuffer[readBufferPosition++] = b;
//                                    }
//                                }
//                            }
//                        } catch (IOException ex) {
//                            stopWorker = true;
//                        }
//                    }
//                }
//            });
//
//            workerThread.start();
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//
//    void openBT() throws IOException{
//        try {
//            UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
//            mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);
//            mmSocket.connect();
//            mmOutputStream = mmSocket.getOutputStream();
//            mmInputStream = mmSocket.getInputStream();
//
//            beginListenForData();
//            Toast.makeText(getApplicationContext(), "Bluetooth Opened", Toast.LENGTH_LONG).show();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//
//    void sendData() throws IOException {
//        try {
//
//            // the text typed by the user
//            msg = "==========================STRUK=============================";
//            msg += "\n";
//            msg += "Tanggal         : "+ txtTanggal.getText().toString();
//            msg += "\n";
//            msg += "No Transaksi    : "+ _noTransaksiPenjualan;
//            msg += "\n";
//            msg += "Costumer        : "+ txtCustomerPenjualan.getText().toString();
//            msg += "\n";
//            msg +=  "============================================================";
//            msg += "\n";
//            msg += "Kode Barang | Nama Barang | Jumlah | Total";
//            msg += "\n";
//            msg +=  "============================================================";
//            msg += "\n";
//            for(int a = 0; a < listData.size(); a++){
//                msg += listData.get(a).getKodeBarang() +" | "+ listData.get(a).getBarang() +" | "+ listData.get(a).getJumlah() +" | "+ listData.get(a).getTotal();
//                msg += "\n";
//            }
//            msg +=  "============================================================";
//            msg += "\n";
//            msg += "Total Jumlah : "+ _totalJumlah;
//            msg += "\n";
//            msg += "Total Harga : "+ _totalHarga;
//            msg += "\n";
//            msg +=  "============================================================";
//
//            mmOutputStream.write(msg.getBytes());
//
//            // tell the user data were sent
////            mylabelpenjualan.setText("Data sent.");
//            Toast.makeText(getApplicationContext(), "Data sent.", Toast.LENGTH_LONG).show();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

}
