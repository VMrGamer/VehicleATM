package cybersociety.vehicleatm;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {
TextView vd1,kd1,res1;
ViewPager viewPager;
PagerViewAdepeter pagerViewAdepeter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vd1 = (TextView)findViewById(R.id.vedd);
        kd1 = (TextView)findViewById(R.id.kul);
        res1 = (TextView)findViewById(R.id.res);

        viewPager = (ViewPager)findViewById(R.id.frag_m);

        pagerViewAdepeter = new PagerViewAdepeter(getSupportFragmentManager());
        viewPager.setAdapter(pagerViewAdepeter);
                res1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(3);
            }
        });
        kd1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1);
            }
        });
        vd1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(0);
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                onChangetab(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        }

    private void onChangetab(int position) {
        if(position == 0)
        {
            vd1.setTextSize(35);
            kd1.setTextSize(15);
            res1.setTextSize(15);

        }

    if (position == 1)
    {
        kd1.setTextSize(35);
        vd1.setTextSize(15);
        res1.setTextSize(15);

    }

        if (position == 2)
        {
            res1.setTextSize(35);
            vd1.setTextSize(15);
            kd1.setTextSize(15);

        }



}
}
