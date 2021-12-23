package com.example.oilafashion.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Query;

import com.example.oilafashion.database.entitas.Oila;

import java.util.List;

@Dao
public interface OilaDao {
    @Query("SELECT * FROM oila")
    List<Oila> getAll();

    @Query("INSERT INTO oila (nama, size, warna, harga, kategori, stok) VALUES(:nama,:size,:warna,:harga,:kategori,:stok)")
    void insertOila(String nama, String size, String warna, String harga, String kategori, String stok);

    @Query("UPDATE oila SET nama=:nama , size=:size, warna=:warna, harga=:harga, kategori=:kategori, stok=:stok where id_oila=:id_oila")
    void update(int id_oila, String nama, String size, String warna, String harga, String kategori, String stok);

    @Query("SELECT * FROM oila WHERE id_oila=:id_oila")
    Oila get(int id_oila);

    @Delete
    void delete(Oila oila);

}
