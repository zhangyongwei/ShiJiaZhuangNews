package com.atguigu.shijiazhuangnews.base;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.atguigu.shijiazhuangnews.R;
import com.atguigu.shijiazhuangnews.activity.MainActivity;

/**
 * Created by 张永卫on 2017/2/5.
 */

public class BasePager {
    /**
     * 上下文
     */
    public final Context mContext;
    public ImageView ib_menu;
    public TextView tv_title;
    public FrameLayout fl_main;
    public ImageButton ib_switch;

    /**
     * 代表各个页面的实例
     *
     */
    public View rootView;
    public BasePager(Context context){
        this.mContext = context;
        rootView = initView();
    }

    private View initView() {
        View view = View.inflate(mContext, R.layout.basepager,null);
        ib_menu = (ImageView) view.findViewById(R.id.ib_menu);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        fl_main = (FrameLayout) view.findViewById(R.id.fl_main);
        ib_switch = (ImageButton) view.findViewById(R.id.ib_switch);

        ib_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) mContext;
                mainActivity.getSlidingMenu().toggle();//关--开
            }
        });
        return view;
    }
    /**
     * 1.在子类重新initData方法，实现子类的视图，并且视图在该方法中和基类的Fragmelayout布局结合在一起
       2.绑定数据或者请求数据再绑定数据
     */
    public void  initData(){

    }
}
