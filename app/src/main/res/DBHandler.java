/**
 * Created by zeid on 29/10/16.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;

public class DBHandler extends  SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 1;

    private  static final String DATABASE_NAME = "UsersBus";

    private  static final String TABLE_BUS = "Users";

    private static final String ACTION_TIMESTAMP = "timeStamp";

    private  static  final  String USER_NAME = "username";

    private static final String LATITUDE = "latitude";

    private  static final  String LONGITUDE = "longitude";

    public DBHandler(Context context){
        super(context, DATABASE_NAME, null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_TABLE = "CREATE TABLE" + TABLE_BUS + "(" + ACTION_TIMESTAMP + "DATE," + USER_NAME + "TEXT," + LATITUDE + "DOUBLE," + LONGITUDE +"DOUBLE," + ")";
        db.execSQL(CREATE_TABLE);
    }

    public void addBus(String username, Date actiontimestamo, double lat, double longitude){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_NAME,username);
        values.put(ACTION_TIMESTAMP,actiontimestamo);
        values.put(LATITUDE,lat);
        values.put(LONGITUDE,longitude);

        db.insert(TABLE_BUS,null,values);
        db.close();
    }

}
