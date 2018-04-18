package com.nelson.glidetest.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.widget.RemoteViews;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.AppWidgetTarget;
import com.nelson.glidetest.R;
import com.nelson.glidetest.model.ResourceConfig;

/**
 * App Widgets
 *
 * Created by Nelson on 16/12/16.
 */
public class GlideAppWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        RemoteViews rv = new RemoteViews(context.getPackageName(),
                R.layout.notification_remoteview);

        AppWidgetTarget appWidgetTarget = new AppWidgetTarget(context, rv,
                R.id.iv_notification_icon, appWidgetIds);
        Glide.with(context)
                .load(ResourceConfig.IMAGE_REMOTE_URLS[0])
                .asBitmap()
                .into(appWidgetTarget);

        pushWidgetUpdate(context, rv);
    }

    private void pushWidgetUpdate(Context context, RemoteViews rv) {
        ComponentName componentName = new ComponentName(context, GlideAppWidgetProvider.class);
        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        manager.updateAppWidget(componentName, rv);
    }

}
