package me.zhaowenhao.runtracker;

import android.content.Context;
import android.location.Location;
import android.util.Log;

/**
 * Created by zhaowenhao on 16/5/8.
 */
public class TrackingLocationReceiver extends LocationReceiver {
    private static final String TAG = "TrackingLocationReceive";

    @Override
    protected void onLocationReceived(Context context, Location location){
        RunManager.get(context).insertLocation(location);
        Log.d(TAG, "location received, longitude: " + location.getLongitude() + ", latitude: " + location.getLatitude());
    }
}
