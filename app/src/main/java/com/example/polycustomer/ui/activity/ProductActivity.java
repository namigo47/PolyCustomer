package com.example.polycustomer.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.polycustomer.Adapter.ProductApdater;
import com.example.polycustomer.Adapter.UserAdapter;
import com.example.polycustomer.Model.Canteen;
import com.example.polycustomer.Model.Product;
import com.example.polycustomer.R;
import com.example.polycustomer.event.callBack;
import com.example.polycustomer.ui.fragment.UsersFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProductActivity extends AppCompatActivity implements callBack {

    private RecyclerView recyclerView;
    private ProductApdater productAdapter;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        recyclerView = findViewById(R.id.product_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference usersRef = mDatabase.child("Product");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Product> list = new ArrayList<>();
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    Product uid = ds.getValue(Product.class);
                    list.add(uid);
                }
                productAdapter = new ProductApdater(ProductActivity.this, list,ProductActivity.this);
                recyclerView.setAdapter(productAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("hehe", databaseError.getMessage()); //Don't ignore errors!
            }
        };
        usersRef.addListenerForSingleValueEvent(valueEventListener);
    }


    @Override
    public void addChatFirbase(Canteen canteen) {

    }

    @Override
    public void productCallBack(Product product) {
        Intent i = new Intent(this, PayActivity.class);
        i.putExtra("product", product);
        startActivity(i);
    }
}