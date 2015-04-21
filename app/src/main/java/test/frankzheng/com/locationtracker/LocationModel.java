package test.frankzheng.com.locationtracker;

import android.content.Context;
import android.location.Location;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhengxiaoqiang on 15/4/15.
 */
public class LocationModel {
    private static final String TAG = LocationModel.class.getSimpleName();

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

    private LocationInfo mLastLocationInfo;

    private static RequestQueue mRequestQueue;

    public void init(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);
    }

    public void addLocationInfo(LocationInfo info) {
        if(info.deltaDistance == 0 && mLastLocationInfo != null) {
            //calculate the delta instance
            info.deltaDistance = calculateDeltaDistance(info, mLastLocationInfo);
        }
        mLastLocationInfo = info;

        mLocationInfoList.add(info);

        //report location info
        reportLocation(mLastLocationInfo);

        //post event to let others know
        notifyDataChange();
    }

    private double calculateDeltaDistance(LocationInfo locationInfo1, LocationInfo locationInfo2) {
        return GPSUtils.gps2m(locationInfo1.lat,locationInfo1.lng,
                locationInfo2.lat, locationInfo2.lng);
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

    private void reportLocation(LocationInfo locationInfo) {
        //for now use a fake API just to simulate the network call
        final String url = "http://www.ip.cn";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //LogUtils.d(TAG, "reportLocation, onResponse:%s", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //LogUtils.d(TAG, "reportLocation, onErrorResponse:%s", error.toString());
            }
        });

        mRequestQueue.add(request);
    }
}
