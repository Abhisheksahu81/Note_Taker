package com.abhi.notetaker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import io.realm.Realm;

public class Add_note extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        EditText titleinput = findViewById(R.id.et_title);
        EditText descriptioninput = findViewById(R.id.et_description);

        MaterialButton saveBtn = findViewById(R.id.save_btn);

        Realm.init(getApplicationContext());
        Realm realm = Realm.getDefaultInstance();

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = titleinput.getText().toString();
                String description = descriptioninput.getText().toString();
                long createdtime =  System.currentTimeMillis();

                realm.beginTransaction();
                Notes notes = realm.createObject(Notes.class);
                notes.setTitle(title);
                notes.setDescription(description);
                notes.setCreatedTime(createdtime);

                realm.commitTransaction();
                Toast.makeText(getApplicationContext(), "Note Saved ", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}