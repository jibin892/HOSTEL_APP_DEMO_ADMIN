package in.techsays.hostel.Splash;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import in.techsays.hostel.Login_and_Registration.Login;
import in.techsays.hostel.R;


public class Splash_home extends AppCompatActivity {
    private FrameLayout frameLayout;

    private Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(getColor(android.R.color.white));
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(Color.BLACK);
            }
        }
        setContentView(R.layout.activity_splash_home);
        frameLayout = findViewById(R.id.frame19);

        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.item_animation_go_up);
        animation.setStartTime(3000);
        animation.setDuration(3500);
        animation.setRepeatCount(3);
        frameLayout.startAnimation(animation);
         if(animation.hasEnded()){
            finish();
        }

         new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                // This method will be executed once the timer is over
                Intent i = new Intent(Splash_home.this, Login.class);
                startActivity(i);
                finish();
            }
        }, 1000);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        animation.cancel();
    }
}
