package com.example.itemxplorerteam1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button loginButton = findViewById(R.id.LoginBTN);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an Intent to navigate to the DashboardActivity
                Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);

                // Start the DashboardActivity
                startActivity(intent);
            }
        });
    }

    public void forgotPasswordClick(View view) {
        // Create an Intent to navigate to the ForgotPasswordActivity
        Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);

        // Start the ForgotPasswordActivity
        startActivity(intent);
    }
}