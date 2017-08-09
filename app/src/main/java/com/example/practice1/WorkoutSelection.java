package com.example.practice1;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.Manifest;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.widget.AdapterView.OnItemClickListener;


import static com.example.practice1.ExercisePreferences.ACTIVITY_PREF;
import static com.example.practice1.R.layout.row;

public class WorkoutSelection extends AppCompatActivity {


    FirebaseDatabase database;
    DatabaseReference myRef;
    private FirebaseAuth mAuth;

    ListView lv; //activities
    ListView lv2; //times
    ArrayAdapter aa;
    ArrayAdapter aa1; //activities
    ArrayAdapter aa2; //times

    TextView t;
    TextView nf;

    String sActivity;
    long startTime;

    public final String[] INSTANCE_PROJECTION = new String[] {
            CalendarContract.Instances.EVENT_ID,      // 0
            CalendarContract.Instances.BEGIN,         // 1
            CalendarContract.Instances.TITLE          // 2
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_selection);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        mAuth = FirebaseAuth.getInstance();

        t=(TextView) findViewById(R.id.tTitle);
        nf=(TextView)findViewById(R.id.nf);
        Typeface customFont = Typeface.createFromAsset(getAssets(),"fonts/Sansation-Bold.ttf");
        t.setTypeface(customFont);

        display();

        lv = (ListView) findViewById(R.id.list_view);
        lv2=(ListView) findViewById(R.id.list_view2);
        aa = new ArrayAdapter(this,row);
        aa1= new ArrayAdapter(this,row);
        aa2= new ArrayAdapter(this,row);
        lv.setAdapter(aa1);
        lv2.setAdapter(aa2);

        SharedPreferences sPref = getSharedPreferences(ACTIVITY_PREF, MODE_PRIVATE);
        boolean walking= sPref.getBoolean("walking", false);
        boolean running= sPref.getBoolean("running", false);
        boolean biking= sPref.getBoolean("biking", false);
        boolean gym= sPref.getBoolean("gym", false);
        boolean swimming= sPref.getBoolean("swimming", false);
        boolean yoga = sPref.getBoolean("yoga", false);
        boolean dancing = sPref.getBoolean("dancing", false);
        boolean boxing = sPref.getBoolean("boxing", false);
        boolean hiking = sPref.getBoolean("hiking",false);
        boolean boating = sPref.getBoolean("boating", false);
        boolean frisbee = sPref.getBoolean("frisbee", false);

        //fill list of recommended activities
        if (walking)
            aa1.add("walking");
        if (running)
            aa1.add("running");
        if (biking)
            aa1.add("biking");
        if (gym)
            aa1.add("gym");
        if (swimming)
            aa1.add("swimming");
        if (yoga)
            aa1.add("yoga");
        if (dancing)
            aa1.add("dancing");
        if(boxing)
            aa1.add("boxing");
        if(hiking)
            aa1.add("hiking");
        if(boating)
            aa1.add("boating");
        if(frisbee)
            aa1.add("frisbee");

        lv.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                sActivity = ((TextView) view).getText().toString();
               // int color = Color.parseColor("#00FFFF");
               // ((TextView) view).setTextColor(color);
            }
        });

        lv2.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {


                DateFormat format = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
                String t = ((TextView) view).getText().toString();
                try {
                    startTime = format.parse(t).getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        displayFreeTimes();

    }

    public void displayFreeTimes()
    {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        areYouFree();


    }


    public void areYouFree() {

        long fifteenMin = 900000; //millis
        // long currentTimeInMillis2 = System.currentTimeMillis() + fifteenMin;
        Calendar rightNow = Calendar.getInstance();
        Calendar endOfDay = Calendar.getInstance();
        int year = rightNow.get(Calendar.YEAR);
        int day = rightNow.get(Calendar.DAY_OF_MONTH);
        int month = rightNow.get(Calendar.MONTH)+1;
        endOfDay.set(year,month,day,11,59);
        long eod= System.currentTimeMillis() + fifteenMin + 3600000*8; //8 hours into future


        String pattern = "hh:mm a";
        Cursor cursor2;

        for(long currentTimeInMillis2 = System.currentTimeMillis() + fifteenMin;
            eod-currentTimeInMillis2 >=0 ;
            currentTimeInMillis2 = currentTimeInMillis2 + fifteenMin*2)
        {

            Uri.Builder eventsUriBuilder2 = CalendarContract.Instances.CONTENT_URI.buildUpon();
            ContentUris.appendId(eventsUriBuilder2, currentTimeInMillis2);
            ContentUris.appendId(eventsUriBuilder2, currentTimeInMillis2 + 3600000); //1 hour
            Uri eventsUri2 = eventsUriBuilder2.build();
            cursor2 = getContentResolver().query(eventsUri2, INSTANCE_PROJECTION, null, null, CalendarContract.Instances.DTSTART + " ASC");
            if (!cursor2.moveToNext())
            {
                Date beginDate = new Date(currentTimeInMillis2);
                SimpleDateFormat formattedDate = new SimpleDateFormat(pattern);
                String stringDate = formattedDate.format(beginDate);
                aa2.add(stringDate);

            }
        }


    }

    public void display()
    {

        // Read from the database
        myRef.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    User user = snapshot.getValue(User.class);
                    String UID = user.getUserID();
                    if (UID == null)
                    {
                        return;
                    }
                    if (UID.compareTo(mAuth.getCurrentUser().getUid())==0) //current user should not be displayed
                        continue;

                    if (user.getAvailability()) {
                        aa.add(user);



                    }
                }
                String s = "Available Friends: " + aa.getCount() + "";
                nf.setText(s);


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(WorkoutSelection.this, "Failed to read number of free friends", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showAll(View v) //display all activities regardless of personal preference
    {
        aa1.clear();
        aa1.add("walking");
        aa1.add("running");
        aa1.add("biking");
        aa1.add("gym");
        aa1.add("swimming");
        aa1.add("yoga");
        aa1.add("dancing");
        aa1.add("boxing");
        aa1.add("hiking");
        aa1.add("boating");
        aa1.add("frisbee");

    }

    public void schedule(View v)
    {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) { return; } //permission check


        Calendar rightNow = Calendar.getInstance();
        Calendar startOfDay = Calendar.getInstance();
        int year = rightNow.get(Calendar.YEAR);
        int day = rightNow.get(Calendar.DAY_OF_MONTH);
        int month = rightNow.get(Calendar.MONTH);
        startOfDay.set(year,month,day,0,00);
        long st = startOfDay.getTimeInMillis();


        ContentResolver cr = this.getContentResolver();
        ContentValues cv = new ContentValues();
        cv.put(CalendarContract.Events.TITLE,sActivity);
        cv.put(CalendarContract.Events.DTSTART,st+startTime);
        cv.put(CalendarContract.Events.DTEND, st+startTime + 3600000); //1 hour later
        cv.put(CalendarContract.Events.CALENDAR_ID,1);
        cv.put(CalendarContract.Events.EVENT_TIMEZONE,Calendar.getInstance().getTimeZone().getID());
        Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI,cv);

        Toast.makeText(getApplicationContext(),"Workout scheduled", Toast.LENGTH_SHORT).show();


    }

}
