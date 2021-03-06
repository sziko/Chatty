package com.example.nagys.chatty.Activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.nagys.chatty.Adapters.ContactsListAdapter;
import com.example.nagys.chatty.Adapters.SearchListAdapter;
import com.example.nagys.chatty.Classes.Contact;
import com.example.nagys.chatty.Classes.DatabaseHelper;
import com.example.nagys.chatty.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private DatabaseReference mDatabaseReference;
    private FirebaseAuth mAuth;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Contact> mContactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mAuth = FirebaseAuth.getInstance();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.search_action);
        final SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                showQueryResult(query); // passing query over
                searchView.clearFocus();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    //searching for query in firebase db
    private void showQueryResult(final String query) {

        mContactList = new ArrayList<>();
        createUsersList();

        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.child("Users").hasChild(query)) {

                    if (query.equals(mAuth.getCurrentUser().getDisplayName())) {

                        Toast.makeText(SearchActivity.this, "That's you!", Toast.LENGTH_SHORT).show();
                    } else {

                        mContactList.add(new Contact(query, "", R.drawable.profile_pic, dataSnapshot.child("Users").child(query).child("uid").toString()));
                        createUsersList();
                    }
                } else {
                    Toast.makeText(SearchActivity.this, "No user found", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void createUsersList() { // populating recycler view after user was found in firebase db

        mRecyclerView = findViewById(R.id.searchable_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new SearchListAdapter(mContactList);
        mRecyclerView.setAdapter(mAdapter);

    }

}
