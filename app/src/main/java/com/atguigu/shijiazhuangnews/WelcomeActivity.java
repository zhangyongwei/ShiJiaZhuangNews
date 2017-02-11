package com.atguigu.shijiazhuangnews;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

import com.atguigu.shijiazhuangnews.activity.GuideActivity;
import com.atguigu.shijiazhuangnews.activity.MainActivity;
import com.atguigu.shijiazhuangnews.utils.CacheUtils;

public class WelcomeActivity extends AppCompatActivity {
    private RelativeLayout activity_welcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        activity_welcome = (RelativeLayout)findViewById(R.id.activity_welcome);
        setAnimation();
    }

    /**
     *  三个动画：旋转动画，渐变动画，缩放动画
     */
    private void setAnimation() {
        RotateAnimation ra = new RotateAnimation(0,360, RotateAnimation.RELATIVE_TO_SELF,0.5f,RotateAnimation.RELATIVE_TO_SELF,0.5f);
        ra.setDuration(2000);
        ra.setFillAfter(true);

        AlphaAnimation aa = new AlphaAnimation(0,1);
        aa.setDuration(2000);
        aa.setFillAfter(true);

        ScaleAnimation sa = new ScaleAnimation(0,1,0,1,ScaleAnimation.RELATIVE_TO_SELF,0.5f,ScaleAnimation.RELATIVE_TO_SELF,0.5f);
        sa.setDuration(2000);
        sa.setFillAfter(true);

        AnimationSet set = new AnimationSet(false);
        set.addAnimation(aa);
        set.addAnimation(ra);
        set.addAnimation(sa);

        //开始播放动画
        activity_welcome.startAnimation(set);

        //监听动画播放完成
        set.setAnimationListener(new MyAnimationListener());


    }
    class MyAnimationListener implements Animation.AnimationListener {
        /**
         * 当动画开始播放的时候回调
         * @param animation
         */
        @Override
        public void onAnimationStart(Animation animation) {

        }
        /**
         * 当动画播放结束的时候回调
         * @param animation
         */
        @Override
        public void onAnimationEnd(Animation animation) {
            boolean startMain = CacheUtils.getBoolean(WelcomeActivity.this,"start_main");

            Intent intent = null;
            if(startMain){
                intent=new Intent(WelcomeActivity.this,MainActivity.class);
            }else {
                intent=new Intent(WelcomeActivity.this,GuideActivity.class);
            }

            startActivity(intent);
            finish();
        }
        /**
         * 当动画重复播放的时候回调
         * @param animation
         */
        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
}
