package com.example.majorproject.Fragment;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.graphics.Bitmap;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;

import com.example.majorproject.Api.ApiInterface;
import com.example.majorproject.Api.Myconfig;
import com.example.majorproject.Api.Constant;

import com.example.majorproject.FileUtils;

import com.example.majorproject.R;

import com.example.majorproject.Api.Shared_Preferences;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment
{

    public ProfileFragment()
    {

    }
    CircleImageView profileImg;
    EditText name,email,phoneno,address,password;
    Button update,btn;
    private CharSequence[] options = {"camera","Gallery","Cancel"};
    String filepath="";
    File imageFile;
    Uri uri;

    ProgressDialog dialog;

    String Date = new SimpleDateFormat("yyyymmdd", Locale.getDefault()).format(new Date());
    String Time = new SimpleDateFormat("HHmmss",Locale.getDefault()).format(new Date());

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        profileImg = root.findViewById(R.id.profile_img);
        password=root.findViewById(R.id.profile_password);
        name= root.findViewById(R.id.profile_name);
        email= root.findViewById(R.id.profile_email);
        phoneno= root.findViewById(R.id.profile_number);
        address= root.findViewById(R.id.profile_address);
        update= root.findViewById(R.id.profile_updateBtn);


        //selecting profile image
        profileImg.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Select Image");
                builder.setItems(options, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int i)
                    {
                        if(options[i].equals("camera"))
                        {
                            Intent takepic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(takepic,0);
                        }
                        else if(options[i].equals("Gallery"))
                        {
                            Intent gallery = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(gallery,1);
                        }
                        else
                        {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
            }
        });


        update.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(filepath.equals(""))
                {
                    Toasty.warning(getContext(), " please Select Profile Image", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    dialog= new ProgressDialog(getContext());
                    dialog.show();
                    dialog.setContentView(R.layout.progressbar);
                    dialog.setCancelable(false);
                    dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                    RequestBody userid1 = RequestBody.create(MediaType.parse("text/plain"),Shared_Preferences.getPrefs(getContext(), Constant.USER_ID));
                    RequestBody name1 = RequestBody.create(MediaType.parse("text/plain"),name.getText().toString());
                    RequestBody email1 = RequestBody.create(MediaType.parse("text/plain"),email.getText().toString());
                    RequestBody password1 = RequestBody.create(MediaType.parse("text/plain"),password.getText().toString());
                    RequestBody phoneNo1 = RequestBody.create(MediaType.parse("text/plain"),phoneno.getText().toString());
                    RequestBody address1 = RequestBody.create(MediaType.parse("text/plain"),address.getText().toString());

                    imageFile = new File(filepath);

                    RequestBody reqBody = RequestBody.create(MediaType.parse("multipart/form-data"),imageFile);

                    MultipartBody.Part profileImg = MultipartBody.Part.createFormData("profileImg", imageFile.getName(), reqBody);


                    ApiInterface apiInterface = Myconfig.getRetrofit().create(ApiInterface.class);
                    Call<ResponseBody> result = apiInterface.updateprofile(userid1,
                            name1,email1,password1,phoneNo1,profileImg,address1);

                    result.enqueue(new Callback<ResponseBody>()
                    {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
                        {
                            dialog.dismiss();
                            try {
                                String output = response.body().string();
                                Log.e("Response", "GetResponse:-" + output);
                                JSONObject jsonObject = new JSONObject(output);
                                if (jsonObject.getString("ResponseCode").equals("1"))
                                {
                                    Toast toast = Toasty.success(getActivity(), "Profile Updated Successfully ", Toast.LENGTH_SHORT, true);
                                    toast.setGravity(Gravity.TOP,0,300);
                                    toast.show();
                                }

                            }
                            catch (IOException e)
                            {
                                e.printStackTrace();
                            }
                            catch (JSONException e)
                            {
                                e.printStackTrace();
                            }
                            dialog.dismiss();
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t)
                        {
                            Toast toast = Toasty.error(getActivity(), "Failed To Update", Toast.LENGTH_SHORT, true);
                            toast.setGravity(Gravity.TOP,0,300);
                            toast.show();
                        }
                    });
                }

            }
        });



        getprofile(); //Fetch data from database

        return root;
    }


    private void getprofile()
    {
        dialog= new ProgressDialog(getContext());
        dialog.show();
        dialog.setContentView(R.layout.progressbar);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        ApiInterface apiInterface = Myconfig.getRetrofit().create(ApiInterface.class);
        Call<ResponseBody> result = apiInterface.getProfile(Shared_Preferences.getPrefs(getContext(), Constant.USER_ID));
        result.enqueue(new Callback<ResponseBody>()
        {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
            {
                try {
                    String output = response.body().string();
                    Log.e("Response", "GetResponse:-" + output);
                    JSONObject jsonObject = new JSONObject(output);
                    if (jsonObject.getString("ResponseCode").equals("1"))
                    {
                        dialog.dismiss();
                        Log.e("jdjd","" +jsonObject.getString("ResponseCode").equals("1"));
                        JSONObject jsonObject1 = jsonObject.getJSONObject("Data");

                        name.setText(jsonObject1.getString("name"));
                        Shared_Preferences.setPrefs(getContext(), Constant.USER_Name,jsonObject1.getString("name"));
                        email.setText(jsonObject1.getString("email"));
                        Shared_Preferences.setPrefs(getContext(), Constant.USER_email,jsonObject1.getString("email"));
                        password.setText(jsonObject1.getString("password"));
                        phoneno.setText(jsonObject1.getString("phoneNo"));
                        Shared_Preferences.setPrefs(getContext(), Constant.USER_phNo,jsonObject1.getString("phoneNo"));
                        address.setText(jsonObject1.getString("address"));
                        String profileurl = jsonObject1.getString("profileImg");
                        Shared_Preferences.setPrefs(getContext(), Constant.USER_ProfileImg,profileurl);
                        Picasso.get().load(Constant.ProfileLink+profileurl)
                                .placeholder(R.drawable.no_image_available)
                                .into(profileImg);

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
                Toast.makeText(getContext(), "Please Check Your Network", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode != RESULT_CANCELED)
        {
            switch (requestCode)
            {
                case 0:
                    if(resultCode == RESULT_OK && data !=null)
                    {
                        Bitmap image =(Bitmap) data.getExtras().get("data");
                        filepath = FileUtils.getPath(getContext(),getImageUri(getContext(),image));

                        profileImg.setImageBitmap(image);

                    }
                    break;

                case 1:
                {
                    if(resultCode == RESULT_OK && data !=null)
                    {
                        uri = data.getData();
                        Bitmap bitmap = null;
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),uri);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        filepath= FileUtils.getPath(getContext(),getImageUri(getContext(),bitmap));

                        Picasso.get().load(uri).into(profileImg);

                    }
                }
            }

        }
    }


    public Uri getImageUri(Context context, Bitmap bitmap)
    {
        String profile = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap,"ProfileImg"+Time+Date,"");

        return Uri.parse(profile);
    }

    public void requiredPermission()
    {
        ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
    }


}