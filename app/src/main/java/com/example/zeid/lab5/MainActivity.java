package com.example.zeid.lab5;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    public static com.example.zeid.lab5.DBHandler db;

    public static SharedPreferences preferenceSettings;
    public static SharedPreferences.Editor preferenceEditor;

    private static final int PREFERENCE_MODE_PRIVATE = 0;
    public String gender="";
    public String type="";
    public String breed="";
    public static String[] breeds = new String[0];
    public static boolean linkFound = false;
    public static String link = "";
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        readJSON();
        super.onCreate(savedInstanceState);
        getWindow().getAttributes().windowAnimations = R.style.Fade;
        setContentView(R.layout.activity_main);

        ((TextView)this.findViewById(R.id.ownerNumber)).requestFocus();

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
        Spinner Breed = (Spinner) this.findViewById(R.id.AnimalBreed);
//        final String[] breeds = new String[]{"German Sheppaasdasrd", "Dog", "Fish", "Bird", "Tortoise", "Parrot", "Lizzard"};
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

       String name = ((TextView)this.findViewById(R.id.petName)).getText().toString();
       String ownerName = ((TextView)this.findViewById(R.id.ownerName)).getText().toString();
       String  ownerNumber = ((TextView)this.findViewById(R.id.ownerNumber)).getText().toString();
        int age = Integer.parseInt(  ((TextView)this.findViewById(R.id.AgeText)).getText().toString()  );
        int price = Integer.parseInt(  ((TextView)this.findViewById(R.id.PriceText)).getText().toString()  );
        switch(breed)
        {
            case"Labrador": link = ""+R.drawable.labrador; break;
            case  "Chihuahua": link = ""+R.drawable.chihuahua; break;
            case "Golden Retriever": link = ""+R.drawable.golden; break;
            case  "Rottweiler": link = ""+R.drawable.rott; break;
            case   "Siberian Husky": link = ""+R.drawable.husky; break;
            case   "German Shepherd": link = ""+R.drawable.german; break;
            case      "Pug": link = ""+R.drawable.pugg; break;
            case      "Dalmatian": link = ""+R.drawable.dalmation; break;
            case     "Saint Bernard": link = ""+R.drawable.saint; break;
            case     "Poodle": link = ""+R.drawable.poodle; break;
        }
        db.addPet(name, new Date(), breed,  gender,  type, age, link,price, ownerName, ownerNumber);


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

    public static void readJSON() {
        Log.d("API", "Reading JSON from local server");
        Thread thread = new Thread(new Runnable() {
            String jsonString = "";
            @Override
            public void run() {
                try {

                    URL url = new URL("http://10.0.2.2:8000/breeds.json");
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    try {
                        String inputlines = "";
                        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                        while ((inputlines = in.readLine()) != null) {
                            jsonString += inputlines;
                        }
                        JSONObject object = new JSONObject(jsonString);
                        breeds = object.get("breeds").toString().split(",");
                        for(int i = 0;i<breeds.length;i++)
                        {
                            breeds[i]=breeds[i].replace('"','~').replace("~","").replace("[","").replace("]","");
                        }
                        Log.d("JSON RESULT", object.toString());
                        Log.d("Connection ID", in.read() + "");
                    } finally {
                        urlConnection.disconnect();
                    }
                } catch (Exception e) {
                    Log.d("Error", "Couldn't access local host");
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        try {
            thread.join();
        }
        catch(Exception e)
        {
            Log.d("Error", "Couldn't wait for thread to execute");
        }
    }
}

