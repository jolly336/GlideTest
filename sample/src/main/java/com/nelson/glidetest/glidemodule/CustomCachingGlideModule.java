package com.nelson.glidetest.glidemodule;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.module.GlideModule;

/**
 * Glide内部使用了{@link MemorySizeCalculator} 类去决定内存缓存大小以及bitmap的缓存池，bitmap池维护了app的堆中的图像分配，
 * 正确的bitmap池是十分必要的，因为它避免了很多的图像重复回收，这样可以确保垃圾回收器的管理更加合理
 *
 * Created by Nelson on 16/12/19.
 */
public class CustomCachingGlideModule implements GlideModule {

    /**
     * 100MB
     */
    public static final int DISK_CACHE_SIZE = 100 * 1024 * 1024;

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {

        MemorySizeCalculator calculator = new MemorySizeCalculator.Builder(context)
                // 设置MemoryCache能容纳的像素值的设备屏幕数，也就是缓存多少屏图片，默认是2.
                .setMemoryCacheScreens(2)
                .setBitmapPoolScreens(2)
                .build();


        int defaultMemoryCacheSize = calculator.getMemoryCacheSize();
        int defaultBitmapPoolSize = calculator.getBitmapPoolSize();

        // app需要多余20%的缓存作为Glide的默认值
        int customMemoryCacheSize = (int) (1.2 * defaultMemoryCacheSize);
        int customBitmapPoolSize = (int) (1.2 * defaultBitmapPoolSize);

        /**
         * 注：
         * 我们不能直接设置大小，我们需要创建一个LruResourceCache和LruBitmapPool的实例，这两个都是Glide的默认实现。
         * 因此，如果你仅仅想要调整大小，就可以继续使用它们通过传两个不同大小的值给构造函数
         */
        // 内存缓存策略
        builder.setMemoryCache(new LruResourceCache(customMemoryCacheSize));
        // Bitmap缓存池
        builder.setBitmapPool(new LruBitmapPool(customBitmapPoolSize));

        // 加载图片的解码模式，默认配置是RGB_565
        //builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);

        //-------自定义磁盘缓存--------------------
        // 读取缓存中图片的异步执行器，默认配置是FifoPriorityThreadPoolExecutor，先入先出原则
        //builder.setDiskCacheService(new FifoPriorityThreadPoolExecutor(1));

        // 读取非缓存中图片的执行器，默认配置也是FifoPriorityThreadPoolExecutor
        //builder.setResizeService(new FifoPriorityThreadPoolExecutor(1));

        // set size & external vs. internal
        // 默认InternalCacheDiskCacheFactory内部存储，可以设置缓存到SD卡上；默认2者磁盘缓存大小都是250MB
        //builder.setDiskCache(new InternalCacheDiskCacheFactory(context, DISK_CACHE_SIZE));

        // ExternalCacheDiskCacheFactory默认缓存路径：sdcard/Android/包名/cache/image_manager_disk_cache目录中
        //builder.setDiskCache(new ExternalCacheDiskCacheFactory(context, DISK_CACHE_SIZE));

        //设置磁盘缓存到指定的目录，要使用DiskLruCacheFactory
        // or any other path
        String downloadDirectoryPath = Environment.getDownloadCacheDirectory().getPath();
        //builder.setDiskCache(new DiskLruCacheFactory(downloadDirectoryPath, cacheSize100MegaBytes));

        // In case you want to specify a cache sub folder(i.e. "glidecache"):
        Log.e("glide", "cache path : " + downloadDirectoryPath);
        builder.setDiskCache(new DiskLruCacheFactory(downloadDirectoryPath, "glidecache",
                customMemoryCacheSize));


    }

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide,
            @NonNull Registry registry) {
        // nothing to do here

    }
}
