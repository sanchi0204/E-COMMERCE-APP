package com.example.android.ecommerce.Adapters;

import android.content.Intent;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.content.Context;

import com.example.android.ecommerce.ProductGridLayout;
import com.example.android.ecommerce.classesInfo.Category;
import com.example.android.ecommerce.R;
import com.squareup.picasso.Picasso;

import java.util.List;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>  {

    Context mCtx;
    List<Category> categoryList;

    public CategoryAdapter(Context mCtx, List<Category> categoryList) {
        this.mCtx = mCtx;
        this.categoryList = categoryList;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.category_page, viewGroup, false);

        CategoryViewHolder categoryViewHolder = new CategoryViewHolder(view);
        return categoryViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder categoryViewHolder, int position) {

        final Category category = categoryList.get(position);
        Uri uri=Uri.parse(category.getImage_uri());

        categoryViewHolder.name.setText(category.getName());
        categoryViewHolder.imageView.setImageURI(Uri.parse(category.getImage_uri()));
        categoryViewHolder.description.setText((category.getDescription()));
        Picasso.get().load(uri).into(categoryViewHolder.imageView);
        categoryViewHolder.categoryLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(mCtx, ProductGridLayout.class);
                intent.putExtra("Category Name",category.getName());
                mCtx.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView name,description;
        LinearLayout categoryLayout;

        public CategoryViewHolder( View itemView) {
            super(itemView);

           imageView=itemView.findViewById(R.id.category_image);
           name=itemView.findViewById(R.id.category_name);
           description=itemView.findViewById(R.id.category_description);
           categoryLayout=itemView.findViewById(R.id.category_linear_layout);
        }
    }
}


