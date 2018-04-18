package com.nelson.glidetest.utils;

import android.content.Context;
import android.net.Uri;

/**
 * Created by Nelson on 2018/3/16.
 */

public final class Util {

    public static final String ANDROID_RESOURCE = "android.resource://";
    public static final String FOREWARD_SLASH = "/";

    public static Uri resourceIdToUri(Context context, int resourceId) {
        return Uri.parse(ANDROID_RESOURCE + context.getPackageName() + FOREWARD_SLASH + resourceId);
    }
}
