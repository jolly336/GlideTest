package com.nelson.glidetest.okhttp;

import android.content.Context;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.GlideModule;
import java.io.InputStream;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;

/**
 * Created by Nelson on 2018/1/22.
 */

public class OkHttpGlideModule implements GlideModule {

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {

        builder.setDiskCache(new ExternalCacheDiskCacheFactory(context));

    }

    @Override
    public void registerComponents(Context context, Glide glide) {
        Builder builder = new Builder();
        builder.retryOnConnectionFailure(true);
        builder.addInterceptor(new ProgressInterceptor());
        OkHttpClient okHttpClient = builder.build();

        glide.register(GlideUrl.class, InputStream.class,
                new OkHttpGlideUrlLoader.Factory(okHttpClient));
    }
}
