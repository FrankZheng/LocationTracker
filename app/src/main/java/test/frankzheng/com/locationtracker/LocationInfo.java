package test.frankzheng.com.locationtracker;

/**
 * Created by zhengxiaoqiang on 15/4/15.
 */
public class LocationInfo {
    public double lat;
    public double lng;
    public long time; //time when create the object
    public float accuracy;
    public String address;
    public String provider;
    public double deltaDistance;
    public long gpsTime; //time from gps locator.

    public LocationInfo() {

    }


}
