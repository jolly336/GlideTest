package com.nelson.glidetest.network;

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
 * 创建一个ModelLoaderFactory，他用SafeHttpClient来提供了一个URL和输入流之前的连接
 *
 * Created by Nelson on 16/12/19.
 */
public class SafeOkHttpUrlLoader implements ModelLoader<GlideUrl, InputStream> {

    @Nullable
    @Override
    public LoadData<InputStream> buildLoadData(@NonNull GlideUrl model, int width, int height,
            @NonNull Options options) {
        return new LoadData<>(model, new OkHttpStreamFetcher(client, model));
    }

    @Override
    public boolean handles(@NonNull GlideUrl glideUrl) {
        return true;
    }

    /**
     * The default factory for{@link com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader}s.
     */
    public static class Factory implements ModelLoaderFactory<GlideUrl, InputStream> {

        private static volatile OkHttpClient internalClient;
        private OkHttpClient client;

        private static OkHttpClient getInternalClient() {
            if (internalClient == null) {
                synchronized (Factory.class) {
                    if (internalClient == null) {
                        internalClient = new OkHttpClient();
                    }
                }
            }
            return internalClient;
        }

        /**
         * Constructor for a new Factory that runs requests using a static singleton client.
         */
        public Factory() {
            this(getInternalClient());
        }

        /**
         * Constuctor for a new Factory that runs request using given client.
         */
        public Factory(OkHttpClient client) {
            this.client = client;
        }

        @NonNull
        @Override
        public ModelLoader<GlideUrl, InputStream> build(
                @NonNull MultiModelLoaderFactory multiFactory) {
            return new SafeOkHttpUrlLoader(client);
        }

        @Override
        public void teardown() {
            // Do nothing,this instance doesn't own the client.
        }
    }

    private OkHttpClient client;

    public SafeOkHttpUrlLoader(OkHttpClient client) {
        this.client = client;
    }

}
