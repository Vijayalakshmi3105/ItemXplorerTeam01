package com.example.itemxplorerteam1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;

import android.util.Patterns;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    public static class User {
        public String deptname, email;

        public User() {

        }

        public User(String name, String email) {
            this.deptname = name;
            this.email = email;
        }
    }

    private EditText editTextName, editTextEmail, editTextPassword, editTextcPassword;
    public Button UserRegisterBtn;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextName = findViewById(R.id.departmentName);
        editTextEmail = findViewById(R.id.emailRegister);
        editTextPassword = findViewById(R.id.passwordRegister);
        editTextcPassword = findViewById(R.id.confirmPassword);
        UserRegisterBtn = findViewById(R.id.button_register);
        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);

        mAuth = FirebaseAuth.getInstance();

        UserRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() != null) {
            // Handle the already logged-in user
        }
    }

    private void registerUser() {
        final String name = editTextName.getText().toString().trim();
        final String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString().trim();
        String cpassword = editTextcPassword.getText().toString().trim();

        if (email.isEmpty()) {
            editTextEmail.setError("It's empty");
            editTextEmail.requestFocus();
            return;
        }
        if (name.isEmpty()) {
            editTextName.setError("It's Empty");
            editTextName.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Not a valid email address");
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError("It's empty");
            editTextPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editTextPassword.setError("Password is too short");
            editTextPassword.requestFocus();
            return;
        }
        if (!password.equals(cpassword)) {
            editTextcPassword.setError("Passwords do not match");
            editTextcPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            final User user = new User(name, email);
                            FirebaseUser userInFirebase = mAuth.getCurrentUser();
                            String userID = userInFirebase.getEmail().replace(".", "");

                            FirebaseDatabase.getInstance("https://console.firebase.google.com/u/0/project/itemxplorer/authentication/users")
                                    .getReference("Users")
                                    .child(userID)
                                    .child("UserDetails")
                                    .setValue(user)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            progressBar.setVisibility(View.GONE);
                                            Toast.makeText(RegisterActivity.this, "Registration Success", Toast.LENGTH_LONG).show();
                                            startActivity(new Intent(RegisterActivity.this, DashboardActivity.class));
                                        }
                                    });
                        } else {
                            progressBar.setVisibility(View.GONE);
                            // Log the error message to identify the registration failure reason
                            String errorMessage = task.getException().getMessage();
                            Toast.makeText(RegisterActivity.this, "Registration Failed: " + errorMessage, Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
