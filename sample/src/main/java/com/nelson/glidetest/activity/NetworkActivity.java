package com.nelson.glidetest.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import com.nelson.glidetest.BaseActivity;
import com.nelson.glidetest.R;
import com.nelson.glidetest.databinding.ActivityNetworkBinding;

/**
 * 集成网络栈
 * Glide为2个网络库提供了实现：Volley和OkHttp，
 * 2者只需要在module的build.gradle文件中添加依赖即可，Gradle会自动合并必要的GlideModule到你的Android.Manifest，
 * Glide会认可在manifest中的存在，然后使用OkHttp做到所有的网络连接。
 *
 * 警告：如果你把这2个库都添加在你的build.gradle声明了，那这两个库都会被添加，因为Glide没有任何特殊的加载顺序，
 * 并不确定使用哪个库，你将会有一个不稳定的状态，所以确保你只添加了一个集成库。
 *
 * @see <a href="https://github.com/bumptech/glide/wiki/Integration-Libraries">
 *
 * Created by Nelson on 2018/4/13.
 */

public class NetworkActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Network");
        ActivityNetworkBinding binding = DataBindingUtil
                .setContentView(this, R.layout.activity_network);
        binding.tvWarning.setText(Html.fromHtml(getString(R.string.network_warning)));
    }
}
