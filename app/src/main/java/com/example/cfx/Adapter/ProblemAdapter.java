package com.example.cfx.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cfx.Model.Problem;
import com.example.cfx.OnBottomReachedListener;
import com.example.cfx.R;

import java.util.List;

public class ProblemAdapter extends RecyclerView.Adapter<ProblemAdapter.ViewHolder> {
    private Context context;
   private List<Problem>userlist;
    OnBottomReachedListener onBottomReachedListener;
    ProgressBar progressBar;
    public ProblemAdapter(Context context, List<Problem> users,ProgressBar progressBar) {
        this.context = context;
        this.userlist = users;
        this.progressBar=progressBar;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.problem_list, parent, false);
        return new ViewHolder(view);
    }
    public void setOnBottomReachedListener(OnBottomReachedListener onBottomReachedListener){
        this.onBottomReachedListener = onBottomReachedListener;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
     Problem submission= userlist.get(position);
     holder.problemtag.setText(submission.getTag());
     holder.problemrating.setText(submission.getRating());
     holder.problemname.setText(submission.getName());
     progressBar.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return userlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
    private final TextView problemname,problemrating,problemtag;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            problemname=itemView.findViewById(R.id.problemname);
            problemtag=itemView.findViewById(R.id.problemtag);
            problemrating=itemView.findViewById(R.id.problemrating);

        }
    }
}
