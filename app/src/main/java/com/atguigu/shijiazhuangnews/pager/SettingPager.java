package com.atguigu.shijiazhuangnews.pager;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;

import com.atguigu.shijiazhuangnews.base.BasePager;

/**
 * Created by 张永卫on 2017/2/5.
 */

public class SettingPager extends BasePager {
    public SettingPager(Context context) {
        super(context);
    }

    @Override
    public void initData() {
        super.initData();
        Log.e("TAG","设置页面加载数据了");
        //设置标题
        tv_title.setText("设置");

        //实例视图
        TextView textView = new TextView(mContext);
        textView.setTextSize(20);
        textView.setGravity(Gravity.CENTER);
        textView.setText("设置");
        textView.setTextColor(Color.RED);

        //和父类的fragment结合
        fl_main.addView(textView);
    }
}
