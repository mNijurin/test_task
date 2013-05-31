package com.test.facebook.menu;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.test.facebook.R;

/**
 * Created with IntelliJ IDEA.
 * User: Max
 * Date: 5/31/13
 * Time: 12:37 PM
 */
public class ActivityWithMenu extends FragmentActivity {

    OnMenuItemClick onMenuItemClickListener;

    public void provideOnMenuItemClickListener(OnMenuItemClick onMenuItemClickListener) {
        this.onMenuItemClickListener = onMenuItemClickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.test_task_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return onMenuItemClickListener.onOptionsMenuItemClick(item);
    }
}
