package com.example.majorproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.text.LineBreaker;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.majorproject.Api.Constant;
import com.example.majorproject.Model.GovList;
import com.squareup.picasso.Picasso;

public class GovSchemeDetail extends AppCompatActivity
{
    GovList list= null;
    ImageView img;
    TextView name,desc;
    Button apply;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gov_scheme_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Back");

        name=findViewById(R.id.text);
        desc=findViewById(R.id.textView12);
        img=findViewById(R.id.imgview);
        apply=findViewById(R.id.btnscheme);

        TextView description=findViewById(R.id.textView12);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            description.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_INTER_WORD);
        }

        getScheme();
    }

    private void getScheme()
    {
        final Object obj = getIntent().getSerializableExtra("GovScheme");

        if(obj instanceof GovList)
        {
            list=(GovList) obj;
        }
        if(list != null)
        {
            name.setText(list.getName());
            desc.setText(list.getDescription());
            Picasso.get().load(Constant.GovSchemeLink+list.getImg_link())
                    .placeholder(R.drawable.no_image_available)
                    .into(img);

            apply.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("" +list.getLink())));
                }
            });
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}