package com.soydai.xiaohujr.location.sdk;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;

/**
 * Created by Yuan Jiwei on 16/12/15.
 */

public final class LocationUpdater {
    public static final int REQUEST_CODE_ACCESS_FINE_LOCATION = 8888;

    public Listener startUpdateLocation(final Activity context) {
        // Acquire a reference to the system Location Manager
        final LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        //设置不需要获取海拔方向数据
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        //设置允许产生资费
        criteria.setCostAllowed(false);
        //要求低耗电
        criteria.setPowerRequirement(Criteria.POWER_MEDIUM);
        final String provider = locationManager.getBestProvider(criteria, false);
        LocationHandler.updateLocationCache(locationManager.getLastKnownLocation(provider));

        final LocationListener listener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
                LocationHandler.updateLocationCache(location);
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {}

            public void onProviderEnabled(String provider) {}

            public void onProviderDisabled(String provider) {}
        };
        // Register the listener with the Location Manager to receive location updates
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_ACCESS_FINE_LOCATION);
        } else {
            locationManager.requestLocationUpdates(provider, LocationStrategy.MIN_MINITE * 60 * 1000, LocationStrategy.MIN_DISTANCE_KM * 1000, listener);
        }
        return new Listener() {
            @Override
            public void onPermissionResult(int requestCode, String[] permissions, int[] grantResults) {
                if (requestCode == REQUEST_CODE_ACCESS_FINE_LOCATION && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(provider, LocationStrategy.MIN_MINITE * 60 * 1000, LocationStrategy.MIN_DISTANCE_KM * 1000, listener);
                }
            }
        };
    }

    public interface Listener {
        void onPermissionResult(int requestCode, String[] permissions, int[] grantResults);
    }
}