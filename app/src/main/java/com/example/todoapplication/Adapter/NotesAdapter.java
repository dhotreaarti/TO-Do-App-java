package com.example.todoapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapplication.Database.NotesDB;
import com.example.todoapplication.DetailActivity;
import com.example.todoapplication.R;
import com.example.todoapplication.model.NotesModel;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    private ArrayList<NotesModel> notesList;
    private Context context;

    private NotesDB notesDB;
    public NotesAdapter(ArrayList<NotesModel> notesList, Context context) {
        this.notesList = notesList;
        this.context = context;
        this.notesDB = new NotesDB(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_layout, parent, false);
        return new ViewHolder(view);
    }

   /* @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final NotesModel note = notesList.get(position);
        holder.noteText.setText(note.getNote());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("NOTE", note.getNote());
                context.startActivity(intent);
            }
        });
    }
*/
   // Inside the NotesAdapter's onBindViewHolder method
   @Override
   public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       NotesModel currentItem = notesList.get(position);
       holder.noteText.setText(currentItem.getNote());

       // Set click listener for edit button
       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               // Pass existing note ID and text to DetailActivity for editing
               Intent intent = new Intent(context, DetailActivity.class);
               intent.putExtra("EXISTING_NOTE_ID", currentItem.getId());
               intent.putExtra("EXISTING_NOTE_TEXT", currentItem.getNote());
               context.startActivity(intent);
           }
       });
       // Set long press listener for deleting a note
       holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
           @Override
           public boolean onLongClick(View v) {
               // Delete the note from the database
               notesDB.deleteNote(currentItem.getId());

               // Remove the note from the list and notify adapter
               notesList.remove(currentItem);
               notifyDataSetChanged();

               // Show a toast message
               Toast.makeText(context, "Note deleted", Toast.LENGTH_SHORT).show();
               return true; // Return true to consume the long click event
           }
       });

   }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public Context getContext() {
        return context;
    }
    public void deleteNote(int position) {
        NotesModel deletedNote = notesList.get(position);
        notesList.remove(position);
        notifyItemRemoved(position);
        notesDB.deleteNote(deletedNote.getId());
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView noteText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            noteText = itemView.findViewById(R.id.textView3);
        }
    }
}
