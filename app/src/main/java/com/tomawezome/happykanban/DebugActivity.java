package com.tomawezome.happykanban;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DebugActivity extends AppCompatActivity {

    private ArrayAdapter<Task> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug);

        ListView taskListView = findViewById(R.id.experiment_list);
        adapter = new DebugActivity.TaskAdapter(this, new ArrayList<Task>());
        taskListView.setAdapter(adapter);
    }


    class TaskAdapter extends ArrayAdapter<Task>
    {
        List<Task> tasks;

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

    public void onClickButton4(View view)
    {
        Task dummy_task = new Task("dummy title", "dummy description", "34", "doing");
        DatabaseQueryHelper query_helper = new DatabaseQueryHelper(getApplicationContext());
        query_helper.insertTask(dummy_task);
    }


    public void onResume()
    {
        super.onResume();
        DatabaseQueryHelper query_helper = new DatabaseQueryHelper(getApplicationContext());

        List<Task> tasks = query_helper.getAllTasks();

        adapter.clear();
        adapter.addAll(tasks);
    }
}