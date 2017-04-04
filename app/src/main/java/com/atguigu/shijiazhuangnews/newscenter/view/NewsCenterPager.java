package com.atguigu.shijiazhuangnews.newscenter.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.atguigu.shijiazhuangnews.activity.MainActivity;
import com.atguigu.shijiazhuangnews.base.BasePager;
import com.atguigu.shijiazhuangnews.base.MenuDetailBasePager;
import com.atguigu.shijiazhuangnews.bean.NewsCenterBean;
import com.atguigu.shijiazhuangnews.detailpager.InteractMenuDetailPager;
import com.atguigu.shijiazhuangnews.detailpager.NewsMenuDetailPager;
import com.atguigu.shijiazhuangnews.detailpager.PhotosMenuDetailPager;
import com.atguigu.shijiazhuangnews.detailpager.TopicMenuDetailPager;
import com.atguigu.shijiazhuangnews.fragment.LeftMenuFragment;
import com.atguigu.shijiazhuangnews.newscenter.presenter.NewsCenterPresenter;
import com.atguigu.shijiazhuangnews.utils.CacheUtils;
import com.atguigu.shijiazhuangnews.utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 张永卫on 2017/2/5.
 */

public class NewsCenterPager extends BasePager implements INewsCenterView{

    private ArrayList<MenuDetailBasePager> menuDetailBasePagers;
    /**
     * 左侧菜单对应的数据
     */
    private List<NewsCenterBean.DataBean> dataBeanList;
    /**
     * mvp模式中的--p
     */
    private NewsCenterPresenter presenter;

    private ProgressDialog dialog;

    public NewsCenterPager(Context context) {
        super(context);
        dialog = new ProgressDialog(context);
        presenter = new NewsCenterPresenter(NewsCenterPager.this);
    }

    @Override
    public void initData() {
        super.initData();
        Log.e("TAG","新闻页面加载数据了");
        //显示菜单按钮
        ib_menu.setVisibility(View.VISIBLE);
        //设置标题
        tv_title.setText("新闻");
        
        //实例视图
        TextView textView = new TextView(mContext);
        textView.setTextSize(20);
        textView.setGravity(Gravity.CENTER);
        textView.setText("新闻中心");
        textView.setTextColor(Color.RED);
        
        //和父类的fragment结合
        fl_main.addView(textView);

        String saveJson = CacheUtils.getString(mContext,Constants.NEWSCENTER_PAGER_URL);
        if(!TextUtils.isEmpty(saveJson)){
//            processData(saveJson);
        }

        //联网请求
//        getDataFromNet();

        presenter.getDataFromNet();
    }




    /**
     * 解析数据
     * 绑定数据
     * @param
     */
    private void processData(NewsCenterBean centerBean) {
        //1.解析数据：手动解析（用系统的api解析）和第三方解析json的框架(Gson,fastjson)

//        NewsCenterBean centerBean = new Gson().fromJson(json,NewsCenterBean.class);


        dataBeanList = centerBean.getData();
        Log.e("TAG","新闻中心解析成功="+dataBeanList.get(0).getChildren().get(0).getTitle());

        //把新闻中心的数据传递给左侧菜单
        MainActivity mainActivity = (MainActivity) mContext;

        //得到左侧菜单
        LeftMenuFragment leftMenuFragment = mainActivity.getLeftMenuFragment();

        //2.绑定数据
        menuDetailBasePagers = new ArrayList<>();
        menuDetailBasePagers.add(new NewsMenuDetailPager(mainActivity,dataBeanList.get(0)));
        menuDetailBasePagers.add(new TopicMenuDetailPager(mainActivity,dataBeanList.get(0)));
        menuDetailBasePagers.add(new PhotosMenuDetailPager(mainActivity,dataBeanList.get(2)));
        menuDetailBasePagers.add(new InteractMenuDetailPager(mainActivity));

        //调用LeftMenuFragment的setData
        leftMenuFragment.setData(dataBeanList);
    }


    /**
     * 增加位置切换到不同的详情页面
     * @param prePosition
     */
    public void switchPager(int prePosition) {
        //设置标题
        tv_title.setText(dataBeanList.get(prePosition).getTitle());

        if(prePosition<menuDetailBasePagers.size()){
            MenuDetailBasePager menuDetailBasePager = menuDetailBasePagers.get(prePosition);
            //调用
            menuDetailBasePager.initData();
            //视图
            View rootView = menuDetailBasePager.rootView;
            fl_main.removeAllViews();//移除之前的
            fl_main.addView(rootView);

            if(prePosition==2){
                ib_switch.setVisibility(View.VISIBLE);
                ib_switch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PhotosMenuDetailPager photosMenuDetailPager = (PhotosMenuDetailPager) menuDetailBasePagers.get(2);
                        photosMenuDetailPager.switchListGrid(ib_switch);
                    }
                });
            }else{
                ib_switch.setVisibility(View.GONE);
            }
        }else{
            Toast.makeText(mContext, "该页面功能暂时未实现", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void showLoading() {

        dialog.show();
    }

    @Override
    public void hideLoading() {

        dialog.dismiss();
    }

    @Override
    public void onSuccess(NewsCenterBean newsCenterBean) {

        processData(newsCenterBean);
    }

    @Override
    public void onFailed(Exception ex) {

        Toast.makeText(mContext, "请求失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public String getUrl() {
        return Constants.NEWSCENTER_PAGER_URL;
    }
}
