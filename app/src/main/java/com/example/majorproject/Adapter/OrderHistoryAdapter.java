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
import com.example.majorproject.Model.OrderHistoryList;
import com.example.majorproject.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.ViewHolder>
{
    private Context context;
    private List<OrderHistoryList> orderHistoryLists;


    public OrderHistoryAdapter(Context context, List<OrderHistoryList> orderHistoryLists)
    {
        this.context = context;
        this.orderHistoryLists = orderHistoryLists;
    }

    @NonNull
    @Override
    public OrderHistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.orderhistory,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHistoryAdapter.ViewHolder holder, int position)
    {
        holder.hisProName.setText(orderHistoryLists.get(position).getName());
        holder.hisProPrice.setText(String.valueOf(orderHistoryLists.get(position).getPrice()));
        holder.hisProQty.setText(String.valueOf(orderHistoryLists.get(position).getQty()));
        holder.hisProTotal.setText(String.valueOf(orderHistoryLists.get(position).getTotalprice()));
        holder.hisProDate.setText("Ordered on "+orderHistoryLists.get(position).getDate());
        Picasso.get().load(Constant.AllProductsLINK+orderHistoryLists.get(position).getImg_url())
                .placeholder(R.drawable.no_image_available)
                .into(holder.historyImg);
    }

    @Override
    public int getItemCount() {
        return orderHistoryLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView historyImg;
        TextView hisProName,hisProPrice,hisProQty,hisProDate,hisProTotal;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            historyImg=itemView.findViewById(R.id.hisproductimg);
            hisProName=itemView.findViewById(R.id.hisproductname);
            hisProDate=itemView.findViewById(R.id.hisproducatDate);
            hisProPrice=itemView.findViewById(R.id.hisproductAmt);
            hisProQty=itemView.findViewById(R.id.histqty);
            hisProTotal=itemView.findViewById(R.id.histtotalAmt);

        }
    }
}
