package com.example.v.cobacobautsmobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

public class MenuTransaksi extends AppCompatActivity {
    ImageButton IVPenjualan,IVPembelian;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menutransaksi);
        IVPenjualan = (ImageButton)findViewById(R.id.IVPenjualan);
        IVPembelian = (ImageButton)findViewById(R.id.IVPembelian);

        IVPembelian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(MenuTransaksi.this,TransaksiPembelian.class);
                startActivity(pindah);
            }
        });
        IVPenjualan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(MenuTransaksi.this,TransaksiPenjualan.class);
                startActivity(pindah);
            }
        });
    }
}