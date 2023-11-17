package com.example.itemxplorerteam1;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class InventoryActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    RecyclerView recyclerListView;
    DatabaseReference databaseDataReference;
    private TextView listTotalItem, totalPriceSum;
    private int totalItemCounter =0;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);


        listTotalItem = findViewById(R.id.listTotalItem);
        totalPriceSum = findViewById(R.id.totalPriceSum);

        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser users = firebaseAuth.getCurrentUser();
        String finaluser=users.getEmail();
        String resultemail = finaluser.replace(".","");
        databaseDataReference = FirebaseDatabase.getInstance().getReference("Users").child(resultemail).child("Items");
        recyclerListView = findViewById(R.id.recyclerViews);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerListView.setLayoutManager(manager);
        recyclerListView.setHasFixedSize(true);
        recyclerListView.setLayoutManager(new LinearLayoutManager(this));

        databaseDataReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    totalItemCounter = (int) dataSnapshot.getChildrenCount();
                    listTotalItem.setText(Integer.toString(totalItemCounter));
                }else{
                    listTotalItem.setText("0");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseDataReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int sum=0;
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    Map<String,Object> map = (Map<String, Object>) ds.getValue();
                    Object price = map.get("productPrice");
                    int pValue = Integer.parseInt(String.valueOf(price));
                    sum += pValue;
                    totalPriceSum.setText(String.valueOf(sum));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    @Override
    protected  void  onStart() {
        super.onStart();
        FirebaseRecyclerOptions<ModalItems> options =
                new FirebaseRecyclerOptions.Builder<ModalItems>()
                        .setQuery(databaseDataReference, ModalItems.class)
                        .build();

        FirebaseRecyclerAdapter<ModalItems, SearchItemsActivity.UsersViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<ModalItems, SearchItemsActivity.UsersViewHolder>(options) {
                    @NonNull
                    @Override
                    public SearchItemsActivity.UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemcard, parent, false);
                        return new SearchItemsActivity.UsersViewHolder(view);
                    }

                    @Override
                    protected void onBindViewHolder(@NonNull SearchItemsActivity.UsersViewHolder holder, int position, @NonNull ModalItems model) {
                        holder.setDetails(getApplicationContext(), model.getProductBarCode(), model.getProductCategory(), model.getProductName(), model.getProductPrice(),model.getProductLocation());
                    }
                };
        recyclerListView.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
        firebaseRecyclerAdapter.notifyDataSetChanged();
    }

}
