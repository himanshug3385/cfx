package com.example.cfx;

import static com.example.cfx.TrackerActivity.username;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.cfx.Adapter.FriendAdapter;
import com.example.cfx.Model.Contest;
import com.example.cfx.Model.Recent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ProblemStatusContest extends AppCompatActivity {
    RecyclerView recyclerView;
    FriendAdapter friendAdapter;
    ArrayList<Recent> arraylist = new ArrayList<>();
    Intent it;
    SharedPreferences sharedPreferences;
   // Bundle b;
    TextView name,rank,timer;

    @Override
    protected void onStart() {
        super.onStart();
        sharedPreferences = getSharedPreferences("PREFS", 0);
        username = sharedPreferences.getString("cfxpassword", "null");
        if (username.equals("") || username.equals("null") || username.isEmpty()) {
            startActivity(new Intent(this, StartActivity.class));
            finish();
        }
        it=getIntent();
        getData(it.getStringExtra("idval")+"",username);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem_status_contest);

//        //b=it.getExtras();
//        sharedPreferences = getSharedPreferences("PREFS", 0);
//        username = sharedPreferences.getString("cfxpassword", "null");
//        if (username.equals("") || username.equals("null") || username.isEmpty()) {
//            startActivity(new Intent(this, StartActivity.class));
//            finish();
//        }
        //Toast.makeText(this,it.getStringExtra("idval")+username,Toast.LENGTH_LONG).show();
        recyclerView = findViewById(R.id.recycler111df);
        name=findViewById(R.id.a1);
        rank=findViewById(R.id.a3);
        timer=findViewById(R.id.a4);
        friendAdapter = new FriendAdapter(ProblemStatusContest.this, arraylist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(friendAdapter);

    }
    private void getData(String id,String username){

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://codeforces.com/")
                .addConverterFactory(ScalarsConverterFactory.create()).build();
        MainInerface mainInerface = retrofit.create(MainInerface.class);
        retrofit2.Call<String> call = mainInerface.STRING_CALL8(username,id);
        call.enqueue(new retrofit2.Callback<String>() {
            @Override
            public void onResponse(retrofit2.Call<String> call, retrofit2.Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body());
                        JSONObject result = jsonObject.getJSONObject("result");
                       JSONObject contest=result.getJSONObject("contest");
                        name.setText(contest.getString("name"));
                        timer.setText(getTime(contest.getInt("durationSeconds")));
                        JSONArray problems= result.getJSONArray("problems");///1
                        JSONObject rows= result.getJSONArray("rows").getJSONObject(0);
                      //  JSONObject kaam = rows.getJSONObject(0);
                        rank.setText(rows.getString("rank"));
                        JSONArray problemres= rows.getJSONArray("problemResults");///2
                        Log.d("lister",problemres.length()+id);
                        for(int i=0;i<problemres.length();i++){
                            JSONObject one=problems.getJSONObject(i);
                            JSONObject two=problemres.getJSONObject(i);
                            if(one.has("points"))
                            arraylist.add(new Recent(one.getString("name"), one.getString("rating"), one.getString("index"), one.getString("points"), two.getString("points"), two.getString("rejectedAttemptCount")));
                            else
                                arraylist.add(new Recent(one.getString("name"), one.getString("rating"), one.getString("index"), "N/A", two.getString("points"), two.getString("rejectedAttemptCount")));
                            Log.d("lister",one.getString("name"));

                        }

                        friendAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(retrofit2.Call<String> call, Throwable t) {
            }
        });
    }
    String getTime(int seconds){
        int S = seconds % 60;
        int H = seconds / 60;
        int M = H % 60;
        H = H / 60;
        String res="";
        if(H<10){
            res+="0";
        }
        res+=H+":";
        if(M<10){
            res+="0";
        }
        res+=M+":";
        if(S<10){
            res+="0";
        }
        res+=S;
        return res;
    }
}