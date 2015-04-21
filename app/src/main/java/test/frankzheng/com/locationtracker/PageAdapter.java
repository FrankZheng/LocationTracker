package test.frankzheng.com.locationtracker;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhengxiaoqiang on 15/4/20.
 */
public class PageAdapter extends FragmentPagerAdapter {
    private static final String TAG = PageAdapter.class.getSimpleName();

    private List<PageInfo> mPages = new ArrayList<PageInfo>();
    private Context mContext;

    public PageAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        LogUtils.d(TAG, "getItem, %d", position);
        PageInfo info = mPages.get(position);
        if(info.fragment == null) {
            info.fragment = Fragment.instantiate(mContext, info.clazz.getName(), info.args);
        }
        return info.fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mPages.get(position).title;
    }

    @Override
    public int getCount() {
        return mPages.size();
    }

    private static class PageInfo {
        private final String title;
        private final Class<? extends Fragment> clazz;
        private final Bundle args;
        public Fragment fragment;

        public PageInfo(String title, Class<? extends Fragment> clazz, Bundle args) {
            this.title = title;
            this.clazz = clazz;
            this.args = args;
        }
    }

    public void addPage(String title, Class<? extends Fragment> clazz, Bundle args) {
        PageInfo pageInfo = new PageInfo(title, clazz, args);
        mPages.add(pageInfo);
    }

}
