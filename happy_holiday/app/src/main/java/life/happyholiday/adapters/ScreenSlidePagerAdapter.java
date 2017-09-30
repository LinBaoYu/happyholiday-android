package life.happyholiday.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import life.happyholiday.fragments.EventActivitiesFragment;
import life.happyholiday.fragments.EventChatFragment;

/**
 * Adapter that shows event details and chat
 *
 * Created by liuyang on 9/30/2017.
 */

public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
    private static final int NUM_PAGES = 2;

    public ScreenSlidePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return EventActivitiesFragment.newInstance();
        } else {
            return EventChatFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }

    @Override
    public float getPageWidth(int position) {
        return 0.93f;
    }
}
