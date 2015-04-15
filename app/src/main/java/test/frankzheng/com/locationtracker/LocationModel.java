package test.frankzheng.com.locationtracker;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhengxiaoqiang on 15/4/15.
 */
public class LocationModel {

    private static LocationModel model = new LocationModel();
    public static LocationModel getInstance() {
        return model;
    }

    private List<LocationInfo> mLocationInfoList = new ArrayList<>();

    //may use a weak reference array later
    private List<Listener> mListeners = new ArrayList<>();

    public List<LocationInfo> getLocationInfoList() {
        return mLocationInfoList;
    }

    public void addLocationInfo(LocationInfo info) {
        mLocationInfoList.add(info);

        //post event to let others know
        notifyDataChange();
    }

    public void addListener(Listener listener) {
        mListeners.add(listener);
    }

    public void removeListener(Listener listener) {
        mListeners.remove(listener);
    }

    public static interface Listener {
        public void onDataChanged();
    }

    private void notifyDataChange() {
        for(Listener listener : mListeners) {
            listener.onDataChanged();
        }
    }
}
