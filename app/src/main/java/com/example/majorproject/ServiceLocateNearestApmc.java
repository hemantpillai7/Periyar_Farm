package com.example.majorproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

public class ServiceLocateNearestApmc extends AppCompatActivity implements OnMapReadyCallback {
    GoogleMap map;
    FusedLocationProviderClient client;
    SupportMapFragment supportMapFragment;

    ArrayList<LatLng> arrayList = new ArrayList<LatLng>();
    LatLng Amc1 = new LatLng(19.95714255861058, 73.75949304820914);
    LatLng Amc2 = new LatLng(19.928964359289598, 73.74097549650749);
    LatLng Amc3 = new LatLng(19.968106205123345, 73.71720190475443);
    LatLng Amc4 = new LatLng(20.0033825472358, 73.73005423468189);
    LatLng Amc5 = new LatLng(20.051276887385743, 73.75312834275749);
    LatLng Amc6 = new LatLng(19.997965135958022, 73.80091756928078);
    LatLng Amc7 = new LatLng(19.963055370881225, 73.85657968742906);
    LatLng Amc8 = new LatLng(19.942894083297595, 73.83470189009078);
    LatLng Amc9 = new LatLng(19.97274095547772, 73.81451912554016);
    LatLng Amc10 = new LatLng(19.989259638103373, 73.78107942177279);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_locate_nearest_amc);


        supportMapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this);
        client = LocationServices.getFusedLocationProviderClient(this);

        arrayList.add(Amc1);
        arrayList.add(Amc2);
        arrayList.add(Amc3);
        arrayList.add(Amc4);
        arrayList.add(Amc5);
        arrayList.add(Amc6);
        arrayList.add(Amc7);
        arrayList.add(Amc8);
        arrayList.add(Amc9);
        arrayList.add(Amc10);

        getCurrentLocation();
        //permission
        if (ActivityCompat.checkSelfPermission(ServiceLocateNearestApmc.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            getCurrentLocation();
        }
        else
        {
            //when permission denied
            ActivityCompat.requestPermissions(ServiceLocateNearestApmc.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }
    }

    private void getCurrentLocation()
    {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(@NonNull Location location) {
                if(location != null)
                {
                    supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(@NonNull GoogleMap googleMap)
                        {
                            //Initialize Lat lng
                            LatLng latLng = new LatLng(location.getLatitude()
                                    ,location.getLongitude());

                            //Create marker option
                            MarkerOptions options = new MarkerOptions().position(latLng)
                                    .title("I am there");

                            //zoom map
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));

                            //add marker on map
                            googleMap.addMarker(options).setIcon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.humanpointer));

                        }
                    });
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 44)
        {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            }
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        map=googleMap;

        for(int i=0; i<arrayList.size(); i++)
        {
            map.addMarker(new MarkerOptions().position(arrayList.get(i)).title("APMC Market")
                    .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.amcpointer)));
            map.moveCamera(CameraUpdateFactory.newLatLng(arrayList.get(i)));
        }
        LatLng Maharashtra = new LatLng(20.000063162733635, 73.79085526510886);
        map.addMarker(new MarkerOptions().position(Maharashtra).title("Our OutLet")
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.outlet)));
        map.moveCamera(CameraUpdateFactory.newLatLng(Maharashtra));



    }
    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId)
    {
        Drawable vectorDrawable= ContextCompat.getDrawable(context,vectorResId);
        vectorDrawable.setBounds(0,0,vectorDrawable.getIntrinsicWidth(),
                vectorDrawable.getIntrinsicHeight());

        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                vectorDrawable.getIntrinsicHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
}