package com.atguigu.shijiazhuangnews.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.atguigu.shijiazhuangnews.R;
import com.atguigu.shijiazhuangnews.activity.PicassoSampleActivity;
import com.atguigu.shijiazhuangnews.bean.PhotosMenuBean;
import com.atguigu.shijiazhuangnews.utils.BitmapCacheUtils;
import com.atguigu.shijiazhuangnews.utils.Constants;
import com.atguigu.shijiazhuangnews.utils.NetCacheUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by 张永卫on 2017/2/9.
 */

public class PhotosMenuDetailPagerAdapter extends RecyclerView.Adapter<PhotosMenuDetailPagerAdapter.ViewHolder> {

    private final RecyclerView recyclerView;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case NetCacheUtils.SECUSS://图片请求成功
                    //位置
                    int position = msg.arg1;
                    Bitmap bitmap = (Bitmap) msg.obj;
                    if(recyclerView.isShown()){
                        ImageView ivIcon = (ImageView) recyclerView.findViewWithTag(position);
                        if(ivIcon!=null && bitmap!=null){
                            Log.e("TAG","网络缓存图片显示成功"+position);
                            ivIcon.setImageBitmap(bitmap);
                        }

                    }
                    break;
                case NetCacheUtils.FAIL://图片请求失败
                    position = msg.arg1;
                    Log.e("TAG","网络缓存失败"+position);
                    break;
            }
        }
    };


    private final Context mContext;
    private final List<PhotosMenuBean.DataBean.NewsBean> datas;
    private BitmapCacheUtils bitmapCacheUtils;
    private DisplayImageOptions options;
    public PhotosMenuDetailPagerAdapter(Context mContext, List<PhotosMenuBean.DataBean.NewsBean> news,RecyclerView recyclerView) {
        this.mContext = mContext;
        this.datas = news;
        this.recyclerView = recyclerView;
        bitmapCacheUtils = new BitmapCacheUtils(handler);
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.news_pic_default)
                .showImageForEmptyUri(R.drawable.news_pic_default)
                .showImageOnFail(R.drawable.news_pic_default)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new RoundedBitmapDisplayer(10))
                .build();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(View.inflate(mContext, R.layout.item_photos_menu_detail_pager, null));

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //1.根据位置得到数据
        PhotosMenuBean.DataBean.NewsBean newsBean = datas.get(position);
        holder.tvTitle.setText(newsBean.getTitle());
        //设置图片
        //Glide加载图片------------
        /*Glide.with(mContext).load(Constants.BASE_URL+newsBean.getListimage())
          .diskCacheStrategy(DiskCacheStrategy.ALL)
          .placeholder(R.drawable.news_pic_default)
          .error(R.drawable.news_pic_default)
          .into(holder.ivIcon);*/
            //自定义图片三级缓存------------
           //设置标识
//         holder.ivIcon.setTag(position);
//         Bitmap bitmap = bitmapCacheUtils.getBitmapFromNet(Constants.BASE_URL+newsBean.getListimage(),position);
//         if(bitmap!=null){//内存或本地
//             Log.e("TAG","我是本地得到的哦=="+bitmap);
//             holder.ivIcon.setImageBitmap(bitmap);
//
//         }

        //ImageLoader加载图片
        ImageLoader.getInstance().displayImage(Constants.BASE_URL+newsBean.getListimage(), holder.ivIcon, options);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

     class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.iv_icon)
        ImageView ivIcon;
        @InjectView(R.id.tv_title)
        TextView tvTitle;
        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);

            //设置点击事件
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, PicassoSampleActivity.class);
                    intent.putExtra("url",Constants.BASE_URL+datas.get(getLayoutPosition()).getListimage());
                    mContext.startActivity(intent);
                }
            });
        }
    }


}
