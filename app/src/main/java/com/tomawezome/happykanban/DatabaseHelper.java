package com.tomawezome.happykanban;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper
{
    private static DatabaseHelper database_helper;

    // All Static variables
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "kanban_db";

    // Constructor
    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //Logger.addLogAdapter(new AndroidLogAdapter());
    }

    public static synchronized DatabaseHelper getInstance(Context context){
        if (database_helper == null)
        {
            database_helper = new DatabaseHelper(context);
        }
        return database_helper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // create tables

        String create_tasks_table_string = "CREATE TABLE tasks (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title TEXT NOT NULL," +
                "description TEXT," +
                "category TEXT NOT NULL" +
        ")";

        Log.d("log", create_tasks_table_string);

        db.execSQL(create_tasks_table_string);

        Log.d("log","database created");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS tasks");

        // Create tables again
        onCreate(db);
    }
}
