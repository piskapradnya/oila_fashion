package com.example.oilafashion;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.oilafashion.database.AppDatabase;
import com.example.oilafashion.database.entitas.Oila;

import java.util.ArrayList;
import java.util.List;

public class Dashboard extends AppCompatActivity {
    Button btnAdd;
    private RecyclerView rvIndex;
    private AppDatabase database;
    private OilaAdapter oilaAdapter;
    private List<Oila> list = new ArrayList<>();
    private AlertDialog.Builder dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        rvIndex = findViewById(R.id.rv_oila);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                intent = new Intent(getApplicationContext(), ActivityForm.class);
                startActivity(intent);
            }
        });

        database = AppDatabase.getInstance(getApplicationContext());
        list.clear();
        list.addAll(database.oilaDao().getAll());
        oilaAdapter = new OilaAdapter(getApplicationContext(), list);
        oilaAdapter.setDialog(new OilaAdapter.Dialog(){
            @Override
            public void onClick(int position){
                final CharSequence[] dialogItem = {"Edit Produk", "Hapus Produk"};
                dialog = new AlertDialog.Builder(Dashboard.this);
                dialog.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        switch (i){
                            case 0:
                                Intent intent = new Intent(Dashboard.this, ActivityForm.class);
                                intent.putExtra("id_oila", list.get(position).id_oila);
                                startActivity(intent);
                                break;
                            case 1:
                                Oila oila = list.get(position);
                                database.oilaDao().delete(oila);
                                onStart();
                                break;
                        }
                    }
                });
                dialog.show();

            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false);
        rvIndex.setLayoutManager(layoutManager);
        rvIndex.setAdapter(oilaAdapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Dashboard.this,ActivityForm.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        list.clear();
        list.addAll(database.oilaDao().getAll());
        oilaAdapter.notifyDataSetChanged();
    }
}


