package com.example.majorproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;

import android.graphics.Bitmap;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import android.view.View;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.majorproject.Api.ApiInterface;
import com.example.majorproject.Api.Constant;
import com.example.majorproject.Api.Myconfig;
import com.example.majorproject.Api.Shared_Preferences;
import com.example.majorproject.Model.ProductList;
import com.example.majorproject.Model.TotalProductList;
import com.squareup.picasso.Picasso;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailedActivity extends AppCompatActivity
{
    ProgressDialog dialog;

    TextView price, description , quantity;
    ImageView detailed_img,addItem,removeItem;
    Button addToCart ;
    RatingBar rating;

    ImageView addItem2,removeItem2;
    Button addToCart2 ;

    ImageView addItem3,removeItem3;
    Button addToCart3 ;

    ImageView addItem4,removeItem4;
    Button addToCart4 ;

    ProductList list = null;
    ProductList popularlist = null;
    ProductList recommendedlist = null;
    TotalProductList totalProductList =null;

    int totalQuantity=1;
    int totalprice=0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        detailed_img=findViewById(R.id.detailed_img);
        price=findViewById(R.id.detailed_price);
        description=findViewById(R.id.detailed_desc);
        rating=findViewById(R.id.starRating);
        quantity=findViewById(R.id.quantity);

        addToCart=findViewById(R.id.add_to_cart);
        addItem=findViewById(R.id.add_item);
        removeItem=findViewById(R.id.remove_item);

        addToCart2=findViewById(R.id.add_to_cart2);
        addItem2=findViewById(R.id.add_item2);
        removeItem2=findViewById(R.id.remove_item2);

        addToCart3=findViewById(R.id.add_to_cart3);
        addItem3=findViewById(R.id.add_item3);
        removeItem3=findViewById(R.id.remove_item3);

        addToCart4=findViewById(R.id.add_to_cart4);
        addItem4=findViewById(R.id.add_item4);
        removeItem4=findViewById(R.id.remove_item4);



        detailed_img.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

            }
        });



        getAllproducts();

        getPopularproducts();

        getRecommendedProducts();

         getExploreAllProducrs();


    }

    private void getExploreAllProducrs()
    {
        //passing data from popular adapter
        final Object obj2 = getIntent().getSerializableExtra("ExploreAll");
        if (obj2 instanceof TotalProductList)
        {
            totalProductList = (TotalProductList) obj2;
            addItem4.setVisibility(View.VISIBLE);
            removeItem4.setVisibility(View.VISIBLE);
            addToCart4.setVisibility(View.VISIBLE);

        }
        //it should be below find by id
        if(totalProductList != null)
        {
            Picasso.get().load(Constant.AllProductsLINK+totalProductList.getImg_url())
                    .placeholder(R.drawable.no_image_available)
                    .into(detailed_img);
            rating.setRating(Float.parseFloat(totalProductList.getRating()));
            description.setText(totalProductList.getDescription());
            price.setText(totalProductList.getPrice()+"/-");
            totalprice= totalProductList.getPrice() * totalQuantity;

        }
        addItem4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(totalQuantity<10)
                {
                    totalQuantity ++;
                    quantity.setText(String.valueOf(totalQuantity));
                    totalprice= totalProductList.getPrice() * totalQuantity;
                }
            }
        });
        removeItem4.setOnClickListener(v -> {
            if(totalQuantity > 1)
            {
                totalQuantity --;
                quantity.setText(String.valueOf(totalQuantity));
                totalprice= totalProductList.getPrice() * totalQuantity;
            }
        });
        addToCart4.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dialog= new ProgressDialog(DetailedActivity.this);
                dialog.show();
                dialog.setContentView(R.layout.progressbar);
                dialog.setCancelable(false);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                ApiInterface apiInterface = Myconfig.getRetrofit().create(ApiInterface.class);
                Call<ResponseBody> result = apiInterface.addtocart(
                        Shared_Preferences.getPrefs(DetailedActivity.this, Constant.USER_ID)
                        , totalProductList.getName()
                        , totalProductList.getDescription()
                        , totalProductList.getDiscount()
                        , totalProductList.getType()
                        , totalQuantity
                        , totalProductList.getPrice()
                        , totalprice
                        , totalProductList.getRating()
                        , totalProductList.getImg_url()
                );
                result.enqueue(new Callback<ResponseBody>()
                {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
                    {
                        dialog.dismiss();
                        Toast.makeText(DetailedActivity.this, "Added To Cart", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t)
                    {
                        dialog.dismiss();
                        Toast.makeText(DetailedActivity.this, "Please Check Your Network", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }


    private void getRecommendedProducts()
    {
        if(recommendedlist != null)
        {
            addItem3.setVisibility(View.VISIBLE);
            removeItem3.setVisibility(View.VISIBLE);
            addToCart3.setVisibility(View.VISIBLE);


            Picasso.get().load(Constant.AllProductsLINK+recommendedlist.getImg_url())
                    .placeholder(R.drawable.no_image_available)
                    .into(detailed_img);
            rating.setRating(Float.parseFloat(recommendedlist.getRating()));
            description.setText(recommendedlist.getDescription());
            price.setText(recommendedlist.getPrice()+"/-");
            totalprice= recommendedlist.getPrice() * totalQuantity;

        }
        addItem3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(totalQuantity<10)
                {
                    totalQuantity ++;
                    quantity.setText(String.valueOf(totalQuantity));
                    totalprice= recommendedlist.getPrice() * totalQuantity;

                }
            }
        });
        removeItem3.setOnClickListener(v -> {
            if(totalQuantity > 1)
            {
                totalQuantity --;
                quantity.setText(String.valueOf(totalQuantity));
                totalprice= recommendedlist.getPrice() * totalQuantity;

            }
        });

        addToCart3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dialog= new ProgressDialog(DetailedActivity.this);
                dialog.show();
                dialog.setContentView(R.layout.progressbar);
                dialog.setCancelable(false);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                ApiInterface apiInterface = Myconfig.getRetrofit().create(ApiInterface.class);
                Call<ResponseBody> result = apiInterface.addtocart(
                        Shared_Preferences.getPrefs(DetailedActivity.this, Constant.USER_ID)
                        , recommendedlist.getName()
                        , recommendedlist.getDescription()
                        , recommendedlist.getDiscount()
                        , recommendedlist.getType()
                        , totalQuantity
                        , recommendedlist.getPrice()
                        , totalprice
                        , recommendedlist.getRating()
                        , recommendedlist.getImg_url()
                );
                result.enqueue(new Callback<ResponseBody>()
                {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
                    {
                        dialog.dismiss();
                        Toast.makeText(DetailedActivity.this, "Added To Cart", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t)
                    {
                        dialog.dismiss();
                        Toast.makeText(DetailedActivity.this, "Please Check Your Network", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void getPopularproducts()
    {
        //passing data from popular adapter
        final Object obj2 = getIntent().getSerializableExtra("Popularproducts");
        if (obj2 instanceof ProductList)
        {
            popularlist = (ProductList) obj2;
            addItem2.setVisibility(View.VISIBLE);
            removeItem2.setVisibility(View.VISIBLE);
            addToCart2.setVisibility(View.VISIBLE);

        }
        //it should be below find by id
        if(popularlist != null)
        {
            Picasso.get().load(Constant.AllProductsLINK+popularlist.getImg_url())
                    .placeholder(R.drawable.no_image_available)
                    .into(detailed_img);
            rating.setRating(Float.parseFloat(popularlist.getRating()));
            description.setText(popularlist.getDescription());
            price.setText(popularlist.getPrice()+"/-");
            totalprice= popularlist.getPrice() * totalQuantity;

        }
        addItem2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(totalQuantity<10)
                {
                    totalQuantity ++;
                    quantity.setText(String.valueOf(totalQuantity));
                    totalprice= popularlist.getPrice() * totalQuantity;
                }
            }
        });
        removeItem2.setOnClickListener(v -> {
            if(totalQuantity > 1)
            {
                totalQuantity --;
                quantity.setText(String.valueOf(totalQuantity));
                totalprice= popularlist.getPrice() * totalQuantity;
            }
        });
        addToCart2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dialog= new ProgressDialog(DetailedActivity.this);
                dialog.show();
                dialog.setContentView(R.layout.progressbar);
                dialog.setCancelable(false);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                ApiInterface apiInterface = Myconfig.getRetrofit().create(ApiInterface.class);
                Call<ResponseBody> result = apiInterface.addtocart(
                        Shared_Preferences.getPrefs(DetailedActivity.this, Constant.USER_ID)
                        , popularlist.getName()
                        , popularlist.getDescription()
                        , popularlist.getDiscount()
                        , popularlist.getType()
                        , totalQuantity
                        , popularlist.getPrice()
                        , totalprice
                        , popularlist.getRating()
                        , popularlist.getImg_url()
                );
                result.enqueue(new Callback<ResponseBody>()
                {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
                    {
                        dialog.dismiss();
                        Toast.makeText(DetailedActivity.this, "Added To Cart", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t)
                    {
                        dialog.dismiss();
                        Toast.makeText(DetailedActivity.this, "Please Check Your Network", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        //passing data from recommended adapter
        final Object obj3 = getIntent().getSerializableExtra("RecommendedProducts");
        if (obj3 instanceof ProductList)
        {
            recommendedlist = (ProductList) obj3;

        }
    }

    private void getAllproducts()
    {
        //passing data from Detailed adapter
        final Object object = getIntent().getSerializableExtra("Allproducts");
        if (object instanceof ProductList)
        {
            list = (ProductList) object;

            addItem.setVisibility(View.VISIBLE);
            removeItem.setVisibility(View.VISIBLE);
            addToCart.setVisibility(View.VISIBLE);

        }
        if(list != null)
        {
            Picasso.get().load(Constant.AllProductsLINK+list.getImg_url())
                    .placeholder(R.drawable.no_image_available)
                    .into(detailed_img);
            rating.setRating(Float.parseFloat(list.getRating()));
            description.setText(list.getDescription());
            price.setText(list.getPrice()+"/-");
            totalprice= list.getPrice() * totalQuantity;
//
        }
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(totalQuantity<10)
                {
                    totalQuantity ++;
                    quantity.setText(String.valueOf(totalQuantity));
                    totalprice= list.getPrice() * totalQuantity;
                }
            }
        });
        removeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(totalQuantity > 1)
                {
                    totalQuantity --;
                    quantity.setText(String.valueOf(totalQuantity));
                    totalprice= list.getPrice() * totalQuantity;
                }
            }
        });
        addToCart.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dialog= new ProgressDialog(DetailedActivity.this);
                dialog.show();
                dialog.setContentView(R.layout.progressbar);
                dialog.setCancelable(false);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                ApiInterface apiInterface = Myconfig.getRetrofit().create(ApiInterface.class);
                Call<ResponseBody> result = apiInterface.addtocart(
                        Shared_Preferences.getPrefs(DetailedActivity.this, Constant.USER_ID)
                        , list.getName()
                        , list.getDescription()
                        , list.getDiscount()
                        , list.getType()
                        , totalQuantity
                        , list.getPrice()
                        , totalprice
                        , list.getRating()
                        , list.getImg_url()
                );
                result.enqueue(new Callback<ResponseBody>()
                {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
                    {
                        dialog.dismiss();
                        Toast.makeText(DetailedActivity.this, "Added To Cart", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t)
                    {
                        dialog.dismiss();
                        Toast.makeText(DetailedActivity.this, "Please Check Your Network", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public Uri getImageUri(Context context, Bitmap bitmap)
    {
        String profile = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap,"ProfileImg","");

        return Uri.parse(profile);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}