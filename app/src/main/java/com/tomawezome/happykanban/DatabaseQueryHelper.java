package com.tomawezome.happykanban;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DatabaseQueryHelper
{
    private Context context;

    public DatabaseQueryHelper(Context context){
        this.context = context;
    }

    public long insertTask(Task task)
    {
        long id = -1;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase db = databaseHelper.getReadableDatabase();


        ContentValues vals = new ContentValues();

        vals.put("title", task.getTitle());
        vals.put("description", task.getDescription());
        vals.put("category", task.getCategory());

        try
        {
            id = db.insertOrThrow("tasks", null, vals);
        }
        catch (SQLiteException e)
        {
            Log.d("Exception: ", e.getMessage());
            Toast.makeText(context, "Operation failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        finally
        {
            db.close();
        }

        return id;
    }

    public long updateTask(Task task)
    {
        long rows = -1;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase db = databaseHelper.getReadableDatabase();


        ContentValues vals = new ContentValues();

        vals.put("title", task.getTitle());
        vals.put("description", task.getDescription());
        vals.put("category", task.getCategory());

        try
        {
            rows = db.update("tasks", vals, "id = " + task.getId(), null);
        }
        catch (SQLiteException e)
        {
            Log.d("Exception: ", e.getMessage());
            Toast.makeText(context, "Operation failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        finally
        {
            db.close();
        }

        return rows;
    }

    public long deleteTask(Task task)
    {
        long rows = -1;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase db = databaseHelper.getReadableDatabase();


        try
        {
            rows = db.delete("tasks", "id = " + task.getId(), null);
        }
        catch (SQLiteException e)
        {
            Log.d("Exception: ", e.getMessage());
            Toast.makeText(context, "Operation failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        finally
        {
            db.close();
        }

        return rows;
    }

    public List<Task> getAllTasksInCategory(String category_in)
    {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        Cursor cursor = null;
        try
        {
            //cursor = db.query("tasks", null, null, null, null, null, null, null);
            String SELECT_QUERY = String.format("SELECT * FROM tasks WHERE category LIKE '%s'", category_in);
            cursor = db.rawQuery(SELECT_QUERY, null);

            if (cursor != null)
            {
                if (cursor.moveToFirst())
                {
                    List<Task> task_list = new ArrayList<>();
                    do
                    {
                        int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                        String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
                        String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
                        String category = cursor.getString(cursor.getColumnIndexOrThrow("category"));
                        task_list.add(new Task(title, description, Integer.toString(id), category)); // todo make id in Task int ?

                    } while (cursor.moveToNext());

                    return task_list;
                }
            }
        }
        catch (Exception e)
        {
            Log.d("Exception:", e.getMessage());
            Toast.makeText(context, "Operation failed", Toast.LENGTH_SHORT).show();
        }
        finally
        {
            if (cursor != null)
                cursor.close();
            db.close();
        }

        return Collections.emptyList();
    }

    public List<Task> getAllTasks()
    {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        Cursor cursor = null;
        try
        {
            //cursor = db.query("tasks", null, null, null, null, null, null, null);
            String SELECT_QUERY = String.format("SELECT * from tasks");
            cursor = db.rawQuery(SELECT_QUERY, null);

            if (cursor != null)
            {
                if (cursor.moveToFirst())
                {
                    List<Task> task_list = new ArrayList<>();
                    do
                    {
                        int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                        String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
                        String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
                        String category = cursor.getString(cursor.getColumnIndexOrThrow("category"));
                        task_list.add(new Task(title, description, Integer.toString(id), category)); // todo make id in Task int ?

                    } while (cursor.moveToNext());

                    return task_list;
                }
            }
        }
        catch (Exception e)
        {
            Log.d("Exception:", e.getMessage());
            Toast.makeText(context, "Operation failed", Toast.LENGTH_SHORT).show();
        }
        finally
        {
            if (cursor != null)
                cursor.close();
            db.close();
        }

        return Collections.emptyList();
    }

}
