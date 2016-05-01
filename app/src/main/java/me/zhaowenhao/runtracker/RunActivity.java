package me.zhaowenhao.runtracker;


import android.os.Bundle;
import android.support.v4.app.Fragment;

public class RunActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment(){
        return new RunFragment();
    }
}
