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

public class RecommendedAdapter extends RecyclerView.Adapter<RecommendedAdapter.ViewHolder>
{
    private Context context;
    private List<ProductList> list;


    public RecommendedAdapter(Context context, List<ProductList> list)
    {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecommendedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recommended_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendedAdapter.ViewHolder holder, int position)
    {
        //Glide.with(context).load(list.get(position).getImg_url()).into(holder.imageView);
        holder.name.setText(list.get(position).getName());
        holder.description.setText(list.get(position).getDescription());
        holder.rating.setText(list.get(position).getRating());
        holder.ratingBar.setRating(Float.parseFloat(list.get(position).getRating()));

        Picasso.get().load(Constant.AllProductsLINK+list.get(position).getImg_url())
                .placeholder(R.drawable.no_image_available)
                .into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, DetailedActivity.class);
                i.putExtra("RecommendedProducts",list.get(position));
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
        ImageView imageView;
        TextView name,description,rating;
        RatingBar ratingBar;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            imageView=itemView.findViewById(R.id.rec_img);
            name=itemView.findViewById(R.id.rec_name);
            description=itemView.findViewById(R.id.rec_dec);
            rating=itemView.findViewById(R.id.rec_rating);
            ratingBar=itemView.findViewById(R.id.rec_ratingbar);
        }
    }
}
