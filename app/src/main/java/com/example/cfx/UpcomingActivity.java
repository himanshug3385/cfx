
package com.example.cfx;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import com.example.cfx.Adapter.ContestAdapter;
import com.example.cfx.Adapter.UpcomingAdapter;
import com.example.cfx.Model.Contest;
import com.example.cfx.Model.Upcoming;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.FoldingCube;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class UpcomingActivity extends AppCompatActivity {
    UpcomingAdapter contestAdapter;
    RecyclerView recyclerView2;
    ArrayList<Upcoming> arraylist=new ArrayList<>();
    RelativeLayout relativeLayout;
    ProgressBar progressBar;
    SwipeRefreshLayout swipeRefreshLayout;
    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Snackbar snackbar = Snackbar.make(relativeLayout, "Press again to exit", Snackbar.LENGTH_SHORT).setAction("Ok", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        snackbar.show();
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    @Override
    protected void onStart() {
        super.onStart();
        readdata23();
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
                swipeRefreshLayout.setVisibility(View.VISIBLE);
            }
        }, 2000);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming);
        recyclerView2=findViewById(R.id.recyclerupcoming);
        relativeLayout=findViewById(R.id.r2);
        Window window = this.getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.white2));
        contestAdapter = new UpcomingAdapter(UpcomingActivity.this,arraylist);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));
        recyclerView2.setAdapter(contestAdapter);
        progressBar = (ProgressBar)findViewById(R.id.spin_kit);
        Sprite doubleBounce = new FoldingCube();
        progressBar.setIndeterminateDrawable(doubleBounce);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                RearrangeItems();
            }
        });
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigation.setItemIconTintList(null);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.contest:
                        break;
                    case R.id.problems:
                        Intent intent = new Intent(getApplicationContext(), ProblemSetActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent); finish();
                        break;
                    case R.id.nsubmission:
                        Intent intent2 = new Intent(getApplicationContext(), MainActivity.class);
                        intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent2); finish();
                        break;
                    case R.id.profile:
                        Intent intent3 = new Intent(getApplicationContext(),ProfileSection.class);
                        intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent3); finish();
                }
                return false;
            }
        });

    }
    public void RearrangeItems() {
        readdata23();
    }
    private void readdata23(){
        progressBar.setVisibility(View.VISIBLE);
        Retrofit retrofit=new Retrofit.Builder().baseUrl("https://codeforces.com/")
                .addConverterFactory(ScalarsConverterFactory.create()).build();
        MainInerface mainInerface=retrofit.create(MainInerface.class);
        retrofit2.Call<String> call=mainInerface.STRING_CALL5();
        call.enqueue(new retrofit2.Callback<String>() {
            @Override
            public void onResponse(retrofit2.Call<String> call, retrofit2.Response<String> response) {
                if(response.isSuccessful() && response.body()!=null){
                    try {
                        arraylist.clear();
                        JSONObject jsonObject=new JSONObject(response.body());
                        JSONArray result=jsonObject.getJSONArray("result");
                        for(int i=0;i<result.length();i++){
                            JSONObject js=result.getJSONObject(i);
                            String be=js.getString("phase");
                            if(be.equals("BEFORE")){
                                String a=js.getString("name");
                                String b=js.getString("type");
                                String c=js.getString("durationSeconds");
                                String d=js.getString("startTimeSeconds");
                                String e=js.getString("relativeTimeSeconds");
                                e=e.substring(1);
                                Upcoming contest= new Upcoming(a,b,timer(c),getDate(d),timer(e));
                                arraylist.add(0,contest);
                            }

                        }
                        contestAdapter.notifyDataSetChanged();
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
    private  String getDate(String st){
        long unix_seconds = Long.parseLong(st);
        //convert seconds to milliseconds
        Date date = new Date(unix_seconds*1000L);
        // format of the date
        SimpleDateFormat jdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        jdf.setTimeZone(TimeZone.getTimeZone("GMT+05:30"));
        String java_date = jdf.format(date);
        // System.out.println("\n"+java_date+"\n");
        return java_date;
    }
    private String timer(String timex){
        Long time =Long.parseLong(timex);
        Long hour,min,sec;
        hour = time/3600;
       time = time%3600;
        min = time/60;
//        time = time%60;
//        sec = time;"

        return hour +" hr " +min+" min";
    }
    }

