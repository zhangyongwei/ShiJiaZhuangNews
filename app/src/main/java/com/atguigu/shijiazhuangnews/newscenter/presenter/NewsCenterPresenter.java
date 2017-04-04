package com.atguigu.shijiazhuangnews.newscenter.presenter;

import android.os.Handler;

import com.atguigu.shijiazhuangnews.bean.NewsCenterBean;
import com.atguigu.shijiazhuangnews.newscenter.model.INewsCenterModel;
import com.atguigu.shijiazhuangnews.newscenter.model.NewsCenterModelImpl;
import com.atguigu.shijiazhuangnews.newscenter.model.OnRequestListener;
import com.atguigu.shijiazhuangnews.newscenter.view.INewsCenterView;

/**
 * Created by 张永卫on 2017/4/3.
 */

/**
 * mvp模式中的--p
 */
public class NewsCenterPresenter {
    /**
     * mvp模式中的--m
     */
    private INewsCenterModel iNewsCenterModel;
    /**
     * mvp模式中的--v
     */
    private INewsCenterView iNewsCenterView;
    //构造器
    public NewsCenterPresenter(INewsCenterView iNewsCenterView) {

        this.iNewsCenterView = iNewsCenterView;
        this.iNewsCenterModel = new NewsCenterModelImpl();
    }

    /**
     * 从网络获取数据
     */
    public void getDataFromNet(){
        //显示加载效果
        iNewsCenterView.showLoading();

        //请求网络
        iNewsCenterModel.getDataFromNet(iNewsCenterView.getUrl(), new OnRequestListener() {
            @Override
            public void onSuccess(final NewsCenterBean newsCenterBean) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //隐藏加载效果
                        iNewsCenterView.hideLoading();
                        //成功的数据回调给view
                        iNewsCenterView.onSuccess(newsCenterBean);
                    }
                }, 2000);

            }

            @Override
            public void onFailed(Exception ex) {
                //隐藏加载效果
                iNewsCenterView.hideLoading();
                //失败的信息
                iNewsCenterView.onFailed(ex);
            }
        });
    }
}
