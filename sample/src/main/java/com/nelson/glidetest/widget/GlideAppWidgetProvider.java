package com.nelson.glidetest.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.widget.RemoteViews;
import com.bumptech.glide.request.target.AppWidgetTarget;
import com.bumptech.glide.request.target.Target;
import com.nelson.glidetest.R;
import com.nelson.glidetest.model.ResourceConfig;
import com.nelson.glidetest.network.okhttp.GlideApp;

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

        AppWidgetTarget appWidgetTarget = new AppWidgetTarget(context, Target.SIZE_ORIGINAL,
                Target.SIZE_ORIGINAL,
                R.layout.notification_remoteview, rv,
                appWidgetIds);

        GlideApp.with(context)
                .asBitmap()
                .load(ResourceConfig.IMAGE_REMOTE_URLS[0])
                .into(appWidgetTarget);

        pushWidgetUpdate(context, rv);
    }

    private void pushWidgetUpdate(Context context, RemoteViews rv) {
        ComponentName componentName = new ComponentName(context, GlideAppWidgetProvider.class);
        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        manager.updateAppWidget(componentName, rv);
    }

}
