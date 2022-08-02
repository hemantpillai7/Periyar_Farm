package com.example.majorproject.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.majorproject.Adapter.CartListAdapter;
import com.example.majorproject.AddressActivity;
import com.example.majorproject.Api.ApiInterface;
import com.example.majorproject.Api.Constant;
import com.example.majorproject.Api.Myconfig;
import com.example.majorproject.Model.CartList;
import com.example.majorproject.Payment.PaymentGateway;
import com.example.majorproject.R;
import com.example.majorproject.Api.Shared_Preferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import es.dmoral.toasty.Toasty;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartFragment extends Fragment
{
    ProgressDialog dialog;
    RecyclerView cartRec;
    TextView subtotal ;
    String total;
    int val=0;
    Button buynow;
    ArrayList<CartList> cartLists = new ArrayList<CartList>();

    CartListAdapter cartListAdapter;

    RelativeLayout emptylayout,cartlayout,buttonlayout;

    public CartFragment()
    {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_cart, container, false);
        cartRec = root.findViewById(R.id.cartlist);
        subtotal = root.findViewById(R.id.subtotal);
        buynow=root.findViewById(R.id.cartbuy_now);
        emptylayout=root.findViewById(R.id.emptycart);
        cartlayout=root.findViewById(R.id.rlone);
        buttonlayout=root.findViewById(R.id.amt);

        getCartItem();

        buynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(val<=0)
                {
                    Toast.makeText(getContext(), "Please Add a Product To Cart", Toast.LENGTH_SHORT).show();
                }
                else
                {
//                    Intent i = new Intent(getContext(), PaymentGateway.class);
//                    i.putExtra("total",total);
//                    i.putExtra("mylist", cartLists);
//                    i.putExtra("name","cart");
//                    startActivity(i);

                    Intent i = new Intent(getContext(), AddressActivity.class);
                    i.putExtra("total",total);
                    i.putExtra("mylist", cartLists);
                    startActivity(i);
                }

            }
        });

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run()
            {

                getTotalAmt();
                handler.postDelayed(this,100);
            }
        }, 500);

        return root;
    }

    private void getCartItem()
    {
        dialog= new ProgressDialog(getContext());
        dialog.show();
        dialog.setContentView(R.layout.progressbar);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        ApiInterface apiInterface = Myconfig.getRetrofit().create(ApiInterface.class);
        Call<ResponseBody> result = apiInterface.getCartItem(Shared_Preferences.getPrefs(getContext(), Constant.USER_ID));
        result.enqueue(new Callback<ResponseBody>()
        {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
            {
                dialog.dismiss();
                try {
                    String output = response.body().string();
                    Log.d("Response2", "GetComments:-" + output);
                    JSONObject jsonObject = new JSONObject(output);
                    //String reason = jsonObject.getString("ResponseMessage");
                    if (jsonObject.getString("ResponseCode").equals("1"))
                    {
                        JSONArray jsonArray = jsonObject.getJSONArray("Data");
                        for (int i = 0; i < jsonArray.length(); i++)
                        {
                            JSONObject object = jsonArray.getJSONObject(i);
                            cartLists.add(new CartList(object));
                            int c= cartLists.size();
                            if(c==0)
                           {
                               cartlayout.setVisibility(View.GONE);
                               buttonlayout.setVisibility(View.GONE);
                               emptylayout.setVisibility(View.VISIBLE);
                           }

                        }
                        cartRec.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
                        cartListAdapter = new CartListAdapter(getContext(),cartLists);
                        cartRec.setAdapter(cartListAdapter);
                        cartlayout.setVisibility(View.VISIBLE);
                        buttonlayout.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        emptylayout.setVisibility(View.VISIBLE);
                    }
                    getTotalAmt();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t)
            {
                dialog.dismiss();
                Toasty.error(getContext(),"Please Check Your Network",Toast.LENGTH_SHORT, true).show();
            }
        });

    }

    public void getTotalAmt()
    {
        int t=0;
        for(int i =0; i<cartLists.size(); i++)
        {
            t= t+cartLists.get(i).getTotalprice();
        }
        val=t;
        total= String.valueOf(t);

        subtotal.setText("Total Amount : "+t+"/-");
    }
}