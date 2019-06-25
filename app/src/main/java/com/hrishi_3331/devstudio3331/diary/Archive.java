package com.hrishi_3331.devstudio3331.diary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Archive extends AppCompatActivity {

    private TextView year;
    private String archive_year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archive);

        year = (TextView)findViewById(R.id.year);
        archive_year = year.getText().toString();
    }

    public void Back(View view){
        finish();
    }

    public void IncrementYear(View view){
        int temp = Integer.valueOf(year.getText().toString());
        if (temp < 2050){
            temp++;
            year.setText(temp + "");
        }
        else {
            Toast.makeText(this, "App valid till 2050 only !", Toast.LENGTH_SHORT).show();
        }
    }

    public void DecrementYear(View view){
        int temp = Integer.valueOf(year.getText().toString());
        if (temp > 2019){
            temp--;
            year.setText(temp + "");
        }
        else {
            Toast.makeText(this, "App launched in 2019!", Toast.LENGTH_SHORT).show();
        }
    }

    public void ViewArchive(View view){
        String parse = view.getContentDescription().toString() + " " + year.getText().toString();
        Intent intent = new Intent(Archive.this, Archive_View.class);
        intent.putExtra("parse", parse);
        startActivity(intent);
    }
}
