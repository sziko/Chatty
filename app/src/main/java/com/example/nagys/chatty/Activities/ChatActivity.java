package com.example.nagys.chatty.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nagys.chatty.Adapters.ChatListAdapter;
import com.example.nagys.chatty.Classes.Message;
import com.example.nagys.chatty.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;

public class ChatActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private EditText edtMessage;
    private ChatListAdapter chatAdapter;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mRecyclerViewAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private DatabaseReference mDatabaseReference;
    private String contactUID;
    private String chatID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Intent thisIntent = getIntent();
        contactUID = thisIntent.getStringExtra("contactUID");

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();


        chatID = sortUID(contactUID + mAuth.getCurrentUser().getUid());

        ImageView imgSend = findViewById(R.id.img_send);

        imgSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });

        edtMessage = findViewById(R.id.edt_message);

        edtMessage.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                sendMessage();
                return true;
            }
        });

        mRecyclerView = findViewById(R.id.chat_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);


        mDatabaseReference = FirebaseDatabase.getInstance().getReference();


        mRecyclerViewAdapter = new ChatListAdapter(mDatabaseReference, mAuth.getCurrentUser().getDisplayName(), chatID);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);

        chatAdapter = (ChatListAdapter) mRecyclerViewAdapter;

    }

    private void sendMessage() {
        String message = edtMessage.getText().toString();

        if(!message.equals("")) {
            Message msg = new Message(mAuth.getCurrentUser().getDisplayName(), message);

            mDatabase.child("Messages").child(chatID).push().setValue(msg);
            edtMessage.setText("");
        }

    }

    @Override
    public void onStop() {
        super.onStop();

        chatAdapter.cleanup();
    }

    private String sortUID(String uid) {

        char[] input = uid.toCharArray();
        Arrays.sort(input);

        return new String(input);
    }

}
