/**
 * Created by zeid on 29/10/16.
 */
package com.example.zeid.lab5;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DBHandler extends  SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 1;

    private  static final String DATABASE_NAME = "UsersBus";

    private  static final String TABLE_BUS = "Pets";

    private static final String ACTION_TIMESTAMP = "timeStamp";

    private  static  final  String NAME = "username";

    private  static  final  String BREED = "breed";

    private  static  final  String AGE = "age";

    private static final String LATITUDE = "latitude";

    private  static final  String LONGITUDE = "longitude";

    public DBHandler(Context context){
        super(context, DATABASE_NAME, null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_TABLE = "CREATE TABLE " + TABLE_BUS + "(" + ACTION_TIMESTAMP + " TEXT," + NAME + " TEXT," + BREED + " TEXT," + AGE +" INTEGER" + ")";
        db.execSQL(CREATE_TABLE);
    }

    public void addBus(String username, Date actiontimestamp, double lat, double longitude){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME,username);
        values.put(ACTION_TIMESTAMP,actiontimestamp.toString());
        values.put(LATITUDE,lat);
        values.put(LONGITUDE,longitude);

        db.insert(TABLE_BUS,null,values);
        db.close();
    }

    public void addPet(Date actiontimestamp, String name, String breed, int age){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ACTION_TIMESTAMP,actiontimestamp.toString());
        values.put(NAME,name);
        values.put(BREED,breed);
        values.put(AGE,age);

        db.insert(TABLE_BUS,null,values);
        db.close();
    }

    public ArrayList<Bus> getAllBuses()
    {
        ArrayList<Bus> buses = new ArrayList<Bus>();
        String selQuery = "SELECT * FROM " + TABLE_BUS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selQuery,null);

        if (cursor.moveToFirst())
        {
            do {

                Bus bus = new Bus();
                bus.date = cursor.getString(0);
                bus.name = cursor.getString(1);
                bus.breed =  cursor.getString(2);
                bus.age = Integer.parseInt( cursor.getString(3));

                buses.add(bus);
            }
            while (cursor.moveToNext());
        }
        return buses;
    }

    @Override
    public  void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BUS);
        onCreate(db);
    }

}
