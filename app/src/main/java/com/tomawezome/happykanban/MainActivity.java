package com.tomawezome.happykanban;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Set;

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
        SharedPreferences pref = getSharedPreferences("kanban", Context.MODE_PRIVATE);
        if (pref.contains("keys")) {
            Toast.makeText(this, "Welcome back!", Toast.LENGTH_SHORT).show();
            Set<String> keys = pref.getStringSet("keys", null);
            Toast.makeText(this, "Tasks found: " + Integer.toString(keys.size()), Toast.LENGTH_SHORT).show();

        }
        startActivity(intent);
    }

    public void onClickDebug(View view)
    {
        Intent intent = new Intent(this, DebugActivity.class);
        startActivity(intent);
    }


}
