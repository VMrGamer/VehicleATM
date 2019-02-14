package cybersociety.vehicleatm;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    public MyFragmentPagerAdapter(FragmentManager fm){
        super(fm);
    }
    public Fragment getItem(int position){
        Fragment fragment =null;
        switch (position){
            case 0:
                fragment=new fragmentAll();
                break;
            case 1:
                fragment=new fragmentRegister();
                break;
            case 2:
                fragment=new fragmentHistory();
        }
        return  fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
