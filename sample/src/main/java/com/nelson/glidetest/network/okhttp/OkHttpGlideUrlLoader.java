package com.nelson.glidetest.network.okhttp;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.load.model.MultiModelLoaderFactory;
import java.io.InputStream;
import okhttp3.OkHttpClient;

/**
 * Created by Nelson on 2018/1/22.
 */

public class OkHttpGlideUrlLoader implements ModelLoader<GlideUrl, InputStream> {


    private final OkHttpClient mOkHttpClient;

    @Nullable
    @Override
    public LoadData<InputStream> buildLoadData(@NonNull GlideUrl model, int width, int height,
            @NonNull Options options) {
        return new LoadData<>(model, new OkHttpFetcher(mOkHttpClient, model));
    }

    @Override
    public boolean handles(@NonNull GlideUrl glideUrl) {
        return false;
    }

    public static class Factory implements ModelLoaderFactory<GlideUrl, InputStream> {

        private OkHttpClient client;

        public Factory() {

        }

        public Factory(OkHttpClient client) {
            this.client = client;
        }

        private OkHttpClient getOkHttpClient() {
            if (client == null) {
                client = new OkHttpClient();
            }
            return client;
        }

        @NonNull
        @Override
        public ModelLoader<GlideUrl, InputStream> build(
                @NonNull MultiModelLoaderFactory multiFactory) {
            return new OkHttpGlideUrlLoader(getOkHttpClient());
        }

        @Override
        public void teardown() {

        }
    }

    public OkHttpGlideUrlLoader(OkHttpClient client) {
        this.mOkHttpClient = client;
    }

}
