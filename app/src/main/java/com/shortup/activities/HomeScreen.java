package com.shortup.activities;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.shortup.R;
import com.shortup.fragments.AddUrlFragment;
import com.shortup.fragments.UrlListFragment;

public class HomeScreen extends AppCompatActivity implements AddUrlFragment.OnFragmentInteractionListener, UrlListFragment.OnFragmentInteractionListener{

    private FrameLayout fl_hs_fragment_container;

    public static FragmentManager mSupportFragmentManager;
    public static Context mApplicationContext;
    public static Resources mResources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        initialize();

        Fragment mAddUrlFragment = AddUrlFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_hs_fragment_container, mAddUrlFragment)
                .addToBackStack(null)
                .commit();
    }

    private void initialize() {

        mSupportFragmentManager = getSupportFragmentManager();
        mApplicationContext = getApplicationContext();
        mResources = getResources();
        fl_hs_fragment_container = (FrameLayout)findViewById(R.id.fl_hs_fragment_container);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAddUrlFragmentInteraction() {
        Fragment mUrlListFragment = UrlListFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.from_middle, R.anim.to_middle)
                .replace(R.id.fl_hs_fragment_container, mUrlListFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onUrlListFragmentInteraction() {

    }
}
