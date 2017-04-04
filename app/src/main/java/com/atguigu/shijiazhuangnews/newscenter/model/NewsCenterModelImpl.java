package com.atguigu.shijiazhuangnews.newscenter.model;

import android.util.Log;

import com.atguigu.shijiazhuangnews.MyApplication;
import com.atguigu.shijiazhuangnews.bean.NewsCenterBean;
import com.atguigu.shijiazhuangnews.utils.CacheUtils;
import com.atguigu.shijiazhuangnews.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 张永卫on 2017/4/3.
 */

public class NewsCenterModelImpl implements INewsCenterModel {
    /**
     * 联网请求数据
     */
    @Override
    public void getDataFromNet(String url, final OnRequestListener listener) {

        RequestParams params = new RequestParams(url);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                CacheUtils.putString(MyApplication.getInstance(),Constants.NEWSCENTER_PAGER_URL,result);

                Log.e("TAG","请求成功=="+result);

                NewsCenterBean newsCenterBean = paraseJson(result);
//                processData(result);
                listener.onSuccess(newsCenterBean);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("TAG","请求失败=="+ex.getMessage());
                listener.onFailed((Exception) ex);
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.e("TAG","onCancelled=="+cex.getMessage());
            }

            @Override
            public void onFinished() {
                Log.e("TAG","onFinished==");
            }
        });
    }

    /**
     * 手动解析json数据使用系统的api
     * @param json
     * @return
     */
    private NewsCenterBean paraseJson(String json){
        NewsCenterBean centerBean = new NewsCenterBean();

        try {
            JSONObject jsonObject = new JSONObject(json);
            int retcode = jsonObject.optInt("retcode");
            centerBean.setRetcode(retcode);
            JSONArray data = jsonObject.optJSONArray("data");

            //数据集合
            List<NewsCenterBean.DataBean> dataBeans = new ArrayList<>();
            centerBean.setData(dataBeans);

            for(int i = 0;i<data.length();i++){
                JSONObject itemObject = (JSONObject) data.get(i);

                if(itemObject!= null){

                    //集合数据
                    NewsCenterBean.DataBean itemBean = new NewsCenterBean.DataBean();
                    dataBeans.add(itemBean);

                    int id = itemObject.optInt("id");
                    itemBean.setId(id);
                    String title = itemObject.optString("title");
                    itemBean.setTitle(title);
                    int type = itemObject.optInt("type");
                    itemBean.setType(type);
                    String url = itemObject.optString("url");
                    itemBean.setUrl(url);
                    String url1 = itemObject.optString("url1");
                    itemBean.setUrl1(url1);
                    String excurl = itemObject.optString("excurl");
                    itemBean.setExcurl(excurl);
                    String dayurl = itemObject.optString("dayurl");
                    itemBean.setDayurl(dayurl);
                    String weekurl = itemObject.optString("weekurl");
                    itemBean.setWeekurl(weekurl);

                    JSONArray children = itemObject.optJSONArray("children");

                    if(children!=null&& children.length()>0){

                        //设置children的数据
                        List<NewsCenterBean.DataBean.ChildrenBean> childrenBeans = new ArrayList<>();
                        itemBean.setChildren(childrenBeans);

                        for(int j = 0;j<children.length();j++){

                            NewsCenterBean.DataBean.ChildrenBean childrenBean = new NewsCenterBean.DataBean.ChildrenBean();
                            //添加到集合
                            childrenBeans.add(childrenBean);
                            JSONObject childrenObje = (JSONObject) children.get(j);
                            int idc = childrenObje.optInt("id");
                            childrenBean.setId(idc);
                            String titlec = childrenObje.optString("title");
                            childrenBean.setTitle(titlec);
                            int typec = childrenObje.optInt("type");
                            childrenBean.setType(typec);
                            String urlc = childrenObje.optString("url");
                            childrenBean.setUrl(urlc);


                        }

                    }
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return centerBean;
    }
}
