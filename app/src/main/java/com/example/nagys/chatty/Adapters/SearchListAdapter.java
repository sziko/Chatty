package com.example.nagys.chatty.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nagys.chatty.Classes.Contact;
import com.example.nagys.chatty.Classes.User;
import com.example.nagys.chatty.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nagys on 3/22/2018.
 */

public class SearchListAdapter extends RecyclerView.Adapter<SearchListAdapter.ViewHolder> {

    private List<Contact> mUserList = new ArrayList<>();

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CheckBox mCheckBox;
        private ImageView profilePic;
        private TextView displayName;

        public ViewHolder(View itemView) {
            super(itemView);

            mCheckBox = itemView.findViewById(R.id.chb_searchable_user);
            profilePic = itemView.findViewById(R.id.img_searchable_profile);
            displayName = itemView.findViewById(R.id.txt_searchable_name);
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

    public void clearList() {

        final int size = mUserList.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                mUserList.remove(0);
            }

            notifyItemRangeRemoved(0, size);
        }
    }
}
