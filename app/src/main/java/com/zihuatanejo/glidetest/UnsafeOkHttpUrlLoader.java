package com.zihuatanejo.glidetest;

import android.content.Context;
import com.bumptech.glide.integration.okhttp3.OkHttpStreamFetcher;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.GenericLoaderFactory;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import okhttp3.OkHttpClient;

import java.io.InputStream;

/**
 * 创建一个ModelLoaderFactory，他用UnsafeHttpClient来提供了一个URL和输入流之前的连接
 * Created by Zihuatanejo on 16/12/19.
 */
public class UnsafeOkHttpUrlLoader implements ModelLoader<GlideUrl, InputStream> {

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
                        internalClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();
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
         * @param client
         */
        public Factory(OkHttpClient client) {
            this.client = client;
        }

        @Override
        public ModelLoader<GlideUrl, InputStream> build(Context context, GenericLoaderFactory factories) {
            return new UnsafeOkHttpUrlLoader(client);
        }

        @Override
        public void teardown() {
            // Do nothing,this instance doesn't own the client.
        }
    }

    @Override
    public DataFetcher<InputStream> getResourceFetcher(GlideUrl model, int width, int height) {
        return new UnsafeOkHttpStreamFetcher(client, model);
    }

    private OkHttpClient client;

    public UnsafeOkHttpUrlLoader(OkHttpClient client) {
        this.client = client;
    }

}
