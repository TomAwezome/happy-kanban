package com.tomawezome.happykanban;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class OverviewActivity extends AppCompatActivity {
    private static final String TASKS = "tasks";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);
    }

    public void onClickBacklog(View view)
    {
        Intent intent = new Intent(this, CategoryListActivity.class);
        intent.putExtra("choice","backlog");
        startActivity(intent);
    }
    public void onClickTodo(View view)
    {
        Intent intent = new Intent(this, CategoryListActivity.class);
        intent.putExtra("choice","todo");
        startActivity(intent);
    }
    public void onClickDoing(View view)
    {
        Intent intent = new Intent(this, CategoryListActivity.class);
        intent.putExtra("choice","doing");
        startActivity(intent);
    }
    public void onClickReview(View view)
    {
        Intent intent = new Intent(this, CategoryListActivity.class);
        intent.putExtra("choice","review");
        startActivity(intent);
    }
    public void onClickDone(View view)
    {
        Intent intent = new Intent(this, CategoryListActivity.class);
        intent.putExtra("choice","done");
        startActivity(intent);
    }

}