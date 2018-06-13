package com.nelson.glidetest.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.RemoteViews;
import com.bumptech.glide.request.target.NotificationTarget;
import com.nelson.glidetest.BaseActivity;
import com.nelson.glidetest.R;
import com.nelson.glidetest.model.ResourceConfig;
import com.nelson.glidetest.network.okhttp.GlideApp;

/**
 * 加载图片到通知栏和应用小部件中
 *
 * Created by Nelson on 2018/4/13.
 */

public class NotificationTargetActivity extends BaseActivity {

    private static final int ID_NOTIFICATION = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("NotificationTarget");
        DataBindingUtil.setContentView(this, R.layout.activity_notification_target);
    }

    public void notification(View view) {
        triggerNotification();
    }

    private void triggerNotification() {
        //build remoteView
        RemoteViews rv = new RemoteViews(this.getPackageName(), R.layout.notification_remoteview);
        rv.setImageViewResource(R.id.iv_notification_icon, R.drawable.ic_launcher);
        rv.setTextViewText(R.id.tv_notification_headline, "Headline");
        rv.setTextViewText(R.id.tv_notification_short_msg, "Short Message");
        rv.setProgressBar(R.id.progressbar, 100, 10, false);

        //build notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle("Content Title")
                .setContentText("Content Text")
                .setContent(rv)
                .setPriority(NotificationCompat.PRIORITY_MIN);

        Notification notification = builder.build();

        //set big content view for newer adndroids
        if (Build.VERSION.SDK_INT >= 16) {
            notification.bigContentView = rv;
        }

        NotificationManager notificationManager = (NotificationManager) this.getSystemService(
                Context.NOTIFICATION_SERVICE);
        notificationManager.notify(ID_NOTIFICATION, notification);

        //build target provided by glide
        NotificationTarget notificationTarget = new NotificationTarget(this,
                R.id.iv_notification_icon, rv, notification, ID_NOTIFICATION);

        GlideApp.with(this)
                .asBitmap()
                .load(ResourceConfig.IMAGE_REMOTE_URLS[0])
                .into(notificationTarget);
    }

}
