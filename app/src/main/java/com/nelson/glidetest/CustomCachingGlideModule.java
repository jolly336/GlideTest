package com.nelson.glidetest;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.*;
import com.bumptech.glide.module.GlideModule;

/**
 * Glide内部使用了{@link MemorySizeCalculator} 类去决定内存缓存大小以及bitmap的缓存池，bitmap池维护了app的堆中的图像分配，
 * 正确的bitmap池是十分必要的，因为它避免了很多的图像重复回收，这样可以确保垃圾回收器的管理更加合理
 * Created by Zihuatanejo on 16/12/19.
 */
public class CustomCachingGlideModule implements GlideModule {

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {

        MemorySizeCalculator calculator = new MemorySizeCalculator(context);
        int defaultMemoryCacheSize = calculator.getMemoryCacheSize();
        int defaultBitmapPoolSize = calculator.getBitmapPoolSize();

        int customMemoryCacheSize = (int) (1.2 * defaultMemoryCacheSize);
        int customBitmapPoolSize = (int) (1.2 * defaultBitmapPoolSize);

        /**
         * 注：
         * 我们不能直接设置大小，我们需要创建一个LruResourceCache和LruBitmapPool的实例，这两个都是Glide的默认实现。
         * 因此，如果你仅仅想要调整大小，就可以继续使用它们通过传两个不同大小的值给构造函数
         */
        builder.setMemoryCache(new LruResourceCache(customMemoryCacheSize));
        builder.setBitmapPool(new LruBitmapPool(customBitmapPoolSize));

        //-------自定义磁盘缓存--------------------
        // set size & external vs. internal
        int cacheSize100MegaBytes = 104857600;//100MB
//        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, cacheSize100MegaBytes));
        //builder.setDiskCache(new ExternalCacheDiskCacheFactory(context, cacheSize100MegaBytes));

        //设置磁盘缓存到指定的目录，要使用DiskLruCacheFactory
        // or any other path
        String downloadDirectoryPath = Environment.getDownloadCacheDirectory().getPath();
//        builder.setDiskCache(new DiskLruCacheFactory(downloadDirectoryPath, cacheSize100MegaBytes));

        // In case you want to specify a cache sub folder(i.e. "glidecache"):
        Log.e("glide", "cache path : " + downloadDirectoryPath);
        builder.setDiskCache(new DiskLruCacheFactory(downloadDirectoryPath, "glidecache", customMemoryCacheSize));


    }

    @Override
    public void registerComponents(Context context, Glide glide) {
        // nothing to do here
    }
}
