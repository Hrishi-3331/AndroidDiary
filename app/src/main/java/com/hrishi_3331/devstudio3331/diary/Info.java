package com.hrishi_3331.devstudio3331.diary;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Info extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
    }

    public void RateApp(View view){
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.hrishi_3331.devstudio3331.diary")));
    }
}
