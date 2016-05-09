package me.zhaowenhao.runtracker;

import android.support.v4.app.Fragment;

/**
 * Created by zhaowenhao on 16/5/8.
 */
public class RunListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment(){
        return new RunListFragment();
    }
}
