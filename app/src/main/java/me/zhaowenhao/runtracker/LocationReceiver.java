package me.zhaowenhao.runtracker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

/**
 * Created by zhaowenhao on 16/5/3.
 */
public class LocationReceiver extends BroadcastReceiver {
    private static final String TAG = "LocationReceiver";

    @Override
    public void onReceive(Context context, Intent intent){
        Location location = (Location) intent.getParcelableExtra(LocationManager.KEY_LOCATION_CHANGED);
        if (location != null){
            onLocationReceived(context, location);
            return;
        }

        if(intent.hasExtra(LocationManager.KEY_PROVIDER_ENABLED)){
            boolean enabled = intent.getBooleanExtra(LocationManager.KEY_PROVIDER_ENABLED, false);
            onProviderEnabledChanged(enabled);
            return;
        }

    }

    protected void onLocationReceived(Context context, Location location){
        Log.d(TAG, this + " Got location from " + location.getProvider() + ": " + location.getLatitude()  + ", " + location.getLongitude());
    }

    protected void onProviderEnabledChanged(boolean enabled){
        Log.d(TAG, this + " Provider " + (enabled? "enabled" : "disabled"));
    }


}
