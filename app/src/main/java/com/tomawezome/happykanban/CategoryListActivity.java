package com.tomawezome.happykanban;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import java.util.List;

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
                intent2.putExtra("title", task.getTitle());
                intent2.putExtra("description", task.getDescription());
                intent2.putExtra("id", task.getId());
                intent2.putExtra("category", choice);

                startActivity(intent2);
            }
        });

        DatabaseQueryHelper query_helper = new DatabaseQueryHelper(getApplicationContext());
        List<Task> tasks = query_helper.getAllTasks();

        if (tasks.size() == 0)
        { // user hasn't put any tasks into app at all, offer them welcoming help getting started text
            Toast.makeText(this, "Welcome! Press the + to make a new task!", Toast.LENGTH_SHORT).show();
        }

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

        DatabaseQueryHelper query_helper = new DatabaseQueryHelper(getApplicationContext());
        List<Task> tasks = query_helper.getAllTasksInCategory(choice);

        // iterate result task list, if searchText set, pluck out ones that don't match
        if (!searchText.equals(""))
        {
            for (int i = 0; i < tasks.size(); i++)
            {
                Task task = tasks.get(i);
                if (!task.getDescription().toLowerCase().contains(searchText.toLowerCase()) && !task.getTitle().toLowerCase().contains(searchText.toLowerCase()))
                {
                    tasks.remove(i);
                    i--; // decrement i, since we remove(i) we want to keep i in same spot
                }
            }
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
