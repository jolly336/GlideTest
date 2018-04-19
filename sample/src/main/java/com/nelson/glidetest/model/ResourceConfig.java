package com.nelson.glidetest.model;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;

/**
 * Created by Nelson on 2018/3/16.
 */

public class ResourceConfig {

    public static final String ANDROID_RESOURCE = "android.resource://";

    public static final String FOREWARD_SLASH = "/";

    public static final String GIF_REMOTE_URL = "http://opkjcw4sd.bkt.clouddn.com/wind_blowing_leaf.gif";

    // This is my phone local path,you can replace this with yours.
    public static final String IMAGE_LOCAL_PATH =
            Environment.getExternalStorageDirectory() + FOREWARD_SLASH + "beijing.jpg";

    public static final String VIDEO_LOCAL_PATH =
            Environment.getExternalStorageDirectory() + "/" + "zhangjiajie.mp4";

    public static final String IMAGE_HTTPS_URL = "https://travel.12306.cn/imgs/resources/uploadfiles/images/a9b9c76d-36ba-4e4a-8e02-9e6a1a991da0_news_W540_H300.jpg";

    public static final String[] IMAGE_REMOTE_URLS = {
            "http://opkjcw4sd.bkt.clouddn.com/unsplash_01.jpeg",
            "http://opkjcw4sd.bkt.clouddn.com/unsplash_02.jpeg",
            "http://opkjcw4sd.bkt.clouddn.com/unsplash_03.jpeg",
            "http://opkjcw4sd.bkt.clouddn.com/unsplash_04.jpeg",
            "http://opkjcw4sd.bkt.clouddn.com/unsplash_05.jpeg",
            "http://opkjcw4sd.bkt.clouddn.com/unsplash_06.jpeg",
            "http://opkjcw4sd.bkt.clouddn.com/unsplash_07.jpeg",
            "http://opkjcw4sd.bkt.clouddn.com/unsplash_08.jpeg",
            "http://opkjcw4sd.bkt.clouddn.com/unsplash_09.jpeg",
            "http://opkjcw4sd.bkt.clouddn.com/unsplash_10.jpeg",
    };

    public static Uri resourceIdToUri(Context context, int resourceId) {
        return Uri.parse(ANDROID_RESOURCE + context.getPackageName() + FOREWARD_SLASH + resourceId);
    }

}
