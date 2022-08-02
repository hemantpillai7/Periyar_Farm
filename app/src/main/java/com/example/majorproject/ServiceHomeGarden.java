package com.example.majorproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.majorproject.Adapter.GardenAdapter;

import com.example.majorproject.Api.ApiInterface;
import com.example.majorproject.Api.Constant;
import com.example.majorproject.Api.Myconfig;
import com.example.majorproject.Api.Shared_Preferences;
import com.example.majorproject.Model.Gardenlist;


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

public class ServiceHomeGarden extends AppCompatActivity
{
    ArrayList<Gardenlist> list = new ArrayList<Gardenlist>();
    GardenAdapter adapter;
    RecyclerView gardenRec;

    EditText name, email, phone, address, message;
    String name1,email1,address1,phoneno1,message1;
    Button submit;

    private Dialog dialog;
    private Button btn_yes, btn_no;

    String currentdate;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_home_garden);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Back");

        gardenRec= findViewById(R.id.garden_rec);
        name=findViewById(R.id.garden_name);
        email=findViewById(R.id.garden_email);
        phone=findViewById(R.id.garden_number);
        address=findViewById(R.id.garden_address);
        message=findViewById(R.id.garden_message);
        submit=findViewById(R.id.garden_submit);


        submit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                name1=name.getText().toString();
                email1=email.getText().toString();
                address1=address.getText().toString();
                phoneno1=phone.getText().toString();
                message1=message.getText().toString();

                //Validation
                if(TextUtils.isEmpty(name1))
                {
                    Toasty.warning(ServiceHomeGarden.this, "Please Fill Your Name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(email1))
                {
                    Toasty.warning(ServiceHomeGarden.this, "Please Fill Your Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(phoneno1))
                {
                    Toasty.warning(ServiceHomeGarden.this, "Please Enter your Number", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(phoneno1.length() < 10)
                {
                    Toasty.warning(ServiceHomeGarden.this, " Enter Valid Number", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(phoneno1.length() > 10)
                {
                    Toasty.warning(ServiceHomeGarden.this, " Enter Valid Number", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(address1))
                {
                    Toasty.warning(ServiceHomeGarden.this, "Please Fill Your Address", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(message1))
                {
                    Toasty.warning(ServiceHomeGarden.this, "Please fill your Message", Toast.LENGTH_SHORT).show();
                    return;
                }

                showDialog();
            }

            private void showDialog()
            {
                dialog = new Dialog(ServiceHomeGarden.this);
                dialog.setCancelable(false);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.alert_dialog);
                btn_yes = dialog.findViewById(R.id.btn_logout_yes);
                btn_no = dialog.findViewById(R.id.btn_logout_no);
                btn_yes.setText("Yes");
                btn_no.setText("No");
                TextView text_msg = (TextView) dialog.findViewById(R.id.text_msg);
                ImageView iv_image = (ImageView) dialog.findViewById(R.id.iv_image);
                iv_image.setImageDrawable(getResources().getDrawable(R.drawable.garden));
                TextView text = (TextView) dialog.findViewById(R.id.text);
                text_msg.setText("Submit Your request");
                text_msg.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                text.setText("Home Garden Services");
                text.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                btn_yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {
                        Boooknow();

                    }
                });

                btn_no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        getprojectImage();

    }

    private void Boooknow()
    {
        currentdate =  new SimpleDateFormat("yyyy MM dd", Locale.getDefault()).format(new Date());
        dialog.dismiss();
        ApiInterface apiInterface = Myconfig.getRetrofit().create(ApiInterface.class);
        Call<ResponseBody> result =apiInterface.bookGardenService(Shared_Preferences.getPrefs(getApplicationContext(), Constant.USER_ID),name1,email1,phoneno1,address1,currentdate,message1,"GardenService","Booked");
        result.enqueue(new Callback<ResponseBody>()
        {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
            {
                Toast toast = Toasty.success(ServiceHomeGarden.this, "Your Request has been Submited", Toast.LENGTH_LONG, true);
                toast.setGravity(Gravity.TOP,0,300);
                toast.show();

                name.setText("");
                email.setText("");
                phone.setText("");
                address.setText("");
                message.setText("");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t)
            {
                Toast toast = Toasty.error(ServiceHomeGarden.this, "Failed", Toast.LENGTH_SHORT, true);
                toast.setGravity(Gravity.TOP,0,300);
                toast.show();
            }
        });

    }

    private void getprojectImage()
    {
        ApiInterface apiInterface = Myconfig.getRetrofit().create(ApiInterface.class);
        Call<ResponseBody> result =apiInterface.getGardenProjects();
        result.enqueue(new Callback<ResponseBody>()
        {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
            {
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
                                list.add(new Gardenlist(object));
                                Log.d("lists","list:- "+object);
                            } catch (JSONException e)
                            {
                                e.printStackTrace();
                            }
                        }
                        gardenRec.setLayoutManager(new LinearLayoutManager(ServiceHomeGarden.this,RecyclerView.HORIZONTAL,false));
                        adapter= new GardenAdapter(ServiceHomeGarden.this,list);
                        gardenRec.setAdapter(adapter);
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

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}