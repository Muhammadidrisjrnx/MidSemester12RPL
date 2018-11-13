package com.example.rplrus021.midsemester12rpl;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class database_helper {

    database_sql2 myhelper;

    public database_helper(Context context) {
        myhelper = new database_sql2(context);
    }

    public long insertData(String title, String overview, String poster_path) {
        SQLiteDatabase dbb = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(database_sql2.title, title);
        contentValues.put(database_sql2.overview, overview);
        contentValues.put(database_sql2.poster_path, poster_path);
        long id = dbb.insert(database_sql2.TABLE_NAME, null, contentValues);
        return id;
    }

    public ArrayList<data> getData() {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from "+database_sql2.TABLE_NAME,null);
        cursor.moveToFirst();
        ArrayList<data> arrayList = new ArrayList<>();
        data dataModel;
        if (cursor.getCount()>0) {
            do {
                dataModel = new data();
                dataModel.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(database_sql2.title)));
                dataModel.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(database_sql2.overview)));
                dataModel.setPosterPath(cursor.getString(cursor.getColumnIndexOrThrow(database_sql2.poster_path)));
                arrayList.add(dataModel);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public int delete(String uname) {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] whereArgs = {uname};

        int count = db.delete(database_sql2.TABLE_NAME, database_sql2.title + " = ?", whereArgs);
        return count;
    }

    public int updateName(String oldName, String newName) {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(database_sql2.title, newName);
        String[] whereArgs = {oldName};
        int count = db.update(database_sql2.TABLE_NAME, contentValues, database_sql2.title + " = ?", whereArgs);
        return count;
    }

    static class database_sql2 extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "my_movie";    // Database Name
        private static final String TABLE_NAME = "my_table_movie";   // Table Name
        private static final int DATABASE_Version = 1;    // Database Version
        //private static final String UID = "_id";     // Column I (Primary Key)
        private static final String title = "title";    //Column II
        private static final String overview = "overview";    // Column III
        private static final String poster_path = "poster_path";    // Column III
//        private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +
//                " (" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + title + "  VARCHAR(255) ," + overview + " VARCHAR(225)," + poster_path + " VARCHAR(225))";
private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +
        " (" + title + "  VARCHAR(255) PRIMARY KEY ," + overview + " VARCHAR(225)," + poster_path + " VARCHAR(225))";
        private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
        private Context context;

        public database_sql2(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_Version);
            this.context = context;
        }

        public void onCreate(SQLiteDatabase db) {

            try {
                db.execSQL(CREATE_TABLE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
//                Message.message(context,"OnUpgrade");
                db.execSQL(DROP_TABLE);
                onCreate(db);
            } catch (Exception e) {
//                Message.message(context,""+e);
            }
        }
    }

}
