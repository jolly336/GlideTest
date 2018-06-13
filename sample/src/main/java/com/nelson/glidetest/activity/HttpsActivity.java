package com.nelson.glidetest.activity;

import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.nelson.glidetest.BaseActivity;
import com.nelson.glidetest.R;
import com.nelson.glidetest.databinding.ActivityHttpsBinding;
import com.nelson.glidetest.model.ResourceConfig;
import com.nelson.glidetest.network.SafeOkHttpClient;
import com.nelson.glidetest.network.okhttp.GlideApp;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.Response;

/**
 * self-signed 自签名 HTTPS认证
 * 12306.cn的证书在Assets目录下
 *
 * Created by Nelson on 2018/4/13.
 */

public class HttpsActivity extends BaseActivity {

    public static final String TAG = "HttpsActivity";

    private ActivityHttpsBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Https");

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_https);

        showUnsafe();

        showSafe();

        getHttpsHtml();
    }

    private void showUnsafe() {
        GlideApp.with(this)
                .load(ResourceConfig.IMAGE_HTTPS_URL)  // Https image hreflink
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .into(mBinding.ivUnsafe);
    }

    private void showSafe() {
        GlideApp.with(this)
                .load(ResourceConfig.IMAGE_HTTPS_URL)  // Https image hreflink
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model,
                            Target<Drawable> target,
                            boolean isFirstResource) {
                        Log.e(TAG, "#onException() :" + e.toString());
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model,
                            Target<Drawable> target,
                            DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(mBinding.ivSafe);
    }

    public void getHttpsHtml() {

        final Request request = new Builder()
                .url(ResourceConfig.IMAGE_HTTPS_URL)
                .build();

        OkHttpClient okHttpClient = SafeOkHttpClient.getsafeOkHttpClient(this);
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "#onFailure(): " + e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String body = response.body().string();
                Log.e(TAG, "#onResponse(): " + body);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mBinding.tvSafe.setText(body);
                    }
                });
            }
        });
    }
}
