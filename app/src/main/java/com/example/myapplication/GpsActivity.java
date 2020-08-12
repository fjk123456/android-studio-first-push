package com.example.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class GpsActivity extends AppCompatActivity implements View.OnClickListener {
    private LocationManager locationManager;
    private Button button;
    private TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        button = findViewById(R.id.gps_btn);
        button.setOnClickListener(this);
        textView = findViewById(R.id.gps_pos);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(this, "请开启GPS", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Settings.ACTION_SECURITY_SETTINGS);
            startActivityForResult(intent, 78);
        }
        String best_p = locationManager.getBestProvider(getCriteria(), true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location = locationManager.getLastKnownLocation(best_p);
        updateView(location);
        locationManager.requestLocationUpdates(best_p,1000,0.5f,locationListener);
    }
    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            updateView(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            switch (status) {
                case GpsStatus.GPS_EVENT_FIRST_FIX:
                    Log.i("tag", "第一次定位 ");
                    break;
                case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
                    Log.i("tag", "w卫星信息发生变化 ");
                    break;
                case GpsStatus.GPS_EVENT_STARTED:
                    Log.d("tag", "定位启动 ");
                    break;
                case GpsStatus.GPS_EVENT_STOPPED:
                    Log.i("tag", "定位结束 ");
                    break;
            }
        }

        @Override
        public void onProviderEnabled(String provider) {
            Toast.makeText(GpsActivity.this, "GPS可用", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onProviderDisabled(String provider) {
            Toast.makeText(GpsActivity.this, "GPS不可用", Toast.LENGTH_SHORT).show();
        }
    };


    private void updateView(Location location) {
        if(location!=null){
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("纬度为:"+location.getLatitude()+"\n");
            stringBuilder.append("精度为:"+location.getLongitude()+"\n");
            stringBuilder.append("海拔为:"+location.getAltitude()+"\n");
            stringBuilder.append("方向角为:"+location.getBearing()+"\n");
            textView.setText(stringBuilder.toString());
        }else {
            textView.setText("此时还未获取到相应的坐标");
        }
    }

    private Criteria getCriteria() {
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setSpeedRequired(true);
        criteria.setCostAllowed(true);
        criteria.setBearingRequired(true);
        criteria.setBearingAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(true);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        return  criteria;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(this, "还未开启GPS，自行退出", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {

    }
}
