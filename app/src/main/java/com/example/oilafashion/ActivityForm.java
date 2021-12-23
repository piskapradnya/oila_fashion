package com.example.oilafashion;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Size;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oilafashion.database.AppDatabase;
import com.example.oilafashion.database.entitas.Oila;

public class ActivityForm extends AppCompatActivity implements View.OnClickListener {

    EditText editNamaBarang, editWarna, editHarga;
    SeekBar seekBarStock;
    TextView stockSeekbar;
    RadioButton radioAtasan, radioBawahan, rb;
    RadioGroup rgKategori;
    Button btnTampil;
    CheckBox cb1, cb2, cb3, cb4, cb5, cb6;
    String size ="";

    private AppDatabase database;
    private boolean isEdit = false;
    private int id_oila = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        database = AppDatabase.getInstance(getApplicationContext());


        editNamaBarang = (EditText) findViewById(R.id.editBarang);
        editWarna = (EditText) findViewById(R.id.editWarna);
        editHarga = (EditText) findViewById(R.id.editHarga);

        radioAtasan = (RadioButton) findViewById(R.id.radioAtasan);
        radioBawahan = (RadioButton) findViewById(R.id.radioBawahan);
        rgKategori = (RadioGroup) findViewById(R.id.rGroupKategori);

        cb1 = (CheckBox) findViewById(R.id.Size1);
        cb2 = (CheckBox) findViewById(R.id.Size2);
        cb3 = (CheckBox) findViewById(R.id.Size3);
        cb4 = (CheckBox) findViewById(R.id.Size4);
        cb5 = (CheckBox) findViewById(R.id.Size5);
        cb6 = (CheckBox) findViewById(R.id.Size6);


        btnTampil = (Button) findViewById(R.id.btnTampilkan);
        btnTampil.setOnClickListener(this);

        seekBarStock = (SeekBar) findViewById(R.id.seekbarStock);
        stockSeekbar = (TextView) findViewById(R.id.stockSeekbar);

        Intent intent = getIntent();
        id_oila = intent.getIntExtra( "id_oila", 0);
        Toast.makeText(ActivityForm.this,"" + String.valueOf(id_oila), Toast.LENGTH_SHORT).show();
        if(id_oila>0) {
            isEdit = true;
            Oila oila = database.oilaDao().get(id_oila);
            editNamaBarang.setText(oila.nama);
            editWarna.setText(oila.warna);
            editHarga.setText(oila.harga);
            stockSeekbar.setText(oila.stok);

            if (oila.kategori.toString().equals("Top")) {
                radioAtasan.setChecked(true);
            } else if (oila.kategori.toString().equals("Pants")) {
                radioBawahan.setChecked(true);
            }

        }else {
            isEdit = false;
        }

        seekBarStock.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                stockSeekbar.setText(String.valueOf(progress));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        int radio = rgKategori.getCheckedRadioButtonId();
        rb = findViewById(radio);

        String Nama = editNamaBarang.getText().toString();
        String Warna = editWarna.getText().toString();
        String Harga = editHarga.getText().toString();
        String Stock = stockSeekbar.getText().toString();
        String Kategori  = rb.getText().toString();


        //Check Box
        if (cb1.isChecked()) {
            size += "XS";
        }
        if (cb2.isChecked()) {
            size += "S";
        }
        if (cb3.isChecked()) {
           size += "M";
        }
        if (cb4.isChecked()) {
           size += "L";
        }
        if (cb5.isChecked()) {
            size += "XL";
        }
        if (cb6.isChecked()) {
            size += "XXL";
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        Toast.makeText(this, "Mohon Cek Kembali", Toast.LENGTH_SHORT).show();
        builder.setTitle("Pengecekan Ulang");
        builder.setMessage(
                 "NamaBarang : " + String.valueOf (Nama) + "\n" +
                 "Size : " + String.valueOf (size) + "\n" +
                 "Warna : " + String.valueOf (Warna) + "\n" +
                 "Harga : " + "Rp." + String.valueOf(Harga) +  "\n" +
                 "Kategori : " + String.valueOf(Kategori) + "\n" +
                 "Stok : " + String.valueOf(Stock) + "." + "\n" + "\n" + "Apakah anda yakin ingin menyimpan produk ini ?")
                .setPositiveButton("Simpan", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(), Dashboard.class);

                        if(isEdit){
                            database.oilaDao().update(id_oila, Nama, size, Warna, Harga, Kategori, Stock);
                            intent.putExtra("nama", Nama);
                            intent.putExtra("size", size.toString());
                            intent.putExtra("warna", Warna);
                            intent.putExtra("harga", Harga);
                            intent.putExtra("kategori", Kategori);
                            intent.putExtra("Stok", Stock);
                        }else{
                            database.oilaDao().insertOila(Nama, size, Warna, Harga, Kategori, Stock);
                            intent.putExtra("nama", Nama);
                            intent.putExtra("size", size.toString());
                            intent.putExtra("warna", Warna);
                            intent.putExtra("harga", Harga);
                            intent.putExtra("kategori", Kategori);
                            intent.putExtra("stok", Stock);
                        }

                        startActivity(intent);
                        finish();
                    }
                });

        builder.setNegativeButton(
                "Batalkan", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(ActivityForm.this, "Mohon input dengan benar", Toast.LENGTH_SHORT).show();
                    }
                });
        AlertDialog dialoghasil = builder.create();
        dialoghasil.show();
    }

    public void openMainActivity() {
        Intent intent = new Intent(this, Dashboard.class);
        startActivity(intent);
    };

    //Untuk menampilkan hasil input data diatas di Activity Tampil
    public void openActivityTampil() {
        Intent intent = new Intent(this, ActivityForm.class);

        String Nama = editNamaBarang.getText().toString();
        String size = "";
        String warna = editWarna.getText().toString();
        String Harga = editHarga.getText().toString();
        String stok = stockSeekbar.getText().toString();
        String kategori = "";

        //Radio Button Size
        if (radioAtasan.isChecked()) {
            kategori += "Top";
        }
        if (radioBawahan.isChecked()) {
            kategori += "Pants";
        }


        //Check Box
        if (cb1.isChecked()) {
            size += "XS";
        }
        if (cb2.isChecked()) {
            size += "S";
        }
        if (cb3.isChecked()) {
            size += "M";
        }
        if (cb4.isChecked()) {
           size += "L";
        }
        if (cb5.isChecked()) {
            size += "XL";
        }
        if (cb6.isChecked()) {
            size += "XXL";
        }

        intent.putExtra("nama", Nama);
        intent.putExtra("size", size);
        intent.putExtra("warna", warna);
        intent.putExtra("harga", Harga);
        intent.putExtra("kategori", kategori);
        intent.putExtra("stok", stok);
        startActivity(intent);

    }

    //Lifecycle
    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "Silahkan Masukan Data Produk", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "Input Data Sedang Berjalan",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this, "Mohon Menunggu",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(this, "Produk Berhasil Ditambahkan",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Aplikasi ditutup, Selamat Tinggal",Toast.LENGTH_SHORT).show();
    }
}

