package com.atguigu.shijiazhuangnews.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by 张永卫on 2017/2/7.
 */

public class HorizontalScrollViewPager extends ViewPager {
    public HorizontalScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private float startX;
    private float startY;

    /**
     * 水平方向滑动
     * 1.当页面是第0个，并且滑动方向是从左到右
     *getParent().requestDisallowInterceptTouchEvent(false);
     * 2.当页面是最后一个页面，并且滑动方向是从右到做滑动
     *getParent().requestDisallowInterceptTouchEvent(false);
     * 3.其他部分，就是中间部分
     * getParent().requestDisallowInterceptTouchEvent(true);
     *
     *
     * 竖直方向滑动
     * getParent().requestDisallowInterceptTouchEvent(false);
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);
                //1.记录起始位置
                startX = ev.getX();
                startY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                //2.来到结束坐标
                float endX = ev.getX();
                float endY = ev.getY();
                //3.计算水平方向和竖直方向滑动的距离
                float distanceX = Math.abs(endX-startX);
                float distanceY = Math.abs(endY-startY);

                if(distanceX>distanceY){
                    //水平方向
                    //1.当页面是第0个，并且滑动方向是从左到右
                    if(getCurrentItem()==0 && endX-startX>0){

                        getParent().requestDisallowInterceptTouchEvent(false);
                    }
                    //2.当页面是最后一个页面，并且滑动方向是从右到做滑动
                    else if(getCurrentItem()==getAdapter().getCount()-1 && endX-startX<0){
                        getParent().requestDisallowInterceptTouchEvent(false);
                    }else{
                        //3.其他部分，就是中间部分
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }
                }else{
                    //竖直方向
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;
        }

        return super.dispatchTouchEvent(ev);
    }
}
