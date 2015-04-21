package test.frankzheng.com.locationtracker;

import android.app.Application;
import android.content.Context;

import com.meituan.android.common.locate.GeoCoder;
import com.meituan.android.common.locate.GeoCoderImpl;
import com.meituan.android.common.locate.LocationLoaderFactory;
import com.meituan.android.common.locate.LocationLoaderFactoryImpl;
import com.meituan.android.common.locate.MasterLocator;
import com.meituan.android.common.locate.MasterLocatorFactoryImpl;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * Created by ponyets on 14-6-27.
 */
public class MainApplication extends Application{
    public final HttpClient httpClient = new DefaultHttpClient();
    public LocationLoaderFactory loaderFactory;
    public GeoCoder geoCoder;

    private static MainApplication app;


    @Override
    public void onCreate() {
        super.onCreate();
        app = this;

        MasterLocator masterLocator = new MasterLocatorFactoryImpl().createMasterLocator(getApplicationContext(), httpClient);
        loaderFactory = new LocationLoaderFactoryImpl(masterLocator);
        geoCoder = new GeoCoderImpl(httpClient);
    }

    public static MainApplication getApp() {
        return app;
    }

    public static Context getAppContext() {
        return app == null ? null : app.getApplicationContext();
    }
}
