package com.bitbytebitcreations.studyguide.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by JeremysMac on 1/18/17.
 */

public class DB_Controller extends AppCompatActivity{


    private final String TAG = "DB_CONTROLLER";

    //DATA BASE NAME
    private static String MAIN_DB_NAME = "STUDY_DB";
    private static String TABLE_NAME= "STUDY_TABLE";
    private static int DB_VERSION = 1;
    //COLUMN NAMES
    private static final String COL_ID = "id";
    private static final String COL_DATE = "date";
    private static final String COL_ACTIVITY = "activity";
    private static final String COL_CATEGORY = "category";
    private static final String COL_NAME = "name";
    private static final String COL_CONTENT = "content"; //THIS COULD BE F.C. ANSWER, URL, DEFINITION

    private static final String[] COL_NAMES = new String[] {
            COL_ID,
            COL_DATE,
            COL_ACTIVITY,
            COL_CATEGORY,
            COL_NAME,
            COL_CONTENT
    };

    //KEYS FOR COL NAMES
    private static final int KEY_ID = 0;
    private static final int KEY_DATE = 1;
    private static final int KEY_ACTIVITY = 2;
    private static final int KEY_CATEGORY = 3;
    private static final int KEY_NAME = 4;
    private static final int KEY_CONTENT = 5;

    //CREATE TABLE STRING
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME
            +" ("+
            COL_ID + " integer primary key autoincrement, "+
            COL_DATE + " INTEGER NOT NULL, "+
            COL_ACTIVITY + " TEXT NOT NULL, "+
            COL_CATEGORY + " TEXT NOT NULL, "+
            COL_NAME + " TEXT, " +
            COL_CONTENT + " TEXT"+");";

    //SQLITE HELPERS
    private DatabaseHelper DB_Helper;
    private SQLiteDatabase DB;


    /* DB CALLS */


    //INIT CALLS TO DB
    public void DB_OPEN(Context context){
        DB_Helper = new DatabaseHelper(context);
        DB = DB_Helper.getWritableDatabase();
    }

    //CLOSE CONNECTION TO DB
    public void DB_CLOSE(){
        DB_Helper.close();
    }

    //ADD NEW ENTRY
    public long addNewEntry(Entry_Object object){
        ContentValues values = entryValues(object);
        long rowId = DB.insert(TABLE_NAME, null, values);
        Log.i(TAG, "ROW ID IN CONTROLLER: " + rowId);
        DB_Helper.close();
        return rowId;
    }

    //UPDATE EXISTING ENTRY
    public void updateEntry(long rowID, Entry_Object object){
        String where = COL_ID +"="+rowID;
        ContentValues values = entryValues(object);
        long deleted = DB.update(TABLE_NAME, values, where, null);
        Log.i(TAG, "WAS IT DELETED: " + deleted);
        DB_Helper.close();
    }

    //GET ALL DATA FOR ACTIVITY
    public ArrayList<Entry_Object> getActivityData(String _ActivityName){
//        Cursor cursor = DB.query(TABLE_NAME, null, COL_ACTIVITY + "=" + _ActivityName, null, null, null, null);
        Cursor cursor = DB.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COL_ACTIVITY +"='"+ _ActivityName+"'", null);
//        Cursor cursor = DB.query(true, TABLE_NAME, COL_NAMES, null, null, null, null, null, null);
        ArrayList<Entry_Object> entryList = new ArrayList<>();
        if (cursor != null){
            if (cursor.moveToFirst()){
                do {
                    Entry_Object object = new Entry_Object();
                    object.setRowID(cursor.getLong(KEY_ID));
                    object.setEntryDate(cursor.getLong(KEY_DATE));
                    object.setEntryActivity(cursor.getString(KEY_ACTIVITY));
                    object.setCatID(cursor.getLong(KEY_CATEGORY)); //USES CATEGORY ROW ID TO LINK
                    object.setEntryName(cursor.getString(KEY_NAME));
                    object.setEntryContent(cursor.getString(KEY_CONTENT));
                    entryList.add(object);
                }while (cursor.moveToNext());
            }
            cursor.close();
        }
        return entryList;
    }

    //DELETE
    public void deleteEntry(long rowID, String name){
        String[] who = new String[]{String.valueOf(rowID),String.valueOf(name)};
        DB.delete(TABLE_NAME,COL_ID+"=? and "+COL_NAME+"=?", who);
        DB_Helper.close();
    }


    /* CONTENT VALUE CONVERTERS */
    public ContentValues entryValues(Entry_Object object){
        ContentValues values = new ContentValues();
        values.put(COL_DATE, object.entryDate.getTime());
        values.put(COL_ACTIVITY, object.entryActivity);
        values.put(COL_CATEGORY, object.catID);
        values.put(COL_NAME, object.entryName);
        values.put(COL_CONTENT, object.entryContent);
        return values;
    }


    /* ================================ DB HELPER ================================ */
    private static class DatabaseHelper extends SQLiteOpenHelper {


        public DatabaseHelper(Context context) {
            super(context, MAIN_DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(sqLiteDatabase);
        }
    }
}
