package com.example.zeid.lab5;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;


public class Main2Activity extends AppCompatActivity {


    ArrayList<String> data;
    public static NotificationEventReceiver ner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        setTitle("Petish");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ner.setupAlarm(getApplicationContext(),this);


        Intent intent = getIntent();
        ListView listView = (ListView)this.findViewById(R.id.listView);
        Log.d("DB","Database initialized");
        MainActivity.db = new com.example.zeid.lab5.DBHandler(this);
        ArrayList<Pet> pets =MainActivity.db.getAllPets();
        data = new ArrayList();
        for (int i =0;i<pets.size();i++) {
            data.add(pets.get(i).date + "," + pets.get(i).name + "," +
                    pets.get(i).breed + "," + pets.get(i).age );
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data);
        listView.setAdapter(adapter);
    }

    @Override
    public void onPause()
    {
        super.onPause();
        ner.appClosed();
        Log.d("App closed","App closed");
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
