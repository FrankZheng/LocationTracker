package test.frankzheng.com.locationtracker;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhengxiaoqiang on 15/4/20.
 */
public class LocationModelManager {

    public static final String KEY_AMAP = "AMap";
    public static final String KEY_AMAP_SERVICE = "AMapService";
    public static final String KEY_MT_SDK = "MTSDK";

    static Map<String, LocationModel> models = new HashMap<>();

    public static LocationModel getAMapModel() {
        return models.get(KEY_AMAP);
    }

    public static LocationModel getAMapServiceModel() {
        return models.get(KEY_AMAP_SERVICE);
    }

    public static LocationModel getMTSdkModel() {
        return models.get(KEY_MT_SDK);
    }

    public static LocationModel getModel(String key) {
        return models.get(key);
    }

    public static void initModels(Context context) {
        String[] keys = {KEY_AMAP, KEY_AMAP_SERVICE, KEY_MT_SDK};
        for( String key : keys) {
            if(!models.containsKey(key)) {
                LocationModel model = new LocationModel();
                model.init(context);
                models.put(key, model);
            }
        }
    }


}
