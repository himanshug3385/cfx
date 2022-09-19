package com.example.cfx;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.bumptech.glide.Glide;
import com.example.cfx.Adapter.ContestAdapter;
import com.example.cfx.Adapter.FriendAdapter;
import com.example.cfx.Adapter.PieAdapter;
import com.example.cfx.Model.Contest;
import com.example.cfx.Model.Problem;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ProfileSection extends AppCompatActivity {

//    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    //FriendAdapter friendadapter;
//    LinearLayoutManager HorizontalLayout;
//    View ChildView;
//    int RecyclerViewItemPosition;
    CircleImageView imagepr;
    SwipeRefreshLayout swipeRefreshLayout;
    SharedPreferences sharedPreferences;
    String username;
    ImageView openclose;
    CardView cardView;
    TextView handle, rankstr, ratingstr, friend,name;
    ContestAdapter contestAdapter;
    RecyclerView recyclerView2, recyclerViewpie,recyclerViewonline;
    PieChart pieChart;
    ArrayList<Contest> arraylist = new ArrayList<>();
   // ArrayList<Contest> source = new ArrayList<>();
    ArrayList<Problem> arraylist2 = new ArrayList<>();
    PieAdapter pieAdapter;
    RelativeLayout relativeLayout;
    ArrayList<String> arrp;
    AlertDialog dialog;
    boolean doubleBackToExitPressedOnce = false;
    private int CurrentProgress = 0;
    private ProgressBar progressBar;
    int [] color={Color.parseColor("#ff1744"), Color.parseColor("#e040fb"), Color.parseColor("#651fff"), Color.parseColor("#40c4ff"), Color.parseColor("#00e676"), Color.parseColor("#ffff00"), Color.parseColor("#ff9100"), Color.parseColor("#ff3d00"), Color.parseColor("#455a64"), Color.parseColor("#ff4000"), Color.parseColor("#ff8000"), Color.parseColor("#ffbf00"), Color.parseColor("#ffff00"), Color.parseColor("#bfff00"), Color.parseColor("#80ff00"), Color.parseColor("#40ff00"), Color.parseColor("#00ff00"), Color.parseColor("#00ff40"), Color.parseColor("#00ff80"), Color.parseColor("#00ffbf"), Color.parseColor("#00ffff"), Color.parseColor("#00bfff"), Color.parseColor("#0080ff"),};
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
        progressBar.setMax(100);
        while (CurrentProgress < 100) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressBar.setProgress(CurrentProgress);
                }
            }, 1200);
            CurrentProgress += 30;
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
                cardView.setVisibility(View.VISIBLE);
               // cardView2.setVisibility(View.VISIBLE);
                swipeRefreshLayout.setVisibility(View.VISIBLE);
            }
        }, 2000);
        sharedPreferences = getSharedPreferences("PREFS", 0);
        // onlyc=false;
        username = sharedPreferences.getString("cfxpassword", "null");
        if (username.equals("") || username.equals("null") || username.isEmpty()) {
            startActivity(new Intent(this, StartActivity.class));
            finish();
        }
        readdata(username);
        readdata23(username);
        readdata222(username);
        //getonlineuser();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_section);
        imagepr = findViewById(R.id.imgsmall);
        name = findViewById(R.id.namestr1);
        rankstr = findViewById(R.id.rankstr1);
        openclose=findViewById(R.id.openclose);
        handle = findViewById(R.id.locationstr1);
        ratingstr = findViewById(R.id.ratingstr1);
        relativeLayout = findViewById(R.id.r3);
        friend = findViewById(R.id.friendstr1);
        recyclerView2 = findViewById(R.id.recycler111);
        progressBar = findViewById(R.id.progressBar);
        //recyclerViewonline =findViewById(R.id.recyclerviewonline);
        Window window = this.getWindow();
        openclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(swipeRefreshLayout.getVisibility()==View.VISIBLE){
                    openclose.setImageResource(R.drawable.opener);
                    swipeRefreshLayout.setVisibility(View.GONE);
                }
                else{
                    openclose.setImageResource(R.drawable.daew);
                    swipeRefreshLayout.setVisibility(View.VISIBLE);
                }
            }
        });
      ///  cardView2=findViewById(R.id.piescroll);
        pieChart = findViewById(R.id.piechart);
        recyclerViewpie = findViewById(R.id.picrecycler);

        pieAdapter = new PieAdapter(ProfileSection.this, arraylist2);
        recyclerViewpie.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewpie.setAdapter(pieAdapter);

