package com.hrishi_3331.devstudio3331.diary;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Lock extends AppCompatActivity {

    private ImageView digit1, digit2, digit3, digit4;
    private String PIN;
    private String PIN_ENTERED;
    private int num;
    private TextView notice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);

        digit1 = (ImageView)findViewById(R.id.char_1);
        digit2 = (ImageView)findViewById(R.id.char_2);
        digit3 = (ImageView)findViewById(R.id.char_3);
        digit4 = (ImageView)findViewById(R.id.char_4);
        notice = (TextView)findViewById(R.id.notice);

        SharedPreferences preferences = getSharedPreferences("mDiary", MODE_PRIVATE);
        PIN = preferences.getString("PIN", "1234");
        num = 1;
        PIN_ENTERED = "";
    }

    public void ButtonPressed(View view){

        String digit = view.getContentDescription().toString();

        switch (num){
            case 1:
                notice.setVisibility(View.INVISIBLE);
                PIN_ENTERED = PIN_ENTERED + digit;
                digit1.setImageResource(R.drawable.icons8_filled_circle);
                num++;
                break;

            case 2:
                PIN_ENTERED = PIN_ENTERED + digit;
                digit2.setImageResource(R.drawable.icons8_filled_circle);
                num++;
                break;

            case 3:
                PIN_ENTERED = PIN_ENTERED + digit;
                digit3.setImageResource(R.drawable.icons8_filled_circle);
                num++;
                break;

            case 4:
                PIN_ENTERED = PIN_ENTERED + digit;
                digit4.setImageResource(R.drawable.icons8_filled_circle);
                if (PIN_ENTERED.equals(PIN)){
                    Intent intent = new Intent(Lock.this, Home.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    PIN_ENTERED = "";
                    num = 1;
                    digit1.setImageResource(R.drawable.icons8_circle_filled);
                    digit2.setImageResource(R.drawable.icons8_circle_filled);
                    digit3.setImageResource(R.drawable.icons8_circle_filled);
                    digit4.setImageResource(R.drawable.icons8_circle_filled);
                    notice.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    public void onClear(View view){
        switch (num){
            case 1:
                break;

            case 2:
                PIN_ENTERED = PIN_ENTERED.substring(0,PIN_ENTERED.length() - 1);
                num--;
                digit1.setImageResource(R.drawable.icons8_circle_filled);
                break;

            case 3:
                PIN_ENTERED = PIN_ENTERED.substring(0,PIN_ENTERED.length() - 1);
                num--;
                digit2.setImageResource(R.drawable.icons8_circle_filled);
                break;

            case 4:
                PIN_ENTERED = PIN_ENTERED.substring(0,PIN_ENTERED.length() - 1);
                num--;
                digit3.setImageResource(R.drawable.icons8_circle_filled);
                break;

        }
    }
}
