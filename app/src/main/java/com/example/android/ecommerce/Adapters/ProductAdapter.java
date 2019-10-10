package com.example.android.ecommerce.Adapters;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.ecommerce.classesInfo.Product;
import com.example.android.ecommerce.R;
import com.example.android.ecommerce.form;
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

import java.util.HashMap;
import java.util.List;


public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder>{

    Context context;
    List<Product> productList;
    FirebaseDatabase productdb=FirebaseDatabase.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    ProgressDialog loadingbar;

    public ProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.product_page_temp, viewGroup, false);
        ProductViewHolder productViewHolder = new ProductViewHolder(view);
        loadingbar=new ProgressDialog(context);
        return productViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder productViewHolder, int position) {

       final Product product = productList.get(position);
        Uri uri=Uri.parse(product.getImage_uri());

        productViewHolder.textViewName.setText(product.getName());
        //productViewHolder.textViewBrand.setText(product.getProd_brand());
        //productViewHolder.textViewBuy.setText(product.getBuy_button());
        productViewHolder.textViewPrice.setText(String.valueOf(product.getPrice()));
        //productViewHolder.description.setText(product.getDescription());
        Picasso.get().load(uri).into(productViewHolder.imageView);
        productViewHolder.productLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, form.class);
                intent.putExtra("Product Name",product.getName());
                intent.putExtra("Category Name",product.getCategory());
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

       // productViewHolder.setImageDrawable(context.getResources().getDrawable(product.getImage(), null));
//        productViewHolder.wishlist.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                addToWishlist();
//                loadingbar.setTitle("Adding To Wishlist");
//                loadingbar.setMessage("Please Wait");
//                loadingbar.show();
//            }
//
//            private void addToWishlist() {
//                final DatabaseReference wishRef=productdb.getReference().child("Wishlist");
//
//                wishRef.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        if(dataSnapshot.child(user.getUid()).child(product.getName()).exists())
//                        {
//                            Toast.makeText(context, "Product Already in Wishlist", Toast.LENGTH_SHORT).show();
//                            loadingbar.dismiss();
//                        }
//                        else
//                        {
//                            final HashMap<String,Object> cartData=new HashMap<>();
//                            cartData.put("Name",product.getName());
//                            cartData.put("Description",product.getDescription());
//                            cartData.put("Price",product.getPrice());
//                            cartData.put("image_uri",product.getImage_uri());
//                            cartData.put("Category",product.getCategory());
//                            wishRef.child(user.getUid()).child(product.getName()).updateChildren(cartData)
//                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                        @Override
//                                        public void onComplete(@NonNull Task<Void> task) {
//                                            if(task.isSuccessful())
//                                            {
//                                                Toast.makeText(context, "Added To Wishlist", Toast.LENGTH_SHORT).show();
//                                                loadingbar.dismiss();
//                                                cartData.clear();
//                                            }
//                                            else
//                                            {
//                                                Toast.makeText(context, "Network Error", Toast.LENGTH_SHORT).show();
//                                                loadingbar.dismiss();
//                                            }
//                                        }
//                                    });
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//            }
//        });
//        productViewHolder.cart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                addToCart();
//                loadingbar.setTitle("Adding To Cart");
//                loadingbar.setMessage("Please Wait");
//                loadingbar.show();
//            }
//
//            private void addToCart() {
//                final DatabaseReference cartref=productdb.getReference().child("Cart");
//
//                cartref.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        if(dataSnapshot.child(user.getUid()).child(product.getName()).exists())
//                        {
//                            Toast.makeText(context, "Product Already in Cart", Toast.LENGTH_SHORT).show();
//                            loadingbar.dismiss();
//                        }
//                        else
//                        {
//                            final HashMap<String,Object> cartData=new HashMap<>();
//                            cartData.put("Name",product.getName());
//                            cartData.put("Description",product.getDescription());
//                            cartData.put("Price",product.getPrice());
//                            cartData.put("image_uri",product.getImage_uri());
//                            cartData.put("Category",product.getCategory());
//                            cartref.child(user.getUid()).child(product.getName()).updateChildren(cartData)
//                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                        @Override
//                                        public void onComplete(@NonNull Task<Void> task) {
//                                            if(task.isSuccessful())
//                                            {
//                                                Toast.makeText(context, "Added To Cart", Toast.LENGTH_SHORT).show();
//                                                loadingbar.dismiss();
//                                                cartData.clear();
//                                            }
//                                            else
//                                            {
//                                                Toast.makeText(context, "Network Error", Toast.LENGTH_SHORT).show();
//                                                loadingbar.dismiss();
//                                            }
//                                        }
//                                    });
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder{

    ImageView imageView;
    TextView textViewName, textViewBrand, textViewBuy, textViewPrice;//description
    CardView productLayout;
    Button cart,wishlist;


    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);

        imageView=itemView.findViewById(R.id.product_imageView);
        //textViewBrand=itemView.findViewById(R.id.product_brand);
        textViewName=itemView.findViewById(R.id.product_ViewTitle);
        textViewPrice=itemView.findViewById(R.id.product_ViewPrice);
        //description=itemView.findViewById(R.id.product_ViewShortDesc);
        productLayout=itemView.findViewById(R.id.ProductLayout);
       //textViewBuy=itemView.findViewById(R.id.product_buy_button);
        //cart=itemView.findViewById(R.id.product_cart);
        //wishlist=itemView.findViewById(R.id.product_wish);
    }
}

}


