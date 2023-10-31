package com.example.itemxplorerteam1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Button button = findViewById(R.id.removeItemsBTN);
        Button addItembutton=findViewById(R.id.addItemsBTN);
        Button viewbtn = findViewById(R.id.viewItemsBTN);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an Intent to navigate to the RemoveItemsActivity
                Intent intent = new Intent(DashboardActivity.this, RemoveItemsActivity.class);

                // Start the RemoveItemsActivity
                startActivity(intent);
            }
        });




        addItembutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an Intent to navigate to the LoginActivity
                Intent intent = new Intent(DashboardActivity.this, AddActivity.class);

                // Start the LoginActivity
                startActivity(intent);
            }
        });

        viewbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an Intent to navigate to the LoginActivity
                Intent intent = new Intent(DashboardActivity.this, ViewItemActivity.class);

                // Start the LoginActivity
                startActivity(intent);
            }
        });



    }
}