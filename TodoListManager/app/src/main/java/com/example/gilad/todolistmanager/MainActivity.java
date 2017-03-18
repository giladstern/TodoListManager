package com.example.gilad.todolistmanager;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.*;
import android.view.inputmethod.EditorInfo;
import android.widget.*;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    static final String MESSAGES = "messages";

    ArrayList<CharSequence> content;
    MyAdapter adapter;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        if (savedInstanceState == null) {
            content = new ArrayList<>();
        }
        else {
            content = savedInstanceState.getCharSequenceArrayList(MESSAGES);
        }

        adapter = new MyAdapter(content);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        editText = (EditText) findViewById(R.id.editText);

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND ||
                        (actionId == EditorInfo.IME_NULL &&
                                event.getAction() == KeyEvent.ACTION_DOWN))
                {
                    entered();
                }
                return true;
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                entered();
            }
        });
    }

    private void entered() {
        CharSequence text = editText.getText().toString();

        if (!text.equals("")) {
            content.add(text);
            adapter.notifyDataSetChanged();
        }

        editText.getText().clear();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putCharSequenceArrayList(MESSAGES, content);
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
