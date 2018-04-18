package com.nelson.glidetest;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.nelson.glidetest.databinding.ActivityAboutBinding;
import com.nelson.glidetest.view.WebViewWrapper;

/**
 * About me
 *
 * Created by Nelson on 2018/4/18.
 */

public class AboutActivity extends BaseActivity {

    WebViewWrapper webViewWrapper;
    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbarLayout;
    ImageView ivNature;
    TextView tvTitle;
    AppBarLayout appBarLayout;

    private String url = "file:///android_asset/about.html";
    private ActivityAboutBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarTranslucent(false);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_about);
        initView();
        initToolBar();
        webViewWrapper.loadUrl(url);
    }

    private void initView() {
        webViewWrapper = mBinding.webViewWrapper;
        toolbar = mBinding.toolbar;
        collapsingToolbarLayout = mBinding.collapsingToolbarLayout;
        ivNature = mBinding.ivNature;
        tvTitle = mBinding.tvTitle;
        appBarLayout = mBinding.appBarLayout;
    }

    private void initToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        collapsingToolbarLayout.setTitle("");
        toolbar.setTitle("");
        collapsingToolbarLayout.setExpandedTitleColor(
                getResources().getColor(R.color.transparent));//设置还没收缩时状态下字体颜色
        collapsingToolbarLayout.setCollapsedTitleTextColor(
                getResources().getColor(R.color.transparent));//设置收缩后Toolbar上字体的颜色
        tvTitle.setText(getString(R.string.app_name));
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBar, int offset) {
                tvTitle.setAlpha(Math.abs(offset * 1f / appBar.getTotalScrollRange()));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        webViewWrapper.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        webViewWrapper.onPause();
    }

    @Override
    protected void onDestroy() {
        webViewWrapper.onDestroy();
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webViewWrapper.goBack()) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
