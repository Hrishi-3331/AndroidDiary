package com.hrishi_3331.devstudio3331.diary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class New_Event extends AppCompatActivity {

    private EditText title;
    private EditText content;
    private TextView day_date, month_date, day_of_week_date, time;

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
        java.util.Date date = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        SimpleDateFormat day_date = new SimpleDateFormat("dd", Locale.getDefault());
        SimpleDateFormat time_date = new SimpleDateFormat("h:mm a", Locale.getDefault());
        SimpleDateFormat month_date = new SimpleDateFormat("MMMM", Locale.getDefault());
        SimpleDateFormat year_date = new SimpleDateFormat("yyyy", Locale.getDefault());
        SimpleDateFormat day_of_week = new SimpleDateFormat("EEEE", Locale.getDefault());
        String mDate = format.format(date);

        this.day_date.setText(day_date.format(date));
        this.month_date.setText(month_date.format(date) + " " + year_date.format(date));
        this.day_of_week_date.setText(day_of_week.format(date));
        this.time.setText(time_date.format(date));
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
            String FileName = Event_Title + ".txt";
            try {
                FileOutputStream outputStream = openFileOutput(FileName, MODE_PRIVATE);
                outputStream.write(Event_Content.getBytes());
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
