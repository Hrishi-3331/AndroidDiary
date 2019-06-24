package com.hrishi_3331.devstudio3331.diary;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout anim = (LinearLayout)findViewById(R.id.anim);
        anim.animate().scaleX(20).setDuration(2000).start();

        Handler handler = new Handler();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                SharedPreferences preferences = getSharedPreferences("mDiary", MODE_PRIVATE);
                Boolean temp = preferences.getBoolean("Authorized", false);
                Intent intent;
                if (temp){
                    intent = new Intent(MainActivity.this, Lock.class);
                }
                else {
                    intent = new Intent(MainActivity.this, SetPassword.class);
                }
                startActivity(intent);
                finish();
            }
        };

        handler.postDelayed(runnable, 1500);
    }
}
