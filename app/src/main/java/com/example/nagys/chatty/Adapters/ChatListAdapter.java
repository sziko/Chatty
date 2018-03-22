package com.example.nagys.chatty.Adapters;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.nagys.chatty.Classes.Message;
import com.example.nagys.chatty.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nagys on 2/28/2018.
 */

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ViewHolder> {

    private List<DataSnapshot> mDataSnapshots;
    private DatabaseReference mDatabaseReference;
    private String mDisplayName;


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView author;
        private TextView message;
        private LinearLayout.LayoutParams mParams;

        public ViewHolder(View itemView) {
            super(itemView);

            author = itemView.findViewById(R.id.author);
            message = itemView.findViewById(R.id.message);

        }

        public LinearLayout.LayoutParams getParams() {
            return mParams;
        }

        public void setParams(LinearLayout.LayoutParams params) {
            mParams = params;
        }
    }

    public ChatListAdapter(DatabaseReference reference, String name) {
        mDataSnapshots = new ArrayList<>();

        mDatabaseReference = reference.child("Messages");

        mDatabaseReference.addChildEventListener(mListener);

        mDisplayName = name;
    }

    @Override
    public ChatListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_msg_row, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ChatListAdapter.ViewHolder holder, int position) {

        Message m = mDataSnapshots.get(position).getValue(Message.class);

        holder.author.setText(m.getUser());
        holder.message.setText(m.getMessage());

        holder.setParams((LinearLayout.LayoutParams) holder.author.getLayoutParams());

        boolean isMe = m.getUser().equals(mDisplayName);
        setChatRowAppearence(isMe, holder);
    }

    @Override
    public int getItemCount() {
        return mDataSnapshots.size();
    }

    private ChildEventListener mListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            mDataSnapshots.add(dataSnapshot);
            notifyDataSetChanged();
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };



    public void cleanup() {
        mDatabaseReference.removeEventListener(mListener);
    }

    private void setChatRowAppearence(boolean isMe, ViewHolder holder) {

        if(isMe) {
            holder.getParams().gravity = Gravity.END;
            holder.author.setTextColor(Color.GREEN);
            holder.message.setBackgroundResource(R.drawable.bubble2);
        }
        else {
            holder.getParams().gravity = Gravity.START;
            holder.author.setTextColor(Color.BLUE);
            holder.message.setBackgroundResource(R.drawable.bubble1);
        }

        holder.author.setLayoutParams(holder.getParams());
        holder.message.setLayoutParams(holder.getParams());

    }
}
