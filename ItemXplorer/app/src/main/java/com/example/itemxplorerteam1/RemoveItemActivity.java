package com.example.itemxplorerteam1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RemoveItemActivity extends AppCompatActivity {

    public static TextView DeleteView;
    private FirebaseAuth firebaseAuth;
    Button scanQrCodeToRemoveProduct, removeProductBtn;
    DatabaseReference databaseReference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_items);

        firebaseAuth = FirebaseAuth.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        DeleteView = findViewById(R.id.barcodedelete);
        scanQrCodeToRemoveProduct = findViewById(R.id.scanQrCodeToRemoveProduct);
        removeProductBtn = findViewById(R.id.removeProductBtn);

        scanQrCodeToRemoveProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), BarCodeDeleteActivity.class));
            }
        });

        removeProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] deleteBarCode = DeleteView.getText().toString().split(":");
                if (deleteBarCode.length >= 2) {
                    String deleteBarCodeValue = deleteBarCode[1].trim();
                    if (deleteBarCodeValue.isEmpty()) {
                        Toast.makeText(RemoveItemActivity.this, "Please scan Barcode", Toast.LENGTH_SHORT).show();
                    } else {
                        deletefrmdatabase(deleteBarCodeValue);
                    }
                } else {
                    Toast.makeText(RemoveItemActivity.this, "Please scan Barcode", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void deletefrmdatabase(String deletebarcodevalue) {
        final FirebaseUser users = firebaseAuth.getCurrentUser();
        String finaluser = users.getEmail();
        String resultemail = finaluser.replace(".", "");

        DatabaseReference itemRef = databaseReference.child(resultemail).child("Items").child(deletebarcodevalue);
        itemRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    itemRef.removeValue();
                    DeleteView.setText("");
                    Toast.makeText(RemoveItemActivity.this, "Item is Deleted", Toast.LENGTH_SHORT).show();
                } else {

                    Toast.makeText(RemoveItemActivity.this, "Item with barcode " + deletebarcodevalue + " not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(RemoveItemActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}