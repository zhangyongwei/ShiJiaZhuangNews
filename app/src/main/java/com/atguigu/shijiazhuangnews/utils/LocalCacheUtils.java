package com.atguigu.shijiazhuangnews.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by 张永卫on 2017/2/10.
 */

public class LocalCacheUtils {

    private  final MemoryCacheUtils memoryCacheUtils;

    public LocalCacheUtils(MemoryCacheUtils memoryCacheUtils){
        this.memoryCacheUtils = memoryCacheUtils;

    }

    public void putBitmap(String url,Bitmap bitmap){

        try {
            String fileName = MD5Encoder.encode(url);

            String dir = Environment.getExternalStorageDirectory() + "/shijiazhuangnews/image/";

            File file = new File(dir,fileName);

            File parent = file.getParentFile();
            if(!parent.exists()){
                parent.mkdirs();//创建多级目录

            }
            //创建文件目录
            if(!file.exists()){
                file.createNewFile();
            }

            FileOutputStream stream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
            stream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 根据url获取图片
     * @param url
     * @return
     */
    public Bitmap getBitmapFromUrl(String url) {

        try {
            String fileName = MD5Encoder.encode(url);

            String dir = Environment.getExternalStorageDirectory() + "/shijiazhuangnews/image/";

            File file = new File(dir, fileName);

            //如果该文件存在

            if (!file.exists()) {
                FileInputStream inputStream = new FileInputStream(file);

                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                //当本地获取图片的时候，并且保存到内存中
                if(bitmap!=null){
                    memoryCacheUtils.putBitmap(url,bitmap);
                }
                return bitmap;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
