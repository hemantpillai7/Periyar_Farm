package com.example.majorproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.majorproject.Api.ApiInterface;
import com.example.majorproject.Api.AppConfig;
import com.example.majorproject.Api.Myconfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import es.dmoral.toasty.Toasty;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRegistration extends AppCompatActivity {

    TextView login;
    EditText email,password,name,phno,addr;
    Button signup;
    ProgressDialog dialog;

    String userName,userEmail,userPassword,userPhone,userAddr;
    private AppConfig appConfig;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);


        login=findViewById(R.id.goToUserLogin);
        signup=findViewById(R.id.signUpBtn);
        name=findViewById(R.id.name_reg);
        email=findViewById(R.id.email_reg);
        password=findViewById(R.id.password_reg);
        phno=findViewById(R.id.phone_reg);
        addr=findViewById(R.id.addr_reg);

        login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(UserRegistration.this,UserLogin.class);
                startActivity(i);
                finish();
            }
        });

        signup.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                createUser();
            }
        });
    }

    private void createUser()
    {
        userName= name.getText().toString();
        userEmail= email.getText().toString();
        userPassword= password.getText().toString();
        userPhone = phno.getText().toString();
        userAddr = addr.getText().toString();

        //Validation
        if(TextUtils.isEmpty(userName))
        {
            Toast.makeText(this, "Name is Empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(userEmail))
        {
            Toast.makeText(this, "Eamil is Empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(userPassword))
        {
            Toast.makeText(this, "Password is Empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if(userPassword.length() < 6)
        {
            Toast.makeText(this, "Password Length must be grater than 6 letters", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(userPhone))
        {
            Toast.makeText(this, "Phnone Number is Empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if(userPhone.length() < 9)
        {
            Toast.makeText(this, "Please enter valid Phone Number", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(userAddr))
        {
            Toast.makeText(this, "Address is Empty", Toast.LENGTH_SHORT).show();
            return;
        }
        else
        {
            Log.e("Hemant",""+userName);
            Log.e("Hemant",""+userEmail);
            Log.e("Hemant",""+userPassword);
            Log.e("Hemant",""+userPhone);
            Log.e("Hemant",""+userAddr);
            register();
      }

    }

    private void register()
    {
        dialog= new ProgressDialog(UserRegistration.this);
        dialog.show();
        dialog.setContentView(R.layout.progressbar);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        ApiInterface apiInterface = Myconfig.getRetrofit().create(ApiInterface.class);
        final Call<ResponseBody> result = (Call<ResponseBody>) apiInterface.performUserSignIn(userName,userEmail,userPassword,userPhone,"no_image_available.jpg",userAddr);
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
                        Toast.makeText(UserRegistration.this, "User Already Exists", Toast.LENGTH_SHORT).show();
                        name.setText("");
                        email.setText("");
                        password.setText("");
                        phno.setText("");
                        addr.setText("");
                        Intent i = new Intent(UserRegistration.this, UserLogin.class);
                        startActivity(i);
                        finish();
                    }
                    else if (jsonObject.getString("ResponseCode").equals("2"))
                    {
                        Toast.makeText(UserRegistration.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                        name.setText("");
                        email.setText("");
                        password.setText("");
                        phno.setText("");
                        addr.setText("");
                        Intent i = new Intent(UserRegistration.this, MainActivity.class);
                        startActivity(i);
                        finish();
                    }
                    else
                    {
                        Toast.makeText(UserRegistration.this, "Failed", Toast.LENGTH_SHORT).show();
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
                Toasty.error(UserRegistration.this,"Please Check Your Network",Toast.LENGTH_SHORT, true).show();
            }
        });
    }
}