//        friendadapter = new FriendAdapter(ProfileSection.this, source);
//        recyclerViewonline.setLayoutManager(new LinearLayoutManager(ProfileSection.this, LinearLayoutManager.HORIZONTAL, false));
//        recyclerViewonline.setAdapter(friendadapter);

        window.setStatusBarColor(getResources().getColor(R.color.white2));
        cardView = findViewById(R.id.cardw);
        contestAdapter = new ContestAdapter(ProfileSection.this, arraylist);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));
        recyclerView2.setAdapter(contestAdapter);
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
                    case R.id.profile:
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
                        startActivity(intent33); finish();
                        break;
                    case R.id.nsubmission:
                        Intent intent3 = new Intent(getApplicationContext(), MainActivity.class);
                        intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent3);
                        finish();
                }
                return false;
            }
        });

    }
    private void readdata23(String username) {
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
                        //Log.d("pluh",response.body().toString());
                        JSONArray result = jsonObject.getJSONArray("result");
                        for (int i = 0; i < result.length(); i++) {
                            JSONObject js = result.getJSONObject(i);
                            int id=js.getInt("contestId");
                            String a = js.getString("contestName");
                            String b = js.getString("oldRating");
                            String c = js.getString("newRating");
                            String d = js.getString("rank");
                            Contest contest = new Contest(id,a, b, c, d);
                            arraylist.add(0, contest);
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
    public void RearrangeItems() {
        sharedPreferences = getSharedPreferences("PREFS", 0);
        // onlyc=false;
        username = sharedPreferences.getString("cfxpassword", "null");
        if (username.equals("") || username.equals("null") || username.isEmpty()) {
            startActivity(new Intent(this, StartActivity.class));
            finish();
        }
        readdata23(username);
    }
    private void readdata(String username) {
        // progressBar.setVisibility(View.VISIBLE);
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://codeforces.com/")
                .addConverterFactory(ScalarsConverterFactory.create()).build();
        MainInerface mainInerface = retrofit.create(MainInerface.class);
        // retrofit2.Call<String> call=mainInerface.STRING_CALL(from,page,username);
        retrofit2.Call<String> call = mainInerface.STRING_CALL3(username);
        //Toast.makeText(MainActivity.this,"rmr",Toast.LENGTH_SHORT).show();
        call.enqueue(new retrofit2.Callback<String>() {
            @Override
            public void onResponse(retrofit2.Call<String> call, retrofit2.Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {

                        JSONObject jsonObject = new JSONObject(response.body());
                        JSONArray result = jsonObject.getJSONArray("result");
                        JSONObject obj = result.getJSONObject(0);
                        String url = obj.getString("titlePhoto");
                        if (url.isEmpty()) {
                            imagepr.setImageResource(R.drawable.man);
                        } else
                            Glide.with(ProfileSection.this).load(url).into(imagepr);
                      if(obj.has("firstName") && obj.has("lastName")) {
                          String fn = obj.getString("firstName");
                          String ln = obj.getString("lastName");
                          name.setText(fn + " " + ln);
                      }
                      else{
                          name.setText("Unknown User");
                      }
                     String handlestr= obj.getString("handle").isEmpty()?"---":obj.getString("handle");
                     String ranks= obj.getString("rank").isEmpty()?"---":obj.getString("rank");
                     String ratings= obj.getString("rating").isEmpty()?"---":obj.getString("rating");
                     String friends= obj.getString("friendOfCount").isEmpty()?"---":obj.getString("friendOfCount");
                        handle.setText(handlestr);
                        rankstr.setText(ranks);
                        ratingstr.setText(ratings);
                        friend.setText(friends);

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
    ArrayList<String> generate() {
        int min = 1;
        int max = 100;
        System.out.println("Random value of type int between " + min + " to " + max + ":");
        int b = (int) (Math.random() * (max - min + 1) + min);
        int c = (int) (Math.random() * (max - min + 1) + min);
        ArrayList<String> arr = new ArrayList<>();
        arr.add("Enter Value of \n" + b + "+" + c + "?");
        arr.add(String.valueOf(b + c));
        return arr;

    }
    public void logout(View view) {
        arrp = generate();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sure Want to Logout ?");
        //  Toast.makeText(UpcomingActivity.this, arrp.get(0), Toast.LENGTH_SHORT).show();
        //   builder.setTitle(arrp.get(0));
        builder.setMessage(arrp.get(0));
        final View customLayout = getLayoutInflater().inflate(R.layout.custom_layout, null);
        builder.setView(customLayout);
        builder.setIcon(R.drawable.logo);
        // arrp=generate();
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(
                    DialogInterface dialog,
                    int which) {
                EditText editText = customLayout.findViewById(R.id.editText);
                // editText.setText(arrp.get(0));
                sendDialogDataToActivity(editText.getText().toString());
            }
        });
        dialog = builder.create();
        dialog.show();

    }
    private void sendDialogDataToActivity(String data) {
        String b = arrp.get(1);
        //Toast.makeText(UpcomingActivity.this, data+" "+b, Toast.LENGTH_SHORT).show();
        if (b.equals(data)) {
            SharedPreferences sharedPreferences = getSharedPreferences("PREFS", 0);
            sharedPreferences.edit().remove("cpassword").commit();
            startActivity(new Intent(ProfileSection.this, StartActivity.class));
            finish();
        } else {
            arrp = generate();
            dialog.cancel();
        }
    }
    private void readdata222(String username) {
        HashMap<String, Integer> mp = new HashMap<>();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://codeforces.com/")
                .addConverterFactory(ScalarsConverterFactory.create()).build();
        MainInerface mainInerface = retrofit.create(MainInerface.class);
        retrofit2.Call<String> call = mainInerface.STRING_CALL2(username);
        call.enqueue(new retrofit2.Callback<String>() {
            @Override
            public void onResponse(retrofit2.Call<String> call, retrofit2.Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        arraylist2.clear();
                        JSONObject jsonObject = new JSONObject(response.body());
                        JSONArray result = jsonObject.getJSONArray("result");
                        long total = 0;
                        for (int i = 0; i < result.length(); i++) {
                            JSONArray tags = result.getJSONObject(i).getJSONObject("problem").getJSONArray("tags");
                            for (int j = 0; j < tags.length(); j++) {
                                //Log.d("op",tags.getString(j));
                                if (mp.containsKey(tags.getString(j))) {
                                    mp.put(tags.getString(j), mp.get(tags.getString(j)) + 1);
                                } else {
                                    mp.put(tags.getString(j), 1);
                                }
                            }
                        }
                        int k = 0;
                        for (Map.Entry entry : mp.entrySet()) {
                            total += Integer.parseInt(entry.getValue().toString());
                            arraylist2.add(new Problem(entry.getKey().toString(),entry.getValue().toString()));
                            k++;
                            if(k==23)k=0;
                        }
                       k=0;
                        for (Map.Entry entry : mp.entrySet()) {
                            int c = Integer.parseInt(entry.getValue().toString());
                            float x=(float)c/total*100;
                            pieChart.addPieSlice(new PieModel(entry.getKey().toString(), x, color[k]));
                            k++;
                            if(k==23)k=0;
                        }
                        pieChart.startAnimation();
                        pieAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    // progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

    }
//    private  void getonlineuser(){
//        long unixTime = System.currentTimeMillis() / 1000L;;
//        String s1 = hash512("123456/user.friends?apiKey=0384f3b840aa97c9a69e7c577c5c5bd7f36eae12&time="+unixTime+"#191921391119cb6bf23d8fdfc658774f097ffc11");
//        Log.d("friend",unixTime+"\n"+s1+"\n");
//        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://codeforces.com/").addConverterFactory(ScalarsConverterFactory.create()).build();
//        MainInerface mainInerface = retrofit.create(MainInerface.class);
//        retrofit2.Call<String> call = mainInerface.STRING_CALL7(String.valueOf(unixTime),"123456"+s1);
//        call.enqueue(new retrofit2.Callback<String>() {
//            @Override
//            public void onResponse(retrofit2.Call<String> call, retrofit2.Response<String> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    try {
//                        JSONObject jsonObject = new JSONObject(response.body());
//                        JSONArray result = jsonObject.getJSONArray("result");
//                         Log.d("friend",result+"\n");
//                        for (int i=0;i<result.length();i++){
//                            source.add(new Contest(result.get(i).toString()));
//                        }
//                        Log.d("friend",source.toString());
//                        friendadapter.notifyDataSetChanged();
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(retrofit2.Call<String> call, Throwable t) {
//            }
//        });
//    }
//    String hash512(String input){
//        try {
//            // getInstance() method is called with algorithm SHA-512
//            MessageDigest md = MessageDigest.getInstance("SHA-512");
//
//            byte[] messageDigest = md.digest(input.getBytes());
//            // Convert byte array into signum representation
//            BigInteger no = new BigInteger(1, messageDigest);
//
//            // Convert message digest into hex value
//            String hashtext = no.toString(16);
//
//            // Add preceding 0s to make it 32 bit
//            while (hashtext.length() < 32) {
//                hashtext = "0" + hashtext;
//            }
//
//            // return the HashText
//            return hashtext;
//        }
//
//        // For specifying wrong message digest algorithms
//        catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException(e);
//        }
//    }
}