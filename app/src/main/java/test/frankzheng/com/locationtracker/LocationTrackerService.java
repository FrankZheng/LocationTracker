package test.frankzheng.com.locationtracker;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;

/**
 * Created by zhengxiaoqiang on 15/4/15.
 */
public class LocationTrackerService extends Service implements AMapLocationListener {
    private static final String TAG = LocationTrackerService.class.getSimpleName();

    private LocationManagerProxy mLocationManagerProxy;

    private double mLat;
    private double mLng;

    public static void start(Context context) {
        Intent intent = new Intent(context, LocationTrackerService.class);
        context.startService(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //create AMap services
        Log.d(TAG, "onCreate");

        setupLocationManager();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //do something
        Log.d(TAG, "onStartCommand");
        boolean restartAuto = true;
        if(restartAuto) {
            return START_STICKY;
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //destroy the location manager when service destroyed
        mLocationManagerProxy.destroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void setupLocationManager() {
        // 初始化定位，只采用网络定位
        mLocationManagerProxy = LocationManagerProxy.getInstance(this);
        mLocationManagerProxy.setGpsEnable(false);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用removeUpdates()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用destroy()方法
        // 其中如果间隔时间为-1，则定位只定一次,
        // 在单次定位情况下，定位无论成功与否，都无需调用removeUpdates()方法移除请求，定位sdk内部会移除
        mLocationManagerProxy.requestLocationData(
                LocationProviderProxy.AMapNetwork, 30 * 1000, 15, this);
    }

    private void log(String tag, String message) {
        Log.d(tag, message);
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null
                && amapLocation.getAMapException().getErrorCode() == 0) {

            double lat = amapLocation.getLatitude();
            double lng = amapLocation.getLongitude();
            double deltaDistance = 0;

            if(mLat != 0 && mLng != 0) {
                deltaDistance = GPSUtils.gps2m(lat, lng, mLat, mLng);
                log(TAG, "delta distance:  " + deltaDistance);
            }
            mLat = lat;
            mLng = lng;

            String coordinate = mLat + " " + mLng;
            String provider = amapLocation.getProvider();
            String address = amapLocation.getAddress();
            float accuracy = amapLocation.getAccuracy();
            long time = amapLocation.getTime();

            log(TAG, "time: " + time);
            log(TAG, "coordinate: " + coordinate);
            log(TAG, "provider: " + provider);
            log(TAG, "address: " + address);
            log(TAG, "accuracy: " + accuracy);

            LocationInfo info = new LocationInfo();
            info.accuracy = accuracy;
            info.provider = provider;
            info.address = address;
            info.lat = mLat;
            info.lng = mLng;
            info.deltaDistance = deltaDistance;
            info.time = time;

            LocationModel.getInstance().addLocationInfo(info);


        } else {
            log(TAG, "Location Error: " + amapLocation.getAMapException().getErrorMessage());
        }
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
