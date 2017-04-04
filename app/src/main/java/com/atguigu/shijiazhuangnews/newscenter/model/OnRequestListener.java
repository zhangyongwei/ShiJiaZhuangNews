package com.atguigu.shijiazhuangnews.newscenter.model;

import com.atguigu.shijiazhuangnews.bean.NewsCenterBean;

/**
 * Created by 张永卫on 2017/4/3.
 */

public interface OnRequestListener {
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
}
