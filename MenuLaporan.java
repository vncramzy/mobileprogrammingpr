package com.example.v.cobacobautsmobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

public class MenuLaporan extends AppCompatActivity {
    ImageButton IVLPenjualan,IVLDPenjualan,IVLPembelian,IVLDPembelian,IVLStok;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menulaporan);
        IVLPembelian = (ImageButton)findViewById(R.id.IVLPembelian);
//        IVLDPembelian = (ImageButton)findViewById(R.id.IVLDPembelian);
//        IVLDPenjualan = (ImageButton)findViewById(R.id.IVLDPenjualan);
        IVLPenjualan = (ImageButton)findViewById(R.id.IVLPenjualan);
        IVLStok = (ImageButton)findViewById(R.id.IVLStok);

        IVLPembelian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(MenuLaporan.this,LaporanPembelian.class);
                startActivity(pindah);
            }
        });
        IVLPenjualan.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(MenuLaporan.this, LaporanPenjualan.class);
                startActivity(pindah);
            }
        });
        IVLStok.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent pindah = new Intent(MenuLaporan.this,LaporanStok.class);
                startActivity(pindah);
            }
        });
    }
}