package com.tomawezome.happykanban;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void onClickStart(View view)
    {
        Intent intent = new Intent(this, OverviewActivity.class);

        DatabaseQueryHelper query_helper = new DatabaseQueryHelper(getApplicationContext());
        List<Task> tasks = query_helper.getAllTasks();

        if (tasks.size() > 1)
        {
            Toast.makeText(this, "Welcome back!", Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Tasks found: " + Integer.toString(tasks.size()), Toast.LENGTH_SHORT).show();
        }
        startActivity(intent);
    }

    public void onClickDebug(View view)
    {
        Intent intent = new Intent(this, DebugActivity.class);
        startActivity(intent);
    }


}
