package com.example.android.ecommerce;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.ecommerce.classesInfo.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class form extends AppCompatActivity {
    ImageView iconCart;
    private String ProductName,CategoryName;
    private TextView productName,description,price;
    private  ImageView productImage;
    private TextView Cart,Wishlist;
    private Product product;
    private FirebaseDatabase productdb=FirebaseDatabase.getInstance();
    private DatabaseReference productref=productdb.getReference().child("Categories");
    private FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        iconCart=findViewById(R.id.icon_cart);
        productName=findViewById(R.id.product_Name);
        description=findViewById(R.id.Product_Description);
        price=findViewById(R.id.product_price);
        productImage=findViewById(R.id.ProductImage);
        Cart=findViewById(R.id.cart_btn);
        Wishlist=findViewById(R.id.wishlist_btn);
        loadingBar=new ProgressDialog(this);


         getIntentValue();

        productref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                putValuesInViews(dataSnapshot);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
//        productName.setText(product.getName());
//        description.setText(product.getDescription());
//        price.setText(product.getPrice());
//        Picasso.with(this).load(Uri.parse(product.getImage_uri())).into(productImage);

        Cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddToCart();
                loadingBar.setMessage("Please Wait");
                loadingBar.setTitle("Adding To Cart");
                loadingBar.show();
            }
        });
        Wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddToWishlist();
                loadingBar.setMessage("Please Wait");
                loadingBar.setTitle("Adding To Wishlist");
                loadingBar.show();
            }
        });
        iconCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(form.this,Mycart.class));
            }
        });


    }

    private void AddToWishlist() {
        final DatabaseReference wishRef=productdb.getReference().child("Wishlist");

        wishRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(user.getUid()).child(ProductName).exists())
                {
                    Toast.makeText(form.this, "Product Already in Wishlist", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }
                else
                {
                    final HashMap<String,Object> cartData=new HashMap<>();
                    cartData.put("Name",product.getName());
                    cartData.put("Description",product.getDescription());
                    cartData.put("Price",product.getPrice());
                    cartData.put("image_uri",product.getImage_uri());
                    cartData.put("Category",product.getCategory());
                    wishRef.child(user.getUid()).child(ProductName).updateChildren(cartData)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {
                                        Toast.makeText(form.this, "Added To Wishlist", Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();
                                        cartData.clear();
                                    }
                                    else
                                    {
                                        Toast.makeText(form.this, "Network Error", Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();
                                    }
                                }
                            });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void AddToCart() {
        final DatabaseReference cartref=productdb.getReference().child("Cart");

        cartref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(user.getUid()).child(ProductName).exists())
                {
                    Toast.makeText(form.this, "Product Already in Cart", Toast.LENGTH_SHORT).show();
                   loadingBar.dismiss();
                }
                else
                {
                    final HashMap<String,Object> cartData=new HashMap<>();
                    cartData.put("Name",product.getName());
                    cartData.put("Description",product.getDescription());
                    cartData.put("Price",product.getPrice());
                    cartData.put("image_uri",product.getImage_uri());
                    cartData.put("Category",product.getCategory());
                    cartref.child(user.getUid()).child(ProductName).updateChildren(cartData)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {
                                        Toast.makeText(form.this, "Added To Cart", Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();
                                        cartData.clear();
                                    }
                                    else
                                    {
                                        Toast.makeText(form.this, "Network Error", Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();
                                    }
                                }
                            });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void putValuesInViews(@NotNull DataSnapshot dataSnapshot) {
        for(DataSnapshot ds:dataSnapshot.child(CategoryName).child("Products").getChildren())
        {
            product=ds.child(ProductName).getValue(Product.class);
            productName.setText(product.getName());
            description.setText(product.getDescription());
            price.setText(product.getPrice());

            Picasso.get().load(Uri.parse(product.getImage_uri())).into(productImage);


        }
    }

    private void getIntentValue() {
        if(getIntent().hasExtra("Product Name"))
        {
           ProductName=getIntent().getStringExtra("Product Name");
           CategoryName=getIntent().getStringExtra("Category Name");
            Toast.makeText(this, ProductName, Toast.LENGTH_SHORT).show();
        }
    }
}
