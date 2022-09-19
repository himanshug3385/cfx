package com.example.cfx;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.widget.ProgressBar;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.github.ybq.android.spinkit.style.Pulse;
import com.github.ybq.android.spinkit.style.Wave;
import com.hanks.htextview.typer.TyperTextView;

public class SplashScreen extends AppCompatActivity {
ProgressBar progressBar;
//TyperTextView typerTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
      //  progressBar = (ProgressBar)findViewById(R.id.spin_kit);
        Window window = this.getWindow();
        //typerTextView=findViewById(R.id.ddd);
        //typerTextView.animate();
        //typerTextView.animateText("Hello world");
        window.setStatusBarColor(getResources().getColor(R.color.white2));
       // Sprite doubleBounce= new Wave();
//        progressBar.setIndeterminateDrawable(doubleBounce);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               startActivity(new Intent(SplashScreen.this, MainActivity.class));
                    finish();
            }
        }, 4000);
    }
    private boolean isDarkModeOn() {
        int x= getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        return x == Configuration.UI_MODE_NIGHT_YES;
    }
}