package com.tomawezome.happykanban;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class TaskEditActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private String title;
    private String description;
    private String id;
    private String category;

    private static final String TASKS = "tasks";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_edit);

        title = getIntent().getStringExtra("title");
        description = getIntent().getStringExtra("description");
        id = getIntent().getStringExtra("id");
        category = getIntent().getStringExtra("category");

        TextView t = findViewById(R.id.titleEditText);
        TextView d = findViewById(R.id.descriptionEditText);
        Button de = findViewById(R.id.deleteButton);
        Button s = findViewById(R.id.submitButton);

        Spinner spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(null);

        if (category.equals("backlog"))
            spinner.setSelection(0);
        else if (category.equals("todo"))
            spinner.setSelection(1);
        else if (category.equals("doing"))
            spinner.setSelection(2);
        else if (category.equals("review"))
            spinner.setSelection(3);
        else if (category.equals("done"))
            spinner.setSelection(4);

        if (!title.equals(""))
            t.setText(title);
        if (!description.equals(""))
            d.setText(description);

        if (id.equals("")) { // if this is new entry and not existing
            de.setEnabled(false);
            de.setAlpha(0);
            s.setText("ADD TASK");
        }
        else
        {
            s.setText("UPDATE TASK");
        }
    }

    public void onSubmitClick(View view)
    {
        TextView t = findViewById(R.id.titleEditText);
        TextView d = findViewById(R.id.descriptionEditText);
        title = t.getText().toString();
        description = d.getText().toString();
        id = getIntent().getStringExtra("id");

        if (title.equals(""))
        { // empty desc is fine, but you at least need a task title !
            Toast.makeText(TaskEditActivity.this,
                    "Title must be non-empty!",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        Task ta = new Task(title, description, id, category);
        if (id.equals(""))
        { // new entry, not existing
            String rand_id;
            Random random = new Random();
            SharedPreferences pref = getSharedPreferences("kanban", Context.MODE_PRIVATE);
            Set<String> keys = pref.getStringSet("keys", null);
            Set<String> out_keys = new HashSet<String>();
            do {
                rand_id = Integer.toString(random.nextInt(65535));
            } while (keys.contains(rand_id)); // make sure we don't clobber existing
            id = rand_id;
            ta.setId(id);
            for (String key: keys)
                out_keys.add(key);
            out_keys.add(id);
            SharedPreferences.Editor editor = pref.edit();
            editor.putStringSet("keys", out_keys);

            editor.putString(id + "_description", description);
            editor.putString(id + "_title", title);
            editor.putString(id + "_category", category);
            // id??... seems redundant to do id_id...

            editor.apply();
        }
        else
        { // existing entry, update it
            SharedPreferences pref = getSharedPreferences("kanban", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString(id + "_description", description);
            editor.putString(id + "_title", title);
            editor.putString(id + "_category", category);
            editor.apply();
        }

        finish();
    }

    public void onDeleteClick(View view)
    {
        // firebase things, but with a spicy delete twist ?
        TextView t = findViewById(R.id.titleEditText);
        TextView d = findViewById(R.id.descriptionEditText);
        title = t.getText().toString();
        description = d.getText().toString();
        id = getIntent().getStringExtra("id");

        SharedPreferences pref = getSharedPreferences("kanban", Context.MODE_PRIVATE);
        Set<String> keys = pref.getStringSet("keys", null);
        Set<String> out_keys = new HashSet<String>();
        for (String key: keys)
            out_keys.add(key);
        out_keys.remove(id);
        SharedPreferences.Editor editor = pref.edit();
        editor.putStringSet("keys", out_keys);
        editor.remove(id + "_description");
        editor.remove(id + "_title");
        editor.remove(id + "_category");


        editor.apply();
        finish();
    }

    @Override
    public void onUserInteraction() {
        Spinner spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        super.onUserInteraction();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Spinner spinner = findViewById(R.id.spinner);
        spinner.setSelected(false);
        spinner.setEnabled(true);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        Spinner spinner = findViewById(R.id.spinner); // get spinner
        //String choice = String.valueOf(spinner.getSelectedItem()); // get choice string
        int choice_index = spinner.getSelectedItemPosition();

        switch (choice_index)
        {
            case 0: // Backlog
                category = "backlog";
                break;
            case 1: // Todo
                category = "todo";
                break;
            case 2: // Doing
                category = "doing";
                break;
            case 3: // Review
                category = "review";
                break;
            case 4: // Done
                category = "done";
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {}


}

