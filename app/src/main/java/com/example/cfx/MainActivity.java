package com.example.cfx;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.example.cfx.Adapter.SubmissionAdapter;
import com.example.cfx.Model.Submission;
import com.example.cfx.Model.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.hanks.htextview.scale.ScaleTextView;
import com.hanks.htextview.typer.TyperTextView;
import com.todkars.shimmer.ShimmerRecyclerView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Retrofit;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView1;
    RelativeLayout relativeLayout;
    RelativeLayout relativeLayout12;
    TyperTextView textViewrr;
    boolean doubleBackToExitPressedOnce = false;
    TextView totalsub;
    LottieAnimationView lottieAnimationView;
    ImageView onlycorrect;
    CircleImageView openprofile;
    SubmissionAdapter submissionAdapter;
    List<Submission> submissionList;
    List<Submission> submissionListcopy;
    SharedPreferences sharedPreferences;
    String username;
    SwipeRefreshLayout swipeRefreshLayout;
    ProgressBar progressBar;
    boolean verdict,ftime=true;
    static int count=20;
    static int from=1;
    static boolean correct,onlyc;
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
        sharedPreferences = getSharedPreferences("PREFS", 0);
        onlyc=false;
         username = sharedPreferences.getString("cfxpassword", "null");
         if(username.equals("") || username.equals("null") || username.isEmpty()){
             startActivity(new Intent(this,StartActivity.class));
             finish();
         }
        readdata(username, count,correct,from);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        totalsub=findViewById(R.id.totalsub);
        relativeLayout = findViewById(R.id.relativelayout1);
        relativeLayout12 = findViewById(R.id.lllr);
        onlycorrect=findViewById(R.id.onlycorrect);
        openprofile=findViewById(R.id.openprofile);
        progressBar=findViewById(R.id.progress);
        lottieAnimationView=findViewById(R.id.animation_viewolj);
        recyclerView1 = findViewById(R.id.recycler1);
        textViewrr=findViewById(R.id.djfk);
        textViewrr.animateText("CodeForcesX-Track YourSelf");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                textViewrr.animateText("CodeForcesX-Track YourSelf");
            }
        }, 3000);

        recyclerView1.setHasFixedSize(true);
        Window window = this.getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.white2));
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                RearrangeItems();
            }
        });
        recyclerView1.setLayoutManager(new LinearLayoutManager(this));
        submissionList = new ArrayList<>();
        submissionListcopy = new ArrayList<>();
        submissionAdapter = new SubmissionAdapter(this, submissionList);
        recyclerView1.setAdapter(submissionAdapter);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigation.setItemIconTintList(null);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nsubmission:
                       break;
                    case R.id.problems:
                        Intent intent = new Intent(getApplicationContext(), ProblemSetActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent); finish();
                       break;
                    case R.id.contest:
                        Intent intent2 = new Intent(getApplicationContext(), UpcomingActivity.class);
                        intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent2); finish();
                       break;
                    case R.id.tracker:
                        Intent intent33 = new Intent(getApplicationContext(), TrackerActivity.class);
                        intent33.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent33); finish();
                       break;
                    case R.id.profile:
                        Intent intent3 = new Intent(getApplicationContext(),ProfileSection.class);
                        intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent3); finish();
                }
                return false;
            }
        });

        openprofile.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent i = new Intent(Intent.ACTION_VIEW);
                 i.setData(Uri.parse("https://agomi123.github.io/AGOMI-INDIA.github.io/"));
                 startActivity(i);
             }
         });
        onlycorrect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                correct=!correct;
                onlyc=true;
                if(correct) {
                    onlycorrect.setImageResource(R.drawable.remove);
                    totalsub.setVisibility(View.GONE);
                }
                else {
                    onlycorrect.setImageResource(R.drawable.check);
                    totalsub.setVisibility(View.VISIBLE);
                }
                from=1;
                readdata(username,count,correct,from);
            }
        });
       relativeLayout.setOnTouchListener(new OnSwipeTouchListener(MainActivity.this){
           @Override
           public void onSwipeBottom() {
               super.onSwipeBottom();
              from=submissionList.size();
               readdata(username,count,correct,from);
           }

           @Override
           public void onSwipeLeft() {
               super.onSwipeLeft();
               startActivity(new Intent(MainActivity.this,UpcomingActivity.class));
           }
       });
       relativeLayout12.setOnTouchListener(new OnSwipeTouchListener(MainActivity.this){
           @Override
           public void onSwipeBottom() {
               super.onSwipeBottom();
               from=submissionList.size();
               readdata(username,count,correct,from);
           }
           @Override
           public void onSwipeLeft() {
               super.onSwipeLeft();
               startActivity(new Intent(MainActivity.this,UpcomingActivity.class));
           }
       });
        submissionAdapter.setOnBottomReachedListener(new OnBottomReachedListener() {
            @Override
            public void onBottomReached(int position) {
                onlyc=false;
                from=submissionList.size()+1;
                readdata(username,count,correct,from);
            }
        });
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
             //   Submission deletedCourse = submissionList.get(viewHolder.getAdapterPosition());
                int position = viewHolder.getAdapterPosition();
                String contestid=submissionList.get(position).getContestid();
                String index=submissionList.get(position).getIdx();
                String url = "https://codeforces.com/problemset/problem/"+contestid+"/"+index;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);

            }
        }).attachToRecyclerView(recyclerView1);
    }
    private void readdata(String username ,int page,boolean correct,int from) {
        //int totla=0;
        swipeRefreshLayout.setVisibility(View.VISIBLE);
        lottieAnimationView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        Retrofit retrofit=new Retrofit.Builder().baseUrl("https://codeforces.com/")
                .addConverterFactory(ScalarsConverterFactory.create()).build();
        MainInerface mainInerface=retrofit.create(MainInerface.class);
        retrofit2.Call<String> call=mainInerface.STRING_CALL(from,count,username);
        retrofit2.Call<String> call2=mainInerface.STRING_CALL2(username);
        //Toast.makeText(MainActivity.this,"rmr",Toast.LENGTH_SHORT).show();
        call.enqueue(new retrofit2.Callback<String>() {
            @Override
            public void onResponse(retrofit2.Call<String> call, retrofit2.Response<String> response) {
                if(response.isSuccessful() && response.body()!=null){
                 //   Log.d("pro",response.body().toString());
                 //   Log.d("acti",from+" "+count+" "+username);

                    try {
                        if(onlyc){
                            submissionList.clear();
                            ftime=true;
                        }
                        JSONObject jsonObject=new JSONObject(response.body());
                        JSONArray result=jsonObject.getJSONArray("result");

                            totalsub.setText("Total Submission: " + result.length());
                            ftime=false;
                        for (int i=0;i<result.length();i++){
                            JSONObject js1=result.getJSONObject(i);
                            JSONObject js2=js1.getJSONObject("problem");
                            String dt=js1.getString("creationTimeSeconds");
                            String contstid=js2.getString("contestId");
                            String index=js2.getString("index");
                            String problemname=js2.getString("name");
                            String ver=js1.getString("verdict");
                            if(!ver.equals("OK"))verdict=false;
                            else
                                verdict=true;
                            Submission submission=new Submission(problemname,verdict,getDate(dt),contstid,index);
                            if(correct){
                                if(verdict)
                                    submissionList.add(submissionList.size(),submission);
                            }else
                                submissionList.add(submissionList.size(),submission);
                            //Toast.makeText(MainActivity.this,"rr",Toast.LENGTH_SHORT).show();
                           // Log.d("Tag",problemname);
                        }
                        submissionAdapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    Toast.makeText(MainActivity.this,"Server Error Try Again1",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(retrofit2.Call<String> call, Throwable t) {
            }
        });
        call2.enqueue(new retrofit2.Callback<String>() {
            @Override
            public void onResponse(retrofit2.Call<String> call, retrofit2.Response<String> response) {
                if(response.isSuccessful() && response.body()!=null){
                    try {
                        JSONObject jsonObject=new JSONObject(response.body());
                        JSONArray result=jsonObject.getJSONArray("result");
                            totalsub.setText("Total Submission: " + result.length());
                            ftime=false;

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    swipeRefreshLayout.setVisibility(View.GONE);
                    lottieAnimationView.setVisibility(View.VISIBLE);
                    Toast.makeText(MainActivity.this,"Server Error Try Again",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(retrofit2.Call<String> call, Throwable t) {
                swipeRefreshLayout.setVisibility(View.GONE);
                lottieAnimationView.setVisibility(View.VISIBLE);
                Toast.makeText(MainActivity.this,"Check Whether Username is Wrong!",Toast.LENGTH_LONG).show();
            }
        });
    }
    private  String getDate(String st){
        long unix_seconds = Long.parseLong(st);
        //convert seconds to milliseconds
        Date date = new Date(unix_seconds*1000L);
        // format of the date
        SimpleDateFormat jdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        jdf.setTimeZone(TimeZone.getTimeZone("GMT-4"));
        String java_date = jdf.format(date);
       // System.out.println("\n"+java_date+"\n");
        return java_date;
    }
    public void RearrangeItems() {
        sharedPreferences = getSharedPreferences("PREFS", 0);
        // onlyc=false;
        username = sharedPreferences.getString("cpassword", "null");
        if (username.equals("") || username.equals("null") || username.isEmpty()) {
            startActivity(new Intent(this, StartActivity.class));
            finish();
        }
        from=1;
        readdata(username,count,correct,from);
    }
}