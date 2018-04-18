package com.nelson.glidetest.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.nelson.glidetest.BaseActivity;
import com.nelson.glidetest.R;
import com.nelson.glidetest.databinding.ActivityHttpsBinding;
import com.nelson.glidetest.model.ResourceConfig;
import com.nelson.glidetest.network.SafeOkHttpClient;
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
        Glide.with(this)
                .load(ResourceConfig.IMAGE_HTTPS_URL)  // Https image hreflink
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .into(mBinding.ivUnsafe);
    }

    private void showSafe() {
        Glide.with(this)
                .load(ResourceConfig.IMAGE_HTTPS_URL)  // Https image hreflink
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model,
                            Target<GlideDrawable> target,
                            boolean isFirstResource) {
                        Log.e(TAG, "#onException() :" + e.toString());
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model,
                            Target<GlideDrawable> target, boolean isFromMemoryCache,
                            boolean isFirstResource) {
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
