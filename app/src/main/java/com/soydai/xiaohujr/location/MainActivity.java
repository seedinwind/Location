package com.soydai.xiaohujr.location;

import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.soydai.xiaohujr.location.sdk.LocationHandler;
import com.soydai.xiaohujr.location.sdk.LocationUpdater;

public class MainActivity extends AppCompatActivity {
    private LocationUpdater.Listener mListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListener = LocationHandler.startLocationService(this);
    }

    void getLocation(View v) {
        Location location = LocationHandler.getLocation();
        if (location != null) {
            Toast.makeText(this, location.getLongitude() + "," + location.getLatitude(), Toast.LENGTH_SHORT).show();
            Log.e("LocationHandler", location.getLongitude() + "," + location.getLatitude());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mListener.onPermissionResult(requestCode, permissions, grantResults);
    }
}
