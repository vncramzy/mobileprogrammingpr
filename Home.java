package com.example.v.cobacobautsmobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.net.Inet4Address;

public class Home extends AppCompatActivity {
    ImageButton IVMaster,IVTransaksi,IVLaporan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.home);
    IVMaster = (ImageButton) findViewById(R.id.IVMaster);
    IVTransaksi = (ImageButton) findViewById(R.id.IVTransaksi);
    IVLaporan = (ImageButton) findViewById(R.id.IVLaporan);

    IVMaster.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent pindah = new Intent(Home.this,MenuMaster.class);
            startActivity(pindah);
        }
    });
    IVTransaksi.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent pindah = new Intent(Home.this,MenuTransaksi.class);
            startActivity(pindah);
        }
    });
    IVLaporan.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent pindah = new Intent(Home.this,MenuLaporan.class);
            startActivity(pindah);
        }
    });

    }
}