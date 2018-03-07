package com.example.nagys.chatty;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ContactsActivity extends AppCompatActivity {

    private List<Contact> mContactList;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

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
}
