package com.zihuatanejo.glidetest;

import android.content.Context;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.module.GlideModule;

import java.io.InputStream;

/**
 * Created by Zihuatanejo on 16/12/19.
 */
public class CustomImageSizeGlideModule implements GlideModule {

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        // nothing to do here
    }

    @Override
    public void registerComponents(Context context, Glide glide) {
        /**
         * .register()调用了Glide的配置去给所有的请求，实现CustomImageSizeModel接口（替换常规的GlideUrl接口），
         * 所以在这里可以创建并传递一个CustomImageSizeModel的实例去实现给Glide,为了处理这个新的自定义的model，
         * 我们需要去写一个CustomImageSizeModelFactory类，创建了我们的model处理的实例
         */
        glide.register(CustomImageSizeModel.class, InputStream.class, new CustomImageSizeModelFactory());
    }
}
