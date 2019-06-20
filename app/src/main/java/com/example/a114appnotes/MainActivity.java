package com.example.a114appnotes;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {


    static public int SaveArrayListAs(String name,ArrayList ArrayName)                         // Step3 Save Data
    {
        try
        {
            MainActivity.sharedPreferences.edit().putString(name,ObjectSerializer.serialize(ArrayName)).apply();
            return 1;
        }
        catch(IOException e)
        {
            e.printStackTrace();
            return 0;
        }
    }
    static public ArrayList<String> GetArrayList(String SavedName)
    {
        ArrayList<String> newArray=new ArrayList<String>();
        try
        {
            newArray=(ArrayList<String>)ObjectSerializer.deserialize(MainActivity.sharedPreferences.getString(SavedName,ObjectSerializer.serialize(new ArrayList<String>())));

        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return newArray;
    }
    static ArrayList<String> notes =new ArrayList<String>();
    static  ArrayAdapter<String> arrayAdapter;
    static SharedPreferences sharedPreferences;


    // MENU BAR
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.tool_kit, menu);

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.newNote:
                setNewNote();
            default:
                return false;

        }
    }
    // Menu Bar Over

    boolean setNewNote()
    {
    Intent intent = new Intent(getApplicationContext(), NoteEditorActivity.class);
    startActivity(intent); // Sending an Empty Intent
    return true;
    }










    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences=this.getSharedPreferences("com.example.sharedpreferences", Context.MODE_PRIVATE); // Step2 Passing this context

        notes=GetArrayList("savedNotes" );

        if(notes.isEmpty())
        {
            notes.add("EXAMPLE NOTE");
        }




        ListView myListView =(ListView)findViewById(R.id.myListView);
        arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,notes);  //Step2 ArrayAdaptor         //Step3 ListView
        myListView.setAdapter(arrayAdapter);


        //Edit Text
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){                                                       //Step5  Listener for the tapped item
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i/*Position of element clicked*/, long l) {

                Intent intent = new Intent(getApplicationContext(), NoteEditorActivity.class);
                intent.putExtra("noteId", i);
                startActivity(intent);
            }
        });// Edit Text Over


        // Delete Item On Long Click
       myListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
           @Override
           public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {

               final int itemToDelete=position;

               new AlertDialog.Builder(MainActivity.this)
                       .setIcon(android.R.drawable.ic_dialog_alert)
                       .setTitle(" Are you sure ?")
                       .setMessage("Do you really want to remove selected item ?")
                       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {
                               notes.remove(itemToDelete);


                               if(notes.isEmpty())
                               {
                                   notes.add("EXAMPLE NOTE");
                               }



                               arrayAdapter.notifyDataSetChanged();
                               SaveArrayListAs("savedNotes",notes);
                           }
                       })
                       .setNegativeButton("No",null)
                       .show();
               return true;
           }
       }); // Delete Item On Long Click Over





    }



}
