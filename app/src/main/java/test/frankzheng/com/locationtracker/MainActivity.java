package test.frankzheng.com.locationtracker;

import android.location.Location;
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


public class MainActivity extends ActionBarActivity implements LocationModel.Listener {

    private static final String TAG = "ActionBarActivity";

    private LocationManagerProxy mLocationManagerProxy;


    @InjectView(R.id.main_list)
    ListView mList;

    LocationInfoListAdapter mListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //inject views
        ButterKnife.inject(this);

        mListAdapter = new LocationInfoListAdapter(this, LocationModel.getInstance().getLocationInfoList());
        mList.setAdapter(mListAdapter);

        LocationModel.getInstance().addListener(this);


        //start service
        LocationTrackerService.start(this);
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
        LocationModel.getInstance().removeListener(this);
    }


    @Override
    public void onDataChanged() {
        mListAdapter.notifyDataSetChanged();
    }
}
