package com.example.majorproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.majorproject.Api.ApiInterface;
import com.example.majorproject.Api.AppConfig;
import com.example.majorproject.Api.Constant;
import com.example.majorproject.Api.MyValidator;
import com.example.majorproject.Api.Myconfig;
import com.example.majorproject.Api.Shared_Preferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import es.dmoral.toasty.Toasty;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserLogin extends AppCompatActivity {

    TextView signup;
    EditText emailtxt,passwordtxt;
    Button login;
    ProgressDialog dialog;
    String email,pass;

    // to remember saved login
    private boolean isRememberUserLogin = false;
    private AppConfig appConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        signup=findViewById(R.id.goToUsersignup);
        emailtxt=findViewById(R.id.email_login);
        passwordtxt=findViewById(R.id.password_login);
        login=findViewById(R.id.loginBn);

        appConfig = new AppConfig(this);
        if(appConfig.isUserLogin())
        {
            String name = appConfig.getNameofUser();
            Intent intent = new Intent(UserLogin.this,MainActivity.class);
            intent.putExtra("name",name);
            startActivity(intent);
            finish();
        }

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserLogin.this,UserRegistration.class));
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if (validateFields())
                {
                    loginn();
                }

            }
        });
    }

    private boolean validateFields()
    {
        boolean result = true;
        if (!MyValidator.isValidField(emailtxt))
        {
            result = false;
        }
        if (!MyValidator.isValidField(passwordtxt)) {
            result = false;
        }
        return result;
    }

    private void loginn()
    {
        dialog= new ProgressDialog(UserLogin.this);
        dialog.show();
        dialog.setContentView(R.layout.progressbar);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        email= emailtxt.getText().toString();
        pass= passwordtxt.getText().toString();
        ApiInterface apiInterface = Myconfig.getRetrofit().create(ApiInterface.class);
        Call<ResponseBody> call = apiInterface.performUserLogin(email,pass);
        call.enqueue(new Callback<ResponseBody>()
        {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
            {
                dialog.dismiss();
                try {
                    String output = response.body().string();
                    Log.d("Response", "Response:-" + output);
                    JSONObject jsonObject = new JSONObject(output);
                    if (jsonObject.getString("ResponseCode").equals("1"))
                    {
                        appConfig.updateUserLogin(true);
                        String id = jsonObject.getString("userid");
                        Shared_Preferences.setPrefs(UserLogin.this, Constant.USER_ID,id);
                        Toast.makeText(UserLogin.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(UserLogin.this,MainActivity.class);
                        startActivity(i);
                        finish();
                    }
                    else if (jsonObject.getString("ResponseCode").equals("0"))
                    {
                        Toast.makeText(UserLogin.this, "Enter Valid Credientials", Toast.LENGTH_SHORT).show();
                        passwordtxt.setText("");
                    }
                    else
                    {
                        Toast.makeText(UserLogin.this, "Check Network Connection", Toast.LENGTH_SHORT).show();
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
                Toasty.error(UserLogin.this,"Please Check Your Network",Toast.LENGTH_SHORT, true).show();
            }
        });

    }


    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
    }
}