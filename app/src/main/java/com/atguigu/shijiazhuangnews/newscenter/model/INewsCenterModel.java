package com.atguigu.shijiazhuangnews.newscenter.model;

/**
 * Created by 张永卫on 2017/4/3.
 */

public interface INewsCenterModel {
    /**
     * 新闻中心的联网请求
     * @param url
     * @param listener
     */
    public void getDataFromNet(String url,OnRequestListener listener);
}
