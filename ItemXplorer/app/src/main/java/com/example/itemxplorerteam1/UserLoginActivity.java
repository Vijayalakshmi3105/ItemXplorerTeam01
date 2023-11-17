package com.example.itemxplorerteam1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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

public class UserLoginActivity extends AppCompatActivity {

    private EditText Email;
    private EditText Password;
    private Button Login;
    private TextView passwordreset,gotoregisterpage;
    private ProgressBar progressBar;

    private FirebaseAuth auth;
    private ProgressDialog processDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);

        Email = (EditText) findViewById(R.id.emailSignIn);
        Password = (EditText) findViewById(R.id.password);
        Login = (Button) findViewById(R.id.Login);
        gotoregisterpage=(TextView)findViewById(R.id.gotoregisterpage);

        passwordreset = findViewById(R.id.forgotpassword);
        progressBar = (ProgressBar) findViewById(R.id.progressbars);
        progressBar.setVisibility(View.GONE);
        auth = FirebaseAuth.getInstance();
        processDialog = new ProgressDialog(this);



        gotoregisterpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserLoginActivity.this, UserRegisterActivity.class));
            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(Email.getText().toString(), Password.getText().toString());
            }

        });

        passwordreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetpasword();
            }
        });
    }

    public void resetpasword(){
        startActivity(new Intent(UserLoginActivity.this, ForgetPasswordActivity.class));
    }




    public void validate(String userEmail, String userPassword){

        final String email = Email.getText().toString();
        String password = Password.getText().toString().trim();
        if (email.isEmpty()) {
            Email.setError("It's empty");
            Email.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            Password.setError("Its empty");
            Password.requestFocus();
            return;
        }
        processDialog.setMessage("Authenticating Your Email");
        processDialog.show();

        auth.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    processDialog.dismiss();
                    Toast.makeText(UserLoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(UserLoginActivity.this, HomePageActivity.class));
                }
                else{
                    Toast.makeText(UserLoginActivity.this,"Login Failed", Toast.LENGTH_SHORT).show();
                    processDialog.dismiss();
                }
            }
        });
    }
}