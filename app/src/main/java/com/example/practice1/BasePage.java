package com.example.practice1;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class BasePage extends AppCompatActivity {

    TextView t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_page);
        t=(TextView) findViewById(R.id.tTitle);
        Typeface customFont = Typeface.createFromAsset(getAssets(),"fonts/Sansation-Bold.ttf");
        t.setTypeface(customFont);
    }

    public void goToProgress(View view){
        Intent intent = new Intent(this,Progress.class);
        startActivity(intent);
    }

    public void goToConfiguration(View view)
    {

        Intent intent = new Intent(this,Preferences.class);
        startActivity(intent);

    }


    public void goToPlace(View view)
    {
        Intent intent = new Intent(this,MapsActivity.class);
        startActivity(intent);

    }
    public void goToWeather(View view){
        Intent intent = new Intent(this,weather.class);
        startActivity(intent);
    }

    public void goToFriends(View v)
    {
        Intent i = new Intent(this,Friends.class);
        startActivity(i);
    }


    public void goToWorkoutScheduler(View v)
    {
        Intent i = new Intent(this,WorkoutSelection.class);
        startActivity(i);

    }




}
