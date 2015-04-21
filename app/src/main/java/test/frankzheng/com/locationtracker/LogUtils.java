package test.frankzheng.com.locationtracker;

import android.util.Log;

/**
 * Created by zhengxiaoqiang on 15/4/17.
 */
public class LogUtils {

    public static boolean Debug = true;

    public static void d(String tag, String fmt, Object... args) {
        String message = String.format(fmt, args);

        if(Debug) {
            Log.d(tag, message);
        }
    }

}
