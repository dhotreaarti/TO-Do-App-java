package com.example.todoapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapplication.Adapter.NotesAdapter;
import com.example.todoapplication.Database.NotesDB;
import com.example.todoapplication.databinding.ActivityDetailBinding;
import com.example.todoapplication.model.NotesModel;

public class DetailActivity extends AppCompatActivity {

    ActivityDetailBinding binding;
    NotesDB notesDB;
    RecyclerView recyclerView;
    NotesAdapter notesAdapter;
    int existingNoteId = -1; // Default value for a new note
    boolean isUpdatingNote = false; // Flag to determine if it's an update operation

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        notesDB = new NotesDB(this);

        // Retrieve the note ID and text from the intent extras
        existingNoteId = getIntent().getIntExtra("EXISTING_NOTE_ID", -1);
        String existingNoteText = getIntent().getStringExtra("EXISTING_NOTE_TEXT");

        if (existingNoteId != -1) {
            // If the note ID is valid, it's an update operation
            isUpdatingNote = true;
            binding.button1.setText("Update"); // Change button text
            binding.multiAutoCompleteTextView.setText(existingNoteText); // Set existing note text
        }

        binding.button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String noteText = binding.multiAutoCompleteTextView.getText().toString();
                if (!noteText.isEmpty()) {
                    if (isUpdatingNote) {
                        // Update existing note
                        updateNote();
                    } else {
                        // Add new note
                        addNote();
                    }
                } else {
                    Toast.makeText(DetailActivity.this, "Note cannot be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ImageView button2 =binding.imageButton;
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void addNote() {
        String noteText = binding.multiAutoCompleteTextView.getText().toString();
        NotesModel newNote = new NotesModel();
        newNote.setNote(noteText);
        boolean isInserted = notesDB.insertNote(newNote);
        if (isInserted) {
            Toast.makeText(DetailActivity.this, "Note added successfully", Toast.LENGTH_SHORT).show();
            finish(); // Finish the activity after adding the note
        } else {
            Toast.makeText(DetailActivity.this, "Error adding note", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateNote() {
        String updatedNoteText = binding.multiAutoCompleteTextView.getText().toString();
        NotesModel updatedNote = new NotesModel();
        updatedNote.setId(existingNoteId);
        updatedNote.setNote(updatedNoteText);
        notesDB.updateNote(updatedNote);
        Toast.makeText(DetailActivity.this, "Note updated successfully", Toast.LENGTH_SHORT).show();
        finish(); // Finish the activity after updating the note
    }
}
