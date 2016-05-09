package me.zhaowenhao.runtracker;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by zhaowenhao on 16/5/8.
 */
public class RunListFragment extends android.support.v4.app.ListFragment {
    private RunDatabaseHelper.RunCursor mRunCursor;
    private static final int REQUEST_NEW_RUN = 0;
    private static final String TAG = "RunListFragment";

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        mRunCursor = RunManager.get(getActivity()).queryRuns();

        RunCursorAdapter adapter = new RunCursorAdapter(getActivity(), mRunCursor);
        setListAdapter(adapter);
    }

    @Override
    public void onDestroy(){
        mRunCursor.close();
        super.onDestroy();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id){
        Intent i = new Intent(getActivity(), RunActivity.class);
        i.putExtra(RunActivity.EXTRA_RUN_ID, id);
        startActivity(i);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu, inflater);
        Log.d(TAG, "create option menu");
        inflater.inflate(R.menu.run_list_options, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.menu_item_new_run:
                Intent i = new Intent(getActivity(), RunActivity.class);
                startActivityForResult(i, REQUEST_NEW_RUN);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (REQUEST_NEW_RUN == requestCode){
            mRunCursor.requery();
            ((RunCursorAdapter) getListAdapter()).notifyDataSetChanged();
        }
    }

    private static class RunCursorAdapter extends CursorAdapter{
        private RunDatabaseHelper.RunCursor mRunCursor;

        private RunCursorAdapter(Context context, RunDatabaseHelper.RunCursor cursor){
            super(context, cursor, 0);
            mRunCursor = cursor;
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            return inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor){
            Run run = mRunCursor.getRun();

            TextView startDateTextView = (TextView) view;
            String celltxt = context.getString(R.string.cell_txt, run.getStartDate());
            startDateTextView.setText(celltxt);
        }


    }
}


