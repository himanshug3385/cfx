package com.example.cfx.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cfx.Model.Problem;
import com.example.cfx.OnBottomReachedListener;
import com.example.cfx.R;

import java.util.List;

public class PieAdapter extends RecyclerView.Adapter<PieAdapter.ViewHolder> {
    private Context context;
    private List<Problem>userlist;
    OnBottomReachedListener onBottomReachedListener;
    public PieAdapter(Context context, List<Problem> users) {
        this.context = context;
        this.userlist = users;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.pichartrec_item, parent, false);
        return new ViewHolder(view);
    }
    public void setOnBottomReachedListener(OnBottomReachedListener onBottomReachedListener){
        this.onBottomReachedListener = onBottomReachedListener;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Problem submission= userlist.get(position);
        holder.problemname.setText(submission.getName());
        holder.problemrating.setText(submission.getRating());
    }

    @Override
    public int getItemCount() {
        return userlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView problemname,problemrating;
        private ImageView circleImageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            problemname=itemView.findViewById(R.id.piname);
            problemrating=itemView.findViewById(R.id.picount);
        }
    }
}
