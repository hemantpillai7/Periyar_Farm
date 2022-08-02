package com.example.majorproject.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.majorproject.Api.Constant;
import com.example.majorproject.DetailedActivity;
import com.example.majorproject.Model.ProductList;
import com.example.majorproject.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.ViewHolder>
{
    private Context context;
    private List<ProductList> list;


    public PopularAdapter(Context context, List<ProductList> list)
    {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public PopularAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull PopularAdapter.ViewHolder holder, int position)
    {
        //Glide.with(context).load(list.get(position).getImg_url()).into(holder.popImg);
        holder.name.setText(list.get(position).getName());
        holder.rating.setText(list.get(position).getRating());
        holder.ratingBar.setRating(Float.parseFloat(list.get(position).getRating()));
        holder.description.setText(list.get(position).getDescription());
        holder.discount.setText("Discount "+list.get(position).getDiscount()+"% OFF");

        Picasso.get().load(Constant.AllProductsLINK+list.get(position).getImg_url())
                .placeholder(R.drawable.no_image_available)
                .into(holder.popImg);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, DetailedActivity.class);
                i.putExtra("Popularproducts",list.get(position));
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView popImg;
        TextView name, description, rating, discount;
        RatingBar ratingBar;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            popImg=itemView.findViewById(R.id.pop_img);
            name=itemView.findViewById(R.id.pop_name);
            description=itemView.findViewById(R.id.pop_des);
            discount=itemView.findViewById(R.id.pop_discount);
            rating=itemView.findViewById(R.id.pop_rating);
            ratingBar=itemView.findViewById(R.id.pop_ratingbar);
        }
    }
}
