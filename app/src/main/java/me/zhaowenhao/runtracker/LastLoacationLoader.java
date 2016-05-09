package me.zhaowenhao.runtracker;

import android.content.Context;
import android.location.Location;

/**
 * Created by zhaowenhao on 16/5/9.
 */
public class LastLoacationLoader extends DataLoader<Location> {
    private long mRunId;

    public LastLoacationLoader(Context context, long runId){
        super(context);
        mRunId = runId;
    }

    @Override
    public Location loadInBackground(){
        return RunManager.get(getContext()).getLastLocationForRun(mRunId);
    }
}
