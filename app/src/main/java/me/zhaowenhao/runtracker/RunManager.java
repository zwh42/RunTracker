package me.zhaowenhao.runtracker;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;

/**
 * Created by zhaowenhao on 16/5/1.
 */

public class RunManager {
    private static final String TAG = "RunManager";
    public  static final String ACTION_LOCATION = "me.zhaowenhao.runtracker.ACTION_LOCATION";

    private static RunManager sRunManager;
    private Context mContext;
    private LocationManager mLocationManager;

    private RunManager(Context context){
        mContext = context;
        mLocationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
    }

    public static RunManager get(Context context){
        if (sRunManager == null){
            sRunManager = new RunManager(context.getApplicationContext());
        }

        return sRunManager;
    }

    private PendingIntent getLocationPendingIntent(boolean shouldCreate){
        Intent broadcast = new Intent(ACTION_LOCATION);
        int flags = shouldCreate ? 0: PendingIntent.FLAG_NO_CREATE;
        return PendingIntent.getBroadcast(mContext, 0, broadcast, flags);
    }

    public void startLocationUpdates(){
        String provider = LocationManager.GPS_PROVIDER;

        Location lastKnown = mLocationManager.getLastKnownLocation(provider);
        if (lastKnown != null){
            lastKnown.setTime(System.currentTimeMillis());
            broadcastLocation(lastKnown);
        }

        PendingIntent pi = getLocationPendingIntent(true);
        mLocationManager.requestLocationUpdates(provider, 0, 0, pi);
    }

    public void stopLocationUpdates(){
        PendingIntent pi = getLocationPendingIntent(false);

        if (pi != null){
            mLocationManager.removeUpdates(pi);
            pi.cancel();
        }
    }

    private void broadcastLocation(Location location){
        Intent broadcast = new Intent(ACTION_LOCATION);
        broadcast.putExtra(LocationManager.KEY_LOCATION_CHANGED, location);
        mContext.sendBroadcast(broadcast);
    }

    public boolean isTrackingRun(){
        return getLocationPendingIntent(false) != null;
    }
}


