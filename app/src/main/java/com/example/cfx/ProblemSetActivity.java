package com.example.cfx;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.cfx.Adapter.ProblemAdapter;
import com.example.cfx.Model.Problem;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ProblemSetActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    ProblemAdapter contestAdapter;
    RecyclerView recyclerView2;
    boolean doubleBackToExitPressedOnce = false;
    RelativeLayout relativeLayout;
    static int c = 0;
    ProgressBar progressBar;
    SwipeRefreshLayout swipeRefreshLayout;
    List<Problem> arraylist;
    static String tag = "implementation";

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

    String[] courses = {"implementation", "dfs and similar", "math", "greedy", "combinatorics",
            "dp", "sortings", "bitmasks",
            "brute force",
            "data structures","graphs",
            "divide and conquer", "two pointers", "constructive algorithms", "strings", "number theory", "binary search", "hashing"};

    @Override
    protected void onStart() {
        super.onStart();
        readdata23(tag, c);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem_set);
        recyclerView2 = findViewById(R.id.recyclerproblem);
        relativeLayout = findViewById(R.id.r1);
        recyclerView2.setHasFixedSize(true);
        progressBar=findViewById(R.id.spin_kit);
        Sprite doubleBounce = new DoubleBounce();
        progressBar.setIndeterminateDrawable(doubleBounce);
        Window window = this.getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.white2));
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));
        arraylist = new ArrayList<>();
        contestAdapter = new ProblemAdapter(ProblemSetActivity.this, arraylist,progressBar);
        recyclerView2.setAdapter(contestAdapter);
        Spinner spin = findViewById(R.id.coursesspinner);
        spin.setOnItemSelectedListener(this);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                RearrangeItems();
            }
        });
        contestAdapter.setOnBottomReachedListener(new OnBottomReachedListener() {
            @Override
            public void onBottomReached(int position) {
                c = arraylist.size() + 1;
                readdata23(tag, c);
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
                String contestid = arraylist.get(position).getCi();
                String index = arraylist.get(position).getIndex();
                String url = "https://codeforces.com/problemset/problem/" + contestid + "/" + index;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);

            }
        }).attachToRecyclerView(recyclerView2);
        ArrayAdapter ad = new ArrayAdapter(this, R.layout.spinner_item, courses);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(ad);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigation.setItemIconTintList(null);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.problems:
                        break;
                    case R.id.nsubmission:
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.contest:
                        Intent intent2 = new Intent(getApplicationContext(), UpcomingActivity.class);
                        intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent2);
                        finish();
                        break;
                    case R.id.tracker:
                        Intent intent33 = new Intent(getApplicationContext(), TrackerActivity.class);
                        intent33.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent33); finish();
                        break;
                    case R.id.profile:
                        Intent intent3 = new Intent(getApplicationContext(), ProfileSection.class);
                        intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent3);
                        finish();
                }
                return false;
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        c = 0;
        arraylist.clear();
        tag = courses[position];
        readdata23(tag, c);
    }

    private void readdata23(String tag, int c) {
        progressBar.setVisibility(View.VISIBLE);
      //  Toast.makeText(this, tag + "pl", Toast.LENGTH_SHORT).show();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://codeforces.com/")
                .addConverterFactory(ScalarsConverterFactory.create()).build();
        MainInerface mainInerface = retrofit.create(MainInerface.class);
        retrofit2.Call<String> call = mainInerface.STRING_CALL6(tag);
        call.enqueue(new retrofit2.Callback<String>() {
            @Override
            public void onResponse(@NonNull retrofit2.Call<String> call, @NonNull retrofit2.Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body());
                        JSONObject result2 = jsonObject.getJSONObject("result");
                        JSONArray result = result2.getJSONArray("problems");
                        for (int i = c; i < c + 20; i++) {
                            JSONObject js = result.getJSONObject(i);
                            String b = js.getString("index");
                            String a = js.getString("name");
                            String c = js.getString("rating");
                            JSONArray ja = js.getJSONArray("tags");
                            boolean test = false;
                            for (int j = 0; j < ja.length(); j++) {
                                if (ja.get(j).equals(tag)) {
                                    test = true;
                                }
                            }
                            if (test) {
                                Problem problem = new Problem(a, b, ja.toString(), c, js.getString("contestId"));
                                arraylist.add(arraylist.size(), problem);
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
  //  progressBar.setVisibility(View.GONE);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void RearrangeItems() {
        c = 0;
        readdata23(tag, c);
    }
}