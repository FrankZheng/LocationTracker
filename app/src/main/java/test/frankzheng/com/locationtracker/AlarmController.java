package test.frankzheng.com.locationtracker;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.SystemClock;

/**
 * Created by zhengxiaoqiang on 15/4/17.
 */
public class AlarmController {
    private static final String TAG = AlarmController.class.getSimpleName();
    private static final String ACTION_ALARM_FIRED = AlarmController.class.getCanonicalName() + ".ACTION_ALARM_FIRED";


    private static AlarmController controller = new AlarmController();

    private AlarmManager alarmManager;
    private PendingIntent alarmIntent;

    public static AlarmController getInstance() {
        return controller;
    }

    private Context mContext;

    public void init(Context context) {
        mContext = context;
        alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, AlarmReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
    }

    public void startAlarm() {
        //start a repeating alarm

        BatteryStatus status = checkBatteryStatus();
        LogUtils.d(TAG, "status is " + status.toString());

        long triggerAtMillis = SystemClock.elapsedRealtime();
        long intervalMillis = 10 * 1000;

        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                triggerAtMillis,
                intervalMillis,
                alarmIntent);
    }

    public void cancelAlarm() {
        if(alarmManager != null) {
            alarmManager.cancel(alarmIntent);
        }
    }

    private static class BatteryStatus {
        public boolean isCharging;
        public boolean usbCharge;
        public boolean acCharge;
        @Override
        public String toString() {
            return String.format("{isCharging:%b, usbCharge:%b, acCharge:%b}",
                    isCharging, usbCharge, acCharge);
        }
    }

    private BatteryStatus checkBatteryStatus() {
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatusIntent = mContext.registerReceiver(null, intentFilter);

        BatteryStatus batteryStatus = new BatteryStatus();
        int status = batteryStatusIntent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);

        batteryStatus.isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                status == BatteryManager.BATTERY_STATUS_FULL;


        int chargePlug = batteryStatusIntent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        batteryStatus.usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
        batteryStatus.acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;

        return batteryStatus;
    }
}
