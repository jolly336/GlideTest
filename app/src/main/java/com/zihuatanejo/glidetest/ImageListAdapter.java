package com.zihuatanejo.glidetest;

import android.content.Context;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;

/**
 * Created by Zihuatanejo on 16/12/15.
 */
public class ImageListAdapter extends ArrayAdapter {

    private Context context;
    private String imageUrls[];

    public ImageListAdapter(Context context, String[] imageUrls) {
        super(context, R.layout.item_image_list_adapter, imageUrls);
        this.imageUrls = imageUrls;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_image_list_adapter, parent, false);
        }

        //2、缩略图用法二，使用Glide请求作为参数，和原图请求独立开来，独自请求缩略图
        File thumbnailPath = new File(Environment.getExternalStorageDirectory(), "green.jpg");
        DrawableTypeRequest<File> thumbnailRequest = Glide.with(context)
                .load(thumbnailPath);

        Glide.with(context)
                .load(imageUrls[position])
                //.skipMemoryCache(true) //禁止内存缓存

                //比如请求的图片是1000x1000像素，但你的ImageView是500x500像素
                // 磁盘四个枚举值：ALL 所有版本图片，NONE 不缓存图片，SOURCE 仅仅只缓存原来全分辨率图片，RESULT 仅仅缓存最终的图片
                .diskCacheStrategy(DiskCacheStrategy.NONE)// 禁止磁盘缓存

                //1、缩略图用法一，使用thumbnail()函数，这个请求和原图请求是同一个请求
//                .thumbnail(0.1f) //缩略图，先显示原图的10%的大小，等到原始图像到达后，会抹除缩略图，显示原始图片
                .thumbnail(thumbnailRequest)
                .into((ImageView) convertView);

        return convertView;
    }
}
