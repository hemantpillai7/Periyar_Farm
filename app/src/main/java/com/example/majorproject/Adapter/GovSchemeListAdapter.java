package com.example.majorproject.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.majorproject.Api.Constant;

import com.example.majorproject.GovSchemeDetail;
import com.example.majorproject.Model.GovList;
import com.example.majorproject.R;

import java.util.ArrayList;

public class GovSchemeListAdapter extends ArrayAdapter<GovList>
{

    public GovSchemeListAdapter(@NonNull Context context, ArrayList<GovList> govLists)
    {
        super(context, R.layout.custom_govschemelist,govLists);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        GovList list=getItem(position);
        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_govschemelist,parent,false);
        }

        TextView name = convertView.findViewById(R.id.schemename);
        ImageView img =convertView.findViewById(R.id.schemeimg);

        name.setText(list.getName());
        Glide.with(convertView).load(Constant.GovSchemeLink+list.getImg_link()).into(img);

        convertView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(getContext(), GovSchemeDetail.class);
                i.putExtra("GovScheme",list);
                getContext().startActivity(i);
            }
        });

        return convertView;
    }
}
