package com.nelson.glidetest.model;

import android.os.Environment;

/**
 * Created by Nelson on 2018/3/16.
 */

public class Resource {

    public static final String IMAGE_URL_IMAGE = "http://opkjcw4sd.bkt.clouddn.com/2017.jpg";

    public static final String IMAGE_URL_GIF = "http://7xrdbm.com1.z0.glb.clouddn.com/%E4%BA%BA%E4%BA%BA%E8%BD%A6%E6%BC%94%E7%A4%BA%E5%9B%BE.gif";

    public static final String IMAGE_LOCAL_PATH =
            Environment.getExternalStorageDirectory() + "/DCIM/Camera/123.jpg";

    // This is my phone local path,you can replace this with yours.
    public static final String VIDEO_LOCAL_PATH =
            Environment.getExternalStorageDirectory() + "/DCIM/Camera/VID_20170921_152145.mp4";

}
