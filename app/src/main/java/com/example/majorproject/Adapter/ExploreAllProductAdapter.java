package com.example.majorproject.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.majorproject.Api.Constant;
import com.example.majorproject.DetailedActivity;
import com.example.majorproject.Model.TotalProductList;
import com.example.majorproject.R;
import com.squareup.picasso.Picasso;

import java.util.List;


public class ExploreAllProductAdapter extends RecyclerView.Adapter<ExploreAllProductAdapter.ViewHolder>
{
    private Context context;
    private List<TotalProductList> totalProductLists;

    public ExploreAllProductAdapter(Context context, List<TotalProductList> list)
    {
        this.context = context;
        this.totalProductLists = list;
    }

    @NonNull
    @Override
    public ExploreAllProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.exp_all,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ExploreAllProductAdapter.ViewHolder holder, int position)
    {
        holder.name.setText(totalProductLists.get(position).getName());
        holder.description.setText(totalProductLists.get(position).getDescription());
        holder.price.setText(totalProductLists.get(position).getPrice()+" /Rs");
        Picasso.get().load(Constant.AllProductsLINK+totalProductLists.get(position).getImg_url())
                .placeholder(R.drawable.no_image_available)
                .into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(context, DetailedActivity.class);
                i.putExtra("ExploreAll",totalProductLists.get(position)); //using recommended data to transfer both search & recommended data
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return totalProductLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageView;
        TextView name,price,description;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            imageView=itemView.findViewById(R.id.Expimage);
            name=itemView.findViewById(R.id.Expname);
            description=itemView.findViewById(R.id.Expdesc);
            price=itemView.findViewById(R.id.ExpRate);
        }
    }
}
