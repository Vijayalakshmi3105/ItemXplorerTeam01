package com.example.itemxplorerteam1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button registerButton = findViewById(R.id.signupBTN);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an Intent to navigate to the LoginActivity
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);

                // Start the LoginActivity
                startActivity(intent);
            }
        });
    }
}