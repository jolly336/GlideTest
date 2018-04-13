package com.nelson.glidetest.activity;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.nelson.glidetest.R;
import com.nelson.glidetest.databinding.ActivityImageListAdapterBinding;

/**
 * ListAdapter(ListView)
 *
 * Created by Nelson on 16/12/15.
 */

public class ImageListActivity extends AppCompatActivity {

    public static String[] IMAGES = {
            "http://opkjcw4sd.bkt.clouddn.com/anthony-intraversato-257182.jpg",
            "http://opkjcw4sd.bkt.clouddn.com/photo-1449034446853-66c86144b0ad.jpeg",
            "http://opkjcw4sd.bkt.clouddn.com/brooke-lark-158017.jpg",
            "http://opkjcw4sd.bkt.clouddn.com/brooke-lark-254998.jpg",
            "http://opkjcw4sd.bkt.clouddn.com/christian-joudrey-96208.jpg",
            "http://opkjcw4sd.bkt.clouddn.com/clement-h-544786.jpg",
            "http://opkjcw4sd.bkt.clouddn.com/kevin-noble-524437.jpg",
            "http://opkjcw4sd.bkt.clouddn.com/photo-1483168527879-c66136b56105.jpeg",
            "http://opkjcw4sd.bkt.clouddn.com/photo-1484893341013-5f1d9c91a96e.jpeg",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("ImageList");

        ActivityImageListAdapterBinding dataBinding = DataBindingUtil
                .setContentView(this, R.layout.activity_image_list_adapter);
        dataBinding.lvImageList.setAdapter(new ImageListAdapter(this, IMAGES));
    }


    static class ImageListAdapter extends ArrayAdapter {

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

            Glide.with(context)
                    .load(imageUrls[position])
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.error)
                    .skipMemoryCache(true) //禁止内存缓存
                    // 比如请求的图片是1000x1000像素，但你的ImageView是500x500像素
                    // 磁盘四个枚举值：ALL 所有版本图片，NONE 不缓存图片，SOURCE 仅仅只缓存原来全分辨率图片，RESULT 仅仅缓存最终的图片
                    .diskCacheStrategy(DiskCacheStrategy.NONE)// 禁止磁盘缓存
                    // 缩略图，先显示原图的10%的大小，等到原始图像到达后，会抹除缩略图，显示原始图片
                    .thumbnail(0.1f)
                    .into((ImageView) convertView);

            return convertView;
        }
    }

}
