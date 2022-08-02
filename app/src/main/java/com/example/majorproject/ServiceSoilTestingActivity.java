package com.example.majorproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.majorproject.Api.MyValidator;
import com.example.majorproject.Payment.PaymentGateway;

public class ServiceSoilTestingActivity extends AppCompatActivity
{

    Button submit;
    EditText name,email,address,plotaddress,phoneno;
    Spinner type;

    private Dialog dialog;
    private Button btn_yes, btn_no;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_soil_testing);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Back");

        name=findViewById(R.id.plot_name);
        email=findViewById(R.id.plot_email);
        address=findViewById(R.id.plot_address);
        plotaddress=findViewById(R.id.plot_plotaddress);
        phoneno=findViewById(R.id.plot_number);
        type=findViewById(R.id.plot_spinner);
        submit=findViewById(R.id.plot_submit);


        submit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (validateFields())
                {
                    showDialog();
                }

            }
        });
    }

    private boolean validateFields()
    {
        boolean result = true;
        if (!MyValidator.isValidField(name))
        {
            result = false;
        }
        if (!MyValidator.isValidField(email)) {
            result = false;
        }
        if (!MyValidator.isValidField(address)) {
            result = false;
        }
        if (!MyValidator.isValidField(plotaddress)) {
            result = false;
        }
        if (!MyValidator.isValidMobile(phoneno)) {
            result = false;
        }

        return result;
    }

    private void showDialog()
    {
        dialog = new Dialog(ServiceSoilTestingActivity.this);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.alert_dialog);
        btn_yes = dialog.findViewById(R.id.btn_logout_yes);
        btn_no = dialog.findViewById(R.id.btn_logout_no);
        btn_yes.setText("PayNow");
        btn_no.setText("Cancel");
        TextView text_msg = (TextView) dialog.findViewById(R.id.text_msg);
        ImageView iv_image = (ImageView) dialog.findViewById(R.id.iv_image);
        iv_image.setImageDrawable(getResources().getDrawable(R.drawable.payment));
        TextView text = (TextView) dialog.findViewById(R.id.text);
        text_msg.setText("Are you Ready to Pay 500/-Rs ");
        text.setText("  Payment");

        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                dialog.dismiss();

                Intent i = new Intent(ServiceSoilTestingActivity.this, PaymentGateway.class);
                i.putExtra("total","500");
                i.putExtra("name","soil");
                i.putExtra("name1",name.getText().toString());
                i.putExtra("email1",email.getText().toString());
                i.putExtra("address1",address.getText().toString());
                i.putExtra("plotaddress1",plotaddress.getText().toString());
                i.putExtra("phoneno1",phoneno.getText().toString());
                i.putExtra("type1",type.getSelectedItem().toString());

                startActivity(i);
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
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}