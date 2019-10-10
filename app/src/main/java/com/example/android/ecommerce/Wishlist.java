package com.example.android.ecommerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.ecommerce.Adapters.MyWishlistAdapter;
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

public class Wishlist extends AppCompatActivity {
    RecyclerView recyclerView;
    MyWishlistAdapter adapter;
    FirebaseDatabase database;
    List<Product> productList;
    Product product;
    //hi
    FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);
        recyclerView=(RecyclerView)findViewById(R.id.wishlist_RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        productList =new ArrayList<>();
        DatabaseReference dbCategory= FirebaseDatabase.getInstance().getReference("Wishlist");
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
                    Toast.makeText(Wishlist.this, "Please add something in the wishlist", Toast.LENGTH_SHORT).show();
                }
                adapter=new MyWishlistAdapter(Wishlist.this,productList);
//                adapter.notifyItemChanged(productList.indexOf(product),productList.size());
//                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


}
