package com.example.batur.step_counter;

import android.app.Activity;
import android.content.Context;
import android.hardware.*;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Locale;
import static android.widget.Toast.LENGTH_LONG;

public class MainActivity extends Activity implements SensorEventListener {

    SensorManager sensorManager;
    TextView cSteps, adder;
    TextToSpeech t1;
    Button button, restartButton;
    boolean activityRunning;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //initialize
        cSteps = (TextView) findViewById(R.id.cSteps);
        adder = (TextView) findViewById(R.id.adder);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        button=(Button)findViewById(R.id.button);
        restartButton=(Button)findViewById(R.id.restartButton);
        int cStep = Integer.valueOf(cSteps.getText().toString());

        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status!=TextToSpeech.ERROR)
                {
                    t1.setLanguage(Locale.US);
                }
            }
        });
        button.setOnClickListener(new View.OnClickListener() {

                public void onClick(View view)
                {
                    String toSpeak=Integer.parseInt(cSteps.getText().toString())+adder.getText().toString();
                    Toast.makeText(getApplicationContext(),toSpeak, Toast.LENGTH_SHORT).show();
                    t1.speak(toSpeak,TextToSpeech.QUEUE_FLUSH,null);
                }
        });
        restartButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view)
            {

                cSteps.setText("0");

            }
        });




    }

    @Override
    protected void onResume() {
        super.onResume();
        activityRunning = true;
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (countSensor != null) {
            sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);
        } else {
            Toast.makeText(this, "Count sensor not available or your phone doesn't support!", LENGTH_LONG).show();
        }

    }

    @Override
    protected void onPause() {
       /* if(t1!=null)
        {
            t1.stop();
            t1.shutdown();                                                  //look here
        }*/
        super.onPause();
        activityRunning = false;

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (activityRunning) {
            cSteps.setText(String.valueOf(event.values[0]);        //değişiklik burada
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}