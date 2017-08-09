package com.example.practice1;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class Preferences extends AppCompatActivity {

    Button bDoNotDisturbTimes;
    Button bActivityPreferences;
    Button bPlacePicker;
    TextView t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);
      //  bWorkLocation = (Button) findViewById(R.id.button_workLocation);
        bDoNotDisturbTimes = (Button) findViewById(R.id.button_doNotDisturb);
        bActivityPreferences = (Button) findViewById(R.id.button_activityPreferences);
        bPlacePicker = (Button) findViewById(R.id.button5);
        t=(TextView) findViewById(R.id.tTitle);
        Typeface customFont = Typeface.createFromAsset(getAssets(),"fonts/Sansation-Bold.ttf");
        t.setTypeface(customFont);

    }


    public void updateDoNotDisturbTimes(View v)
    {
        Intent intent = new Intent(this,AddDoNotDisturbTime.class);
        startActivity(intent);

    }

    public void updateActivityPreferences(View v)
    {
        Intent intent = new Intent(this,ExercisePreferences.class);
        startActivity(intent);
    }

    public void goToAccount(View v){
        Intent intent = new Intent(this,Account1.class);
        startActivity(intent);
    }

}
