package com.namaltechnologysolutions.bunny.bookbank.Activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.CubeGrid;
import com.namaltechnologysolutions.bunny.bookbank.R;

public class Splash_Activity extends AppCompatActivity {
   // private int SPLASH_TIME_OUT=800;
   private int SPLASH_TIME_OUT=2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ProgressBar progressBar = findViewById(R.id.splashScreenProgressBar);
        Sprite CubeGrid = new CubeGrid();
        CubeGrid.setColor(R.color.colorAccent);
        progressBar.setIndeterminateDrawable(CubeGrid);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // To move user from this activity to Login Or Register Activity
                Intent moveToLoginRegisterActivity=new Intent(Splash_Activity.this,Login_Register_Activity.class);
                startActivity(moveToLoginRegisterActivity);
                finish();
            }
        },SPLASH_TIME_OUT);
    }
}
