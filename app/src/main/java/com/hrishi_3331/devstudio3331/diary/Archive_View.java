package com.hrishi_3331.devstudio3331.diary;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Archive_View extends AppCompatActivity {

    private String parse;
    private RecyclerView events_view;
    private LinearLayoutManager manager;
    private ArrayList<Event> eventArrayList;
    private DatabaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archive__view);

        Intent intent = getIntent();
        parse = intent.getStringExtra("parse");

        TextView header = (TextView)findViewById(R.id.header);
        header.setText(parse);

        events_view = (RecyclerView)findViewById(R.id.archive_events);
        manager = new LinearLayoutManager(Archive_View.this, LinearLayoutManager.VERTICAL, true);
        manager.setSmoothScrollbarEnabled(true);
        manager.setStackFromEnd(true);
        events_view.setLayoutManager(manager);

        helper = new DatabaseHelper(Archive_View.this);
        eventArrayList = new ArrayList<>();
    }

    @Override
    protected void onStart() {
        eventArrayList.clear();
        FilterEvents();
        super.onStart();
        RecyclerView.Adapter<EventsViewHolder> adapter = new RecyclerView.Adapter<EventsViewHolder>() {
            @NonNull
            @Override
            public EventsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.event_card, viewGroup, false);
                return new EventsViewHolder(view);
            }

            @Override
            public void onBindViewHolder(@NonNull EventsViewHolder eventsViewHolder, int i) {
                Event event = eventArrayList.get(i);
                eventsViewHolder.setView(Archive_View.this, event.getDate(), event.getTitle(), event.getFilename(), event.getTime());
                eventsViewHolder.setLisner();
            }

            @Override
            public int getItemCount() {
                return eventArrayList.size();
            }
        };

        events_view.setAdapter(adapter);
    }

    public static class EventsViewHolder extends RecyclerView.ViewHolder{

        private View mView;
        private TextView date, month, title, content;
        private String filename;
        private String Date;
        private String Title;
        private String Time;
        private Context context;

        public EventsViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            date = mView.findViewById(R.id.date_day);
            month = mView.findViewById(R.id.date_month);
            title = mView.findViewById(R.id.event_title);
            content = mView.findViewById(R.id.event_content);
        }

        public void setView(Context context, String date, String title, String filename, String time){
            this.Time = time;
            this.filename = filename;
            this.Date = date;
            this.Title = title;
            this.context = context;

            try {
                Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(date);
                String month1 = new SimpleDateFormat("MMMM").format(date1);
                String temp = new SimpleDateFormat("dd").format(date1);
                this.date.setText(temp);
                this.month.setText(month1);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            this.title.setText(title);
            try {
                FileInputStream inputStream = context.openFileInput(filename);
                InputStreamReader reader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(reader);
                StringBuilder builder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null){
                    builder.append(line).append("\n");
                }
                String content = builder.toString();
                this.content.setText(content);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void setLisner(){
            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ViewPost.class);
                    intent.putExtra("filename", filename);
                    intent.putExtra("date", Date);
                    intent.putExtra("time", Time);
                    intent.putExtra("title", Title);
                    context.startActivity(intent);
                }
            });
        }
    }

    private void FilterEvents() {
        Cursor data = helper.getEvents();

        while (data.moveToNext()){
            Event event = new Event(Archive_View.this, data.getString(1), data.getString(2), data.getString(3), data.getString(4));
            try {
                Date date = new SimpleDateFormat("dd/MM/yyyy").parse(event.getDate());
                String month = new SimpleDateFormat("MMMM").format(date);
                String year = new SimpleDateFormat("yyyy").format(date);
                String filter = month + " " + year;

                if (parse.equals(filter)){
                    eventArrayList.add(event);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public void Back(View view){
        finish();
    }

}
