package com.example.itemxplorerteam1;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {

    private EditText itemname, itemcategory, itemlocation, itemprice;
    Button scanbutton, additemtodatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        itemname = findViewById(R.id.edititemname);
        itemcategory = findViewById(R.id.editcategory);
        itemlocation = findViewById(R.id.editlocation);
        itemprice = findViewById(R.id.editprice);

        scanbutton = findViewById(R.id.buttonscan);
        additemtodatabase = findViewById(R.id.additembuttontodatabase);

        scanbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ViewItemActivity.class));
            }
        });

        additemtodatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                additem();
            }
        });
    }

    public void additem() {
        String itemnameValue = itemname.getText().toString();
        String itemcategoryValue = itemcategory.getText().toString();
        String itemlocationValue = itemlocation.getText().toString();
        String itempriceValue = itemprice.getText().toString();

        if (!TextUtils.isEmpty(itemnameValue) && !TextUtils.isEmpty(itemcategoryValue)
                && !TextUtils.isEmpty(itemlocationValue) && !TextUtils.isEmpty(itempriceValue)) {
            Intent intent = new Intent(AddActivity.this, ViewItemActivity.class);
            intent.putExtra("item_name", itemnameValue);
            intent.putExtra("item_category", itemcategoryValue);
            intent.putExtra("item_location", itemlocationValue);
            intent.putExtra("item_price", itempriceValue);
            // No need to include itembarcode, as it's not used

            startActivity(intent);
        } else {
            Toast.makeText(AddActivity.this, "Please Fill all the fields", Toast.LENGTH_SHORT).show();
        }
    }
}
