package com.example.todoapplication;

import static com.example.todoapplication.LoginActivity.SHARED_PREFS;

import android.app.ActivityManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.todoapplication.Adapter.NotesAdapter;
import com.example.todoapplication.Database.NotesDB;
import com.example.todoapplication.model.NotesModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class NotesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FloatingActionButton addButton;
    private NotesDB notesDB;
    private NotesAdapter adapter;
    private View borderBox;
    private TextView textView1, textView2;
    ArrayList<NotesModel> notesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = findViewById(R.id.recyclerview2);
        addButton = findViewById(R.id.floatingActionButton);
        textView1 = findViewById(R.id.textView2);
        textView2 = findViewById(R.id.textView5);
        notesDB = new NotesDB(this);
        notesList = notesDB.getAllNotes();

        adapter = new NotesAdapter(notesList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotesActivity.this, DetailActivity.class);
                startActivity(intent);
            }
        });

        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Set text color for Notes TextView
                textView1.setTextColor(getResources().getColor(R.color.blue)); // Change to your desired color
                // Set text color for Tasks TextView
                textView2.setTextColor(getResources().getColor(R.color.black)); // Change to your desired color
            }
        });

        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotesActivity.this, MainActivity.class);
                startActivity(intent);
                // Set text color for Tasks TextView
                textView2.setTextColor(getResources().getColor(R.color.blue)); // Change to your desired color
                // Set text color for Notes TextView
                textView1.setTextColor(getResources().getColor(R.color.black)); // Change to your desired color
            }
        });
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecyclerViewTouchHelperNotes(adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.notesmenu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            // Perform logout action
            logoutUser();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void logoutUser() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", ""); // Clear the login status
        editor.apply();

        Intent intent = new Intent(NotesActivity.this, LoginActivity.class);
        startActivity(intent);
        finish(); // Finish the current activity to prevent going back to MainActivity when pressing back
    }


    @Override
    protected void onResume() {
        super.onResume();
        // Refresh the notes list from the database
        notesList.clear();
        notesList.addAll(notesDB.getAllNotes());
        adapter.notifyDataSetChanged();
    }

}