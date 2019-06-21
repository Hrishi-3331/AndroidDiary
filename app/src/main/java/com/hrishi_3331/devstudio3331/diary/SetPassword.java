package com.hrishi_3331.devstudio3331.diary;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SetPassword extends AppCompatActivity {

    private EditText username;
    private EditText userpin1;
    private EditText userpin2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);

        username = (EditText)findViewById(R.id.username);
        userpin1 = (EditText)findViewById(R.id.userpin);
        userpin2 = (EditText)findViewById(R.id.userpin2);

    }

    public void Submit(View view){
        String name = username.getText().toString();
        String pin1 = userpin1.getText().toString();
        String pin2 = userpin2.getText().toString();

        if (name.isEmpty()){
            Toast.makeText(this, "Please Enter your name", Toast.LENGTH_SHORT).show();
        }
        else if(pin1.isEmpty()){
            Toast.makeText(this, "Please enter a pin", Toast.LENGTH_SHORT).show();
        }
        else if (pin1.length() != 4){
            Toast.makeText(this, "Pin should be 4 digit only", Toast.LENGTH_SHORT).show();
        }
        else if (!pin1.equals(pin2)){
            Toast.makeText(this, "Pin does not match. Please re-enter pin again", Toast.LENGTH_SHORT).show();
        }
        else {
            SaveData(name, pin1);
        }
    }

    private void SaveData(String name, String pin1) {
        SharedPreferences preferences = getSharedPreferences("mDiary", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("Authorized", true);
        editor.putString("Name", name);
        editor.putString("PIN", pin1);
        editor.commit();

        Intent intent = new Intent(SetPassword.this, Home.class);
        startActivity(intent);
        finish();
    }

}
