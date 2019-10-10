package com.example.android.ecommerce;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.Toast;

import com.example.android.ecommerce.Adapters.ProductAdapter;
import com.example.android.ecommerce.classesInfo.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProductGridLayout extends AppCompatActivity{
    RecyclerView recyclerView;
    ProductAdapter adapter;
    FirebaseDatabase database;
    List<Product> productList;
    private  String CategoryName;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_grid_layout);
        getIncominIntent();

        recyclerView=(RecyclerView)findViewById(R.id.recycler_view_product_temp);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        productList =new ArrayList<>();
        DatabaseReference dbCategory= FirebaseDatabase.getInstance().getReference("Categories");

        dbCategory.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.child(CategoryName).child("Products").child("Name").getChildren())
                {
                    Product product=ds.getValue(Product.class);
                    productList.add(product);
                }
                adapter=new ProductAdapter(ProductGridLayout.this,productList);
                recyclerView.setAdapter(adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    private void getIncominIntent()
    {
        if(getIntent().hasExtra("Category Name"))
        {
            CategoryName=getIntent().getStringExtra("Category Name");
            Toast.makeText(this, CategoryName, Toast.LENGTH_SHORT).show();
        }
    }

}
