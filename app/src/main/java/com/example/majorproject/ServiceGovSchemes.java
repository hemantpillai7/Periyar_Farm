package com.example.majorproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.example.majorproject.Adapter.GovSchemeListAdapter;
import com.example.majorproject.Api.ApiInterface;
import com.example.majorproject.Api.Myconfig;
import com.example.majorproject.Model.GovList;

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

public class ServiceGovSchemes extends AppCompatActivity
{
    ListView listview;
    private ArrayList<GovList> govLists = new ArrayList<GovList>();
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_gov_schemes);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Back");

        listview=findViewById(R.id.listscheme);

        getscheme();
    }

    private void getscheme()
    {
        dialog= new ProgressDialog(ServiceGovSchemes.this);
        dialog.show();
        dialog.setContentView(R.layout.progressbar);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        ApiInterface apiInterface = Myconfig.getRetrofit().create(ApiInterface.class);
        Call<ResponseBody> result =apiInterface.getGovSchemes();
        result.enqueue(new Callback<ResponseBody>()
        {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
            {
                dialog.dismiss();
                try {
                    String output = response.body().string();
                    Log.d("Response", "GetComments:-" + output);
                    JSONObject jsonObject = new JSONObject(output);
                    if (jsonObject.getString("ResponseCode").equals("1"))
                    {
                        JSONArray jsonArray = jsonObject.getJSONArray("Data");
                        for (int i = 0; i < jsonArray.length(); i++)
                        {
                            try
                            {
                            JSONObject object = jsonArray.getJSONObject(i);
                            govLists.add(new GovList(object));
                            Log.d("lists","list:- "+object);
                            } catch (JSONException e)
                            {
                                e.printStackTrace();
                            }
                        }
                        GovSchemeListAdapter adapter = new GovSchemeListAdapter(ServiceGovSchemes.this,govLists);
                        listview.setAdapter(adapter);
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
                Toasty.error(ServiceGovSchemes.this,"Please Check Your Network", Toast.LENGTH_SHORT, true).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}