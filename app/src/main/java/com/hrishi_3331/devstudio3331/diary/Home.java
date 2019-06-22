package com.hrishi_3331.devstudio3331.diary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void NewPost(View view){
        Intent intent = new Intent(Home.this, New_Event.class);
        startActivity(intent);
    }
}
