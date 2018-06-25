package com.nelson.glidetest.activity;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.RequestOptions;
import com.nelson.glidetest.BaseActivity;
import com.nelson.glidetest.R;
import com.nelson.glidetest.databinding.ActivityDownloadBinding;
import com.nelson.glidetest.model.ResourceConfig;
import com.nelson.glidetest.utils.ThreadManager;
import java.io.File;

/**
 * Created by Nelson on 2018/6/25.
 */
public class DownloadActivity extends BaseActivity {

    private ActivityDownloadBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Download");

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_download);

        mBinding.btnDownloadFile.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                showDownloadFile();
            }
        });

        mBinding.btnDownloadBitmap.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                showDownloadBitmap();
            }
        });
    }

    private void showDownloadFile() {

        String url = ResourceConfig.IMAGE_REMOTE_URLS[8];

        RequestOptions options = new RequestOptions()
                .error(R.drawable.error)
                .placeholder(R.drawable.placeholder);

        final FutureTarget<File> target = Glide.with(this)
                .asFile()
                .load(url)
                .apply(options)
                .submit();

        ThreadManager.runOnThreadPool(new Runnable() {
            @Override
            public void run() {
                try {
                    final File imageFile = target.get();
                    toast(imageFile.getPath());
                    updateMessage(imageFile.getPath());
                } catch (final Exception e) {
                    toast(e.getMessage());
                }
            }
        });
    }


    private void showDownloadBitmap() {
        String url = ResourceConfig.IMAGE_REMOTE_URLS[8];

        final FutureTarget<Bitmap> target = Glide.with(this)
                .asBitmap()
                .load(url)
                .submit();

        ThreadManager.runOnThreadPool(new Runnable() {
            @Override
            public void run() {
                try {
                    Bitmap bitmap = target.get();
                    String format = String.format("Bitmpap: width = %d , height = %d",
                            bitmap.getWidth(), bitmap.getHeight());
                    toast(format);
                    updateMessage(format);
                } catch (Exception e) {
                    toast(e.getMessage());
                }
            }
        });
    }

    private void updateMessage(final String update) {
        ThreadManager.runOnMainThread(new Runnable() {
            @Override
            public void run() {
                mBinding.tvMessage.setText(update);
            }
        });
    }

    private void toast(final String message) {
        ThreadManager.runOnMainThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(DownloadActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
