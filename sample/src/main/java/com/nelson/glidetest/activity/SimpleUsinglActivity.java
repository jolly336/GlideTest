package com.nelson.glidetest.activity;

import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.bumptech.glide.Glide;
import com.nelson.glidetest.BaseActivity;
import com.nelson.glidetest.R;
import com.nelson.glidetest.databinding.ActivitySimpleUsingBinding;
import com.nelson.glidetest.model.ResourceConfig;
import java.io.File;

/**
 * 使用入门：从网络加载、从资源中加载、从文件中加载、从Uri中加载、加载Gif和Video等
 *
 * Created by Nelson on 2018/3/15.
 */

public class SimpleUsinglActivity extends BaseActivity {

    ActivitySimpleUsingBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_simple_using);
        setTitle("SimpleUsing");

        // load from network url
        loadImageByUrl();

        // load from res id(Drawable + Raw)
        loadImageWithDrawableIdByResource();
        loadImageWithRawIdByResource();

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
        String remoteUrl = ResourceConfig.IMAGE_REMOTE_URLS[0];
        Glide.with(this)
                .load(remoteUrl)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .into(mBinding.ivUrl);
    }


    private void loadImageWithDrawableIdByResource() {
        Glide.with(this)
                .load(R.drawable.see)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .into(mBinding.ivDrawableId);
    }

    private void loadImageWithRawIdByResource() {
        Glide.with(this)
                .load(R.raw.francisco)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .into(mBinding.ivRawId);
    }

    private void loadImageByFile() {
        Glide.with(this)
                .load(ResourceConfig.IMAGE_LOCAL_PATH)
                .placeholder(R.drawable.placeholder) // can also be a drawable
                .error(R.drawable.error)            // will be displayed if the image cannot be loaded
//                .override(600, 200)//resizes the image to these dimensions(in pixel).does not respect aspect ratio
//                .centerCrop()//this cropping technique scales the image so that it fills the requested bounds and then crops the extra
                .fitCenter() // this cropping technique scales the image so that it shows all image bounds and maybe cannot fill the targert view
                .into(mBinding.ivFile);
    }

    private void loadImageByUri() {
        Uri uri = ResourceConfig.resourceIdToUri(this, R.drawable.ballon);
        Glide.with(this)
                .load(uri)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .into(mBinding.ivUri);
    }

    private void loadImageByGif() {
        String gifUrl = ResourceConfig.GIF_REMOTE_URL;
        Glide.with(this)
                .load(gifUrl)
                // Gif检查，如果提供的来源不是一个Gif，就没有办法显示，Glide接受Gif或者图片作为load()参数，如果你期望URL是一个Gif，Glide不会自动检查，
                //引入了一个额外的防区强制Glide变成一个Gif,asGif()方法
//                .asGif()
                .asBitmap() //Gif转为Bitmap，仅仅显示Gif的第一帧图片，可调用asBitmap()去保证其作为一个常规的图片显示，即使这个URL是一个Gif
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .into(mBinding.ivGif);
    }


    private void loadVideoByUri() {
        String videoPath = ResourceConfig.VIDEO_LOCAL_PATH;
        Glide.with(this)
                // Glide只能加载存储在设备本地上的视频，如果是网络URL视频，Glide是不工作的，如果想显示远端视频，去看看VideoView
                .load(Uri.fromFile(new File(videoPath)))
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .into(mBinding.ivVideo);
    }

}
