package com.example.itemxplorerteam1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SaveItemActivity extends AppCompatActivity {
    private EditText productName, productCategory, productPrice, productLocation;
    private TextView productBarCode;
    private FirebaseAuth firebaseAuth;
    public static TextView resultText;
    Button scanQrBtn, saveProductBtn;
    DatabaseReference databaseReference;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_item);
        firebaseAuth = FirebaseAuth.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        resultText = findViewById(R.id.productBarCodeView);
        saveProductBtn = findViewById(R.id.saveProductBtn);
        scanQrBtn = findViewById(R.id.scanQrBtn);
        productName = findViewById(R.id.productName);
        productCategory = findViewById(R.id.productCategory);
        productPrice = findViewById(R.id.productPrice);
        productBarCode = findViewById(R.id.productBarCodeView);
        productLocation =findViewById(R.id.productLocationView);

        scanQrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), BarCodeActivity.class));
            }
        });

        saveProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                additem();
            }
        });
    }

    public void additem() {
        String itemnameValue = productName.getText().toString();
        String itemcategoryValue = productCategory.getText().toString();
        String itempriceValue = productPrice.getText().toString();
        String itemLocationValue = productLocation.getText().toString();
        String[] deletebarcode = productBarCode.getText().toString().split(":");

        if (deletebarcode.length >= 2) {
            String itembarcodeValue = deletebarcode[1].trim();
            final FirebaseUser users = firebaseAuth.getCurrentUser();
            String finaluser = users.getEmail();
            String resultemail = finaluser.replace(".", "");

            if (itembarcodeValue.isEmpty()) {
                productBarCode.setError("It's Empty");
                productBarCode.requestFocus();
                return;
            }

            DatabaseReference userItemsRef = databaseReference.child(resultemail).child("Items");
            userItemsRef.child(itembarcodeValue).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        Toast.makeText(SaveItemActivity.this, "Item with barcode " + itembarcodeValue + " already exists.", Toast.LENGTH_SHORT).show();
                    } else {
                        if (!TextUtils.isEmpty(itemnameValue) && !TextUtils.isEmpty(itemcategoryValue) && !TextUtils.isEmpty(itempriceValue) && !TextUtils.isEmpty(itemLocationValue)) {
                            ModalItems modalItems = new ModalItems(itemnameValue, itemcategoryValue, itempriceValue, itembarcodeValue, itemLocationValue);
                            userItemsRef.child(itembarcodeValue).setValue(modalItems);
                            Toast.makeText(SaveItemActivity.this, itemnameValue + " Added", Toast.LENGTH_SHORT).show();
                            productName.setText("");
                            productCategory.setText("");
                            productPrice.setText("");
                            productBarCode.setText("");
                            productLocation.setText("");
                        }
                        else
                        {
                            Toast.makeText(SaveItemActivity.this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        } else {
            Toast.makeText(SaveItemActivity.this, "Please Fill all the fields", Toast.LENGTH_SHORT).show();
        }
    }


    private void Logout()
    {
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(SaveItemActivity.this, UserLoginActivity.class));
        Toast.makeText(SaveItemActivity.this,"LOGOUT SUCCESSFUL", Toast.LENGTH_SHORT).show();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.logoutMenu){
            Logout();
        }
        return super.onOptionsItemSelected(item);
    }
}
