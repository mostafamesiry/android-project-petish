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

    private  static final String DATABASE_NAME = "USERPETS";

    private  static final String TABLE_PETS = "Pets";

    private static final String ACTION_TIMESTAMP = "timeStamp";

    private  static  final  String NAME = "name";

    private  static  final  String BREED = "breed";

    private  static  final  String AGE = "age";

    private static final String TYPE = "type";

    private  static final  String GENDER = "gender";

    public DBHandler(Context context){
        super(context, DATABASE_NAME, null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        Log.d("Created","Created");
        String CREATE_TABLE = "CREATE TABLE " + TABLE_PETS + "(" + ACTION_TIMESTAMP + " TEXT," + NAME + " TEXT," +  TYPE  + " TEXT," + BREED + " TEXT," + AGE +" INTEGER," + GENDER + " TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    public void addPet(String name, Date date, String breed, String gender, String type,int age ){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME,name);
        values.put(ACTION_TIMESTAMP,date.toString());
        values.put(BREED,breed);
        values.put(TYPE,type);
        values.put(GENDER,gender);
        values.put(AGE,age);

        db.insert(TABLE_PETS,null,values);
        db.close();
    }

    public ArrayList<Pet> getAllPets()
    {


        ArrayList<Pet> pets = new ArrayList<Pet>();
        String selQuery = "SELECT * FROM " + TABLE_PETS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selQuery,null);

        if (cursor.moveToFirst())
        {
            do {

                Pet pet = new Pet(cursor.getString(0), cursor.getString(1),cursor.getString(2),
                                  cursor.getString(3), Integer.parseInt( cursor.getString(4)),
                                  cursor.getString(5));
                pets.add(pet);
            }
            while (cursor.moveToNext());
        }
        return pets;
    }

    @Override
    public  void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PETS);
        Log.d("delete","deleted");
        onCreate(db);

    }

}
