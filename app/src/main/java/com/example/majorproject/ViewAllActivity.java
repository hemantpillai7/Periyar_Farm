package com.example.majorproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.majorproject.Adapter.ViewAllAdapter;
import com.example.majorproject.Api.ApiInterface;
import com.example.majorproject.Api.Myconfig;
import com.example.majorproject.Model.ProductList;

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


public class ViewAllActivity extends AppCompatActivity
{
    RecyclerView recyclerView;
    ProgressDialog dialog;

        //All items
        ArrayList<ProductList> list = new ArrayList<ProductList>();
        ViewAllAdapter adapter;
        String typee;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("View All Products");

        Intent i = getIntent();
        typee = i.getStringExtra("type");

        recyclerView=findViewById(R.id.view_all_rec);
        getproducts();


    }

    private void getproducts()
    {
        dialog= new ProgressDialog(ViewAllActivity.this);
        dialog.show();
        dialog.setContentView(R.layout.progressbar);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        ApiInterface apiInterface = Myconfig.getRetrofit().create(ApiInterface.class);
        Call<ResponseBody> result = apiInterface.getAllproducts();
        result.enqueue(new Callback<ResponseBody>()
        {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
            {
                dialog.dismiss();
                try {
                    String output = response.body().string();
                    JSONObject jsonObject = new JSONObject(output);
                    if (jsonObject.getString("ResponseCode").equals("1"))
                    {
                        JSONArray jsonArray = jsonObject.getJSONArray("Data");
                        for (int i = 0; i < jsonArray.length(); i++)
                        {
                            JSONObject object = jsonArray.getJSONObject(i);

                            if(typee.equals(object.getString("type")))
                            {
                                list.add(new ProductList(object));
                            }

                        }
                        recyclerView.setLayoutManager(new LinearLayoutManager(ViewAllActivity.this,RecyclerView.VERTICAL,false));
                        adapter = new ViewAllAdapter(ViewAllActivity.this,list);
                        recyclerView.setAdapter(adapter);
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
                dialog.dismiss();
                Toasty.error(ViewAllActivity.this,"Please Check Your Network",Toast.LENGTH_SHORT, true).show();
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}