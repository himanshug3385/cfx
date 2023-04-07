package com.example.cfx;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.example.cfx.Model.Contest;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class TrackerActivity extends AppCompatActivity {
    BarChart barChart;
    ArrayList<Contest> arraylist = new ArrayList<>();
    // variable for our bar data.
    BarData barData;
    LottieAnimationView lottieAnimationView;
  // SwipeRefreshLayout swipeRefreshLayout;
    SharedPreferences sharedPreferences;
    static String username;
    boolean doubleBackToExitPressedOnce = false;
    RelativeLayout relativeLayout;
    BarDataSet barDataSet;
    ArrayList barEntriesArrayList;

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
        username = sharedPreferences.getString("cfxpassword", "null");
        if (username.equals("") || username.equals("null") || username.isEmpty()) {
            startActivity(new Intent(this, StartActivity.class));
            finish();
        }
        Log.d("adarsh", username);
        readdata23(username);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracker);
        relativeLayout = findViewById(R.id.qpq);
        barChart = findViewById(R.id.idBarChart);
        lottieAnimationView=findViewById(R.id.animation_view7);
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
                        startActivity(intent33);
                        finish();
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
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                int ref = arraylist.get(0).getId();
                int n = arraylist.size();
                for (int i = 1; i < n; i++) {
                    barEntriesArrayList.add(new BarEntry(i, arraylist.get(i).getRk()));
                }
                barChart.setVisibility(View.VISIBLE);
                lottieAnimationView.setVisibility(View.GONE);
                lottieAnimationView.cancelAnimation();
                barDataSet = new BarDataSet(barEntriesArrayList, "CFX Rating Change");
                barData = new BarData(barDataSet);
                barChart.setData(barData);
                barChart.setVisibleXRangeMaximum(10f);
                barChart.moveViewToX(1);
                barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                barDataSet.setValueTextColor(Color.BLACK);
                barDataSet.setValueTextSize(16f);
                barChart.getDescription().setEnabled(false);
               barChart.animateXY(2000,2000);
            }
        }, 3000);

    }

    private void readdata23(String username) {
        barEntriesArrayList = new ArrayList<>();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://codeforces.com/")
                .addConverterFactory(ScalarsConverterFactory.create()).build();
        MainInerface mainInerface = retrofit.create(MainInerface.class);
        retrofit2.Call<String> call = mainInerface.STRING_CALL4(username);
        call.enqueue(new retrofit2.Callback<String>() {
            @Override
            public void onResponse(retrofit2.Call<String> call, retrofit2.Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        arraylist.clear();
                        JSONObject jsonObject = new JSONObject(response.body());

                        JSONArray result = jsonObject.getJSONArray("result");
                        Log.d("pluh", result.length() + "");
                        for (int i = 0; i < result.length(); i++) {
                            JSONObject js = result.getJSONObject(i);
                            int a = js.getInt("contestId");
                            int d = js.getInt("newRating");
                            Contest contest = new Contest(a, d);
                            arraylist.add(contest);
                        }
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
    }
