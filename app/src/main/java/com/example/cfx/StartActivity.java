package com.example.cfx;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.example.cfx.Model.User;

public class StartActivity extends AppCompatActivity {
EditText username;
Button apply;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        username=findViewById(R.id.header);
        apply=findViewById(R.id.apply);
        Window window = this.getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.white2));
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("PREFS", 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("cfxpassword", username.getText().toString().trim());
                editor.apply();
                Intent it =new Intent(StartActivity.this,MainActivity.class);
                startActivity(it);
                finish();
            }
        });
    }
}