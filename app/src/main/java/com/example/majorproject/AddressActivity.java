package com.example.majorproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.majorproject.Api.ApiInterface;
import com.example.majorproject.Api.Constant;
import com.example.majorproject.Api.Myconfig;
import com.example.majorproject.Api.Shared_Preferences;
import com.example.majorproject.Model.CartList;
import com.example.majorproject.Payment.PaymentGateway;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressActivity extends AppCompatActivity
{
    EditText name,number,address,email;
    Button btn;
    String amt;
    ArrayList<CartList> cart = new ArrayList<CartList>();
    ArrayList<CartList> arraylist=new ArrayList<>();
    int size=0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Address");

        name=findViewById(R.id.name);
        number=findViewById(R.id.phoneno);
        address=findViewById(R.id.address);
        email=findViewById(R.id.email);
        btn=findViewById(R.id.con);

        Intent i = getIntent();
        amt= getIntent().getStringExtra("total");
        arraylist = (ArrayList<CartList>) i.getSerializableExtra("mylist");

        btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String name1= name.getText().toString();
                String address1=address.getText().toString();
                String number1=number.getText().toString();
                String email1=email.getText().toString();

                Intent i = new Intent(AddressActivity.this, PaymentGateway.class);
                    i.putExtra("username",name1);
                    i.putExtra("address",address1);
                    i.putExtra("number",number1);
                    i.putExtra("email",email1);

                    i.putExtra("total",amt);
                    i.putExtra("mylist", arraylist);
                    i.putExtra("name","cart");
                    startActivity(i);


            }
        });

        getaddress();

    }

    private void getaddress()
    {
        ApiInterface apiInterface = Myconfig.getRetrofit().create(ApiInterface.class);
        Call<ResponseBody> result = apiInterface.getProfile(Shared_Preferences.getPrefs(AddressActivity.this, Constant.USER_ID));
        result.enqueue(new Callback<ResponseBody>()
        {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
            {
                try {
                    String output = response.body().string();
                    Log.e("Response", "GetResponse:-" + output);
                    JSONObject jsonObject = new JSONObject(output);
                    if (jsonObject.getString("ResponseCode").equals("1"))
                    {
                        Log.e("jdjd","" +jsonObject.getString("ResponseCode").equals("1"));
                        JSONObject jsonObject1 = jsonObject.getJSONObject("Data");

                        name.setText(jsonObject1.getString("name"));
                        number.setText(jsonObject1.getString("phoneNo"));
                        address.setText(jsonObject1.getString("address"));
                        email.setText(jsonObject1.getString("email"));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}