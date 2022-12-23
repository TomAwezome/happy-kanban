package com.tomawezome.happykanban;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class CategoryListActivity extends AppCompatActivity {

    private ArrayAdapter<Task> adapter;
    private static final String TASKS = "tasks";

    private String choice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);
        Intent intent = getIntent();

        choice = intent.getStringExtra("choice");
        setTitle(choice.toUpperCase());

        EditText search = findViewById(R.id.search);
        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE ||
                        (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) && (keyEvent.getAction() == KeyEvent.ACTION_DOWN))
                {
                    onResume();
                }
                return false;
            }
        });


        ListView taskListView = findViewById(R.id.taskListView);
        adapter = new TaskAdapter(this, new ArrayList<Task>());
        taskListView.setAdapter(adapter);
        taskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Task task = adapter.getItem(i);
                Intent intent2 = new Intent(adapter.getContext(), TaskEditActivity.class);
                // putExtra: title, desc, documentId (need doc Id for deletion?)
                intent2.putExtra("title", task.getTitle());
                intent2.putExtra("description", task.getDescription());
                intent2.putExtra("id", task.getId());
                intent2.putExtra("category", choice);

                startActivity(intent2);
            }
        });

        // look for a 'keys' SharedPreferences key... if no find, make and Toast about this ig
        SharedPreferences pref = getSharedPreferences("kanban", Context.MODE_PRIVATE);
        if (!pref.contains("keys"))
        {
            SharedPreferences.Editor editor = pref.edit();
            /// make an empty Set<String> and push to it
            Set<String> keys = new HashSet<String>();
            editor.putStringSet("keys", keys);
            editor.apply();
            Toast.makeText(this, "Welcome! Press the + to make a new task!", Toast.LENGTH_SHORT).show();
        }/*
        else
        {
            Set<String> keys = pref.getStringSet("keys", null);
            Toast.makeText(this, "Welcome back!", Toast.LENGTH_SHORT).show();
        }*/
    }

    class TaskAdapter extends ArrayAdapter<Task>
    {
        ArrayList<Task> tasks;
        TaskAdapter(Context context, ArrayList<Task> tasks) {
            super(context, 0, tasks);
            this.tasks = tasks;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.task_list_item, parent, false);
            }

            TextView taskTitle = convertView.findViewById(R.id.itemTitle);
            TextView taskDescription = convertView.findViewById(R.id.itemDescription);

            Task task = tasks.get(position);
            taskTitle.setText(task.getTitle());
            taskDescription.setText(task.getDescription());

            return convertView;
        }
    }

    public void onResume()
    {
        super.onResume();
        EditText search = findViewById(R.id.search);
        String searchText = search.getText().toString();

        SharedPreferences pref = getSharedPreferences("kanban", Context.MODE_PRIVATE);
        Set<String> keys = pref.getStringSet("keys", null);
        //Toast.makeText(this, "Keys found ... " + Integer.toString(keys.size()), Toast.LENGTH_SHORT).show();

        ArrayList<Task> tasks = new ArrayList<>();
        for (String key: keys)
        {
            Task task = new Task();
            task.setDescription(pref.getString(key + "_description", null));
            task.setTitle(pref.getString(key + "_title", null));
            task.setCategory(pref.getString(key + "_category", null));
            task.setId(key); // needed???

            if (choice.equals(task.getCategory()))
            {
                if (!searchText.equals(""))
                {
                    if (task.getDescription().toLowerCase().contains(searchText.toLowerCase()) || task.getTitle().toLowerCase().contains(searchText.toLowerCase()))
                        tasks.add(task);
                }
                else
                    tasks.add(task);
            }

            Log.d("onResume:", task.getTitle() + ":" + task.getDescription());
        }

        if (tasks.size() == 0)
        {
            if (!searchText.equals(""))
                Toast.makeText(CategoryListActivity.this,
                        "No search results!",
                        Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(CategoryListActivity.this,
                        "Category is empty!",
                        Toast.LENGTH_SHORT).show();
        }
        adapter.clear();
        adapter.addAll(tasks);
    }

    public void onClickSearch(View view)
    {
        onResume();
    }

    public void onClickFAB(View view) {
        Intent intent = new Intent(this, TaskEditActivity.class);
        // putExtra: ?? this is a new Task... make null vars that we know mean create anew?
        intent.putExtra("title", "");
        intent.putExtra("description", "");
        intent.putExtra("id", "");
        intent.putExtra("category", choice);

        startActivity(intent);

    }

}
