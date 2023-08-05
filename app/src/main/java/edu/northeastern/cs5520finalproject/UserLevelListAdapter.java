package edu.northeastern.cs5520finalproject;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;


public class UserLevelListAdapter extends ListAdapter<UserLevel, UserLevelViewHolder> {

    public UserLevelListAdapter(@NonNull DiffUtil.ItemCallback<UserLevel> diffCallback) {
        super(diffCallback);
    }

    @Override
    public UserLevelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return UserLevelViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(UserLevelViewHolder holder, int position) {
        UserLevel current = getItem(position);
        holder.bind(current.getLevel());
    }

    static class UserLevelDiff extends DiffUtil.ItemCallback<UserLevel> {

        @Override
        public boolean areItemsTheSame(@NonNull UserLevel oldItem, @NonNull UserLevel newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull UserLevel oldItem, @NonNull UserLevel newItem) {
            return oldItem.getLevel() == (newItem.getLevel());
        }
    }
}
