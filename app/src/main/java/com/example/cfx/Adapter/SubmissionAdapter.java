package com.example.cfx.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cfx.Model.Submission;
import com.example.cfx.OnBottomReachedListener;
import com.example.cfx.R;

import java.util.List;

public class SubmissionAdapter extends RecyclerView.Adapter<SubmissionAdapter.ViewHolder> {
    private Context context;
    static boolean ft=true;
   private List<Submission>userlist;
    OnBottomReachedListener onBottomReachedListener;
    public SubmissionAdapter(Context context, List<Submission> users) {
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
     Submission submission= userlist.get(position);
     holder.qtname.setText(submission.getIdx()+". "+submission.getQuestionname());
        if (position == userlist.size() - 1){
            onBottomReachedListener.onBottomReached(position);
        }
        holder.qdate.setText(submission.getDat());
    boolean verdi=submission.isVerdict();
        if(verdi)holder.verdict.setImageResource(R.drawable.correct);
        else
            holder.verdict.setImageResource(R.drawable.cancel);

    }

    @Override
    public int getItemCount() {
        return userlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView qtname,qdate;
        private ImageView verdict;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            verdict=itemView.findViewById(R.id.verdict);
         qtname=itemView.findViewById(R.id.question);
         qdate=itemView.findViewById(R.id.dater);
        }
    }
}
