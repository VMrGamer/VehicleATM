package cybersociety.vehicleatm;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

class PagerViewAdepeter extends FragmentPagerAdapter {
    public PagerViewAdepeter(FragmentManager fm){
        super(fm);
    }
    public Fragment getItem(int position){
        Fragment fragment =null;
        switch (position){
            case 0:
                fragment=new fg_vd();
                break;
            case 1:
                fragment=new fg_res();
                break;
                case 2:
                fragment=new fg_kd();
        }
        return  fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
