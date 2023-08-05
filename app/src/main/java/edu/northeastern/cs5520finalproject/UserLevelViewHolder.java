package edu.northeastern.cs5520finalproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

class UserLevelViewHolder extends RecyclerView.ViewHolder {

    private final TextView levelItemView;

    private UserLevelViewHolder(View itemView) {
        super(itemView);
        levelItemView = itemView.findViewById(R.id.textView);
    }

    public void bind(int level) {
        levelItemView.setText(level);
    }

    static UserLevelViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item, parent, false);
        return new UserLevelViewHolder(view);
    }
}
