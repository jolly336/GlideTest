package com.nelson.glidetest.model;

import android.os.Environment;

/**
 * Created by Nelson on 2018/3/16.
 */

public class ResourceConfig {

    public static final String GIF_REMOTE_URL = "http://opkjcw4sd.bkt.clouddn.com/wind_blowing_leaf.gif";

    // This is my phone local path,you can replace this with yours.
    public static final String IMAGE_LOCAL_PATH =
            Environment.getExternalStorageDirectory() + "/" + "beijing.jpg";

    public static final String VIDEO_LOCAL_PATH =
            Environment.getExternalStorageDirectory() + "/" + "zhangjiajie.mp4";


    public static final String[] IMAGE_REMOTE_URLS = {
            "http://opkjcw4sd.bkt.clouddn.com/anthony-intraversato-257182.jpg",
            "http://opkjcw4sd.bkt.clouddn.com/photo-1449034446853-66c86144b0ad.jpeg",
            "http://opkjcw4sd.bkt.clouddn.com/brooke-lark-158017.jpg",
            "http://opkjcw4sd.bkt.clouddn.com/brooke-lark-254998.jpg",
            "http://opkjcw4sd.bkt.clouddn.com/christian-joudrey-96208.jpg",
            "http://opkjcw4sd.bkt.clouddn.com/clement-h-544786.jpg",
            "http://opkjcw4sd.bkt.clouddn.com/kevin-noble-524437.jpg",
            "http://opkjcw4sd.bkt.clouddn.com/photo-1483168527879-c66136b56105.jpeg",
            "http://opkjcw4sd.bkt.clouddn.com/photo-1484893341013-5f1d9c91a96e.jpeg",
    };

}
