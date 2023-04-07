package com.example.cfx.Adapter;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cfx.MainInerface;
import com.example.cfx.Model.Contest;
import com.example.cfx.Model.Recent;
import com.example.cfx.ProfileSection;
import com.example.cfx.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.ViewHolder> {
    private final Context context;
    private final ArrayList<Recent> userlist2;
    public FriendAdapter(Context context, ArrayList<Recent> users) {
        this.context = context;
        this.userlist2 = users;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.friend_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Recent friendAdapter=userlist2.get(position);
         holder.name.setText(friendAdapter.getName());
         holder.idx.setText(friendAdapter.getIndex());
         holder.mxpt.setText(friendAdapter.getMxpoints());
         holder.ptobt.setText(friendAdapter.getPtobt());
         holder.rating.setText(friendAdapter.getRating());
         holder.reject.setText(friendAdapter.getRejectedcnt());

    }

    @Override
    public int getItemCount() {
        return userlist2.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView idx,name,mxpt,ptobt,rating,reject;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            idx=itemView.findViewById(R.id.idex);
            name=itemView.findViewById(R.id.proname);
            mxpt=itemView.findViewById(R.id.mxpt);
            ptobt=itemView.findViewById(R.id.ptobt);
            rating=itemView.findViewById(R.id.ratingpo);
            reject=itemView.findViewById(R.id.rejectedcnt);
        }
    }
}
