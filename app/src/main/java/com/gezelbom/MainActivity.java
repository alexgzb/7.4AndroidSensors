package com.gezelbom;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity implements SensorEventListener {

    SensorManager sensorManagerIlluminance;
    SensorManager sensorManagerMagnetic;
    SensorManager sensorManagerAccelerometer;
    SensorManager sensorManagerTemperature;

    Sensor illuminanceSensor;
    Sensor magneticSensor;
    Sensor accelerometerSensor;
    Sensor temperatureSensor;

    TextView illuminanceValueTextView;
    TextView magneticValueTextView;
    TextView accelerometerValueTextViewX;
    TextView accelerometerValueTextViewY;
    TextView accelerometerValueTextViewZ;
    TextView temperatureValueTextView;

    @SuppressWarnings("unused")
    final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Sensor Managers
        sensorManagerIlluminance = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorManagerMagnetic = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorManagerAccelerometer = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorManagerTemperature = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        //Sensors
        illuminanceSensor = sensorManagerIlluminance.getDefaultSensor(Sensor.TYPE_LIGHT);
        magneticSensor = sensorManagerMagnetic.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        accelerometerSensor = sensorManagerMagnetic.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        temperatureSensor = sensorManagerMagnetic.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);

        //TexViews
        magneticValueTextView = (TextView) findViewById(R.id.textView_magnetic_value);
        accelerometerValueTextViewX = (TextView) findViewById(R.id.textView_acc_valueX);
        accelerometerValueTextViewY = (TextView) findViewById(R.id.textView_acc_valueY);
        accelerometerValueTextViewZ = (TextView) findViewById(R.id.textView_acc_valueZ);
        illuminanceValueTextView = (TextView) findViewById(R.id.textView_illuminance_value);
        temperatureValueTextView = (TextView) findViewById(R.id.textView_temperature_value);

    }

    @Override
    protected void onResume() {
        super.onResume();
        // Register a listener for the sensor
        sensorManagerIlluminance.registerListener(this, illuminanceSensor, SensorManager.SENSOR_DELAY_FASTEST);
        sensorManagerMagnetic.registerListener(this, magneticSensor, SensorManager.SENSOR_DELAY_FASTEST);
        sensorManagerAccelerometer.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_FASTEST);
        sensorManagerTemperature.registerListener(this, temperatureSensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Unregister all the listeners
        sensorManagerIlluminance.unregisterListener(this);
        sensorManagerMagnetic.unregisterListener(this);
        sensorManagerAccelerometer.unregisterListener(this);
        sensorManagerTemperature.unregisterListener(this);
    }

    /**
     * Called from system when sensor has changed
     *
     * @param event that triggered the change
     */
    @SuppressLint("SetTextI18n")
    @Override
    public void onSensorChanged(SensorEvent event) {

        int eventType = event.sensor.getType();
//        Log.d(TAG, event.sensor.getStringType() + " " + Integer.toString(eventType));

        if (eventType == Sensor.TYPE_MAGNETIC_FIELD) {
            magneticValueTextView.setText(Float.toString(event.values[0]));
        } else if (eventType == Sensor.TYPE_LIGHT) {
            illuminanceValueTextView.setText(Float.toString(event.values[0]));
        } else if (eventType == Sensor.TYPE_ACCELEROMETER) {
            accelerometerValueTextViewX.setText(String.format("%.2f", event.values[0]));
            accelerometerValueTextViewY.setText(String.format("%.2f", event.values[1]));
            accelerometerValueTextViewZ.setText(String.format("%.2f", event.values[2]));
        } else if (eventType == Sensor.TYPE_AMBIENT_TEMPERATURE) {
            temperatureValueTextView.setText(Float.toString(event.values[0]));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
