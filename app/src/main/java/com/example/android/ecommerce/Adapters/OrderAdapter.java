package com.example.android.ecommerce.Adapters;

import android.content.Context;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.ecommerce.classesInfo.Order;
import com.example.android.ecommerce.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder>{

    //button is left
    Context context2;
    List<Order> orderList;

    public OrderAdapter(Context context2, List<Order> orderList) {
        this.context2 = context2;
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int ViewType) {

        View view = LayoutInflater.from(context2).inflate(R.layout.my_orders_page, viewGroup, false);
        OrderViewHolder orderViewHolder = new OrderViewHolder(view);
        return orderViewHolder;
    }



    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.OrderViewHolder orderViewHolder, int position) {

    Order order = orderList.get(position);
    orderViewHolder.textViewName.setText(order.getName());
    orderViewHolder.textViewdescription.setText(order.getDescription());
    orderViewHolder.textViewPrice.setText(order.getDate());
    orderViewHolder.textViewDate.setText(order.getDate());
    orderViewHolder.textViewTime.setText(order.getTime());
    Picasso.get().load(Uri.parse(order.getImage_uri())).into(orderViewHolder.imageView);

    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    class OrderViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textViewName, textViewdescription, textViewDate, textViewPrice, textViewTime;


        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.order_image);
            textViewdescription=itemView.findViewById(R.id.my_order_description);
            textViewName=itemView.findViewById(R.id.order_name);
            textViewPrice=itemView.findViewById(R.id.order_price);
            textViewDate=itemView.findViewById(R.id.order_date);
            textViewTime=itemView.findViewById(R.id.order_time);

        }
    }

}

