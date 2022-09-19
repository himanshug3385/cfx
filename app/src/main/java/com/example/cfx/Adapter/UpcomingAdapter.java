package com.example.cfx.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cfx.Model.Upcoming;
import com.example.cfx.OnBottomReachedListener;
import com.example.cfx.R;

import java.util.ArrayList;

public class UpcomingAdapter extends RecyclerView.Adapter<UpcomingAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Upcoming> userlist2;
    OnBottomReachedListener onBottomReachedListener;
    public UpcomingAdapter(Context context, ArrayList<Upcoming> users) {
        this.context = context;
        this.userlist2 = users;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.upcoming_contest, parent, false);
        return new ViewHolder(view);
    }
    public void setOnBottomReachedListener(OnBottomReachedListener onBottomReachedListener){
        this.onBottomReachedListener = onBottomReachedListener;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       Upcoming contest= userlist2.get(position);
       holder.name.setText(contest.getName());
       holder.duration.setText(contest.getDuration());
       holder.type.setText(contest.getType());
       holder.sttime.setText(contest.getSttime());
       holder.remainingtime.setText(contest.getRemainingtime());
    }

    @Override
    public int getItemCount() {
        return userlist2.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
    TextView name,duration,type,sttime,remainingtime;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.cname);
            duration=itemView.findViewById(R.id.duration);
           type=itemView.findViewById(R.id.ctype);
            sttime=itemView.findViewById(R.id.stttime);
            remainingtime=itemView.findViewById(R.id.timer);

        }
    }
}
