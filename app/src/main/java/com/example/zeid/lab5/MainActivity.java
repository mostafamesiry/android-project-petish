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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    public static com.example.zeid.lab5.DBHandler db;

    public static SharedPreferences preferenceSettings;
    public static SharedPreferences.Editor preferenceEditor;

    private static final int PREFERENCE_MODE_PRIVATE = 0;
    public String gender="";
    public String type="";
    public String breed="";
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((TextView)this.findViewById(R.id.NameText)).requestFocus();

        preferenceSettings = getPreferences(PREFERENCE_MODE_PRIVATE);
        preferenceEditor = preferenceSettings.edit();
        Spinner Gender = (Spinner) this.findViewById(R.id.Gender);
        String[] genders = new String[]{"Male", "Female"};
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, genders);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Gender.setAdapter(adapter);
        Gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.v("gender", (String) parent.getItemAtPosition(position));
                gender = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                gender = "";
            }
        });
        Spinner Type = (Spinner) this.findViewById(R.id.AnimalType);
        String[] types = new String[]{"Cat", "Dog", "Fish", "Bird", "Tortoise", "Parrot", "Lizzard"};
        ArrayAdapter<String> adapterTypes = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, types);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Type.setAdapter(adapterTypes);
        Type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.v("type", (String) parent.getItemAtPosition(position));
                type = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                type = "";
            }
        });
        Spinner Breed = (Spinner) this.findViewById(R.id.AnimalBreed);
        final String[] breeds = new String[]{"German Sheppard", "Dog", "Fish", "Bird", "Tortoise", "Parrot", "Lizzard"};
        ArrayAdapter<String> adapterBreeds = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, breeds);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Breed.setAdapter(adapterBreeds);
        Breed.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.v("type", (String) parent.getItemAtPosition(position));
                breed = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                breed = "";
            }
        });


    }

    public void addPetButtonPressed(View view)
    {

       String name = ((TextView)this.findViewById(R.id.NameText)).getText().toString();
        int age = Integer.parseInt(  ((TextView)this.findViewById(R.id.AgeText)).getText().toString()  );
        db.addPet(name, new Date(), breed,  gender,  type, age);
        Intent intent = new Intent(this,Main2Activity.class);
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
