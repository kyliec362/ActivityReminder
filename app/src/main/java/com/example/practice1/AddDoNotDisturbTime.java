package com.example.practice1;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.example.practice1.R.layout.row;
import static com.example.practice1.R.layout.row2;


public class AddDoNotDisturbTime extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{

    Spinner spinner_hour1;
    Spinner spinner_minute1;
    Spinner spinner_am_pm1;
    Spinner spinner_hour2;
    Spinner spinner_minute2;
    Spinner spinner_am_pm2;
    Spinner spinner_freq;

    Button submitTimeButton;

    EditText eventDescription;

    ArrayAdapter<CharSequence> adapter1;
    ArrayAdapter<CharSequence> adapter2;
    ArrayAdapter<CharSequence> adapter3;
    ArrayAdapter<CharSequence> adapter4;

    int startHour=-1;
    int startMinute=-1;
    String startAM_PM=null;
    int endHour=-1;
    int endMinute=-1;
    String endAM_PM=null;
    String startTime;
    String endTime;
    String freq;

    ListView lv;
    ArrayAdapter aa;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_do_not_disturb_time);

        //***start time dropdown***
        //hours dropdown
        spinner_hour1 = (Spinner) findViewById(R.id.hour_spinner1);
        adapter1 = ArrayAdapter.createFromResource(this, R.array.hours, row2);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_hour1.setAdapter(adapter1);
        spinner_hour1.setOnItemSelectedListener(this);
        //minutes dropdown
        spinner_minute1 = (Spinner) findViewById(R.id.minute_spinner1);
        adapter2 = ArrayAdapter.createFromResource(this, R.array.minutes, row2);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_minute1.setAdapter(adapter2);
        spinner_minute1.setOnItemSelectedListener(this);
        //am vs pm dropdown
        spinner_am_pm1 = (Spinner) findViewById(R.id.am_pm_spinner1);
        adapter3 = ArrayAdapter.createFromResource(this, R.array.am_pm, row2);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_am_pm1.setAdapter(adapter3);
        spinner_am_pm1.setOnItemSelectedListener(this);

        //***end time dropdown***
        //adapters already set up from previous section
        spinner_hour2 = (Spinner) findViewById(R.id.hour_spinner2);
        spinner_hour2.setAdapter(adapter1);
        adapter1 = ArrayAdapter.createFromResource(this, R.array.hours, row2);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_hour2.setOnItemSelectedListener(this);
        //minutes dropdown
        spinner_minute2 = (Spinner) findViewById(R.id.minute_spinner2);
        adapter2 = ArrayAdapter.createFromResource(this, R.array.minutes, row2);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_minute2.setAdapter(adapter2);
        spinner_minute2.setOnItemSelectedListener(this);
        //am vs pm dropdown
        spinner_am_pm2 = (Spinner) findViewById(R.id.am_pm_spinner2);
        adapter3 = ArrayAdapter.createFromResource(this, R.array.am_pm, row2);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_am_pm2.setAdapter(adapter3);
        spinner_am_pm2.setOnItemSelectedListener(this);

        spinner_freq=(Spinner)findViewById(R.id.freq_spinner);
        adapter4 = ArrayAdapter.createFromResource(this, R.array.frequency,row2);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_freq.setAdapter(adapter4);
        spinner_freq.setOnItemSelectedListener(this);

        submitTimeButton = (Button) findViewById(R.id.submitTimeButton);
        eventDescription=(EditText) findViewById(R.id.event_description);

        lv = (ListView) findViewById(R.id.list_view);
        aa = new ArrayAdapter(this,row);
        lv.setAdapter(aa);
        setUpArrayAdapter();


       }





    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int pos, long id) {
        Spinner sp = (Spinner) parent;
        hideSoftKeyboard(this);
        switch (sp.getId()) {
            case R.id.hour_spinner1: {
                startHour = Integer.parseInt(parent.getItemAtPosition(pos).toString());
                break;
            }
            case R.id.minute_spinner1: {
                startMinute = Integer.parseInt(parent.getItemAtPosition(pos).toString());

                break;
            }
            case R.id.am_pm_spinner1: {
                startAM_PM = parent.getItemAtPosition(pos).toString();
                break;
            }
            case R.id.hour_spinner2: {
                endHour = Integer.parseInt(parent.getItemAtPosition(pos).toString());
                break;
            }
            case R.id.minute_spinner2: {
                endMinute = Integer.parseInt(parent.getItemAtPosition(pos).toString());
                break;
            }
            case R.id.am_pm_spinner2: {
                endAM_PM = parent.getItemAtPosition(pos).toString();
                break;
            }
            case R.id.freq_spinner:
            {
                freq=parent.getItemAtPosition(pos).toString();
                break;
            }
        }
    }

    public void clearText(View v)
    {
        eventDescription.setText("");
    }

    public void setStartTime()
    {
        if (startHour == -1 || startMinute == -1 || startAM_PM == null)
        {
            Toast.makeText(getApplicationContext(), "Fill out all fields before submitting", Toast.LENGTH_SHORT).show();
            return;
        }
        if (startMinute==0)
            startTime=(startHour + ":" + "00" + " " + startAM_PM); //to fix formatting issue
        else
            startTime =(startHour + ":" + startMinute + " " + startAM_PM);
    }

    public void setEndTime()
    {
        if (endHour == -1 || endMinute == -1 || endAM_PM == null)
        {
            Toast.makeText(getApplicationContext(), "Fill out all fields before submitting", Toast.LENGTH_SHORT).show();
            return;
        }
        if (endMinute==0)
            endTime=(endHour + ":" + "00" + " " + endAM_PM); //to fix formatting issue
        else
            endTime =(endHour + ":" + endMinute + " " + endAM_PM);
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }


    public void submitData(View v)
    /* *******************************************************************************
    need to add the code to send the dates and event description to another activity!
    ********************************************************************************* */

    {
        hideSoftKeyboard(this);
        setStartTime();
        setEndTime();
        String st=startTime;
        String et=endTime;
        if (eventDescription.getText().toString().length() < 1 || (eventDescription.getText().toString()).equals("Enter Description")) {
            Toast.makeText(getApplicationContext(), "Enter event description", Toast.LENGTH_SHORT).show();
            return;
        }

        if(st==null || et==null)
        {
            Toast.makeText(getApplicationContext(), "You must enter a start and end time before submitting.", Toast.LENGTH_SHORT).show();
            return;
        }


        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date dateobj = new Date();
        String dt = df.format(dateobj);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.US);
        Date startTime = new Date();
        Date endTime = new Date();
        try {
            startTime = sdf.parse(dt + " " + st);

            endTime=sdf.parse(dt + " " + et);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (startTime.getTime() > endTime.getTime() || startTime.getTime() == endTime.getTime())
        {
            Toast.makeText(getApplicationContext(),"End time should be after the start time", Toast.LENGTH_SHORT).show();
            return;
        }
        else
        {
/*
            Toast.makeText(getApplicationContext(),"Start time: " + st, Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(),"End time: " + et, Toast.LENGTH_SHORT).show();
            */
        }
        aa.add(eventDescription.getText());

        addEvent(eventDescription.getText().toString(),startTime,endTime,freq);

       eventDescription.setText("");


    }

    public void setUpArrayAdapter()
    {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Cursor cursor = getContentResolver().query(CalendarContract.Events.CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext()) {
            if (cursor != null) {
                int id_1= cursor.getColumnIndex(CalendarContract.Events._ID);
                int id_2 = cursor.getColumnIndex(CalendarContract.Events.TITLE);
                int id_3 = cursor.getColumnIndex(CalendarContract.Events.DESCRIPTION);

                String titleValue = cursor.getString(id_2);
                String descriptionValue = cursor.getString(id_3);

                if (descriptionValue==null) //prevents app from crashing
                    continue;

                if (descriptionValue.compareTo("Do Not Disturb Time")==0) {
                    aa.add(titleValue);
                }

            }
            else {
                Toast.makeText(this, "No Do Not Disturb Times Set", Toast.LENGTH_SHORT).show();
            }
        }

    }


    public void clearTimes(View v)
    {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            return;
        }



        Cursor cursor = getContentResolver().query(CalendarContract.Events.CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext()) {
            if (cursor != null) {
                int id_1=cursor.getColumnIndex(CalendarContract.Events._ID);
                int id_3 = cursor.getColumnIndex(CalendarContract.Events.DESCRIPTION);

                String ID_Val = cursor.getString(id_1);
                long event_ID = Long.parseLong(ID_Val);
                String descriptionValue = cursor.getString(id_3);

                if (descriptionValue==null) //prevents app from crashing
                    continue;

                if (descriptionValue.compareTo("Do Not Disturb Time")==0)
                {
                    Uri uri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI,event_ID);
                    this.getContentResolver().delete(uri, null, null);
                }

            }
            else
                Toast.makeText(this, "No Do Not Disturb Times To Clear", Toast.LENGTH_SHORT).show();


        }

        aa.clear(); //clear list view
        aa.notifyDataSetChanged();
    }



    public void addEvent(String title, Date start, Date end, String freq)
    {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) { return; } //permission check

        freq=getFreq(freq);

        ContentResolver cr = this.getContentResolver();
        ContentValues cv = new ContentValues();
        cv.put(CalendarContract.Events.TITLE,title);
        cv.put(CalendarContract.Events.DESCRIPTION, "Do Not Disturb Time");
        cv.put(CalendarContract.Events.EVENT_LOCATION, "");
        cv.put(CalendarContract.Events.DTSTART, start.getTime());
        cv.put(CalendarContract.Events.DTEND, end.getTime());
        cv.put(CalendarContract.Events.CALENDAR_ID,1);
        cv.put(CalendarContract.Events.RRULE,freq);
        cv.put(CalendarContract.Events.EVENT_TIMEZONE,Calendar.getInstance().getTimeZone().getID());
        Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI,cv);


        Toast.makeText(getApplicationContext(),"Event added", Toast.LENGTH_SHORT).show();


    }



    //returns string format for recurring rule for calendar event
    public String getFreq(String f)
    {
        if (f.compareTo("ONCE")==0)
            f="";
        if (f.compareTo("DAILY")==0)
            f="FREQ=DAILY";
        if(f.compareTo("WEEKLY")==0)
            f="FREQ=WEEKLY";
        if(f.compareTo("MONTHLY")==0)
            f="FREQ=MONTHLY";
        return f;
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0)
    {hideSoftKeyboard(this);}


}