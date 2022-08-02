package com.example.majorproject.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.majorproject.Api.Constant;
import com.example.majorproject.Model.Gardenlist;
import com.example.majorproject.R;

import com.squareup.picasso.Picasso;


import java.util.List;

public class GardenAdapter extends RecyclerView.Adapter<GardenAdapter.ViewHolder>
{
    private Context context;
    private List<Gardenlist> list;


    public GardenAdapter(Context context, List<Gardenlist> list)
    {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public GardenAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return new GardenAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.customgarden_item, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull GardenAdapter.ViewHolder holder, int position)
    {
        Picasso.get().load(Constant.GardenprojectLink+list.get(position).getGardenimg_url())
                .placeholder(R.drawable.no_image_available)
                .into(holder.img);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView img;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            img=itemView.findViewById(R.id.garden_img);
        }
    }
}
