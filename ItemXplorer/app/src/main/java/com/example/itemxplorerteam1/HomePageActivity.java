package com.example.itemxplorerteam1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomePageActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    TextView welcomeText;
    private CardView addItems, removeItems, searchItems, listInventory;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        welcomeText = findViewById(R.id.welcometext);
        addItems = (CardView)findViewById(R.id.addItemsCard);
        removeItems = (CardView) findViewById(R.id.removeItemsCard);
        searchItems = (CardView) findViewById(R.id.searchItemsCard);
        listInventory = (CardView) findViewById(R.id.listInventoryCard);


        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser users = firebaseAuth.getCurrentUser();
        welcomeText.setText("Welcome Xploring, Items!!" );


        addItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePageActivity.this, SaveItemActivity.class));
            }
        });

        removeItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePageActivity.this, RemoveItemActivity.class));
            }
        });

        searchItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePageActivity.this, SearchItemsActivity.class));
            }
        });

        listInventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePageActivity.this, InventoryActivity.class));
            }
        });
    }


    private void Logout()
    {
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(HomePageActivity.this, UserLoginActivity.class));
        Toast.makeText(HomePageActivity.this,"LOGOUT SUCCESSFUL", Toast.LENGTH_SHORT).show();

    }


@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.logoutMenu)
        {
            Logout();
        }
        return super.onOptionsItemSelected(item);
    }
}