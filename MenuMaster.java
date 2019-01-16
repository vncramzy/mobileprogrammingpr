package com.example.v.cobacobautsmobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

public class MenuMaster extends AppCompatActivity {
    ImageButton IVKaryawan,IVBarang,IVSupplier;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menumaster);
        IVKaryawan = (ImageButton)findViewById(R.id.IVKaryawan);
        IVBarang = (ImageButton)findViewById(R.id.IVBarang);
        IVSupplier = (ImageButton)findViewById(R.id.IVSupplier);

        IVKaryawan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(MenuMaster.this,MasterKaryawan.class);
                startActivity(pindah);
            }
        });
        IVBarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(MenuMaster.this,MasterBarang.class);
                startActivity(pindah);
            }
        });
        IVSupplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(MenuMaster.this,MasterSupplier.class);
                startActivity(pindah);
            }
        });
    }
}

