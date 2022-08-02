package com.example.majorproject.Fragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.majorproject.Adapter.VideoAdapter;
import com.example.majorproject.Api.ApiInterface;
import com.example.majorproject.Api.Myconfig;

import com.example.majorproject.Model.Videolist;
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

public class YoutubeVideos extends AppCompatActivity
{
    RecyclerView videoRec;
    ArrayList<Videolist> videolists = new ArrayList<Videolist>();
    VideoAdapter videoAdapter;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_videos);
        videoRec=findViewById(R.id.video_rec);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Our Videos");

        getVideos();
    }

    private void getVideos()
    {
        dialog= new ProgressDialog(YoutubeVideos.this);
        dialog.show();
        dialog.setContentView(R.layout.progressbar);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        ApiInterface apiInterface = Myconfig.getRetrofit().create(ApiInterface.class);
        Call<ResponseBody> result =apiInterface.getOurVideos();
        result.enqueue(new Callback<ResponseBody>()
        {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
            {
                dialog.dismiss();
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
                            videolists.add(new Videolist(object));
                            Log.e("Video",""+videolists);
                        }
                        videoRec.setLayoutManager(new LinearLayoutManager(YoutubeVideos.this,RecyclerView.VERTICAL,false));
                        videoAdapter = new VideoAdapter(YoutubeVideos.this,videolists);
                        videoRec.setAdapter(videoAdapter);

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
                Toasty.error(YoutubeVideos.this,"Please Check Your Network", Toast.LENGTH_SHORT, true).show();
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}