package cybersociety.vehicleatm;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import cybersociety.vehicleatm.Fragments.FragmentAll;
import cybersociety.vehicleatm.Fragments.FragmentHistory;
import cybersociety.vehicleatm.Fragments.FragmentRegister;

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
                fragment = FragmentHistory.newInstance(0, "History");
        }
        return  fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
