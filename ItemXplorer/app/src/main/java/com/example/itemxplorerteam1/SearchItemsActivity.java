package com.example.itemxplorerteam1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class SearchItemsActivity extends AppCompatActivity {

    public static EditText resultsearcheview;
    private FirebaseAuth firebaseAuth;
    ImageButton scanQrToSearch;
    Button searchBtn;
    RecyclerView recyclerViewSearchProduct;
    DatabaseReference mainDbReference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_items);


        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser users = firebaseAuth.getCurrentUser();
        String userEmail=users.getEmail();
        String finalEmail = userEmail.replace(".","");
        mainDbReference = FirebaseDatabase.getInstance().getReference("Users").child(finalEmail).child("Items");
        resultsearcheview = findViewById(R.id.searchfield);
        scanQrToSearch = findViewById(R.id.scanQrToSearch);
        searchBtn = findViewById(R.id.searchBtn);

        recyclerViewSearchProduct = findViewById(R.id.recyclerViews);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerViewSearchProduct.setLayoutManager(manager);
        recyclerViewSearchProduct.setHasFixedSize(true);


        recyclerViewSearchProduct.setLayoutManager(new LinearLayoutManager(this));


        scanQrToSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), BarCodeSearchActivity.class));
            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchtext = resultsearcheview.getText().toString().trim();
                Log.d("SearchItemsActivity", searchtext);
                if(searchtext.isEmpty())
                {
                    Toast.makeText(SearchItemsActivity.this, "Please Scan Product Bar/Qr Code", Toast.LENGTH_SHORT).show();
                }
                else {
                    firebasesearch(searchtext);
                }

            }
        });
    }


    public void firebasesearch(String searchtext){
        Query firebaseSearchQuery = mainDbReference.orderByChild("productBarCode").startAt(searchtext).endAt(searchtext+"\uf8ff");

        firebaseSearchQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    Toast.makeText(SearchItemsActivity.this, "Item With Barcode : " + searchtext + " Does Not Exist..", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("SearchItemsActivity", "Database Error: " + databaseError.getMessage());
            }
        });

        FirebaseRecyclerOptions<ModalItems> options =
                new FirebaseRecyclerOptions.Builder<ModalItems>()
                        .setQuery(firebaseSearchQuery, ModalItems.class)
                        .build();

        FirebaseRecyclerAdapter<ModalItems, UsersViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<ModalItems, UsersViewHolder>(options) {
                    @NonNull
                    @Override
                    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemcard, parent, false);
                        return new UsersViewHolder(view);
                    }

                    @Override
                    protected void onBindViewHolder(@NonNull UsersViewHolder holder, int position, @NonNull ModalItems model) {
                        holder.setDetails(getApplicationContext(), model.getProductBarCode(), model.getProductCategory(), model.getProductName(), model.getProductPrice(),model.getProductLocation());
                    }
                };

        recyclerViewSearchProduct.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
    }

    public static class UsersViewHolder extends RecyclerView.ViewHolder{
        View mView;
        public UsersViewHolder(View itemView){
            super(itemView);
            mView =itemView;
        }

        public void setDetails(Context ctx, String itembarcode, String itemcategory, String itemname, String itemprice,String itemlocation){
            TextView item_barcode = (TextView) mView.findViewById(R.id.viewitembarcode);
            TextView item_name = (TextView) mView.findViewById(R.id.viewitemname);
            TextView item_category = (TextView) mView.findViewById(R.id.viewitemcategory);
            TextView item_price = (TextView) mView.findViewById(R.id.viewitemprice);
            TextView item_location=(TextView) mView.findViewById(R.id.viewItemLocation);
            item_barcode.setText(itembarcode);
            item_category.setText(itemcategory);
            item_name.setText(itemname);
            item_price.setText(itemprice);
            item_location.setText(itemlocation);
        }

    }

}