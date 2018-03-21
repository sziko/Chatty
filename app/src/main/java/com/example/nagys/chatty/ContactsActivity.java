package com.example.nagys.chatty;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

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
import java.util.Random;

public class ContactsActivity extends AppCompatActivity {

    private List<Contact> mContactList;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseReference;

    private String email;
    private String uid;
    private String displayName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        Intent thisIntent = getIntent();
        email = thisIntent.getStringExtra("email");
        uid = thisIntent.getStringExtra("uid");

        mAuth = FirebaseAuth.getInstance();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        if(mAuth.getCurrentUser().getDisplayName() == null) {
            createDisplayName();
        }

        mContactList = new ArrayList<>();


        mContactList.add(new Contact("Nagy Szilard", "adasdasdasdasdas", R.drawable.profile_pic));
        mContactList.add(new Contact("Tovisi", "hello", R.drawable.profile_pic));
        mContactList.add(new Contact("Daniel", "laksjdakjsdas;ldkalskj", R.drawable.profile_pic));
        mContactList.add(new Contact("Nagy", "adasdasqwilwekjnsdklvjbdasdasdas", R.drawable.profile_pic));
        mContactList.add(new Contact("Szilard", "adasdaSziasdasdasdas", R.drawable.profile_pic));
        mContactList.add(new Contact("Oooke", "Ooooooooke", R.drawable.profile_pic));
        mContactList.add(new Contact("Lajos", "laksmdllml", R.drawable.profile_pic));
        mContactList.add(new Contact("Matyus", "Szia", R.drawable.profile_pic));
        mContactList.add(new Contact("NSz", "adasdasdasdasdasdadf;slgjdkfjnvsdklfnvasdasdas", R.drawable.profile_pic));
        mContactList.add(new Contact("Isti", "kajsndkasn dkjasnd", R.drawable.profile_pic));
        mContactList.add(new Contact("Varga", "popopoipoipoi", R.drawable.profile_pic));


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

        if (id == R.id.action_add_contact) {
            Log.d("Actions: ", " Add contact");


            Toast.makeText(this, "\"Add Contact\" feature not yet working!", Toast.LENGTH_SHORT).show();
        }

        if(id == R.id.action_create_group) {
            Log.d("Actions: ", "Create group");

            Toast.makeText(this, "\"Create Group\" feature not yet working!", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void createDisplayName() {


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

    private void addUserToDatabase() {

        User user = new User(displayName, email, uid);

        mDatabaseReference.child("Users").child(displayName).setValue(user);

    }

    private void isDisplayNameValid() {

        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.child("Users").hasChild(displayName)) {


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
