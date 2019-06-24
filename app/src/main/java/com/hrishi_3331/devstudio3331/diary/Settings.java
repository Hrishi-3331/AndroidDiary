package com.hrishi_3331.devstudio3331.diary;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

public class Settings extends AppCompatActivity {

    private boolean notifications;
    private boolean sounds;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Switch notifications_switch = (Switch)findViewById(R.id.switch1);
        Switch sound_switch = (Switch)findViewById(R.id.switch2);

        preferences = getSharedPreferences("mDiary", MODE_PRIVATE);
        notifications = preferences.getBoolean("notifications", false);
        sounds = preferences.getBoolean("sounds", false);
        notifications_switch.setChecked(notifications);
        sound_switch.setChecked(sounds);

        notifications_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("notifications", true);
                    editor.commit();
                }
                else {
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("notifications", false);
                    editor.commit();
                }
            }
        });

        sound_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("sounds", true);
                    editor.commit();
                }
                else {
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("sounds", false);
                    editor.commit();
                }
            }
        });
    }

    public void back(View view){
        finish();
    }

    public void Premium(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(Settings.this);
        LayoutInflater inflater = getLayoutInflater();
        View DialogLayout = inflater.inflate(R.layout.premium_dialog, null);
        builder.setView(DialogLayout);

        Button ok = (Button) DialogLayout.findViewById(R.id.ok_btn);
        Button cancel = (Button) DialogLayout.findViewById(R.id.cancel_btn);

        final AlertDialog premium_dialog = builder.create();

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                premium_dialog.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                premium_dialog.dismiss();
            }
        });

        premium_dialog.show();
    }

    public void PrivacyPolicy(View view){
        Intent intent = new Intent(Settings.this, Privacy_Policy.class);
        startActivity(intent);
    }
}
