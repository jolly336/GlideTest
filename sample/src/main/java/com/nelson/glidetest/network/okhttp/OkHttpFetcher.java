package com.nelson.glidetest.network.okhttp;

import android.support.annotation.NonNull;
import android.util.Log;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.HttpException;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.util.ContentLengthInputStream;
import com.bumptech.glide.util.Preconditions;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map.Entry;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by Nelson on 2018/1/22.
 */

public class OkHttpFetcher implements DataFetcher<InputStream>, okhttp3.Callback {

    private static final String TAG = "OkHttpFetcher";
    private final OkHttpClient mClient;
    private final GlideUrl mUrl;
    private InputStream mStream;
    private ResponseBody mResponseBody;
    private Call mCall;
    private DataCallback<? super InputStream> mCallback;

    public OkHttpFetcher(OkHttpClient client, GlideUrl url) {
        this.mClient = client;
        this.mUrl = url;
    }

    @Override
    public void loadData(@NonNull Priority priority,
            @NonNull DataCallback<? super InputStream> callback) {
        Builder requestBuilder = new Builder().url(mUrl.toStringUrl());
        for (Entry<String, String> headerEntity : mUrl.getHeaders().entrySet()) {
            String key = headerEntity.getKey();
            requestBuilder.addHeader(key, headerEntity.getValue());
        }

        Request request = requestBuilder.build();
        mCall = mClient.newCall(request);
        mCall.enqueue(this);

        this.mCallback = callback;
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        mResponseBody = response.body();
        if (response.isSuccessful()) {
            long contentLength = Preconditions.checkNotNull(mResponseBody).contentLength();
            mStream = ContentLengthInputStream.obtain(mResponseBody.byteStream(), contentLength);
            mCallback.onDataReady(mStream);
        } else {
            mCallback.onLoadFailed(new HttpException(response.message(), response.code()));
        }
    }

    @Override
    public void onFailure(Call call, IOException e) {
        if (Log.isLoggable(TAG, Log.DEBUG)) {
            Log.d(TAG, "OkHttp failed to obtain result", e);
        }

        mCallback.onLoadFailed(e);
    }

    @Override
    public void cleanup() {
        try {
            if (mStream != null) {
                mStream.close();
            }

        } catch (IOException e) {
        }

        if (mResponseBody != null) {
            mResponseBody.close();
        }

        mCallback = null;
    }

    @Override
    public void cancel() {
        Call local = mCall;
        if (local != null) {
            local.cancel();
        }
    }

    @NonNull
    @Override
    public Class<InputStream> getDataClass() {
        return InputStream.class;
    }

    @NonNull
    @Override
    public DataSource getDataSource() {
        return DataSource.REMOTE;
    }
}
