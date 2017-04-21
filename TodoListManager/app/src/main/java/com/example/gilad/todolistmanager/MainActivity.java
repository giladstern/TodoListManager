package com.example.gilad.todolistmanager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.*;
import android.widget.*;
import com.google.firebase.database.*;


import java.util.*;

public class MainActivity extends AppCompatActivity {

    static final String CONTENT = "content";
    ArrayList<DataEntry> content;
    MyAdapter adapter;
    static final DatabaseReference database = FirebaseDatabase.getInstance().getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        if (savedInstanceState == null) {
            content = new ArrayList<>();
            database.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    ArrayList<DataEntry> tmp = dataSnapshot.child("data").
                            getValue(new GenericTypeIndicator<ArrayList<DataEntry>>(){});
                    if (tmp != null) {
                        content.addAll(tmp);
                    }
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.d("Error", "Failed to load,", databaseError.toException());
                }
            });
        }
        else {
            content = (ArrayList<DataEntry>) savedInstanceState.getSerializable(CONTENT);
        }

        adapter = new MyAdapter(content, database);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                entered();
            }
        });
    }

    private void entered() {

        new AlertDialog.Builder(this).setView(
                getLayoutInflater().inflate(R.layout.custom_dialog, null))
                .setTitle("Add New Item")
                .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText editText = (EditText) ((AlertDialog) dialog).findViewById(R.id.editText);
                        DatePicker datePicker = (DatePicker) ((AlertDialog) dialog).findViewById(R.id.datePicker);

                        String text = editText.getText().toString();

                        content.add(new DataEntry(text, datePicker.getDayOfMonth(),
                                datePicker.getMonth(), datePicker.getYear()));

                        Collections.sort(content);

                        adapter.notifyDataSetChanged();

                        database.child("data").setValue(content);

                    }
                }).setNegativeButton("Cancel", null).show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable(CONTENT, content);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add:
                entered();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
