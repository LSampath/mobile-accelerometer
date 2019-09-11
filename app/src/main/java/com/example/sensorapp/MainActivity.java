package com.example.sensorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private boolean enabled = false;

    private TextView xValueTv;
    private TextView yValueTv;
    private TextView zValueTv;
    private Button actionBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        xValueTv = findViewById(R.id.tv_x_value);
        yValueTv = findViewById(R.id.tv_y_value);
        zValueTv = findViewById(R.id.tv_z_value);
        actionBtn = findViewById(R.id.btn_action);

        setPausedText();

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        actionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enabled = !enabled;

                if (enabled) {
                    sensorManager.registerListener(MainActivity.this,
                            sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                            sensorManager.SENSOR_DELAY_NORMAL
                    );
                    actionBtn.setText("STOP");
                } else {
                    sensorManager.unregisterListener(MainActivity.this);
                    setPausedText();
                    actionBtn.setText("START");
                }
            }
        });
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            xValueTv.setText(String.valueOf(x));
            yValueTv.setText(String.valueOf(y));
            zValueTv.setText(String.valueOf(z));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) { }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) { }

    private void setPausedText() {
        xValueTv.setText("UNAVAILABLE");
        yValueTv.setText("UNAVAILABLE");
        zValueTv.setText("UNAVAILABLE");
    }
}
