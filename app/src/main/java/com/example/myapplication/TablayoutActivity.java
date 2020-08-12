package com.example.myapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.TableLayout;


import com.longsh.longshlibrary.PagerSlidingTabStrip;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TablayoutActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private PagerSlidingTabStrip tablayout;

    private ViewPager viewPager;

    private LinearLayout bootom;

    private String[] arrays={"1","2","3","4","5","6","7","8","9",};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablayout);

        toolbar = findViewById(R.id.tolbar);
        tablayout = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.viewpager);
        bootom = findViewById(R.id.bottom);
        init_data();
    }

    private void init_data() {
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return MyFragment.getInstance(arrays[i]);
            }

            @Override
            public int getCount() {
                return arrays.length;
            }
            @Override
            public CharSequence getPageTitle(int position) {  //这个方法是重点！tablayout就是根据这个来绑定导航卡的名字的
                return arrays[position];
            }
        });
        tablayout.setViewPager(viewPager);
        setTabsValue();
        }

    private void setTabsValue() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        // 设置Tab底部选中的指示器Indicator的高度
        tablayout.setIndicatorHeight(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2.5f, dm));
        // 设置Tab底部选中的指示器 Indicator的颜色
        tablayout.setIndicatorColorResource(R.color.colorPrimary);
        //设置指示器Indicatorin是否跟文本一样宽，默认false
        tablayout.setIndicatorinFollowerTv(false);
        //设置小红点提示，item从0开始计算，true为显示，false为隐藏，默认为全部隐藏
        tablayout.setMsgToast(2, true);
        //设置红点滑动到当前页面自动消失,默认为true
        tablayout.setMsgToastPager(true);
        //设置Tab标题文字的颜色
        //tablayout.setTextColor(R.color.***);
        // 设置Tab标题文字的大小
        tablayout.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 15, dm));
        // 设置选中的Tab文字的颜色
        tablayout.setSelectedTextColorResource(R.color.colorPrimary);
        //设置Tab底部分割线的高度
        tablayout.setUnderlineHeight(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1f, dm));
        //设置Tab底部分割线的颜色
        //tablayout.setUnderlineColorResource(R.color.colorGray);
        // 设置点击某个Tab时的背景色,设置为0时取消背景色tablayout.setTabBackground(0);
        //        tablayout.setTabBackground(R.drawable.bg_tab);
        tablayout.setTabBackground(0);
        // 设置Tab是自动填充满屏幕的
        tablayout.setShouldExpand(true);

    }
}
