package com.example.nagys.chatty;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

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

//    private Activity mActivity;
//    private DatabaseReference mDatabase;
//    private String mDisplayName;
//    private ArrayList<DataSnapshot> mSnapshots;
//
//    private ChildEventListener mListener = new ChildEventListener() {
//        @Override
//        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//
//            mSnapshots.add(dataSnapshot);
//            notifyDataSetChanged();
//        }
//
//        @Override
//        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//        }
//
//        @Override
//        public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//        }
//
//        @Override
//        public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//        }
//
//        @Override
//        public void onCancelled(DatabaseError databaseError) {
//
//        }
//    };
//
//    public ChatListAdapter(Activity activity, DatabaseReference ref, String name) {
//        mActivity = activity;
//        mDisplayName = name;
//        mDatabase = ref.child("Messages");
//        mSnapshots = new ArrayList<>();
//
//        mDatabase.addChildEventListener(mListener);
//    }
//
//    static class ViewHolder {
//        TextView userName;
//        TextView body;
//        LinearLayout.LayoutParams params;
//    }
//
//    @Override
//    public int getCount() {
//        return mSnapshots.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//
//        DataSnapshot snapshot = mSnapshots.get(position);
//
//        return snapshot.getValue(Message.class);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return 0;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//
//        if(convertView == null) {
//            LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            convertView = inflater.inflate(R.layout.chat_msg_row, parent, false);
//
//            final ViewHolder holder = new ViewHolder();
//            holder.userName = convertView.findViewById(R.id.author);
//            holder.body = convertView.findViewById(R.id.message);
//            holder.params = (LinearLayout.LayoutParams) holder.userName.getLayoutParams();
//            convertView.setTag(holder);
//        }
//
//        final Message message = (Message) getItem(position);
//        final ViewHolder holder = (ViewHolder) convertView.getTag();
//
//        boolean isMe = message.getUser().equals(mDisplayName);
//        setChatRowAppearence(isMe, holder);
//
//        String user = message.getUser();
//        holder.userName.setText(user);
//
//        String msg = message.getMessage();
//        holder.body.setText(msg);
//
//        return convertView;
//    }
//
//    private void setChatRowAppearence(boolean isMe, ViewHolder holder) {
//
//        if(isMe) {
//            holder.params.gravity = Gravity.END;
//            holder.userName.setTextColor(Color.GREEN);
//            holder.body.setBackgroundResource(R.drawable.bubble2);
//        }
//        else {
//            holder.params.gravity = Gravity.START;
//            holder.userName.setTextColor(Color.BLUE);
//            holder.body.setBackgroundResource(R.drawable.bubble1);
//        }
//
//        holder.userName.setLayoutParams(holder.params);
//        holder.body.setLayoutParams(holder.params);
//
//    }
//
//    public void cleanup() {
//        mDatabase.removeEventListener(mListener);
//    }


}
