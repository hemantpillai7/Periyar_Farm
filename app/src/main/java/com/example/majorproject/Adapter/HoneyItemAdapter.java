package com.example.majorproject.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.majorproject.Api.Constant;
import com.example.majorproject.Model.HoneyItemList;
import com.example.majorproject.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HoneyItemAdapter extends RecyclerView.Adapter<HoneyItemAdapter.ViewHolder>
{
    private Context context;
    private List<HoneyItemList> honeyItemList;

    public HoneyItemAdapter(Context context, List<HoneyItemList> honeyItemList)
    {
        this.context = context;
        this.honeyItemList = honeyItemList;
    }

    @NonNull
    @Override
    public HoneyItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return new HoneyItemAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.honeyitems, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull HoneyItemAdapter.ViewHolder holder, int position)
    {
        holder.name.setText(honeyItemList.get(position).getHoneyname());
        holder.rate.setText("Rs "+honeyItemList.get(position).getHoneyprice()+"/-");
        Picasso.get().load(Constant.HoneyProductLink+honeyItemList.get(position).getHoney_img())
                .placeholder(R.drawable.no_image_available)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount()
    {
        return honeyItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageView;
        TextView name, rate;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            imageView=itemView.findViewById(R.id.honeyimage);
            name=itemView.findViewById(R.id.honeyname);
            rate=itemView.findViewById(R.id.honeyRate);
        }
    }
}
