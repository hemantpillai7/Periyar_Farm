package com.example.majorproject.Adapter;


import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.majorproject.Api.ApiInterface;
import com.example.majorproject.Api.Constant;
import com.example.majorproject.Api.Myconfig;

import com.example.majorproject.Fragment.OfferFragment;
import com.example.majorproject.Model.CartList;
import com.example.majorproject.R;
import com.example.majorproject.Api.Shared_Preferences;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.squareup.picasso.Picasso;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.ViewHolder>
{
    private Context context;
    private List<CartList> cartLists;
    

    public CartListAdapter(Context context, List<CartList> list)
    {
        this.context = context;
        this.cartLists = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cartitem,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        holder.proName.setText(cartLists.get(position).getName());
        holder.proQty.setText(cartLists.get(position).getQty());
        holder.proPrice.setText(cartLists.get(position).getPrice());
        holder.proTotal.setText(String.valueOf("Rs: "+cartLists.get(position).getTotalprice()+"/-"));
        Picasso.get().load(Constant.AllProductsLINK+cartLists.get(position).getImg_url())
                .placeholder(R.drawable.no_image_available)
                .into(holder.productimag);



        holder.prodeleteItem.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                ApiInterface apiInterface = Myconfig.getRetrofit().create(ApiInterface.class);
                Call<ResponseBody> result = apiInterface.deleteCartItem(Shared_Preferences.getPrefs(context, Constant.USER_ID),
                        cartLists.get(position).getId());
                result.enqueue(new Callback<ResponseBody>()
                {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
                    {
                        cartLists.remove(cartLists.get(position));
                        Toast.makeText(context, "Item Removed", Toast.LENGTH_SHORT).show();
                        notifyDataSetChanged();

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t)
                    {
                        Toast.makeText(context, "Please try later", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });

//        holder.offer.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                OfferFragment offerFragment = new OfferFragment();
//                offerFragment.show(getSupportFragmentManager(), offerFragment.getTag());
//
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return cartLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView productimag ,prodeleteItem;
        TextView proName, proQty, proPrice, proTotal,offer;


        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            productimag=itemView.findViewById(R.id.cartproductimg);
            proName=itemView.findViewById(R.id.cartproductname);
            proQty=itemView.findViewById(R.id.cartqty);
            proPrice=itemView.findViewById(R.id.cartproductAmt);
            proTotal=itemView.findViewById(R.id.carttotalAmt);
            prodeleteItem=itemView.findViewById(R.id.prodelete);
            offer=itemView.findViewById(R.id.offer);
        }
    }
}
