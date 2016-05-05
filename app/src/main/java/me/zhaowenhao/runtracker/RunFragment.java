package me.zhaowenhao.runtracker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by zhaowenhao on 16/5/1.
 */
public class RunFragment extends Fragment {
    private static final String TAG = "RunFragment";

    private Location mLastLocation;
    private Run mRun;
    private BroadcastReceiver mLocationReceiver = new LocationReceiver(){
        @Override
        protected void onLocationReceived(Context context, Location location){
            mLastLocation = location;
            Log.d(TAG, "onLocationReceived called");
            if (isVisible()){
                updateUI();
            }
        }

        @Override
        protected void onProviderEnabledChanged(boolean enabled){
            int toastText = enabled? R.string.gps_enabled : R.string.gds_disabled;
            Toast.makeText(getActivity(), toastText, Toast.LENGTH_LONG).show();
        }
    };


    private Button mStartButton;
    private Button mStopButton;

    private TextView mStartedTextView, mLatitudeTextView, mLongitudeTextView, mAltitudeTextView, mDurationTextView;

    private RunManager mRunManager;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        Log.d(TAG, "created");
        mRunManager = RunManager.get(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_run, container, false);

        Log.d(TAG, "view created");
        mStartButton = (Button) view.findViewById(R.id.run_startButton);
        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRunManager.startLocationUpdates();
                mRun = new Run();
                updateUI();
                Log.d(TAG, "start clicked");
            }
        });

        mStopButton = (Button) view.findViewById(R.id.run_stopButton);
        mStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRunManager.stopLocationUpdates();
                updateUI();
                Log.d(TAG, "stop clicked");
            }
        });

        mStartedTextView  = (TextView) view.findViewById(R.id.run_startedTextView);



        mLatitudeTextView = (TextView) view.findViewById(R.id.run_latitudeTextView);
        mLongitudeTextView = (TextView) view.findViewById(R.id.run_longitudeTextView);
        mAltitudeTextView = (TextView) view.findViewById(R.id.run_altitudeTextView);
        mDurationTextView = (TextView) view.findViewById(R.id.run_durationTextView);

        updateUI();
        return view;

    }

    @Override
    public void onStart(){
        super.onStart();
        getActivity().registerReceiver(mLocationReceiver, new IntentFilter(RunManager.ACTION_LOCATION));

    }

    @Override
    public void onStop(){
        getActivity().unregisterReceiver(mLocationReceiver);
        super.onStop();
    }

    private void updateUI(){
        boolean started = mRunManager.isTrackingRun();
        mStartButton.setEnabled(!started);
        mStopButton.setEnabled(started);

        if (mRun != null){
            mStartedTextView.setText(mRun.getStartDate().toString());
            Log.d(TAG, "mRun is not null");
        }

        if (mLastLocation != null){
            Log.d(TAG, "mLastLocation is not null");
        }

        int durationSeconds = 0;
        if (mRun != null && mLastLocation != null){

            durationSeconds = mRun.getDurationSeconds(mLastLocation.getTime());
            mLatitudeTextView.setText(Double.toString(mLastLocation.getLatitude()));
            mLongitudeTextView.setText(Double.toString(mLastLocation.getLongitude()));
            mAltitudeTextView.setText(Double.toString(mLastLocation.getAltitude()));

        }
        mDurationTextView.setText(Run.formatDuration(durationSeconds));
    }

}
