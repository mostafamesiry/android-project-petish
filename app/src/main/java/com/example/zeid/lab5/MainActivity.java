package com.example.zeid.lab5;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.preference.Preference;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    public static com.example.zeid.lab5.DBHandler db;

    public static SharedPreferences preferenceSettings;
    public static SharedPreferences.Editor preferenceEditor;

    private static final int PREFERENCE_MODE_PRIVATE = 0;

    LocationManager locationManager;

    double longitude = 0;
    double latitude = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((TextView)this.findViewById(R.id.NameText)).requestFocus();

        preferenceSettings = getPreferences(PREFERENCE_MODE_PRIVATE);
        preferenceEditor = preferenceSettings.edit();

//        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
//
//        LocationListener locationListener = new LocationListener() {
//            @Override
//            public void onLocationChanged(Location location) {
//                longitude = location.getLongitude();
//                latitude = location.getLatitude();
//            }
//
//
//            @Override
//            public void onStatusChanged(String provider, int status, Bundle extras) {
//
//            }
//
//            @Override
//            public void onProviderEnabled(String provider) {
//
//            }
//
//            @Override
//            public void onProviderDisabled(String provider) {
//
//            }
//        };


//        String locationProvider = LocationManager.GPS_PROVIDER;
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        locationManager.requestLocationUpdates(locationProvider,1,1, locationListener);

//        boolean loggedIn = preferenceSettings.getBoolean("LoggedIn", false);
//        if (loggedIn) {
//            Log.d("login", "logedn");
//
//            String userName = preferenceSettings.getString("Username", "mohamedabozed");
            Intent intent = new Intent(this, Main2Activity.class);
//            intent.putExtra("username", userName);
            startActivity(intent);
            finish();
//        }
//Location loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//longitude = loc.getLongitude();
//        latitude = loc.getLatitude();
    }

    public void addPetButtonPressed(View view)
    {

       String name = ((TextView)this.findViewById(R.id.NameText)).getText().toString();
        String breed = ((TextView)this.findViewById(R.id.BreedText)).getText().toString();
        int age = Integer.parseInt(  ((TextView)this.findViewById(R.id.AgeText)).getText().toString()  );

//        if (name == "") {
//            return;
//        }
//        preferenceEditor.putBoolean("LoggedIn",true);
//        preferenceEditor.putString("Username",name);
//        preferenceEditor.commit();

//        boolean enableTracking = MainActivity.preferenceSettings.getBoolean("EnabledTrack",false);
//        if (enableTracking)
//        {
//            db.addBus(userName, new Date(),latitude,longitude);
//        }
//        else
         db.addPet(new Date(),name, breed,age);

        Intent intent = new Intent(this,Main2Activity.class);
//        intent.putExtra("username",userName);
        startActivity(intent);
        finish();



    }

    public void backButtonPressed(View view)
    {
        Intent intent = new Intent(this,Main2Activity.class);
        startActivity(intent);
        finish();
    }

}
