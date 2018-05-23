package com.example.nagys.chatty.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.nagys.chatty.Adapters.ContactsListAdapter;
import com.example.nagys.chatty.Classes.Contact;
import com.example.nagys.chatty.Classes.DatabaseHelper;
import com.example.nagys.chatty.R;
import com.example.nagys.chatty.Classes.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ContactsActivity extends AppCompatActivity {

    private List<Contact> mContactList;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseReference;
    private DatabaseHelper mDatabaseHelper;

    private String email;
    private String uid;
    private String displayName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        mAuth = FirebaseAuth.getInstance();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();


        mDatabaseHelper = new DatabaseHelper(this); // database instance

        Intent thisIntent = getIntent();
        email = thisIntent.getStringExtra("email");
        uid = thisIntent.getStringExtra("uid");



        if(mAuth.getCurrentUser().getDisplayName() == null) {
            createDisplayName();
        }

        mContactList = new ArrayList<>();

        Cursor contactsFromDB = mDatabaseHelper.getAllData();

        if(contactsFromDB.getCount() != 0) {


            while(contactsFromDB.moveToNext()) { // getting data from db and adding it to mContactList

                String c_uid = contactsFromDB.getString(0);
                String c_dispName = contactsFromDB.getString(1);
                String c_picUrl = contactsFromDB.getString(2);

                mContactList.add(new Contact(c_dispName, "", R.drawable.profile_pic, c_uid));
            }

        }


        // setting up the recycler view
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new ContactsListAdapter(mContactList);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        if(id == R.id.action_add_contact) { // add new contact activity

            startActivity(new Intent(ContactsActivity.this, SearchActivity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void createDisplayName() { // creating display name after registration


        final EditText editText = new EditText(ContactsActivity.this);

        new AlertDialog.Builder(ContactsActivity.this)
                .setTitle("Display Name")
                .setMessage("Insert a name that others will see...")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        displayName = editText.getText().toString();

                        isDisplayNameValid();

                    }
                })
                .setIcon(android.R.drawable.ic_dialog_info)
                .setView(editText)
                .show();

    }

    private void addUserToDatabase() { // adding users to local database

        User user = new User(displayName, email, uid);

        mDatabaseReference.child("Users").child(displayName).setValue(user);

    }

    private void isDisplayNameValid() { // checking if username is valid or not (has to be unique)

        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.child("Users").hasChild(displayName)) { // iterating trough firebase db


                    new AlertDialog.Builder(ContactsActivity.this)
                            .setTitle("Oops!")
                            .setMessage("Display name already used!")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    createDisplayName();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_info)
                            .show();

                }
                else {
                    FirebaseUser user = mAuth.getCurrentUser();

                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(displayName).build();

                    user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            // after display name was updated
                            addUserToDatabase();

                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
