package test.frankzheng.com.locationtracker;

import android.content.Context;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by zhengxiaoqiang on 15/4/15.
 */
public class LocationInfoListAdapter extends ArrayAdapter<LocationInfo> {

    private static final SimpleDateFormat DATE_FMT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    public LocationInfoListAdapter(Context context, List<LocationInfo> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LocationInfo item = getItem(position);
        ViewHolder holder = null;
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_location_info, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        //set data
        //time
        Date date = new Date(item.time);
        String gpsTime = String.format("GPS time: %s", DATE_FMT.format(new Date(item.gpsTime)));
        holder.mGpsTime.setText(gpsTime);

        String time = String.format("time: %s", DATE_FMT.format(new Date(item.time)));
        holder.mTime.setText(time);

        holder.mLat.setText(String.valueOf(item.lat));
        holder.mLng.setText(String.valueOf(item.lng));

        holder.mAccuracy.setText(String.valueOf(item.accuracy));
        holder.mDeltaDistance.setText(String.valueOf(item.deltaDistance));
        holder.mProvider.setText(String.valueOf(item.provider));
        holder.mAddress.setText(String.valueOf(item.address));


        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.row_location_info_time)
        TextView mTime;
        @InjectView(R.id.row_location_info_gps_time)
        TextView mGpsTime;
        @InjectView(R.id.row_location_info_lat)
        TextView mLat;
        @InjectView(R.id.row_location_info_lng)
        TextView mLng;
        @InjectView(R.id.row_location_info_provider)
        TextView mProvider;
        @InjectView(R.id.row_location_info_accuracy)
        TextView mAccuracy;
        @InjectView(R.id.row_location_info_delta_distance)
        TextView mDeltaDistance;
        @InjectView(R.id.row_location_info_address)
        TextView mAddress;

        public ViewHolder(View view) {
            ButterKnife.inject(this,view);
        }


    }



}
