package com.example.nagys.chatty.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nagys.chatty.Activities.ChatActivity;
import com.example.nagys.chatty.Activities.ContactsActivity;
import com.example.nagys.chatty.Activities.SearchActivity;
import com.example.nagys.chatty.Classes.Contact;
import com.example.nagys.chatty.Classes.DatabaseHelper;
import com.example.nagys.chatty.Classes.User;
import com.example.nagys.chatty.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nagys on 3/22/2018.
 */

public class SearchListAdapter extends RecyclerView.Adapter<SearchListAdapter.ViewHolder> {

    private List<Contact> mUserList;
    private DatabaseHelper mDatabaseHelper;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView profilePic;
        private TextView displayName;

        public ViewHolder(View itemView) {
            super(itemView);

            profilePic = itemView.findViewById(R.id.img_searchable_profile);
            displayName = itemView.findViewById(R.id.txt_searchable_name);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            Contact contact = mUserList.get(getAdapterPosition());



            Context context = itemView.getContext();

            mDatabaseHelper = new DatabaseHelper(context);
            mDatabaseHelper.insertData(contact.getUid(), contact.getDisplayName(), "Picture goes here");

            context.startActivity(new Intent(context, ContactsActivity.class));
        }
    }

    public SearchListAdapter(List<Contact> userList) {
        mUserList = userList;
    }

    @Override
    public SearchListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.searchable_contact_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SearchListAdapter.ViewHolder holder, int position) {

        holder.profilePic.setImageResource(mUserList.get(position).getProfilePictureId());
        holder.displayName.setText(mUserList.get(position).getDisplayName());
    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }


}
