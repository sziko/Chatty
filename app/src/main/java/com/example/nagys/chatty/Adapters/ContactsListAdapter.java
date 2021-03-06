package com.example.nagys.chatty.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nagys.chatty.Activities.ChatActivity;
import com.example.nagys.chatty.Classes.Contact;
import com.example.nagys.chatty.R;

import java.util.List;

/**
 * Created by szilardnagy on 07/03/2018.
 */

public class ContactsListAdapter extends RecyclerView.Adapter<ContactsListAdapter.ViewHolder> {

    private List<Contact> mContactList;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView imgProfile;
        private TextView txtDisplayName;
        private TextView txtLastMessage;

        public ViewHolder(View itemView) {
            super(itemView);

            imgProfile = itemView.findViewById(R.id.img_profile);
            txtDisplayName = itemView.findViewById(R.id.txt_display_name);
            txtLastMessage = itemView.findViewById(R.id.txt_last_message);


            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

            Context context = itemView.getContext();

            Intent startChat = new Intent(context, ChatActivity.class);
            startChat.putExtra("contactUID", mContactList.get(getAdapterPosition()).getUid());
            context.startActivity(startChat);
        }
    }

    public ContactsListAdapter(List<Contact> contactList) {
        mContactList = contactList;
    }

    @Override
    public ContactsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_contact_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ContactsListAdapter.ViewHolder holder, int position) {


        holder.txtDisplayName.setText(mContactList.get(position).getDisplayName());
        holder.txtLastMessage.setText(mContactList.get(position).getLastMessage());
        holder.imgProfile.setImageResource(mContactList.get(position).getProfilePictureId());

    }

    @Override
    public int getItemCount() {
        return mContactList.size();
    }
}
