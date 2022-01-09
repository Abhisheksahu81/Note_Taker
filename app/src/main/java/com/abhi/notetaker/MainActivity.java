package com.abhi.notetaker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import io.realm.Sort;

public class MainActivity extends AppCompatActivity {

    MaterialButton addnewButton;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MaterialButton addnewButton = findViewById(R.id.addnewnote);

        addnewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,Add_note.class));
            }
        });

        Realm.init(getApplicationContext());
        Realm realm = Realm.getDefaultInstance();

        RealmResults<Notes> noteslist = realm.where(Notes.class).findAll().sort("createdTime", Sort.DESCENDING);


        recyclerView  = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Adapter myAdapter = new Adapter(getApplicationContext(), noteslist);
        recyclerView.setAdapter(myAdapter);


        noteslist.addChangeListener(new RealmChangeListener<RealmResults<Notes>>() {
            @Override
            public void onChange(RealmResults<Notes> notes) {
                myAdapter.notifyDataSetChanged();
            }
        });

    }
}