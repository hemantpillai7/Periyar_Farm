package com.example.majorproject.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.majorproject.Adapter.OrderHistoryAdapter;
import com.example.majorproject.Api.ApiInterface;
import com.example.majorproject.Api.Constant;
import com.example.majorproject.Api.Myconfig;
import com.example.majorproject.Model.OrderHistoryList;
import com.example.majorproject.R;
import com.example.majorproject.Api.Shared_Preferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderFragment extends Fragment
{
    ProgressDialog dialog;
    ArrayList<OrderHistoryList> list = new ArrayList<OrderHistoryList>();
    OrderHistoryAdapter orderHistoryAdapter;
    RecyclerView recOrder;

    public OrderFragment()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        View root = inflater.inflate(R.layout.fragment_order, container, false);
        Toolbar toolbar =root.findViewById(R.id.toolbar);
        recOrder=root.findViewById(R.id.rec_orderhistory);

        getorderhistory();

        return root;
    }

    private void getorderhistory()
    {
        dialog= new ProgressDialog(getContext());
        dialog.show();
        dialog.setContentView(R.layout.progressbar);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        ApiInterface apiInterface = Myconfig.getRetrofit().create(ApiInterface.class);
        Call<ResponseBody> result = apiInterface.getorderhistory(Shared_Preferences.getPrefs(getContext(), Constant.USER_ID));
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
                            list.add(new OrderHistoryList(object));
                        }
                        recOrder.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL,false));
                        orderHistoryAdapter = new OrderHistoryAdapter(getContext(),list);
                        recOrder.setAdapter(orderHistoryAdapter);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t)
            {

            }
        });
    }
}