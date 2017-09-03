package subiseguro.losalquimistas.com.subiseguro;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

//import com.google.android.gms.location.FusedLocationProviderClient;
//import com.google.android.gms.location.LocationServices;
//import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public final static String TAG = "SubiSeguro";

    private SensorManager mSensorManager;
    private Sensor mSensorMotion;

    private TextView testMotion;
    private TextView testLocation;

    private Button getDown;

    private long currentMillis;
    private long currentTimeUpdate;
    private long lastTimeUpdate;

    private final long motionDetectionDelay = 5000; //In millis
    private final double minAccelerationMagnitude = 1;

//    private FusedLocationProviderClient mFusedLocationClient;

    private long lastAskTime;
    private boolean onBus;

    private final long locationAskDelay = 1000; //In millis

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testMotion = (TextView) findViewById(R.id.testMotion);
        testLocation = (TextView) findViewById(R.id.testLocation);
        getDown = (Button) findViewById(R.id.getDown);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        SetupLinearAcceleration();

//        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

//        HasPermission();

        GetDown(null);
    }

    public void GetDown(View v){
        testLocation.setText("");
        getDown.setVisibility(View.GONE);
        onBus = false;
        lastTimeUpdate = System.currentTimeMillis();
        currentMillis = 0;
    }


    private boolean started;

    @Override
    protected void onPause() {
        super.onPause();
        started = false;
        Log.d(TAG, "Paused");
    }

    @Override
    protected void onStart() {
        super.onStart();
        started = true;
        lastTimeUpdate = System.currentTimeMillis();
        currentMillis = 0;
        Log.d(TAG, "Started");
    }

    private void OnMotionDetected() {
        testMotion.setText("Moving!");
        long currentTime = System.currentTimeMillis();
        if (lastAskTime + locationAskDelay < currentTime) {
            lastAskTime = currentTime;
            Intent i = new Intent(this, BusList.class);
            ArrayList<String> mockList = new ArrayList<>();
            mockList.add("Linea A");
            mockList.add("Linea B");
            mockList.add("Linea C");
            mockList.add("No estoy en un colectivo");
            i.putExtra("BusList", mockList);
            startActivityForResult(i, 200);

            //            if(!HasPermission())return;
            //noinspection MissingPermission
//            mFusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
//                @Override
//                public void onSuccess(Location location) {
//                    if (location != null) {
//                        testLocation.setText(location.getLatitude() + "; " + location.getLongitude());
//                    }
//                }
//            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 200) {
            if (resultCode == RESULT_OK){
                onBus = true;
                getDown.setVisibility(View.VISIBLE);
                testLocation.setText(data.getStringExtra("SelectedBus"));
            }
            else testLocation.setText("");
        } else super.onActivityResult(requestCode, resultCode, data);
    }

    private boolean HasPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission_group.LOCATION)) {
//                AlertDialog dialog = new AlertDialog.Builder(this).create();
//                dialog.setMessage("The app needs your Location in order to work.");
//                dialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission_group.LOCATION}, 500);
//                    }
//                });
//                dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                    }
//                });
//                dialog.show();
//            } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission_group.LOCATION}, 500);
//            }
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 500: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay!

                } else {
                    // permission denied, boo!

                }
                return;
            }
        }
    }

    private void OnStationaryDetected() {
        testMotion.setText("Stationary!");
    }

    private void SetupLinearAcceleration() {
        mSensorMotion = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        SensorEventListener listener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                if (!started || onBus) return;
                currentTimeUpdate = System.currentTimeMillis();
                long deltaTime = currentTimeUpdate - lastTimeUpdate;
                double magnitude = GetMagnitude(event.values);

                if (magnitude >= minAccelerationMagnitude) {
                    currentMillis += deltaTime;
                    if (currentMillis >= motionDetectionDelay) {
                        currentMillis = motionDetectionDelay;
                        OnMotionDetected();
                    }
                } else {
                    currentMillis -= deltaTime;
                    if (currentMillis <= 0) {
                        currentMillis = 0;
                        OnStationaryDetected();
                    }
                }
                lastTimeUpdate = currentTimeUpdate;
//                Log.d(TAG, deltaTime + "; " + magnitude);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {
            }
        };
        mSensorManager.registerListener(listener, mSensorMotion, SensorManager.SENSOR_DELAY_FASTEST);
    }

    private double GetMagnitude(float[] values) {
        double sqrMagnitude = 0;
        for (int i = 0; i < 3; i++) sqrMagnitude += Math.pow(values[i], 2);
        return Math.sqrt(sqrMagnitude);
    }
}
