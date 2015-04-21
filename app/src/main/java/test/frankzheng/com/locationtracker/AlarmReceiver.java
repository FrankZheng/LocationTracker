package test.frankzheng.com.locationtracker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.content.Loader;


import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.meituan.android.common.locate.LocationLoaderFactory;


import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zhengxiaoqiang on 15/4/17.
 */
public class AlarmReceiver extends BroadcastReceiver implements AMapLocationListener {
    private static final String TAG = AlarmReceiver.class.getSimpleName();
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    private LocationManagerProxy mLocationManagerProxy;

    public void onReceive(Context context, Intent intent) {
        LogUtils.d(TAG, "[%s] - onReceive", getCurrentTimeStr());
        //LogUtils.d(TAG, "Key : %s", intent.getStringExtra("Key"));

        requestLocationDataByAMap(context);

        requestLocationDataByMTSdk(context);
    }


    private void requestLocationDataByAMap(Context context) {
        // 初始化定位
        if(mLocationManagerProxy == null) {
            mLocationManagerProxy = LocationManagerProxy.getInstance(context);
        }

        //这里如果禁止掉gps，就是使用网络定位，如果enable，就是混合定位
        //如果是混合定位，需要查询provider，如果是GPS，则有些信息会缺失。
        mLocationManagerProxy.setGpsEnable(false); //这里也许需要根据当前GPS的情况考虑是否设置为true或者false。
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用removeUpdates()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用destroy()方法
        // 其中如果间隔时间为-1，则定位只定一次,
        // 在单次定位情况下，定位无论成功与否，都无需调用removeUpdates()方法移除请求，定位sdk内部会移除
        mLocationManagerProxy.requestLocationData(
                LocationProviderProxy.AMapNetwork, -1, 15, this);
    }

    private void requestLocationDataByMTSdk(Context context) {
        Loader<Location> loader =  MainApplication.getApp().loaderFactory.createLocationLoader(context, LocationLoaderFactory.LoadStrategy.normal);

        loader.registerListener(0, new Loader.OnLoadCompleteListener<Location>() {
            @Override
            public void onLoadComplete(Loader<Location> loader, Location data) {
                //LogUtils.d(TAG, "onLoadComplete %f %f", data.getLatitude(), data.getLongitude());
                LogUtils.d(TAG, "[%s], onLocationChanged", getCurrentTimeStr());
                double lat = data.getLatitude();
                double lng = data.getLongitude();

                String coordinate = lat + " " + lng;
                String provider = data.getProvider();
                String address = "";// = data.getAddress();
                float accuracy = data.getAccuracy();
                long time = data.getTime();
                LogUtils.d(TAG, "time:%s, gps-time:%s, coordinate:%s, provider:%s, address:%s, accuracy:%s",
                        getCurrentTimeStr(), getTimeStr(time), coordinate, provider, address, accuracy);

                LocationInfo info = new LocationInfo();
                info.accuracy = accuracy;
                info.provider = provider;
                info.address = address;
                info.lat = lat;
                info.lng = lng;
                //info.deltaDistance = deltaDistance;
                info.gpsTime = time;
                info.time = new Date().getTime(); //current time

                LocationModelManager.getModel(LocationModelManager.KEY_MT_SDK).addLocationInfo(info);
            }
        });




        loader.startLoading();

    }




    private String getCurrentTimeStr() {
        return DATE_FORMAT.format(new Date());
    }
    private String getTimeStr(long time) { return DATE_FORMAT.format(new Date(time));}

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        LogUtils.d(TAG, "[%s], onLocationChanged", getCurrentTimeStr());
        double lat = amapLocation.getLatitude();
        double lng = amapLocation.getLongitude();

        String coordinate = lat + " " + lng;
        String provider = amapLocation.getProvider();
        String address = amapLocation.getAddress();
        float accuracy = amapLocation.getAccuracy();
        long time = amapLocation.getTime();
        LogUtils.d(TAG, "time:%s, gps-time:%s, coordinate:%s, provider:%s, address:%s, accuracy:%s",
                getCurrentTimeStr(), getTimeStr(time), coordinate, provider, address, accuracy);

        LocationInfo info = new LocationInfo();
        info.accuracy = accuracy;
        info.provider = provider;
        info.address = address;
        info.lat = lat;
        info.lng = lng;
        //info.deltaDistance = deltaDistance;
        info.gpsTime = time;
        info.time = new Date().getTime(); //current time

        LocationModelManager.getModel(LocationModelManager.KEY_AMAP).addLocationInfo(info);
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
