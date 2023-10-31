package com.example.itemxplorerteam1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

public class ViewItemActivity extends AppCompatActivity {

    private TextView viewInventory, totalItemCount, totalSum;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item);


        viewInventory = findViewById(R.id.viewInventory);
        totalItemCount = findViewById(R.id.totalnoitem);
        totalSum = findViewById(R.id.totalsum);
        recyclerView = findViewById(R.id.recyclerViews);


        viewInventory.setText("Items Count");
        totalItemCount.setText("0");
        totalSum.setText("0");


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


    }
}


