package com.atguigu.shijiazhuangnews.detailpager;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.atguigu.shijiazhuangnews.R;
import com.atguigu.shijiazhuangnews.adapter.PhotosMenuDetailPagerAdapter;
import com.atguigu.shijiazhuangnews.base.MenuDetailBasePager;
import com.atguigu.shijiazhuangnews.bean.NewsCenterBean;
import com.atguigu.shijiazhuangnews.bean.PhotosMenuBean;
import com.atguigu.shijiazhuangnews.utils.CacheUtils;
import com.atguigu.shijiazhuangnews.utils.Constants;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by 张永卫on 2017/2/6.
 */

public class PhotosMenuDetailPager extends MenuDetailBasePager {


    private final NewsCenterBean.DataBean dataBean;
    @InjectView(R.id.recyclerview)
    RecyclerView recyclerview;
    @InjectView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipe_refresh_layout;

    private PhotosMenuDetailPagerAdapter adapter;
   /* @InjectView(R.id.listview)
    ListView listview;
    @InjectView(R.id.gridview)
    GridView gridview;*/

    private String url;
    /*private PhotosMenuDetailPagerAdapter adapter;
    private List<PhotosMenuBean.DataBean.NewsBean> news;*/

    public PhotosMenuDetailPager(Context context, NewsCenterBean.DataBean dataBean) {
        super(context);
        this.dataBean = dataBean;
    }

    @Override
    public View initView() {
        //图组详情页面的视图
        View view = View.inflate(mContext, R.layout.photos_menu_detail_pager, null);
        ButterKnife.inject(this, view);
        //设置下拉多少距离起作用
        swipe_refresh_layout.setDistanceToTriggerSync(100);//设置下拉的距离

        swipe_refresh_layout.setColorSchemeColors(Color.BLUE,Color.RED);//设置不同颜色

        //设置背景颜色
        swipe_refresh_layout.setProgressBackgroundColorSchemeResource(android.R.color.holo_blue_bright);
        //设置下拉刷新的监听
        swipe_refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                getDataFromNet(url);
            }
        });

        return view;
    }

    @Override
    public void initData() {
        super.initData();
        url = Constants.BASE_URL + dataBean.getUrl();
        Log.e("TAG", url + "----------");

        String saveJson = CacheUtils.getString(mContext, url);
        if(!TextUtils.isEmpty(saveJson)){
            processData(saveJson);
        }
        getDataFromNet(url);
    }

    /**
     * 联网请求数据
     */
    private void getDataFromNet(final String url) {
        RequestParams params = new RequestParams(url);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                // Log.e("TAG","请求成功=="+result);
                CacheUtils.putString(mContext,url,result);
                processData(result);

                swipe_refresh_layout.setRefreshing(false);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("TAG", "请求失败==" + ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.e("TAG", "onCancelled==" + cex.getMessage());
            }

            @Override
            public void onFinished() {
                Log.e("TAG", "onFinished==");
            }
        });
    }

    private void processData(String result) {
        PhotosMenuBean bean = new Gson().fromJson(result, PhotosMenuBean.class);
        // news = bean.getData().getNews();
        bean.getData().getNews().get(0).getTitle();
        // Log.e("TAG", news.get(0).getTitle() + "图组的解析成功");

        /*if (news != null && news.size() > 0) {
            //设置ListView 的适配器
            adapter = new PhotosMenuDetailPagerAdapter();
            listview.setAdapter(adapter);

        } else {
            Toast.makeText(mContext, "没有请求到数据", Toast.LENGTH_SHORT).show();
        }*/

        //设置RecyclerView的适配器
        adapter = new PhotosMenuDetailPagerAdapter(mContext, bean.getData().getNews(),recyclerview);
        recyclerview.setAdapter(adapter);
        if (!isList) {

            recyclerview.setLayoutManager(new GridLayoutManager(mContext, 2, GridLayoutManager.VERTICAL, false));

            isList = false;


        } else {

            recyclerview.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));

            isList = true;
        }
    }

    /**
     * true:显示ListView
     * false:显示GridView
     */
    // private boolean isShowListView = true;
    private boolean isList = true;

    public void switchListGrid(ImageButton ib_switch) {
        if (isList) {
            //Grid
            isList = false;
            //设置布局管理器
            recyclerview.setLayoutManager(new GridLayoutManager(mContext, 2, GridLayoutManager.VERTICAL, false));
            //按钮设置List效果
            ib_switch.setImageResource(R.drawable.icon_pic_list_type);


        } else {

            //List
            recyclerview.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
            //按钮设置List效果
            ib_switch.setImageResource(R.drawable.icon_pic_grid_type);
            isList = true;
        }

      /*  if (isShowListView) {
            //显示GridView
            gridview.setAdapter(new PhotosMenuDetailPagerAdapter());
            listview.setVisibility(View.GONE);
            gridview.setVisibility(View.VISIBLE);
            //按钮-List
            ib_switch.setImageResource(R.drawable.icon_pic_list_type);
            isShowListView = false;
        } else {
            //显示ListView
            listview.setAdapter(new PhotosMenuDetailPagerAdapter());
            listview.setVisibility(View.VISIBLE);
            gridview.setVisibility(View.GONE);
            //按钮-Grid
            ib_switch.setImageResource(R.drawable.icon_pic_grid_type);
            isShowListView = true;
        }

    }

    class PhotosMenuDetailPagerAdapter extends BaseAdapter {




        @Override
        public int getCount() {
            return news.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;

            if (convertView == null) {
                convertView = View.inflate(mContext, R.layout.item_photos_menu_detail_pager, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            //根据位置得到对应的数据
            PhotosMenuBean.DataBean.NewsBean newsBean = news.get(position);
            Glide.with(mContext).load(Constants.BASE_URL + newsBean.getListimage()).into(viewHolder.ivIcon);
            viewHolder.tvTitle.setText(newsBean.getTitle());

            return convertView;
        }

    }

    static class ViewHolder {
        @InjectView(R.id.iv_icon)
        ImageView ivIcon;
        @InjectView(R.id.tv_title)
        TextView tvTitle;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }*/
    }
}