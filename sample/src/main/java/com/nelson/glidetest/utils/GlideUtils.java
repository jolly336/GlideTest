package com.nelson.glidetest.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.target.ViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.nelson.glidetest.network.okhttp.GlideApp;

/**
 * A class responsibility for loading image with Glide engine.
 *
 * Created by Nelson on 2018/6/20.
 */
public final class GlideUtils {


    private GlideUtils() {

    }

    public static void loadImage(Context context, String path, ImageView imageView,
            RequestOptions options) {

        Glide.with(context)
                .load(path)
                .apply(options)
                .into(imageView);
    }

    public static void loadImage(Context context, ImageView imageView, String path,
            int defaultResId) {

        RequestOptions options = new RequestOptions()
                .placeholder(defaultResId)
                .error(defaultResId)
                .priority(Priority.NORMAL)
                .centerCrop();

        loadImage(context, path, imageView, options);
    }

    public static void loadImage(Context context, ImageView imageView, String path,
            Drawable defaultDrawable) {

        RequestOptions options = new RequestOptions()
                .placeholder(defaultDrawable)
                .error(defaultDrawable)
                .priority(Priority.NORMAL)
                .centerCrop();

        loadImage(context, path, imageView, options);
    }

    @SuppressWarnings("unchecked")
    public static void loadImage(Context context, final View view, String path,
            Drawable defaultDrawable, TargetLoadListener listener) {

        RequestOptions options = new RequestOptions()
                .placeholder(defaultDrawable)
                .error(defaultDrawable);

        Glide.with(context)
                .load(path)
                .apply(options)
                .into(new GlideTargetLoadListener(view, listener));
    }

    @SuppressWarnings("unchecked")
    public static void loadImage(Context context, final View view, String path,
            int defaultResId, TargetLoadListener listener) {

        RequestOptions options = new RequestOptions()
                .placeholder(defaultResId)
                .error(defaultResId);

        Glide.with(context)
                .load(path)
                .apply(options)
                .into(new GlideTargetLoadListener(view, listener));
    }

    @SuppressWarnings("unchecked")
    public static void loadImage(Context context, final ImageView view, String path,
            Drawable defaultDrawable, ImageLoadListener listener) {

        RequestOptions options = new RequestOptions()
                .placeholder(defaultDrawable)
                .error(defaultDrawable);

        Glide.with(context)
                .load(path)
                .listener(new GlideLoadListener(listener))
                .apply(options)
                .into(view);
    }

    /**
     * load image and cache source data of image
     */
    public static void loadImageUsingCacheAll(Context context, ImageView imageView, String path,
            Drawable defaultDrawable) {

        GlideApp.with(context)
                .load(path)
                .placeholder(defaultDrawable)
                .error(defaultDrawable)
                .centerCrop()
                .cacheSource()
                .into(imageView);
    }

    public static void loadImageWithTransformation(Context context, ImageView imageView,
            String path, int defaultResId, Transformation<Bitmap>... transformations) {

        RequestOptions options = new RequestOptions()
                .placeholder(defaultResId)
                .error(defaultResId)
                .transforms(transformations);

        loadImage(context, path, imageView, options);
    }

    public static void loadImageWithoutTransform(Context context, String path,
            ImageView imageView, Drawable defaultDrawable) {

        RequestOptions options = new RequestOptions()
                .placeholder(defaultDrawable)
                .error(defaultDrawable)
                .dontTransform();

        loadImage(context, path, imageView, options);
    }

    public static void cancelAllTasks(Context context) {
        Glide.with(context).pauseRequests();
    }

    public static void resumeAllTasks(Context context) {
        Glide.with(context).resumeRequests();
    }

    public static void clearMemory(Context context) {
        Glide.get(context).clearMemory();
    }

    public static void clearDiskCache(final Context context) {
        ThreadManager.runOnThreadPool(new Runnable() {
            @Override
            public void run() {
                Glide.get(context).clearDiskCache();
            }
        });
    }

    /**
     * Clear all cache including memory and disk.
     */
    public static void cleanAll(Context context) {
        clearMemory(context);
        clearDiskCache(context);
    }

    static class GlideLoadListener<R> implements RequestListener<R> {

        ImageLoadListener mListener;

        GlideLoadListener(ImageLoadListener<R> listener) {
            this.mListener = listener;
        }

        @Override
        public boolean onResourceReady(R resource, Object model, Target<R> target,
                DataSource dataSource, boolean isFirstResource) {
            if (mListener != null) {
                mListener.onLoadComplete(model, resource);
            }
            return false;
        }

        @Override
        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<R> target,
                boolean isFirstResource) {
            if (mListener != null) {
                mListener.onLoadFailed(model, e);
            }
            return false;
        }
    }

    static class GlideTargetLoadListener<T extends View, R> extends ViewTarget<T, R> {

        TargetLoadListener<R> mListener;

        public GlideTargetLoadListener(@NonNull T view, TargetLoadListener<R> listener) {
            super(view);
            this.mListener = listener;
        }

        @Override
        public void onResourceReady(@NonNull R resource,
                @Nullable Transition<? super R> transition) {
            if (mListener != null) {
                mListener.onResourceReady(resource);
            }
        }
    }

    public interface ImageLoadListener<R> {

        void onLoadComplete(Object model, R resource);

        void onLoadFailed(Object model, Exception e);
    }

    public interface TargetLoadListener<R> {

        void onResourceReady(@NonNull R resource);
    }
}
