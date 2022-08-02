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
import com.example.majorproject.Model.ExploreList;

import com.example.majorproject.R;
import com.example.majorproject.ViewAllActivity;
import com.squareup.picasso.Picasso;


import java.util.List;

public class ExploreAdapter extends RecyclerView.Adapter<ExploreAdapter.ViewHolder>
{
    private Context context;
    private List<ExploreList> list;

    public ExploreAdapter(Context context, List<ExploreList> list)
    {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ExploreAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ExploreAdapter.ViewHolder holder, int position)
    {
        holder.name.setText(list.get(position).getName());
        Picasso.get().load(Constant.AllProductsLINK+list.get(position).getImg_url())
                .placeholder(R.drawable.no_image_available)
                .into(holder.catImg);

        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(context, ViewAllActivity.class);
                i.putExtra("type",list.get(position).getType());
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
        ImageView catImg;
        TextView name;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            catImg=itemView.findViewById(R.id.cat_image);
            name=itemView.findViewById(R.id.cat_name);
        }
    }
}
