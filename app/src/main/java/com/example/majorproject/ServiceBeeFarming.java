package com.example.majorproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;

import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.majorproject.Adapter.HoneyItemAdapter;

import com.example.majorproject.Api.ApiInterface;
import com.example.majorproject.Api.Constant;
import com.example.majorproject.Api.Myconfig;

import com.example.majorproject.Api.Shared_Preferences;
import com.example.majorproject.Model.HoneyItemList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import es.dmoral.toasty.Toasty;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceBeeFarming extends AppCompatActivity
{

    RecyclerView honeyRec;
    EditText name,email,address,phoneno,message;
    String name1,email1,address1,phoneno1,message1;
    ArrayList<HoneyItemList> honeyItemArrayList = new ArrayList<HoneyItemList>();
    HoneyItemAdapter adapter;
    Button submit;
    ProgressDialog dialog;
    String currentdate;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_bee_farming);

        currentdate =  new SimpleDateFormat("yyyy MM dd", Locale.getDefault()).format(new Date());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Back");

        submit=findViewById(R.id.honey_submit);
        honeyRec = findViewById(R.id.honey_rec);
        name=findViewById(R.id.honey_name);
        email=findViewById(R.id.honey_email);
        address=findViewById(R.id.honey_address);
        phoneno=findViewById(R.id.honey_number);
        message=findViewById(R.id.honey_message);

        honeyitemListing();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                name1=name.getText().toString();
                email1=email.getText().toString();
                address1=address.getText().toString();
                phoneno1=phoneno.getText().toString();
                message1=message.getText().toString();

                //Validation
                if(TextUtils.isEmpty(name1))
                {
                   Toasty.warning(ServiceBeeFarming.this, "Please Fill Your Name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(email1))
                {
                    Toasty.warning(ServiceBeeFarming.this, "Please Fill Your Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(phoneno1))
                {
                    Toasty.warning(ServiceBeeFarming.this, "Please Enter your Number", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(phoneno1.length() < 10)
                {
                    Toasty.warning(ServiceBeeFarming.this, " Enter Valid Number", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(phoneno1.length() > 10)
                {
                    Toasty.warning(ServiceBeeFarming.this, " Enter Valid Number", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(address1))
                {
                    Toasty.warning(ServiceBeeFarming.this, "Please Fill Your Address", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(message1))
                {
                    Toasty.warning(ServiceBeeFarming.this, "Please fill your Message", Toast.LENGTH_SHORT).show();
                    return;
                }

                submmitrequest();
            }
        });

    }

    private void submmitrequest()
    {
        dialog= new ProgressDialog(ServiceBeeFarming.this);
        dialog.show();
        dialog.setContentView(R.layout.progressbar);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);


        ApiInterface apiInterface = Myconfig.getRetrofit().create(ApiInterface.class);
        Call<ResponseBody> result =apiInterface.bookHoneyService(Shared_Preferences.getPrefs(getApplicationContext(), Constant.USER_ID),name1,email1,phoneno1,address1,currentdate,message1,"HoneyService","Booked");
        result.enqueue(new Callback<ResponseBody>()
        {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
            {
                dialog.dismiss();
                Toast toast = Toasty.success(ServiceBeeFarming.this, "Your Request has been Submited", Toast.LENGTH_SHORT, true);
                toast.setGravity(Gravity.TOP,0,300);
                toast.show();

                name.setText("");
                email.setText("");
                phoneno.setText("");
                address.setText("");
                message.setText("");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t)
            {
                Toast toast = Toasty.error(ServiceBeeFarming.this, "Failed", Toast.LENGTH_SHORT, true);
                toast.setGravity(Gravity.TOP,0,300);
                toast.show();
            }
        });

    }

    private void honeyitemListing()
    {
        {
            ApiInterface apiInterface = Myconfig.getRetrofit().create(ApiInterface.class);
            Call<ResponseBody> result =apiInterface.getHoneyproduct();
            result.enqueue(new Callback<ResponseBody>()
            {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
                {
                    try {
                        String output = response.body().string();
                        Log.d("Response", "Videos:-" + output);
                        JSONObject jsonObject = new JSONObject(output);

                        if (jsonObject.getString("ResponseCode").equals("1"))
                        {
                            JSONArray jsonArray = jsonObject.getJSONArray("Data");
                            for (int i = 0; i < jsonArray.length(); i++)
                            {
                                JSONObject object = jsonArray.getJSONObject(i);
                                honeyItemArrayList.add(new HoneyItemList(object));
                                Log.e("Honey",""+honeyItemArrayList);
                            }
                            GridLayoutManager gridLayoutManager = new GridLayoutManager(ServiceBeeFarming.this,2,GridLayoutManager.VERTICAL,false);
                            honeyRec.setLayoutManager(gridLayoutManager);
                            adapter = new HoneyItemAdapter(ServiceBeeFarming.this,honeyItemArrayList);
                            honeyRec.setAdapter(adapter);

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

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}