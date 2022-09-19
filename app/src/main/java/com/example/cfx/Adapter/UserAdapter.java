package com.example.cfx.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cfx.Model.User;
import com.example.cfx.OnBottomReachedListener;
import com.example.cfx.R;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private Context context;
   private List<User>userlist;
    OnBottomReachedListener onBottomReachedListener;
    public UserAdapter(Context context, List<User> users) {
        this.context = context;
        this.userlist = users;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.submission_item, parent, false);
        return new ViewHolder(view);
    }
    public void setOnBottomReachedListener(OnBottomReachedListener onBottomReachedListener){
        this.onBottomReachedListener = onBottomReachedListener;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
     User submission= userlist.get(position);

    }

    @Override
    public int getItemCount() {
        return userlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView qtname;
        private ImageView verdict;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            verdict=itemView.findViewById(R.id.verdict);
         qtname=itemView.findViewById(R.id.question);
        }
    }
}
