package com.hrishi_3331.devstudio3331.diary;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class Home extends AppCompatActivity {

    private DatabaseHelper helper;
    private ArrayList<Event> Events;
    private RecyclerView EventsView;
    private LinearLayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Events  = new ArrayList<Event>();
        helper = new DatabaseHelper(Home.this);
        EventsView = (RecyclerView)findViewById(R.id.events_view);
        manager = new LinearLayoutManager(Home.this, LinearLayoutManager.VERTICAL, true);
        manager.setStackFromEnd(true);
        manager.setSmoothScrollbarEnabled(true);
        EventsView.setLayoutManager(manager);
        EventsView.setNestedScrollingEnabled(false);
        EventsView.hasFixedSize();

        TextView name = (TextView)findViewById(R.id.user_name);
        SharedPreferences preferences = getSharedPreferences("mDiary", MODE_PRIVATE);
        name.setText(preferences.getString("Name", "User"));
    }

    @Override
    protected void onStart() {
        Events.clear();
        getEvents();
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
                Event event = Events.get(i);
                eventsViewHolder.setView(Home.this, event.getDate().substring(0, 2), event.getTitle(), event.getFilename());
            }

            @Override
            public int getItemCount() {
                return Events.size();
            }
        };

        EventsView.setAdapter(adapter);
    }

    public static class EventsViewHolder extends RecyclerView.ViewHolder{

        private View mView;
        private TextView date, month, title, content;
        private String filename;

        public EventsViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            date = mView.findViewById(R.id.date_day);
            month = mView.findViewById(R.id.date_month);
            title = mView.findViewById(R.id.event_title);
            content = mView.findViewById(R.id.event_content);
        }

        public void setView(Context context, String date, String title, String filename){
            this.filename = filename;
            this.date.setText(date);
            try {
                Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(date);
                String month1 = new SimpleDateFormat("MMMM").format(date1);
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
                    builder.append(line);
                }
                String content = builder.toString();
                this.content.setText(content);
            } catch (Exception e) {
               e.printStackTrace();
            }
        }
    }

    public void getEvents(){
        Cursor data = helper.getEvents();

        while (data.moveToNext()){
            Event event = new Event(Home.this, data.getString(1), data.getString(2), data.getString(3));
            Events.add(event);
        }
    }

    public void NewPost(View view){
        Intent intent = new Intent(Home.this, New_Event.class);
        startActivity(intent);
    }
}
