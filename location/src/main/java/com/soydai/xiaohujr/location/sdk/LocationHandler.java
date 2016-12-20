package com.soydai.xiaohujr.location.sdk;

import android.app.Activity;
import android.location.Location;

/**
 * Created by Yuan Jiwei on 16/12/14.
 */

public final class LocationHandler {
    private static LocationStrategy sLocationStrategy = LocationStrategy.DEFAULT;

    private static Location sLocationCache;

    private static LocationUpdater sLocationUpdater;

    public static LocationUpdater.Listener startLocationService(Activity activity) {
        sLocationUpdater = new LocationUpdater();
        return sLocationUpdater.startUpdateLocation(activity);
    }

    public static void setLocationStrategy(LocationStrategy strategy) {
        sLocationStrategy = strategy;
    }

    public static Location getLocation() {
            return sLocationCache;
    }

    static void updateLocationCache(Location location) {
        Location best = sLocationStrategy.getBestLocation(location, sLocationCache);
            sLocationCache = best;
    }
}