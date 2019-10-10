package com.example.android.ecommerce.Adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.ecommerce.R;
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

import java.util.HashMap;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    Context context;
    List<Product> productList;
    FirebaseDatabase productdb=FirebaseDatabase.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    ProgressDialog loadingbar;

    public CartAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int ViewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.my_cart_page, viewGroup, false);
        CartViewHolder cartViewHolder = new CartViewHolder(view);
        loadingbar=new ProgressDialog(context);
        return cartViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder cartViewHolder, int position) {
        final Product product = productList.get(position);
        Uri uri=Uri.parse(product.getImage_uri());
        cartViewHolder.textViewName.setText(product.getName());
        //productViewHolder.textViewBrand.setText(product.getProd_brand());
        //productViewHolder.textViewBuy.setText(product.getBuy_button());
        cartViewHolder.textViewPrice.setText(String.valueOf(product.getPrice()));

       // cartViewHolder.description.setText(product.getDescription());

     //   cartViewHolder.description.setText(product.getDescription());

        Picasso.get().load(uri).into(cartViewHolder.imageView);

        cartViewHolder.addtoWishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddToWishList();
                loadingbar.setTitle("Adding To Wishlist");
                loadingbar.setMessage("Please Wait");
                loadingbar.show();
            }


            private void AddToWishList() {
                final DatabaseReference wishRef=productdb.getReference().child("Wishlist");

                wishRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child(user.getUid()).child(product.getName()).exists())
                        {
                            Toast.makeText(context, "Product Already in Wishlist", Toast.LENGTH_SHORT).show();
                            loadingbar.dismiss();
                        }
                        else
                        {
                            final HashMap<String,Object> cartData=new HashMap<>();
                            cartData.put("Name",product.getName());
                           // cartData.put("Description",product.getDescription());
                            cartData.put("Price",product.getPrice());
                            cartData.put("image_uri",product.getImage_uri());
                            cartData.put("Category",product.getCategory());
                            wishRef.child(user.getUid()).child(product.getName()).updateChildren(cartData)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful())
                                            {
                                                Toast.makeText(context, "Added To Wishlist", Toast.LENGTH_SHORT).show();
                                                loadingbar.dismiss();
                                                cartData.clear();
                                            }
                                            else
                                            {
                                                Toast.makeText(context, "Network Error", Toast.LENGTH_SHORT).show();
                                                loadingbar.dismiss();
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
        });
        cartViewHolder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeCart();

            }

            private void removeCart() {
                final DatabaseReference cartref=productdb.getReference().child("Cart");

                cartref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        cartref.child(user.getUid()).child(product.getName()).removeValue()

                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful())
                                        {
                                            Toast.makeText(context, "Removed Successfully Please Reopen The page", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
    }





    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textViewName, textViewBrand, textViewBuy, textViewPrice, description;
        CardView productLayout;
        LinearLayout addtoWishlist,remove;
        public CartViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.cart_prod_image);
            //textViewBrand=itemView.findViewById(R.id.product_brand);
            textViewName=itemView.findViewById(R.id.cart_prod_name);
            textViewPrice=itemView.findViewById(R.id.cart_prod_price);
            description=itemView.findViewById(R.id.cart_prod_description);
            addtoWishlist=itemView.findViewById(R.id.addTo_wishList);
            //textViewBuy=itemView.findViewById(R.id.product_buy_button);
            remove=itemView.findViewById(R.id.remove_cart);
        }
    }
}
