package com.example.itemxplorerteam1;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button loginButton = findViewById(R.id.Login); // Use meaningful variable names (e.g., loginButton) for better readability
        Button registerButton = findViewById(R.id.Register);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an Intent to navigate to the LoginActivity
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);

                // Start the LoginActivity
                startActivity(intent);
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an Intent to navigate to the RegisterActivity
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);

                // Start the RegisterActivity
                startActivity(intent);
            }
        });
    }
}
