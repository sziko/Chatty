package com.example.nagys.chatty;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChatActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        ImageView imgSend = findViewById(R.id.img_send);

        imgSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createMessage();
            }
        });
    }

    private void createMessage() {
        //TODO: Fix sending data to databse

        EditText edtMessage = findViewById(R.id.edt_message);

        String message = edtMessage.getText().toString();

        User user = new User(mAuth.getCurrentUser().getEmail(), message);

        mDatabase.child("Messages").child(user.getUser()).child(user.getMessage());
    }


}
