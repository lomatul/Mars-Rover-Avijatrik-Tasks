package com.example.myno;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.myno.Adapters.NotesListAdapter;
import com.example.myno.Database.RoomDB;
import com.example.myno.Models.Notes;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
//we will create some objects for our recyclerview and our adapter

    RecyclerView recyclerView;
    NotesListAdapter notesListAdapter;
    List<Notes> notes = new ArrayList<>();
    RoomDB database;
    FloatingActionButton fab_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initializing
        recyclerView = findViewById(R.id.recycler_home);
        fab_add=findViewById(R.id.fab_add);
        database=RoomDB.getInstance(this);
    }
}