package com.example.zeid.lab5;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;

public class Main3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("Frontend","Initializing Settings");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

            ((RadioButton) findViewById(R.id.TrackRadio1)).setChecked(true);

    }

    public void onRadioButtonClicked(View view)
    {
        Log.d("Frontend","Update Settings Preference");

        boolean checked = ((RadioButton)view).isChecked();

        if (checked) {
            if (view.getId() == R.id.TrackRadio1) {
//                MainActivity.preferenceEditor.putBoolean("EnabledTrack",true);
//                MainActivity.preferenceEditor.commit();

            } else {
//                MainActivity.preferenceEditor.putBoolean("EnabledTrack",false);
//                MainActivity.preferenceEditor.commit();
            }
        }
    }


}
