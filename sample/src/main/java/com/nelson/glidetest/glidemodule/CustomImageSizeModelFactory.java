package com.nelson.glidetest.glidemodule;

import android.support.annotation.NonNull;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.load.model.MultiModelLoaderFactory;
import java.io.InputStream;

/**
 * Created by Nelson on 16/12/19.
 */
public class CustomImageSizeModelFactory implements
        ModelLoaderFactory<CustomImageSizeModel, InputStream> {

    @NonNull
    @Override
    public ModelLoader<CustomImageSizeModel, InputStream> build(
            @NonNull MultiModelLoaderFactory multiFactory) {
        return new CustomImageSizeUrlLoader(multiFactory.build(GlideUrl.class, InputStream.class));
    }

    @Override
    public void teardown() {

    }
}
