package com.nelson.glidetest;

import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.nelson.glidetest.databinding.ActivitySimpleUsingBinding;
import com.nelson.glidetest.model.Resource;
import com.nelson.glidetest.utils.Util;
import java.io.File;

/**
 * Created by Nelson on 2018/3/15.
 */

public class SimpleUsinglActivity extends AppCompatActivity {

    ActivitySimpleUsingBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_simple_using);

        // load from network url
        loadImageByUrl();

        // load from res id
        loadImageByResourceId();

        // load from file--- no effect!!!
        loadImageByFile();

        // load from uri
        loadImageByUri();

        // load gif --- Gif和video都没有播放起来！！！
        loadImageByGif();

        // load video
        loadVideoByUri();
    }

    private void loadImageByUrl() {
        String internetUrl = Resource.IMAGE_URL_IMAGE;
        Glide.with(this)
                .load(internetUrl)
                .placeholder(R.drawable.ic_launcher)
                .into(mBinding.ivUrl);
    }


    private void loadImageByResourceId() {
        int resourceId = R.drawable.see;
        Glide.with(this)
                .load(resourceId)
                .placeholder(R.drawable.ic_launcher)
                .into(mBinding.ivResourceId);
    }

    private void loadImageByFile() {
        Glide.with(this)
                .load(R.raw.francisco)
                .placeholder(R.drawable.ic_launcher) // can also be a drawable
                .error(R.drawable.ic_launcher)       // will be displayed if the image cannot be loaded
//                .override(600, 200) //resizes the image to these dimensions(in pixel).does not respect aspect ratio
//                .centerCrop()//this cropping technique scales the image so that it fills the requested bounds and then crops the extra
                .fitCenter() // this cropping technique scales the image so that it shows all image bounds and maybe cannot fill the targert view
                .into(mBinding.ivFile);
    }

    private void loadImageByUri() {
        Uri uri = Util.resourceIdToUri(this, R.drawable.ballon);
        Glide.with(this)
                .load(uri)
                .placeholder(R.drawable.ic_launcher)
                .into(mBinding.ivUri);
    }

    private void loadImageByGif() {
        String gifUrl = Resource.IMAGE_URL_GIF;
        Glide.with(this)
                .load(gifUrl)
                // Gif检查，如果提供的来源不是一个Gif，就没有办法显示，Glide接受Gif或者图片作为load()参数，如果你期望URL是一个Gif，Glide不会自动检查，
                //引入了一个额外的防区强制Glide变成一个Gif,asGif()方法
//                .asGif()
                .asBitmap() //Gif转为Bitmap，仅仅显示Gif的第一帧图片，可调用asBitmap()去保证其作为一个常规的图片显示，即使这个URL是一个Gif
                .error(R.drawable.ic_launcher)
                .placeholder(R.drawable.ic_launcher)
                .into(mBinding.ivGif);
    }


    private void loadVideoByUri() {
        String videoPath = Resource.VIDEO_LOCAL_PATH;
        Glide.with(this)
                .load(Uri.fromFile(new File(videoPath)))
                .placeholder(R.drawable.ic_launcher)
                .into(mBinding.ivVideo);
    }

}
