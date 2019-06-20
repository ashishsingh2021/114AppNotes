package com.example.a114appnotes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.io.IOException;
import java.util.ArrayList;

public class NoteEditorActivity extends AppCompatActivity {
    int  noteId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);


        EditText editText = (EditText) findViewById(R.id.editText);
        Intent intent = getIntent();

        noteId = intent.getIntExtra("noteId", -1);
       if (noteId>=0) {
           editText.setText(MainActivity.notes.get(noteId)); // Setting the
       }
       else
        {
            MainActivity.notes.add(" Write Your Notes Here");  // For This case in beforeTextChanged is used====>> editText.setText(MainActivity.notes.get(noteId));
            noteId=MainActivity.notes.size()-1;
        }


        editText.setText(MainActivity.notes.get(noteId));
        editText.addTextChangedListener(new TextWatcher() { // Saving Changes
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                MainActivity.notes.set(noteId, String.valueOf(charSequence));
                MainActivity.arrayAdapter.notifyDataSetChanged();
                MainActivity.SaveArrayListAs("savedNotes",MainActivity.notes);
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

}
