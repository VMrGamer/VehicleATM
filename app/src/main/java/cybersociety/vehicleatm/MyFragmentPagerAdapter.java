package cybersociety.vehicleatm;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

import cybersociety.vehicleatm.fragments.FragmentAll;
import cybersociety.vehicleatm.fragments.FragmentRegister;
import cybersociety.vehicleatm.fragments.feed24hr.Fragment24HrFeed;

class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    private String[] tabTitles = new String[]{"All", "Register", "History"};

    public MyFragmentPagerAdapter(FragmentManager fm){
        super(fm);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

    public Fragment getItem(int position){
        Fragment fragment =null;
        switch (position){
            case 0:
                fragment = FragmentAll.newInstance(0, "All");
                break;
            case 1:
                fragment = FragmentRegister.newInstance(0, "Register");
                break;
            case 2:
                ArrayList<String> assHole = new ArrayList<>();
                assHole.add("entry-exit-buffer");
                assHole.add("log-acknowledged");
                assHole.add("log-unacknowledged");
                fragment = Fragment24HrFeed.newInstance(assHole,"0", "24Hr Feed");
        }
        return  fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
