package com.example.majorproject.Fragment;

import static com.example.majorproject.Api.Constant.SliderLINK;

import android.app.ProgressDialog;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.majorproject.Adapter.ExploreAdapter;
import com.example.majorproject.Adapter.PopularAdapter;
import com.example.majorproject.Adapter.RecommendedAdapter;
import com.example.majorproject.Adapter.SearchAdapter;
import com.example.majorproject.Api.ApiInterface;

import com.example.majorproject.Api.Myconfig;
import com.example.majorproject.Model.ExploreList;
import com.example.majorproject.Model.ProductList;
import com.example.majorproject.R;


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

public class HomeFragment extends Fragment
{

    public HomeFragment()
    {

    }
    RecyclerView popularRec, expRec, recommendedRec , searchRec;
    EditText search_box;

    ProgressDialog dialog;

    ArrayList<SlideModel>sliderModelList=new ArrayList<>();
    private ImageSlider imageSlider;

    //Popular items
    ArrayList<ProductList> popularModelList = new ArrayList<ProductList>();
    PopularAdapter popularAdapter;

    //Explore items
    ArrayList<ExploreList> exploreModelList = new ArrayList<ExploreList>();
    ExploreAdapter exploreAdapter;

    //Recommended items
    ArrayList<ProductList> recommendedModelList = new ArrayList<ProductList>();
    RecommendedAdapter recommendedAdapter;

    //Search
    ArrayList<ProductList> searchlist = new ArrayList<ProductList>();
    SearchAdapter searchAdapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        popularRec = root.findViewById(R.id.pop_rec);
        expRec = root.findViewById(R.id.explore_rec);
        recommendedRec = root.findViewById(R.id.recommended_rec);
        imageSlider=root.findViewById(R.id.image_slider);
        searchRec=root.findViewById(R.id.search_rec);
        search_box=root.findViewById(R.id.search_box);


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run()
            {
                search_box.getText().toString();
                Log.d("search123",""+search_box.getText().toString());

                if(search_box.getText().equals(" "))
                {
                    searchRec.setVisibility(View.GONE);
                }
                else
                {
                    searchRec.setVisibility(View.VISIBLE);
                }

                handler.postDelayed(this,100);
            }
        }, 500);


        search_box.setOnKeyListener(new View.OnKeyListener()
        {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent)
            {
                if(keyEvent.getAction() == KeyEvent.ACTION_DOWN && i== KeyEvent.KEYCODE_ENTER || keyEvent.getAction() == KeyEvent.ACTION_DOWN && i== KeyEvent.KEYCODE_BACK)
                {
                    searchlist.clear();

                    search();
                }
                return false;
            }
        });

        imageSlider.setImageList(sliderModelList);


        getPopularproducts();

        getExploreProduct();

        getRecommendedProduct();

        getSlider();



        return root;
    }

    private void search()
    {
        ApiInterface apiInterface = Myconfig.getRetrofit().create(ApiInterface.class);
        Call<ResponseBody> result = apiInterface.search(search_box.getText().toString());
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
                        JSONArray jsonArray = jsonObject.getJSONArray("Data");
                        for (int i = 0; i < jsonArray.length(); i++)
                        {
                            JSONObject object = jsonArray.getJSONObject(i);
                            searchlist.add(new ProductList(object));
                        }
                        searchRec.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
                        searchAdapter= new SearchAdapter(getContext(),searchlist);
                        searchRec.setAdapter(searchAdapter);
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


    private void getSlider()
    {
        ApiInterface apiInterface = Myconfig.getRetrofit().create(ApiInterface.class);
        Call<ResponseBody> result = apiInterface.getSlider();
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
                        JSONArray jsonArray = jsonObject.getJSONArray("Data");
                        for (int i = 0; i < jsonArray.length(); i++)
                        {
                            JSONObject object = jsonArray.getJSONObject(i);

                            String s= SliderLINK+object.getString("sliderImage");

                            sliderModelList.add(new SlideModel(s,ScaleTypes.FIT));
                            imageSlider.setImageList(sliderModelList);

                        }
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

    private void getRecommendedProduct()
    {
        
        ApiInterface apiInterface = Myconfig.getRetrofit().create(ApiInterface.class);
        Call<ResponseBody> result = apiInterface.getRecommendedProducts();
        result.enqueue(new Callback<ResponseBody>()
        {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
            {

                try {
                    String output = response.body().string();
                    JSONObject jsonObject = new JSONObject(output);
                    //Log.e("Recommended","Recommended :-"+output);
                    if (jsonObject.getString("ResponseCode").equals("1"))
                    {
                        JSONArray jsonArray = jsonObject.getJSONArray("Data");
                        for (int i = 0; i < jsonArray.length(); i++)
                        {
                            JSONObject object = jsonArray.getJSONObject(i);
                            recommendedModelList.add(new ProductList(object));
                            //Log.e("List","Recommended:- "+recommendedModelList.toString());
                        }
                        recommendedRec.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
                        recommendedAdapter = new RecommendedAdapter(getContext(), recommendedModelList);
                        recommendedRec.setAdapter(recommendedAdapter);
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

    private void getExploreProduct()
    {
        ApiInterface apiInterface = Myconfig.getRetrofit().create(ApiInterface.class);
        Call<ResponseBody> result = apiInterface.getExploreProducts();
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
                        JSONArray jsonArray = jsonObject.getJSONArray("Data");
                        for (int i = 0; i < jsonArray.length(); i++)
                        {
                            JSONObject object = jsonArray.getJSONObject(i);
                            exploreModelList.add(new ExploreList(object));
                        }
                        expRec.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
                        exploreAdapter = new ExploreAdapter(getContext(),exploreModelList);
                        expRec.setAdapter(exploreAdapter);

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

    private void getPopularproducts()
    {
        dialog= new ProgressDialog(getContext());
        dialog.show();
        dialog.setContentView(R.layout.progressbar);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        ApiInterface apiInterface = Myconfig.getRetrofit().create(ApiInterface.class);
        Call<ResponseBody> result = apiInterface.getPopularProduct();
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
                            popularModelList.add(new ProductList(object));
                        }
                        popularRec.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
                        popularAdapter = new PopularAdapter(getContext(), popularModelList);
                        popularRec.setAdapter(popularAdapter);
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

                Toasty.error(getContext(),"Please Check Your Network",Toast.LENGTH_SHORT, true).show();
            }
        });
    }

}