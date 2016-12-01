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
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    public static com.example.zeid.lab5.DBHandler db;

    public static SharedPreferences preferenceSettings;
    public static SharedPreferences.Editor preferenceEditor;

    private static final int PREFERENCE_MODE_PRIVATE = 0;

    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((TextView)this.findViewById(R.id.NameText)).requestFocus();

        preferenceSettings = getPreferences(PREFERENCE_MODE_PRIVATE);
        preferenceEditor = preferenceSettings.edit();



    }
    public void onUserInteraction(){
        Log.d("Feedback","Screentouched");
        Main2Activity.ner.resetCounter();
    }

    @Override
    public void onPause()
    {
        super.onPause();
        Main2Activity.ner.appClosed();
        Log.d("App closed","App closed");
    }

    @Override
    public void onResume(){
        super.onResume();
        Main2Activity.ner.appOpened();
        Log.d("App opened","App opened");
    }


    public void addPetButtonPressed(View view)
    {
        Log.d("DB","Add Pet to DB");
        Log.d("Frontend","Go back to main screen");

       String name = ((TextView)this.findViewById(R.id.NameText)).getText().toString();
        String breed = ((TextView)this.findViewById(R.id.BreedText)).getText().toString();
        int age = Integer.parseInt(  ((TextView)this.findViewById(R.id.AgeText)).getText().toString() );
         db.addPet(new Date(),name, breed,age);
        Intent intent = new Intent(this,Main2Activity.class);
        startActivity(intent);
        finish();
    }

    public void backButtonPressed(View view)
    {
        Log.d("Frontend","Go back to main screen");
        Intent intent = new Intent(this,Main2Activity.class);
        startActivity(intent);
        finish();
    }

}
