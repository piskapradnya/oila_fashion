package com.example.oilafashion.database.entitas;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Oila {
    @PrimaryKey(autoGenerate = true)
    public int id_oila;

    public String nama;
    public String size;
    public String warna;
    public String harga;
    public String kategori;
    public String stok;
}