package com.example.majorproject.Payment;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.example.majorproject.Api.ApiInterface;
import com.example.majorproject.Api.Constant;
import com.example.majorproject.Api.Myconfig;
import com.example.majorproject.MainActivity;
import com.example.majorproject.Model.CartList;
import com.example.majorproject.R;
import com.example.majorproject.Api.Shared_Preferences;
import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import es.dmoral.toasty.Toasty;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentGateway extends AppCompatActivity implements PaymentResultWithDataListener
{

    private static final String TAG = PaymentGateway.class.getSimpleName();
    ArrayList<CartList> arraylist=new ArrayList<>();
    String currentdate,currentdatelong ,name1,email1,address1,plotaddress1,phoneno1,type1;
    int size=0;
    int count=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_gateway);

        Checkout.preload(getApplicationContext());

        currentdatelong =  new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(new Date());
        currentdate =  new SimpleDateFormat("yyyy MM dd", Locale.getDefault()).format(new Date());

        Intent i = getIntent();
        if(i.getStringExtra("name").equals("cart"))
        {
            arraylist = (ArrayList<CartList>) i.getSerializableExtra("mylist");  //To pass total cart item to orderhistory


            name1= getIntent().getStringExtra("username");
            address1= getIntent().getStringExtra("address");
            phoneno1= getIntent().getStringExtra("number");
            email1= getIntent().getStringExtra("email");
            String amt= getIntent().getStringExtra("total");

            Log.d("Orders",""+name1);
            Log.d("Orders",""+address1);
            Log.d("Orders",""+phoneno1);
            Log.d("Orders",""+amt);
            Log.d("Orders",""+arraylist.size());
            size= arraylist.size();
            PaymentNow(amt);
        }
        else if(i.getStringExtra("name").equals("soil"))
        {
            String amt= getIntent().getStringExtra("total");
            name1= getIntent().getStringExtra("name1");
            email1= getIntent().getStringExtra("email1");
            address1= getIntent().getStringExtra("address1");
            plotaddress1= getIntent().getStringExtra("plotaddress1");
            phoneno1= getIntent().getStringExtra("phoneno1");
            type1= getIntent().getStringExtra("type1");

            PaymentNow(amt);
        }

    }

    private void PaymentNow(String amount)
    {
        final Activity activity = this;
        Checkout checkout = new Checkout();

        checkout.setKeyID("rzp_test_jwABqtLp1DIETF");
        checkout.setImage(R.drawable.lo);
        double finalAmount = Float.parseFloat(amount) * 100;

        try {

            JSONObject options = new JSONObject();

            options.put("name", Shared_Preferences.getPrefs(PaymentGateway.this, Constant.USER_Name));
            options.put("description", "Reference No. #123456");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            //   options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
            options.put("theme.color", "#009688");
            options.put("currency", "INR");
            options.put("product Name", "test");
            options.put("amount", finalAmount + "");//pass amount in currency subunits
            options.put("prefill.email", Shared_Preferences.getPrefs(PaymentGateway.this, Constant.USER_email));
            options.put("prefill.contact", Shared_Preferences.getPrefs(PaymentGateway.this, Constant.USER_phNo));

            checkout.open(activity, options);
        }
        catch (Exception e)
        {
            Log.e(TAG, "Error in starting Razorpay Checkout", e);
        }
    }

    @Override
    public void onPaymentSuccess(String s, PaymentData paymentData)
    {
        try
        {
            Intent i = getIntent();
            if(i.getStringExtra("name").equals("cart"))
            {
                uploadorderhistory();
                Intent ii = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(ii);
            }
            else if(i.getStringExtra("name").equals("soil"))
            {
                bookSoilservice();
                Intent ii = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(ii);
            }
        }
        catch (Exception e)
        {

        }

    }

    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData)
    {
        Toasty.error(this,"Failed",Toast.LENGTH_SHORT).show();
        Intent ii = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(ii);
    }

    //user
    private void uploadorderhistory()
    {
        String status="Ordered";
        for (CartList list: arraylist)
        {
            ApiInterface apiInterface = Myconfig.getRetrofit().create(ApiInterface.class);
            Call<ResponseBody> result = apiInterface.orderhistoryupdate(
                    Shared_Preferences.getPrefs(PaymentGateway.this, Constant.USER_ID),
                    name1,
                    list.getName(),
                    currentdate,
                    list.getQty(),
                    list.getPrice(),
                    list.getTotalprice(),
                    list.getRating(),
                    address1,
                    phoneno1,
                    status,
                    email1,
                    list.getImg_url());
            result.enqueue(new Callback<ResponseBody>()
            {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
                {
                     count =count+1;
                    if(count==size)
                    {
                        Toasty.success(PaymentGateway.this, "Your Order Has Been Placed !", Toast.LENGTH_SHORT, true).show();

                    }

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t)
                {

                }
            });


        }

    }

    private void bookSoilservice()
    {
        ApiInterface apiInterface = Myconfig.getRetrofit().create(ApiInterface.class);
        Call<ResponseBody> result = apiInterface.bookSoilService(Shared_Preferences.getPrefs(getApplicationContext(), Constant.USER_ID),
                name1,email1,phoneno1,address1,plotaddress1,type1,currentdatelong,"SoilService","Booked");
        result.enqueue(new Callback<ResponseBody>()
        {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
            {
                Toast toast = Toasty.success(PaymentGateway.this, "Your Request has been Submited", Toast.LENGTH_SHORT, true);
                toast.setGravity(Gravity.TOP,0,300);
                toast.show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t)
            {
                Toasty.error(PaymentGateway.this, "Failed", Toast.LENGTH_SHORT, true).show();
            }
        });
    }
}