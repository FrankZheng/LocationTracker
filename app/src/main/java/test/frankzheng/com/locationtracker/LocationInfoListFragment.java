package test.frankzheng.com.locationtracker;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by zhengxiaoqiang on 15/4/20.
 */
public class LocationInfoListFragment extends Fragment implements LocationModel.Listener {
    private static final String TAG = LocationInfoListFragment.class.getSimpleName();

    public static final String LOCATION_MODEL_KEY = "LocationModelKey";


    @InjectView(R.id.fragment_list)
    ListView mListView;

    LocationInfoListAdapter mAdapter;


    private String modelKey;

    @Override
    public void onDataChanged() {
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_location_info_list, container, false);
        ButterKnife.inject(this, view);

        modelKey = getArguments().getString(LOCATION_MODEL_KEY);
        LogUtils.d(TAG, "onCreateView, %s, %d", modelKey,
                getLocationModel().getLocationInfoList().size());

        mAdapter = new LocationInfoListAdapter(MainApplication.getAppContext(),
                getLocationModel().getLocationInfoList());

        mListView.setAdapter(mAdapter);

        getLocationModel().addListener(this);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        getLocationModel().removeListener(this);
    }


    private LocationModel getLocationModel() {
        return LocationModelManager.getModel(modelKey);
    }

}
