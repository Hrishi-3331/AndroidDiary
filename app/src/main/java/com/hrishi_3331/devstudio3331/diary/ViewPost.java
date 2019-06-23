package com.hrishi_3331.devstudio3331.diary;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ViewPost extends AppCompatActivity {

    private String filename;
    private String date;
    private String title;
    private DatabaseHelper helper;
    private TextView post_content;
    private TextView post_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_post);

        post_content = (TextView) findViewById(R.id.post_content);
        post_title = (TextView) findViewById(R.id.post_title);
        TextView post_date = (TextView) findViewById(R.id.post_date);
        TextView post_day = (TextView) findViewById(R.id.post_day);
        TextView post_month = (TextView) findViewById(R.id.post_month);
        TextView post_time = (TextView) findViewById(R.id.post_time);

        Intent intent = getIntent();
        filename = intent.getStringExtra("filename");
        date = intent.getStringExtra("date");
        title = intent.getStringExtra("title");

        post_title.setText(title);

        try {
            Date date = new SimpleDateFormat("dd/MM/yyyy").parse(this.date);
            String Date = new SimpleDateFormat("dd").format(date);
            post_date.setText(Date);
            String day = new SimpleDateFormat("EEEE").format(date);
            post_day.setText(day);
            String month = new SimpleDateFormat("MMMM").format(date);
            post_month.setText(month);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            FileInputStream inputStream = openFileInput(filename);
            InputStreamReader reader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(reader);
            StringBuilder builder = new StringBuilder();

            String line = "";

            while ((line = bufferedReader.readLine()) != null){
                builder.append(line).append("\n");
            }

            String content = builder.toString();
            post_content.setText(content);
        } catch (Exception e) {
            e.printStackTrace();
        }

        helper = new DatabaseHelper(ViewPost.this);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public void back(View view){
        finish();
    }

    public void Delete(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(ViewPost.this);
        builder.setTitle("Delete Post")
                .setMessage("Do you want to delete this post?")
                .setCancelable(true)
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        helper.deleteEvent(filename, date);
                        finish();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void Share(View view){
        String shareBody =  getResources().getText(R.string.textmark) + "\n\n" + post_content.getText().toString();
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, post_title.getText().toString());
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    public void Update(View view){
        Intent intent = new Intent(ViewPost.this, New_Event.class);
        intent.putExtra("Title", title)
                .putExtra("Date", date)
                .putExtra("Filename", filename)
                .putExtra("update", true);
        startActivity(intent);
        finish();
    }
}
