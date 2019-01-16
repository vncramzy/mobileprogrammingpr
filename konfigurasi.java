package com.example.v.cobacobautsmobile;

    public class konfigurasi {

        //Dibawah ini merupakan Pengalamatan dimana Lokasi Skrip CRUD PHP disimpan
        //Pada tutorial Kali ini, karena kita membuat localhost maka alamatnya tertuju ke IP komputer
        //dimana File PHP tersebut berada
        //PENTING! JANGAN LUPA GANTI IP SESUAI DENGAN IP KOMPUTER DIMANA DATA PHP BERADA
        public static final String RAMA_URL_TAMBAHBARANG = "http://192.168.1.201/untukandroid/tambahBarang.php";
        public static final String RAMA_URL_TAMPILBARANG = "http://192.168.1.201/untukandroid/tampilSemuaBarang.php";
        public static final String RAMA_URL_TARIKDATABARANG = "http://192.168.1.201/untukandroid/tampilBarang.php?kode=";
        public static final String RAMA_URL_UPDATEBARANG = "http://192.168.1.201/untukandroid/updateBarang.php";
        public static final String RAMA_URL_DELETEBARANG = "http://192.168.1.201/untukandroid/hapusBarang.php?kode=";

        public static final String RAMA_URL_TAMBAHKARYAWAN = "http://192.168.1.201/untukandroid/tambahKaryawan.php";
        public static final String RAMA_URL_TAMPILKARYAWAN = "http://192.168.1.201/untukandroid/tampilSemuaKaryawan.php";
        public static final String RAMA_URL_TARIKDATAKARYAWAN = "http://192.168.1.201/untukandroid/tampilKaryawan.php?kode=";
        public static final String RAMA_URL_UPDATEKARYAWAN = "http://192.168.1.201/untukandroid/updateKaryawan.php";
        public static final String RAMA_URL_DELETEKARYAWAN = "http://192.168.1.201/untukandroid/hapusKaryawan.php?kode=";

        public static final String RAMA_URL_TAMBAHSUPPLIER = "http://192.168.1.201/untukandroid/tambahSupplier.php";
        public static final String RAMA_URL_TAMPILSUPPLIER = "http://192.168.1.201/untukandroid/tampilSemuaSupplier.php";
        public static final String RAMA_URL_TARIKDATASUPPLIER = "http://192.168.1.201/untukandroid/tampilSupplier.php?kode=";
        public static final String RAMA_URL_UPDATESUPPLIER = "http://192.168.1.201/untukandroid/updateSupplier.php";
        public static final String RAMA_URL_DELETESUPPLIER = "http://192.168.1.201/untukandroid/hapusSupplier.php?kode=";

        //Dibawah ini merupakan Kunci yang akan digunakan untuk mengirim permintaan ke Skrip PHP
//        public static final String KEY_EMP_KODE = "kode";
//        public static final String KEY_EMP_NAMA = "nama";
//        public static final String KEY_EMP_SATUAN = "satuan";
//        public static final String KEY_EMP_HARGAJUAL = "hargajual";
//        public static final String KEY_EMP_HARGABELI = "hargabeli";
//        public static final String KEY_EMP_JUMLAH = "jumlah";
        public static final String RAMA_REQUEST_KODEBARANG = "kode";
        public static final String RAMA_REQUEST_NAMABARANG = "nama";
        public static final String RAMA_REQUEST_SATUANBARANG = "satuan";
        public static final String RAMA_REQUEST_HARGAJUALBARANG = "hargajual";
        public static final String RAMA_REQUEST_HARGABELIBARANG = "hargabeli";
        public static final String RAMA_REQUEST_JUMLAHBARANG = "jumlah";

        //JSON Tags
//        public static final String TAG_JSON_ARRAY="result";
//        public static final String TAG_KODE = "kode";
//        public static final String TAG_NAMA = "nama";
//        public static final String TAG_SATUAN = "satuan";
//        public static final String TAG_HARGAJUAL = "hargajual";
//        public static final String TAG_HARGABELI = "hargabeli";
//        public static final String TAG_JUMLAH = "jumlah";
        public static final String RAMA_JSON_ARRAY = "result";
        public static final String RAMA_JSON_KODEBARANG = "kode";
        public static final String RAMA_JSON_NAMABARANG = "nama";
        public static final String RAMA_JSON_SATUANBARANG = "satuan";
        public static final String RAMA_JSON_HARGAJUALBARANG = "hargajual";
        public static final String RAMA_JSON_HARGABELIBARANG = "hargabeli";
        public static final String RAMA_JSON_JUMLAHBARANG = "jumlah";

        //UNTUK NARIK DATA PER 1 KODE
        public static final String RAMA_BARANG_KODE = "BARANG_KODE";
    }
