package com.example.zeid.lab5;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import static java.security.AccessController.getContext;


public class Main2Activity extends AppCompatActivity {


    ArrayList<String> data;
    public static NotificationEventReceiver ner;
    public static BatteryLevelReceiver blr;
    public static Bitmap bm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTitle("Petish");
        blr = new BatteryLevelReceiver();
        registerReceiver(blr, new IntentFilter(Intent.ACTION_BATTERY_LOW));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        getWindow().getAttributes().windowAnimations = R.style.Fade;
        ner.setupAlarm(getApplicationContext(),this);


        Intent intent = getIntent();
        Log.d("DB","Database initialized");
        MainActivity.db = new com.example.zeid.lab5.DBHandler(this);
       final ArrayList<Pet> pets =MainActivity.db.getAllPets();
        data = new ArrayList();
        String[] names = new String[pets.size()];
        String[] breed = new String[pets.size()];
        String[] ages = new String[pets.size()];
        String[] images = new String[pets.size()];
        for (int i =0;i<pets.size();i++) {
            names[i]=pets.get(i).name;
            breed[i]=pets.get(i).breed+"   "+pets.get(i).gender;
            ages[i]=pets.get(i).age+"";
            images[i] = pets.get(i).link;
        }
            // Array of integers points to images stored in /res/drawable-ldpi/
        // Array of strings to store currencies
        List<HashMap<String,Object>> aList = new ArrayList<HashMap<String,Object>>();

        for(int i=0;i<pets.size();i++){
            HashMap<String, Object> hm = new HashMap<String,Object>();
            hm.put("txt", names[i]);
            hm.put("breed", breed[i]);
            hm.put("images", images[i] );
            hm.put("age",ages[i]);
            aList.add(hm);
        }

        // Keys used in Hashmap
        String[] from = {"images","txt","breed" ,"age"};

        // Ids of views in listview_layout
        int[] to = { R.id.images,R.id.txt,R.id.breed,R.id.age};

        // Instantiating an adapter to store each items
        // R.layout.listview_layout defines the layout of each item
        SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), aList, R.layout.listview_layout, from, to);

        // Getting a reference to listview of main.xml layout file
        ListView listView = ( ListView ) findViewById(R.id.listView);

        // Setting the adapter to the listView
        listView.setAdapter(adapter);

    }
    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }

    @Override
    public void onPause()
    {
        super.onPause();
        ner.appClosed();
        Log.d("App closed","App closed");
        unregisterReceiver(blr);
    }

    @Override
    public void onResume(){
        super.onResume();
        ner.appOpened();
        Log.d("App opened","App opened");
    }


    @Override
    public void onUserInteraction(){
        Log.d("Feedback","Screentouched");
        ner.resetCounter();
    }
    public void addPetButtonPressed(View view)
    {
        Log.d("Frontend","Go to form to fill pet information");
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    public static Drawable drawableFromUrl(String url) throws IOException {
        Bitmap x;

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.connect();
        InputStream input = connection.getInputStream();

        x = BitmapFactory.decodeStream(input);
        return new BitmapDrawable(x);



    }


public void saveCSV(View view)
{
    File exportDir = new File(Environment.getExternalStorageDirectory(),"");
    if (!exportDir.exists())
    {
        exportDir.mkdirs();
    }
    File file = new File(exportDir,"databaseUsers.csv");
    try
    {
        file.createNewFile();
        OutputStream st = new FileOutputStream(file.getPath());
        OutputStreamWriter wr = new OutputStreamWriter(st);
        for (int i =0;i<data.size();i++) {
            wr.write(data.get(i) + "\n");
        }
        wr.flush();
        wr.close();
    }
    catch (Exception sqlEx)
    {
Log.d("Error",sqlEx.toString());
    }
}
    public void clearDataBase(View view){
        Log.d("DB","Database cleared");
        MainActivity.db.onUpgrade(MainActivity.db.getWritableDatabase(),0,0);
        ListView listView = (ListView)this.findViewById(R.id.listView);
        listView.setAdapter(null);
    }
    public  void settingsClickedButton(View view)
    {
        Log.d("Frontend","Go to Settings");
        Intent intent = new Intent(this,Main3Activity.class);
        startActivity(intent);
    }

}

