package com.example.itemxplorerteam1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class UserRegisterActivity extends AppCompatActivity {

    private EditText editTextName, editTextEmail, editTextPassword,editTextcPassword;
    public Button UserRegisterBtn;
    private ProgressBar progressBar;

    private TextView gotoLoginPage;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        editTextName = findViewById(R.id.departmentName);
        editTextEmail = findViewById(R.id.emailRegister);
        editTextPassword = findViewById(R.id.passwordRegister);
        editTextcPassword= findViewById(R.id.confirmPassword);
        UserRegisterBtn= findViewById(R.id.button_register);
        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);
        gotoLoginPage=findViewById(R.id.gotoLoginPage);
        mAuth = FirebaseAuth.getInstance();

        UserRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        gotoLoginPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserRegisterActivity.this, UserLoginActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(this, HomePageActivity.class));
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
            editTextEmail.setError("Not a valid emailaddress");
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError("Its empty");
            editTextPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editTextPassword.setError("Less length");
            editTextPassword.requestFocus();
            return;
        }
        if(!password.equals(cpassword)){
            editTextcPassword.setError("Password Donot Match");
            editTextcPassword.requestFocus();
            return;
        }


        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

//                            addStudent();

                            final UserDetailsModal userDetailsModal = new UserDetailsModal(
                                    name,
                                    email

                            );
                            FirebaseUser usernameinfirebase = mAuth.getCurrentUser();
                            String UserID=usernameinfirebase.getEmail();
                            String resultemail = UserID.replace(".","");

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(resultemail).child("UserDetails")
                                    .setValue(userDetailsModal).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            progressBar.setVisibility(View.GONE);
                                            if (task.isSuccessful()) {

                                                Toast.makeText(UserRegisterActivity.this, "Registration Success", Toast.LENGTH_LONG).show();
                                                startActivity(new Intent(UserRegisterActivity.this, HomePageActivity.class));
                                            } else {
                                                //display a failure message
                                            }
                                        }
                                    });

                        } else {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(UserRegisterActivity.this, "Registration Failed", Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }
}