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

public class MyWishlistAdapter extends RecyclerView.Adapter<MyWishlistAdapter.MyWishListViewHolder> {
    Context context1;
    List<Product> productList;
    FirebaseDatabase productdb=FirebaseDatabase.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    ProgressDialog loadingbar;

    public MyWishlistAdapter(Context context1, List<Product> productList) {
        this.context1 = context1;
        this.productList = productList;
    }

    @NonNull
    @Override
    public MyWishListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int ViewType) {
        View view = LayoutInflater.from(context1).inflate(R.layout.my_wishlist_page, viewGroup, false);
        MyWishListViewHolder myWishListViewHolder=new MyWishListViewHolder(view);
        loadingbar=new ProgressDialog(context1);
        return myWishListViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyWishListViewHolder myWishListViewHolder, final int position) {
        final Product product = productList.get(position);
        final Uri uri=Uri.parse(product.getImage_uri());
        myWishListViewHolder.textViewName.setText(product.getName());
        //productViewHolder.textViewBrand.setText(product.getProd_brand());
        //productViewHolder.textViewBuy.setText(product.getBuy_button());
        myWishListViewHolder.textViewPrice.setText(String.valueOf(product.getPrice()));
//        myWishListViewHolder.description.setText(product.getDescription());
        Picasso.get().load(uri).into(myWishListViewHolder.imageView);

        myWishListViewHolder.addtoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddToCart();
                loadingbar.setTitle("Adding To Cart");
                loadingbar.setMessage("Please Wait");
                loadingbar.show();
            }

            private void AddToCart() {
                final DatabaseReference cartref=productdb.getReference().child("Cart");

                cartref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child(user.getUid()).child(product.getName()).exists())
                        {
                            Toast.makeText(context1, "Product Already in Cart", Toast.LENGTH_SHORT).show();
                            loadingbar.dismiss();
                        }
                        else
                        {
                            final HashMap<String,Object> cartData=new HashMap<>();
                            cartData.put("Name",product.getName());
                            cartData.put("Description",product.getDescription());
                            cartData.put("Price",product.getPrice());
                            cartData.put("image_uri",product.getImage_uri());
                            cartData.put("Category",product.getCategory());
                            cartref.child(user.getUid()).child(product.getName()).updateChildren(cartData)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful())
                                            {
                                                Toast.makeText(context1, "Added To Cart", Toast.LENGTH_SHORT).show();
                                                loadingbar.dismiss();
                                                cartData.clear();
                                            }
                                            else
                                            {
                                                Toast.makeText(context1, "Network Error", Toast.LENGTH_SHORT).show();
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
        myWishListViewHolder.removeWish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeWish();
            }

            private void removeWish() {
                final DatabaseReference cartref=productdb.getReference().child("Wishlist");

                cartref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        cartref.child(user.getUid()).child(product.getName()).removeValue()

                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful())
                                        {
                                            Toast.makeText(context1, "Removed Successfully Please Reopen The page", Toast.LENGTH_LONG).show();
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
        if(productList.size()==0)
        {
            Toast.makeText(context1, "Please add something in the wishlist", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class MyWishListViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textViewName, textViewBrand, textViewBuy, textViewPrice, description;
        CardView productLayout;
        LinearLayout addtoCart,removeWish;

        public MyWishListViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.wish_prod_image);
            //textViewBrand=itemView.findViewById(R.id.product_brand);
            textViewName=itemView.findViewById(R.id.wish_prod_name);
            textViewPrice=itemView.findViewById(R.id.wish_prod_price);
            description=itemView.findViewById(R.id.wish_prod_description);
            addtoCart=itemView.findViewById(R.id.addTo_Cart);
            removeWish =itemView.findViewById(R.id.remove_wish);
            //textViewBuy=itemView.findViewById(R.id.product_buy_button);
        }
    }
}
