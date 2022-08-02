package com.example.majorproject.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.majorproject.Api.Constant;

import com.example.majorproject.Model.Videolist;
import com.example.majorproject.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.Viewholder>
{
    private Context context;
    private List<Videolist> videoList;

    public VideoAdapter(Context context, List<Videolist> videoList)
    {
        this.context = context;
        this.videoList = videoList;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return new Viewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.ourvideo,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, @SuppressLint("RecyclerView") int position)
    {
        holder.name.setText(videoList.get(position).getName());
        Picasso.get().load(Constant.VideoLINK+videoList.get(position).getImg_url())
                .placeholder(R.drawable.video)
                .into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("" +videoList.get(position).getLink())));
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(videoList.get(position).getLink()));
                i.putExtra("force_fullscreen",true);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder
    {
        ImageView imageView;
        TextView name;

        public Viewholder(@NonNull View itemView)
        {
            super(itemView);
            imageView=itemView.findViewById(R.id.youtubeplay);
            name=itemView.findViewById(R.id.videoname);
        }
    }
}
