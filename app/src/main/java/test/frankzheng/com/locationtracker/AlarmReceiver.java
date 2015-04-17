package test.frankzheng.com.locationtracker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zhengxiaoqiang on 15/4/17.
 */
public class AlarmReceiver extends BroadcastReceiver implements AMapLocationListener {
    private static final String TAG = AlarmReceiver.class.getSimpleName();
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    public void onReceive(Context context, Intent intent) {
        LogUtils.d(TAG, "[%s] - onReceive", getCurrentTimeStr());

        //request location once
    }

    private String getCurrentTimeStr() {
        return DATE_FORMAT.format(new Date());
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        LogUtils.d(TAG, "[%s], onLocationChanged", getCurrentTimeStr());
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {
        LogUtils.d(TAG, "onProviderEnabled, %s", s);
    }

    @Override
    public void onProviderDisabled(String s) {
        LogUtils.d(TAG, "onProviderDisabled, %s", s);
    }
}
