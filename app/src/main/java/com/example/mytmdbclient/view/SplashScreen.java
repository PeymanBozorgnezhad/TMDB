package com.example.mytmdbclient.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mytmdbclient.R;

public class SplashScreen extends AppCompatActivity
{

    //Variables

    private Animation topAnim, bottomAnim;

    // private ImageView imgSplash;
    private TextView txtName;
    private TextView txtDescription;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        FindViews();


        //imgSplash.setAnimation(topAnim);
        txtName.setAnimation(topAnim);

        txtName.animate().setDuration(2000).setListener(new AnimatorListenerAdapter()
        {
            @Override
            public void onAnimationEnd(Animator animation)
            {
                super.onAnimationEnd(animation);

                progressBar.setVisibility(View.VISIBLE);
                txtDescription.setVisibility(View.VISIBLE);
                txtDescription.setAnimation(bottomAnim);
            }
        }).start();


        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                Intent intent = new Intent(SplashScreen.this, RecyclerViewActivity.class);
                startActivity(intent);
                finish();
            }
        }, 5000);

    }

    private void FindViews()
    {
        //TODO : ImageView
        //imgSplash = (ImageView) findViewById(R.id.imageView);
        //TODO : TextView
        txtName = (TextView) findViewById(R.id.txtName);
        txtDescription = (TextView) findViewById(R.id.txtDescription);
        //TODO : ProgressBar
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }
}
