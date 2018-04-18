package com.nelson.glidetest.network.okhttp;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.util.ContentLengthInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map.Entry;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by Nelson on 2018/1/22.
 */

public class OkHttpFetcher implements DataFetcher<InputStream> {

    private final OkHttpClient mClient;
    private final GlideUrl mUrl;
    private boolean mIsCancelled;
    private InputStream mStream;
    private ResponseBody mResponseBody;

    public OkHttpFetcher(OkHttpClient client, GlideUrl url) {
        this.mClient = client;
        this.mUrl = url;
    }

    @Override
    public InputStream loadData(Priority priority) throws Exception {
        Builder requestBuilder = new Builder().url(mUrl.toStringUrl());
        for (Entry<String, String> headerEntity : mUrl.getHeaders().entrySet()) {
            String key = headerEntity.getKey();
            requestBuilder.addHeader(key, headerEntity.getValue());
        }

        Request request = requestBuilder.build();
        if (mIsCancelled) {
            return null;
        }

        Response response = mClient.newCall(request).execute();
        mResponseBody = response.body();
        if (!response.isSuccessful() || response == null) {
            throw new IOException("Request failed with code: " + response.code());
        }

        mStream = ContentLengthInputStream
                .obtain(mResponseBody.byteStream(), mResponseBody.contentLength());
        return mStream;
    }

    @Override
    public void cleanup() {
        try {
            if (mStream != null) {
                mStream.close();
            }

            if (mResponseBody != null) {
                mResponseBody.close();
            }
        } catch (IOException e) {
        }
    }

    @Override
    public String getId() {
        return mUrl.getCacheKey();
    }

    @Override
    public void cancel() {
        mIsCancelled = true;
    }
}
