package com.hrishi_3331.devstudio3331.diary;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class New_Event extends AppCompatActivity {

    private EditText title;
    private EditText content;
    private TextView day_date, month_date, day_of_week_date, time;
    private DatabaseHelper mDatabaseHelper;
    private String mDate;
    private String time_;
    private boolean update;
    private String filename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new__event);

        title = (EditText)findViewById(R.id.new_event_title);
        content = (EditText)findViewById(R.id.new_event_content);
        day_date = (TextView)findViewById(R.id.new_post_date);
        month_date = (TextView)findViewById(R.id.new_post_month);
        day_of_week_date = (TextView)findViewById(R.id.new_post_day);
        time = (TextView)findViewById(R.id.new_post_time);


        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        java.util.Date date;
        Intent intent = getIntent();
        update = intent.getBooleanExtra("update", false);

        if (update){
            String title = intent.getStringExtra("Title");
            String Date = intent.getStringExtra("Date");
            filename = intent.getStringExtra("Filename");

            this.title.setText(title);
            try {
                FileInputStream inputStream = openFileInput(filename);
                InputStreamReader reader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(reader);
                StringBuilder builder = new StringBuilder();

                String line ="";

                while ((line = bufferedReader.readLine()) != null){
                    builder.append(line).append("\n");
                }

                this.content.setText(builder.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        date = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        SimpleDateFormat day_date = new SimpleDateFormat("dd", Locale.getDefault());
        SimpleDateFormat time_date = new SimpleDateFormat("h:mm a", Locale.getDefault());
        SimpleDateFormat month_date = new SimpleDateFormat("MMMM", Locale.getDefault());
        SimpleDateFormat year_date = new SimpleDateFormat("yyyy", Locale.getDefault());
        SimpleDateFormat day_of_week = new SimpleDateFormat("EEEE", Locale.getDefault());
        mDate = format.format(date);
        time_ = new SimpleDateFormat("h:mm:ss").format(date);

        this.day_date.setText(day_date.format(date));
        this.month_date.setText(month_date.format(date) + " " + year_date.format(date));
        this.day_of_week_date.setText(day_of_week.format(date));
        this.time.setText(time_date.format(date));

        mDatabaseHelper = new DatabaseHelper(New_Event.this);
    }


    public String generateFilename(String title, String date, String time){
        if (update){
            return filename;
        }
        else {
            String[] temps = date.split("/");
            String[] temps2 = time.split(":");
            String file = title + temps[0] + temps[1] + temps[2] + temps2[0] + temps2[1] + temps2[2];
            return file + ".txt";
        }
    }

    public void SaveFile(View view){
        String Event_Title = title.getText().toString();
        String Event_Content = content.getText().toString();

        if(Event_Title.isEmpty()){
            Toast.makeText(this, "Please Enter the title", Toast.LENGTH_SHORT).show();
        }
        else if(Event_Content.isEmpty()) {
            Toast.makeText(this, "No content written to save", Toast.LENGTH_SHORT).show();
        }
        else {
            String FileName = generateFilename(Event_Title, mDate, time_);
            try {
                FileOutputStream outputStream = openFileOutput(FileName, MODE_PRIVATE);
                OutputStreamWriter writer = new OutputStreamWriter(outputStream);
                writer.write(Event_Content);
                writer.close();
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }

            if (!update) {
                boolean res = mDatabaseHelper.AddData(Event_Title, FileName, mDate);

                if (!res) {
                    Toast.makeText(this, "Error in saving file! Please try again", Toast.LENGTH_SHORT).show();
                } else {
                    finish();
                }
            }
            else {
                finish();
            }
        }

    }

    @Override
    public void onBackPressed() {
        goBack();
    }

    public void back(View view){
        goBack();
    }

    public void goBack(){
        AlertDialog.Builder builder = new AlertDialog.Builder(New_Event.this);
        builder.setMessage("Do you want to quit without saving?")
                .setTitle("Are you Sure?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setCancelable(true);

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
