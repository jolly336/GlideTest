package com.nelson.glidetest.network;

import android.support.annotation.NonNull;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.util.ContentLengthInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 需要用我们的不安全的OkHttpClient去连接URL激活输入流，因此，我们需要另外一个类去从一个URL中拉取返回的输入流
 *
 * Created by Nelson on 16/12/19.
 */
public class OkHttpStreamFetcher implements DataFetcher<InputStream> {

    private OkHttpClient client;
    private GlideUrl url;
    private InputStream stream;
    private ResponseBody responseBody;

    public OkHttpStreamFetcher(OkHttpClient client, GlideUrl url) {
        this.client = client;
        this.url = url;
    }

    @Override
    public void loadData(@NonNull Priority priority,
            @NonNull DataCallback<? super InputStream> callback) {
        Request.Builder requestBuilder = new Request.Builder()
                .url(url.toStringUrl());

        for (Map.Entry<String, String> headerEntry : url.getHeaders().entrySet()) {
            String key = headerEntry.getKey();
            requestBuilder.addHeader(key, headerEntry.getValue());
        }

        Request request = requestBuilder.build();

        try {
            Response response = client.newCall(request).execute();
            responseBody = response.body();
            if (!response.isSuccessful()) {
                throw new IOException("Request failed with code: " + response.code());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        long contentLength = responseBody.contentLength();
        stream = ContentLengthInputStream.obtain(responseBody.byteStream(), contentLength);
        callback.onDataReady(stream);
    }

    @Override
    public void cleanup() {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException e) {
                // Ignored
            }
        }

        if (responseBody != null) {
            responseBody.close();
        }

    }

    @Override
    public void cancel() {
        // TODO: 16/12/19 call cancel on the client when this method is called on a background thread. See #257
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
