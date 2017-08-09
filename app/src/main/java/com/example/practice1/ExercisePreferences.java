package com.example.practice1;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class ExercisePreferences extends AppCompatActivity {

    public static final String ACTIVITY_PREF = "MyPrefsFile";

    CheckBox cbWalking;
    CheckBox cbRunning;
    CheckBox cbSwimming;
    CheckBox cbGym;
    CheckBox cbBiking;
    CheckBox cbYoga;
    CheckBox cbDancing;
    CheckBox cbBoxing;
    CheckBox cbHiking;
    CheckBox cbBoating;
    CheckBox cbFrisbee;

    TextView t;

    Button bSave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_preferences);
        cbWalking = (CheckBox) findViewById(R.id.checkbox_walking);
        cbRunning = (CheckBox) findViewById(R.id.checkbox_running);
        cbBiking = (CheckBox) findViewById(R.id.checkbox_biking);
        cbGym = (CheckBox) findViewById(R.id.checkbox_gym);
        cbSwimming = (CheckBox) findViewById(R.id.checkbox_swimming);
        cbYoga= (CheckBox) findViewById(R.id.checkbox_yoga);
        cbDancing =(CheckBox) findViewById(R.id.checkbox_dancing);
        cbBoxing = (CheckBox) findViewById(R.id.checkbox_boxing);
        cbHiking = (CheckBox) findViewById(R.id.checkbox_hiking);
        cbBoating = (CheckBox) findViewById(R.id.checkbox_boating);
        cbFrisbee =(CheckBox) findViewById(R.id.checkbox_frisbee);
        bSave = (Button) findViewById(R.id.bSave);

        t=(TextView) findViewById(R.id.tTitle);
        Typeface customFont = Typeface.createFromAsset(getAssets(),"fonts/Sansation-Bold.ttf");
        t.setTypeface(customFont);

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

        if (walking == true)
            cbWalking.setChecked(true);

        else
            cbWalking.setChecked(false);

        if (running == true)
            cbRunning.setChecked(true);

        else
            cbRunning.setChecked(false);

        if (biking == true)
            cbBiking.setChecked(true);

        else
            cbBiking.setChecked(false);

        if (gym == true)
            cbGym.setChecked(true);
        else
            cbGym.setChecked(false);

        if (swimming == true)
            cbSwimming.setChecked(true);
        else
            cbSwimming.setChecked(false);

        if (yoga == true)
            cbYoga.setChecked(true);
        else
            cbYoga.setChecked(false);

        if (dancing == true)
            cbDancing.setChecked(true);
        else
            cbDancing.setChecked(false);

        if (boxing == true)
            cbBoxing.setChecked(true);
        else
            cbBoxing.setChecked(false);

        if (hiking == true)
            cbHiking.setChecked(true);
        else
            cbHiking.setChecked(false);

        if (boating == true)
            cbBoating.setChecked(true);
        else
            cbBoating.setChecked(false);

        if (frisbee == true)
            cbFrisbee.setChecked(true);
        else
            cbFrisbee.setChecked(false);
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();
    }


    public void savePreferences(View v)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ACTIVITY_PREF, MODE_PRIVATE).edit();
        if (cbRunning.isChecked())
            editor.putBoolean("running", true);
        else
            editor.putBoolean("running", false);
        if(cbWalking.isChecked())
            editor.putBoolean("walking", true);
        else
            editor.putBoolean("walking", false);
        if(cbSwimming.isChecked())
            editor.putBoolean("swimming", true);
        else
            editor.putBoolean("swimming", false);
        if(cbGym.isChecked())
            editor.putBoolean("gym", true);
        else
            editor.putBoolean("gym", false);
        if(cbBiking.isChecked())
            editor.putBoolean("biking", true);
        else
            editor.putBoolean("biking", false);

        if(cbYoga.isChecked())
            editor.putBoolean("yoga", true);
        else
            editor.putBoolean("yoga", false);

        if(cbDancing.isChecked())
            editor.putBoolean("dancing", true);
        else
            editor.putBoolean("dancing", false);

        if(cbBoxing.isChecked())
            editor.putBoolean("boxing", true);
        else
            editor.putBoolean("boxing", false);

        if(cbHiking.isChecked())
            editor.putBoolean("hiking", true);
        else
            editor.putBoolean("hiking", false);

        if(cbBoating.isChecked())
            editor.putBoolean("boating", true);
        else
            editor.putBoolean("boating", false);

        if(cbFrisbee.isChecked())
            editor.putBoolean("frisbee", true);
        else
            editor.putBoolean("frisbee", false);

        editor.apply();

        Toast.makeText(this, "Preferences Saved", Toast.LENGTH_SHORT).show();




    }

}
