package com.nelson.glidetest.model;

import android.os.Environment;

/**
 * Created by Nelson on 2018/3/16.
 */

public class ResourceConfig {

    public static final String IMAGE_REMOTE_URL = "http://opkjcw4sd.bkt.clouddn.com/2017.jpg";

    public static final String GIF_REMOTE_URL = "http://opkjcw4sd.bkt.clouddn.com/wind_blowing_leaf.gif";

    // This is my phone local path,you can replace this with yours.
    public static final String IMAGE_LOCAL_PATH =
            Environment.getExternalStorageDirectory() + "/" + "beijing.jpg";

    public static final String VIDEO_LOCAL_PATH =
            Environment.getExternalStorageDirectory() + "/" + "zhangjiajie.mp4";

}
