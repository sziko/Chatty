package com.example.nagys.chatty;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChatActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private EditText edtMessage;
    private ChatListAdapter chatAdapter;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mRecyclerViewAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser().getDisplayName() == null) {
            createDisplayName();
        }

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


        mRecyclerViewAdapter = new ChatListAdapter(mDatabaseReference, mAuth.getCurrentUser().getDisplayName());
        mRecyclerView.setAdapter(mRecyclerViewAdapter);

    }

    private void sendMessage() {
        String message = edtMessage.getText().toString();

        if(!message.equals("")) {
            Message msg = new Message(mAuth.getCurrentUser().getDisplayName(), message);

            mDatabase.child("Messages").push().setValue(msg);
            edtMessage.setText("");
        }

    }


    public void createDisplayName() {

        final EditText editText = new EditText(ChatActivity.this);

       new AlertDialog.Builder(ChatActivity.this)
                .setTitle("Display Name")
                .setMessage("Insert a name that others will see...")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        FirebaseUser user = mAuth.getCurrentUser();

                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setDisplayName(editText.getText().toString()).build();

                        user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                // after display name was updated
                            }
                        });

                    }
                })
                .setIcon(android.R.drawable.ic_dialog_info)
                .setView(editText)
                .show();
    }

    @Override
    public void onStop() {
        super.onStop();

        chatAdapter.cleanup();
    }

}
