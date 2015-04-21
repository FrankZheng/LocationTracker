package test.frankzheng.com.locationtracker;

import android.location.Location;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainActivity extends ActionBarActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @InjectView(R.id.main_pager)
    ViewPager mViewPager;

    PageAdapter mPageAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //inject views
        ButterKnife.inject(this);

        mPageAdapter = new PageAdapter(MainApplication.getAppContext(), getSupportFragmentManager());


        Bundle arg0 = new Bundle();
        arg0.putString(LocationInfoListFragment.LOCATION_MODEL_KEY, LocationModelManager.KEY_AMAP_SERVICE);
        mPageAdapter.addPage("AMap Service", LocationInfoListFragment.class, arg0);

        Bundle arg1 = new Bundle();
        arg1.putString(LocationInfoListFragment.LOCATION_MODEL_KEY, LocationModelManager.KEY_AMAP);
        mPageAdapter.addPage("AMap Alarm", LocationInfoListFragment.class, arg1);

        Bundle arg2 = new Bundle();
        arg2.putString(LocationInfoListFragment.LOCATION_MODEL_KEY, LocationModelManager.KEY_MT_SDK);
        mPageAdapter.addPage("MTSdk Alarm", LocationInfoListFragment.class, arg2);


        mViewPager.setAdapter(mPageAdapter);
        mViewPager.setOffscreenPageLimit(2);

        LocationModelManager.initModels(MainApplication.getAppContext());



        //start service
        LocationTrackerService.start(this);

        //set the alarm
        AlarmController.getInstance().init(this);
        AlarmController.getInstance().startAlarm();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }







}
