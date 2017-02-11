package com.atguigu.shijiazhuangnews.fragment;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.atguigu.shijiazhuangnews.R;
import com.atguigu.shijiazhuangnews.activity.MainActivity;
import com.atguigu.shijiazhuangnews.base.BaseFragment;
import com.atguigu.shijiazhuangnews.base.BasePager;
import com.atguigu.shijiazhuangnews.pager.HomePager;
import com.atguigu.shijiazhuangnews.pager.NewsCenterPager;
import com.atguigu.shijiazhuangnews.pager.SettingPager;
import com.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by 张永卫on 2017/2/5.
 */

public class ContentFragment extends BaseFragment {
    @InjectView(R.id.viewpager)
    ViewPager viewpager;
    @InjectView(R.id.rg_main)
    RadioGroup rgMain;

    /**
     * 三个页面的集合
     *
     */
    private ArrayList<BasePager> basePagers;

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_content, null);
        //把view注入到Butterknife
        ButterKnife.inject(this,view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();

        //初始化三个页面
        initPaget();

        setAdapter();

        initListener();

    }

    private void initListener() {
        rgMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //先默认设置不可滑动
                MainActivity mainActivity = (MainActivity) mContext;
                mainActivity.getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
                switch (checkedId){
                    case R.id.rb_home:
                        viewpager.setCurrentItem(0,false);
                        break;
                    case R.id.rb_news:
                        viewpager.setCurrentItem(1,false);
                        //当切换到新闻的时候，修改为可滑动
                        mainActivity.getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
                        break;
                    case R.id.rb_setting:
                        viewpager.setCurrentItem(2,false);
                        break;

                }
            }
        });
        rgMain.check(R.id.rb_news);

        //监听页面的选中
        viewpager.addOnPageChangeListener(new MyOnPageChangeListener() );
        basePagers.get(1).initData();//孩子的视图和父类的FrameLayout结合

    }

    /**
     * 得到新闻中心
     * @return
     */
    public NewsCenterPager getNewsCenterpager(){
        return (NewsCenterPager) basePagers.get(1);
    }
    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            //调用initData
            basePagers.get(position).initData();//孩子的视图和父类的FrameLayout结合
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    /**
     * 设置ViewPager的适配器
     */
    private void setAdapter() {
        viewpager.setAdapter(new MyPagerAdapter());

    }

    class MyPagerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return basePagers.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            BasePager basePager = basePagers.get(position);


            View rootView = basePager.rootView;//代表不同页面的实例

            //调用initData
            basePager.initData();

            container.addView(rootView);

            return rootView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    private void initPaget() {
        basePagers = new ArrayList<>();
        basePagers.add(new HomePager(mContext));//主页
        basePagers.add(new NewsCenterPager(mContext));//新闻中心
        basePagers.add(new SettingPager(mContext));//设置中心
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
