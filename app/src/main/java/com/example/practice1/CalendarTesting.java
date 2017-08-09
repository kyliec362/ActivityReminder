package com.example.practice1;


import android.Manifest;
import android.app.NotificationManager;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import android.os.Handler;

public class CalendarTesting extends AppCompatActivity{

   Button button3;
    Cursor cursor;
    Button button4;
    Button addingEventButton;
    EditText eventDescription;


    public final String[] INSTANCE_PROJECTION = new String[] {
            CalendarContract.Instances.EVENT_ID,      // 0
            CalendarContract.Instances.BEGIN,         // 1
            CalendarContract.Instances.TITLE          // 2
    };


    private static final int PROJECTION_ID_INDEX = 0;
    private static final int PROJECTION_BEGIN_INDEX = 1;
    private static final int PROJECTION_TITLE_INDEX = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_testing);

        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        addingEventButton = (Button) findViewById(R.id.addEventButton);
        testTimer();
        eventDescription=(EditText) findViewById(R.id.event_description);
    }


    public void testTimer(){
        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @SuppressWarnings("unchecked")
                    public void run() {
                        try {
                            timerFifteen();
                        }
                        catch (Exception e) {
                            // TODO Auto-generated catch block
                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0, 900000);
    }


    public final void timerFifteen(){
        long currentTimeInMillis2 = System.currentTimeMillis()+1800000;

        Uri.Builder eventsUriBuilder2 = CalendarContract.Instances.CONTENT_URI
                .buildUpon();
        ContentUris.appendId(eventsUriBuilder2, currentTimeInMillis2);
        ContentUris.appendId(eventsUriBuilder2, currentTimeInMillis2+7200000);
        Uri eventsUri2 = eventsUriBuilder2.build();
        Cursor cursor2;
        cursor2 = getContentResolver().query(eventsUri2, INSTANCE_PROJECTION, null, null, CalendarContract.Instances.DTSTART + " ASC");
        if(cursor2.moveToNext()) {
            //do nothing (user is not free)
            Toast.makeText(this, "Not free", Toast.LENGTH_SHORT).show();
        } else {
            NotificationCompat.Builder mBuilder= new NotificationCompat.Builder(this);
            mBuilder.setSmallIcon(R.drawable.freenotification);
            mBuilder.setContentTitle("You are free");
            mBuilder.setContentText("You are free for the next two hours, go to the gym!");
            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(0, mBuilder.build());
            Toast.makeText(this, "You are free for the next two hours", Toast.LENGTH_SHORT).show();
        }
    }

    public void addEvents(View v)
    {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED)
        {
            return;
        }
        ContentResolver cr = this.getContentResolver();
        ContentValues cv = new ContentValues();
        cv.put(CalendarContract.Events.TITLE, "Event Form Java code");
        cv.put(CalendarContract.Events.DESCRIPTION, "Event Form Java code");
        cv.put(CalendarContract.Events.EVENT_LOCATION, "Event Form Java code");
        cv.put(CalendarContract.Events.DTSTART, Calendar.getInstance().getTimeInMillis());
        cv.put(CalendarContract.Events.DTEND, Calendar.getInstance().getTimeInMillis() + 60 * 60 * 1000); //event length is an hour
        cv.put(CalendarContract.Events.CALENDAR_ID, 1);
        cv.put(CalendarContract.Events.EVENT_TIMEZONE, Calendar.getInstance().getTimeZone().getID());
        Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, cv);

        Toast.makeText(this, "Event is successfully Added.", Toast.LENGTH_SHORT).show();

        eventDescription.setText(""); //clear text in text box


    }

    public void showDailyEvents(View v) {

        long currentTimeInMillis = System.currentTimeMillis();

        Uri.Builder eventsUriBuilder = CalendarContract.Instances.CONTENT_URI
                .buildUpon();
        ContentUris.appendId(eventsUriBuilder, currentTimeInMillis);
        ContentUris.appendId(eventsUriBuilder, currentTimeInMillis + 86400000);
        Uri eventsUri = eventsUriBuilder.build();
        Cursor cursor;
        cursor = getContentResolver().query(eventsUri, INSTANCE_PROJECTION, null, null, CalendarContract.Instances.DTSTART + " ASC");
        while (cursor.moveToNext()) {
            String title;
            long eventID = 0;
            long beginVal = 0;

            if (cursor != null) {
                eventID = cursor.getLong(PROJECTION_ID_INDEX);
                beginVal = cursor.getLong(PROJECTION_BEGIN_INDEX);
                title = cursor.getString(PROJECTION_TITLE_INDEX);

                Date beginDate = new Date(currentTimeInMillis);
                String pattern = "MM-dd-hh.mm";
                SimpleDateFormat formattedDate = new SimpleDateFormat(pattern);
                String stringDate = formattedDate.format(beginDate);
                System.out.println(formattedDate);

                Toast.makeText(this, "Title: " + title + ", Time start: " + stringDate, Toast.LENGTH_SHORT).show();

            } else if (cursor.getCount() < 1) {
                Toast.makeText(this, "No events today", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void areYouFree(View v) {

        long currentTimeInMillis2 = System.currentTimeMillis() + 1800000;

        Uri.Builder eventsUriBuilder2 = CalendarContract.Instances.CONTENT_URI
                .buildUpon();
        ContentUris.appendId(eventsUriBuilder2, currentTimeInMillis2);
        ContentUris.appendId(eventsUriBuilder2, currentTimeInMillis2 + 7200000);
        Uri eventsUri2 = eventsUriBuilder2.build();
        Cursor cursor2;
        cursor2 = getContentResolver().query(eventsUri2, INSTANCE_PROJECTION, null, null, CalendarContract.Instances.DTSTART + " ASC");
        if (cursor2.moveToNext()) {
            //do nothing (user is not free)
            Toast.makeText(this, "Not free", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "You are free", Toast.LENGTH_SHORT).show();
        }
    }
}


