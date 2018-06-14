package com.nelson.glidetest.glidemodule;

import android.content.Context;
import android.support.annotation.NonNull;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.module.GlideModule;
import java.io.InputStream;

/**
 * 用自定义尺寸优化加载的图片
 *
 * // previous way: we directly accessed the images
 * https://futurestud.io/images/logo.png
 *
 * // new way, server could handle additional parameter and provide the image in a specific size
 * // in this case, the server would serve the image in 400x300 pixel size
 * https://futurestud.io/images/logo.png?w=400&h=300
 *
 * Created by Nelson on 16/12/19.
 */
public class CustomImageSizeGlideModule implements GlideModule {

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        // nothing to do here
    }

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide,
            @NonNull Registry registry) {

        /**
         * .register()调用了Glide的配置去给所有的请求，实现CustomImageSizeModel接口（替换常规的GlideUrl接口），
         * 所以在这里可以创建并传递一个CustomImageSizeModel的实例去实现给Glide,为了处理这个新的自定义的model，
         * 我们需要去写一个CustomImageSizeModelFactory类，创建了我们的model处理的实例
         */
        registry.append(CustomImageSizeModel.class, InputStream.class,
                new CustomImageSizeModelFactory());

    }
}
