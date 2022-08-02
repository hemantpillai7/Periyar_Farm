package com.example.majorproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.majorproject.Api.ApiInterface;
import com.example.majorproject.Api.AppConfig;
import com.example.majorproject.Api.Constant;
import com.example.majorproject.Api.Myconfig;
import com.example.majorproject.Api.Shared_Preferences;
import com.example.majorproject.Fragment.About_Us;
import com.example.majorproject.Fragment.CartFragment;
import com.example.majorproject.Fragment.ExploareAllFragment;
import com.example.majorproject.Fragment.HomeFragment;
import com.example.majorproject.Fragment.OrderFragment;
import com.example.majorproject.Fragment.ProfileFragment;
import com.example.majorproject.Fragment.YoutubeVideos;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{

    private AppConfig appConfig;
    ApiInterface apiInterface;

    DrawerLayout drawerLayout;
    NavigationView navigationView;

    private Dialog dialog;
    private Button btn_yes, btn_no;

    TextView name;
    CircleImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = Myconfig.getRetrofit();
        apiInterface = retrofit.create(ApiInterface.class);

        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.nav_view);
        name=findViewById(R.id.name);
        image=findViewById(R.id.proImg);

        //Toolbar set
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);//Remove title from toolbar


        //Drawer
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.nav_drawer_open,R.string.nav_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this); //Make Drawer clickable

        replaceFragment(new HomeFragment());//Default Fragment

        Log.e("print","UserId:-"+Shared_Preferences.getPrefs(MainActivity.this, Constant.USER_ID));

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {

                int id = item.getItemId();
                item.setChecked(true); //Highlight
                drawerLayout.closeDrawer(GravityCompat.START);
                switch (id)
                {
                    case R.id.nav_home:
                        replaceFragment(new HomeFragment());
                        break;

                    case R.id.nav_orders:
                        replaceFragment(new OrderFragment());
                        break;

                    case R.id.nav_cart:
                        replaceFragment(new CartFragment());
                        break;

                    case R.id.nav_expall:
                        replaceFragment(new ExploareAllFragment());
                        break;

                    case R.id.nav_profile:
                        replaceFragment(new ProfileFragment());
                        break;

                    case R.id.nav_video:
                        Intent ii = new Intent(MainActivity.this, YoutubeVideos.class);
                        startActivity(ii);
                        break;

                    case R.id.nav_Service:
                        Intent a = new Intent(MainActivity.this,Services.class);
                        startActivity(a);
                        break;

                    case R.id.nav_aboutus:
                        Intent aa = new Intent(MainActivity.this, About_Us.class);
                        startActivity(aa);
                        break;

                    case R.id.nav_share:
                        Intent iii = new Intent(MainActivity.this,Share.class);
                        startActivity(iii);
                        break;

                    case R.id.nav_logout:
                        showDialog();
                        break;

                    default:
                        return true;

                }

                return true;
            }

        });

        getprofile();

    }

    private void getprofile()
    {
        ApiInterface apiInterface = Myconfig.getRetrofit().create(ApiInterface.class);
        Call<ResponseBody> result = apiInterface.getProfile(Shared_Preferences.getPrefs(MainActivity.this, Constant.USER_ID));
        result.enqueue(new Callback<ResponseBody>()
        {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
            {
                try {
                    String output = response.body().string();
                    JSONObject jsonObject = new JSONObject(output);
                    if (jsonObject.getString("ResponseCode").equals("1"))
                    {
                        JSONObject jsonObject1 = jsonObject.getJSONObject("Data");
                        name.setText(jsonObject1.getString("name"));
                        String profileurl = jsonObject1.getString("profileImg");
                        Picasso.get().load(Constant.ProfileLink+profileurl)
                                .placeholder(R.drawable.no_image_available)
                                .into(image);
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

    private void showDialog()
    {
        dialog = new Dialog(MainActivity.this);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.alert_dialog);
        btn_yes = dialog.findViewById(R.id.btn_logout_yes);
        btn_no = dialog.findViewById(R.id.btn_logout_no);
        btn_yes.setText("Logout");
        btn_no.setText("Cancel");
        TextView text_msg = (TextView) dialog.findViewById(R.id.text_msg);
        ImageView iv_image = (ImageView) dialog.findViewById(R.id.iv_image);
        iv_image.setImageDrawable(getResources().getDrawable(R.drawable.logout));
        TextView text = (TextView) dialog.findViewById(R.id.text);
        text_msg.setText("Are you sure you want to Logout");
        text.setText("Logout...!");
        appConfig = new AppConfig(this);
        btn_yes.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                appConfig.updateUserLogin(false);
                startActivity(new Intent(MainActivity.this,UserLogin.class));
                finish();
                Toast.makeText(MainActivity.this, "Logout Successfully...", Toast.LENGTH_SHORT).show();

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

    private void replaceFragment(Fragment fragment)
    {
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }
    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed()
    {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        {
            replaceFragment(new HomeFragment());

            if (doubleBackToExitPressedOnce)
            {
                //super.onBackPressed();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    finishAffinity();
                    // tv_thank.setVisibility(View.VISIBLE);
                } else {
                    System.exit(0);
                }
                return;
            }

            this.doubleBackToExitPressedOnce = true;


            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        return true;
    }
}