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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
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
                threadStart(breed);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                breed = "";
            }
        });



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
        int age = Integer.parseInt(  ((TextView)this.findViewById(R.id.AgeText)).getText().toString()  );
        db.addPet(name, new Date(), breed,  gender,  type, age);


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
    public static void threadStart(String breedName) {

        MyThread thread = new MyThread(breedName);
        new Thread(thread).start();
    }

}
class MyThread implements Runnable {
    String breed ;
    String jsonString = "";
    public MyThread(String breed) {
       this.breed = breed;
    }

    public void run() {
        try {
            Log.d("ss", "tt");
            URL url = new URL("https://www.googleapis.com/customsearch/v1?key=AIzaSyCNycA4uQW47LBZcqr6AQvjUNF8q3OOuyU&cx=002441175508441743644:qmd_a7ahiok&q="+ URLEncoder.encode(breed, "UTF-8")+"&searchType=image&fileType=jpg&imgSize=medium&alt=json&num=1&start=1");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                String inputlines = "";
                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                while ((inputlines = in.readLine()) != null) {
                    jsonString += inputlines;
                }
                JSONObject object = new JSONObject(jsonString);
                String items = object.get("items") + "";
                items = items.replace("[", "");
                items = items.replace("]", "");
                JSONObject results = new JSONObject(items);
                String link = results.get("link") + "";
                Log.d("resul", link);
            } finally {
                urlConnection.disconnect();
            }
        } catch (Exception e) {
            Log.d("Error", "Error");
            e.printStackTrace();
        }
    }
}
