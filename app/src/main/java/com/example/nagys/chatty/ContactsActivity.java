package com.example.nagys.chatty;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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
}
