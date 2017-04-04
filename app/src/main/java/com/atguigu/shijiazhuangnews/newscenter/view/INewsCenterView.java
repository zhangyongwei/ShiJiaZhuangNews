package com.atguigu.shijiazhuangnews.newscenter.view;

import com.atguigu.shijiazhuangnews.bean.NewsCenterBean;

/**
 * Created by 张永卫on 2017/4/3.
 */

public interface INewsCenterView {
    /**
     * 显示加载回调
     */
    public void showLoading();

    /**
     * 隐藏加载回调
     */
    public void hideLoading();

    /**
     * 当请求成功的时候回调
     * @param newsCenterBean
     */
    void onSuccess(NewsCenterBean newsCenterBean);

    /**
     * 当请求失败的时候回调
     * @param ex
     */
    void onFailed(Exception ex);

    String getUrl();
}
