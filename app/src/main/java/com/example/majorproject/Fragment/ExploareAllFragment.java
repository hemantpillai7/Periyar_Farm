package com.example.majorproject.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.majorproject.Adapter.ExploreAllProductAdapter;
import com.example.majorproject.Api.ApiInterface;
import com.example.majorproject.Api.Myconfig;

import com.example.majorproject.Model.TotalProductList;
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


public class ExploareAllFragment extends Fragment
{
    ProgressDialog dialog;

    RecyclerView rec;
    ArrayList<TotalProductList> totalProductLists = new ArrayList<TotalProductList>();
    ExploreAllProductAdapter adapter;
    public ExploareAllFragment()
    {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_exploare_all,container, false);

        rec=root.findViewById(R.id.rec_explore);

        fetchallproducts();

        return root;
    }

    private void fetchallproducts()
    {
        dialog= new ProgressDialog(getContext());
        dialog.show();
        dialog.setContentView(R.layout.progressbar);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        ApiInterface apiInterface = Myconfig.getRetrofit().create(ApiInterface.class);
        Call<ResponseBody> result = apiInterface.getTotalProducts();
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
                            Log.e("Sbc2", "" +object.getString("description"));
                            totalProductLists.add(new TotalProductList(object));

                        }
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2,GridLayoutManager.VERTICAL,false);
                        rec.setLayoutManager(gridLayoutManager);
                        adapter = new ExploreAllProductAdapter(getActivity(),totalProductLists);
                        rec.setAdapter(adapter);

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
                Toasty.error(getContext(),"Please Check Your Network",Toast.LENGTH_SHORT, true).show();
            }
        });
    }
}