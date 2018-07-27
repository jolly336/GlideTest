package com.nelson.glidetest.network;

import android.support.annotation.NonNull;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.util.ContentLengthInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Map;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Fetches an {@link InputStream} using the okhttp.
 * 需要用我们的不安全的OkHttpClient去连接URL激活输入流，因此，我们需要另外一个类去从一个URL中拉取返回的输入流
 *
 * Created by Nelson on 16/12/19.
 */
public class OkHttpStreamFetcher implements DataFetcher<InputStream>, okhttp3.Callback {

    private static final String FAILED_FORMAT = "Request failed with url : %1$s , code: %2$d";

    private final OkHttpClient mClient;
    private final GlideUrl mUrl;
    private ResponseBody mResponseBody;
    private InputStream mStream;
    private DataCallback<? super InputStream> mCallback;
    private long mStartTime;
    // call may be accessed on the main thread while the object is in use on other threads. All other
    // accesses to variables may occur on different threads, but only one at a time.
    private volatile Call mCall;

    public OkHttpStreamFetcher(OkHttpClient client, GlideUrl url) {
        this.mClient = client;
        this.mUrl = url;
    }

    @Override
    public void loadData(@NonNull Priority priority,
            @NonNull DataCallback<? super InputStream> callback) {

        Builder requestbuilder = new Builder().url(this.mUrl.toStringUrl());

        for (Map.Entry<String, String> headerEntity : this.mUrl.getHeaders().entrySet()) {
            String key = headerEntity.getKey();
            requestbuilder.addHeader(key, headerEntity.getValue());
        }

        this.mCallback = callback;

        Request request = requestbuilder.build();
        this.mCall = this.mClient.newCall(request);
        this.mCall.enqueue(this);
    }

    @Override
    public void onFailure(Call call, IOException e) {
        this.mCallback.onLoadFailed(e);
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        this.mResponseBody = response.body();
        if (!response.isSuccessful()) {
            this.mCallback.onLoadFailed(new IOException(
                    String.format(Locale.US, FAILED_FORMAT, this.mUrl, response.code())));

        } else {
            long contentLength = this.mResponseBody.contentLength();
            this.mStream = ContentLengthInputStream
                    .obtain(this.mResponseBody.byteStream(), contentLength);
            this.mCallback.onDataReady(mStream);
        }
    }

    @Override
    public void cleanup() {

        if (this.mStream != null) {
            try {
                this.mStream.close();
            } catch (IOException e) {
                // Ignored
            }
        }

        if (this.mResponseBody != null) {
            this.mResponseBody.close();
        }

        this.mCallback = null;
    }

    @Override
    public void cancel() {
        Call local = this.mCall;
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
