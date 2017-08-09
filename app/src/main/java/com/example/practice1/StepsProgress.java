package com.example.practice1;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;


public class StepsProgress extends AppCompatActivity implements SensorEventListener{

    ProgressBar prg;
    private SensorManager sensorManager;
    private int count;
    private TextView showCount;
    boolean activityRunning;
    private double desiredSteps=100;
    private double progressFraction = count/desiredSteps;
    private double progressBarPercentage = progressFraction*100;
    TextView t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps_progress);
        prg=(ProgressBar) findViewById(R.id.progressBar);
        t=(TextView) findViewById(R.id.tTitle);
        Typeface customFont = Typeface.createFromAsset(getAssets(),"fonts/Sansation-Bold.ttf");
        t.setTypeface(customFont);


        count=0;
        showCount = (TextView) findViewById(R.id.counterValue);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

    }

    @Override
    protected void onResume() {
        super.onResume();
        activityRunning = true;
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        if(countSensor != null) {
            sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);
        } else {
            Toast.makeText(this, "Count sensor not available!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        activityRunning = false;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(activityRunning) {
            count++;
            showCount.setText(Integer.toString(count));
            prg.setProgress((int) progressBarPercentage);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void resetCount(View view){
        count=0;
        showCount.setText(String.valueOf(count));
    }

    public void setSteps (View view){
        EditText editText = (EditText) findViewById(R.id.editText);
        desiredSteps = Double.parseDouble(editText.getText().toString());
        prg.setProgress((int) progressBarPercentage);

        Context context = getApplication();
        CharSequence setSteps = "Desired steps have been set";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, setSteps, duration);
        toast.show();
    }

}
