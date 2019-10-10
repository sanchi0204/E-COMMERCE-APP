package com.example.android.ecommerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.ecommerce.Adapters.CartAdapter;
import com.example.android.ecommerce.classesInfo.Product;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Mycart extends AppCompatActivity {
    RecyclerView recyclerView;
    CartAdapter adapter;
    FirebaseDatabase database;
    TextView buy;
    List<Product> productList;
    FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycart);
        recyclerView=(RecyclerView)findViewById(R.id.cart_RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        productList =new ArrayList<>();
        DatabaseReference dbCategory= FirebaseDatabase.getInstance().getReference("Cart");

        dbCategory.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.child(user.getUid()).getChildren())
                {
                    Product product=ds.getValue(Product.class);
                    productList.add(product);
                }
                if(productList.size()==0)
                {
                    Toast.makeText(Mycart.this, "Please add something in the cart", Toast.LENGTH_SHORT).show();
                }
                adapter=new CartAdapter(Mycart.this,productList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void buy(View view)
    {
        if(!(productList.size()==0))
        {
        startActivity(new Intent(Mycart.this,ProjectDescription.class));
        }
        else
        {
            Toast.makeText(this, "Please add something in cart", Toast.LENGTH_SHORT).show();
        }
    }
}
