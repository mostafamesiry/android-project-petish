package com.example.zeid.lab5;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.VideoView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;


public class Main2Activity extends AppCompatActivity {


    ArrayList<String> data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        setTitle("Petish");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });


        Intent intent = getIntent();
        ListView listView = (ListView)this.findViewById(R.id.listView);
        Log.d("DB","Database initialized");
        MainActivity.db = new com.example.zeid.lab5.DBHandler(this);


        Log.d("Frontend","Fill frontend with db");
        ArrayList<Bus> buses =MainActivity.db.getAllBuses();
        data = new ArrayList<String>();
        for (int i =0;i<buses.size();i++) {
            data.add(buses.get(i).date + "," + buses.get(i).name + "," +
                    buses.get(i).breed + "," + buses.get(i).age );
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data);
        listView.setAdapter(adapter);
    }
    public void addPetButtonPressed(View view)
    {
        Log.d("Frontend","Go to form to fill pet information");
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
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
