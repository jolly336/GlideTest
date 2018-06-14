package com.nelson.glidetest.glidemodule;

import com.bumptech.glide.annotation.GlideExtension;
import com.bumptech.glide.annotation.GlideOption;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

/**
 * 定制自己API，指定图片缓存策略要缓存原始图片。
 *
 * Created by Nelson on 2018/6/14.
 */

@GlideExtension
public class Glide4Extension {

    private Glide4Extension() {

    }

    @GlideOption
    public static void cacheSource(RequestOptions options) {
        options.diskCacheStrategy(DiskCacheStrategy.DATA);
    }

}
