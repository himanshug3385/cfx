package com.example.cfx.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cfx.Model.Contest;
import com.example.cfx.OnBottomReachedListener;
import com.example.cfx.ProblemStatusContest;
import com.example.cfx.R;
import java.util.ArrayList;

public class ContestAdapter extends RecyclerView.Adapter<ContestAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Contest> userlist2;

    OnBottomReachedListener onBottomReachedListener;
    public ContestAdapter(Context context, ArrayList<Contest> users) {
        this.context = context;
        this.userlist2 = users;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.contest_item, parent, false);
        return new ViewHolder(view);
    }
    public void setOnBottomReachedListener(OnBottomReachedListener onBottomReachedListener){
        this.onBottomReachedListener = onBottomReachedListener;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       Contest contest= userlist2.get(position);
         holder.conname.setText(contest.getName());
         holder.oldrating.setText(contest.getPrevrating());
         holder.newrating.setText(contest.getNewrating());
         holder.rank.setText(contest.getRank());
         holder.itemView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent it=new Intent(context, ProblemStatusContest.class);
               //  Toast.makeText(context,contest.getId()+"",Toast.LENGTH_LONG).show();
                 it.putExtra("idval",contest.getId()+"");
                 context.startActivity(it);
             }
         });

    }

    @Override
    public int getItemCount() {
        return userlist2.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
       private TextView conname,oldrating,newrating,rank;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            conname=itemView.findViewById(R.id.conname);
            oldrating=itemView.findViewById(R.id.oldrating);
            newrating=itemView.findViewById(R.id.newrating);
            rank=itemView.findViewById(R.id.rank);

        }
    }
}
