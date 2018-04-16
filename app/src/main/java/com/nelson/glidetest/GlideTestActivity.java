package com.nelson.glidetest;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;
import com.nelson.glidetest.activity.ImageListActivity;

/**
 * Created by Nelson on 2018/3/15.
 */

public class GlideTestActivity extends AppCompatActivity {

    private ImageView targetImageView;
    private ImageView lowImageView_1;
    private ImageView lowImageView_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide_test);

        targetImageView = (ImageView) findViewById(R.id.iv_imge);
        lowImageView_1 = (ImageView) findViewById(R.id.iv_low_1);
        lowImageView_2 = (ImageView) findViewById(R.id.iv_low_2);



        //-----9、custom view target--------------
//        loadImageViewTarget();

        //-----10、glide code debug--------------

        //在终端打开Log开关，即可看到日志adb shell setprop log.tag.GenericRequest DEBUG

//        RequestListener<String, GlideDrawable> requestListener = new RequestListener<String, GlideDrawable>() {
//            @Override
//            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
//                Toast.makeText(MainActivity.this, "图片加载失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
//                return false;
//            }
//
//            @Override
//            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
//                Toast.makeText(MainActivity.this, "图片已加载成成功！！！", Toast.LENGTH_SHORT).show();
//                return false;
//            }
//        };
//
//        Glide.with(this)
//                .load("http://i.imgur.com/rT5vXE1_dasdasdasd.jpg")
//                .listener(requestListener)
//                .error(R.mipmap.ic_launcher)
//                .into(targetImageView);

        //-----11、custom transformation，使用了转换后就不能在使用.centerCrop()和.fitCenter()了！！！--------------
//        Glide.with(this)
//                .load("http://i.imgur.com/rT5vXE1.jpg")
////                .transform(new IBlurTransformation(this))
//                //.bitmapTransform(new IBlurTransformation(this))  //this would work too!
//                .bitmapTransform(new BlurTransformation(this, 25, 1), new CropCircleTransformation(this))
//                .into(targetImageView);

        //-----11、animate()动画--------------

//        ViewPropertyAnimation.Animator animator = new ViewPropertyAnimation.Animator() {
//            @Override
//            public void animate(View view) {
//                //if it's a custom view class,cast it here then find subviews and do the animations here,
//                //we just use the entire view for the fade animation
//                view.setAlpha(0f);
//
//                ObjectAnimator fadeAnim = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f);
//                fadeAnim.setDuration(2500);
//                fadeAnim.start();
//            }
//        };
//
//
//        Glide.with(this)
//                .load("http://i.imgur.com/rT5vXE1.jpg")
////                .animate(android.R.anim.slide_in_left) //slide left to right
////                .animate(R.anim.anim_zoom_in) //zoom in
//                .animate(animator)
//                .into(targetImageView);

        //-----12、用Glide Module 自定义Glide--------------
        //自定义GlideModule类，来改变Glide重要的核心组件，比如：改变磁盘缓存，内存缓存等！

        //-----13、self-signed 自签名 HTTPS认证--------------
//        Glide.with(this)
//                .load("https://ohsoc622i.qnssl.com/grren.jpg")  //Https image hreflink
//                .error(R.mipmap.ic_launcher)
//                .into(targetImageView);

        //-----14、自定义内存缓存--------------
        //见：CustomCachingGlideModule类

        //-----15、用自定义尺寸优化加载的图片(创建一个新的接口，来考虑增加宽度和高度)--------------
        // previous way: we directly accessed the images,https://futurestud.io/images/logo.png
        // new way,server could handle additional parameter and provider the image in a specific size
        // in this case,the server would serve the image in 400x300 pixel size;https://futurestud.io/images/logo.png?w=400&h=300
//        String baseImageUrl = "http://i.imgur.com/rT5vXE1.jpg";
//        CustomImageSizeModelFuture customImageRequest = new CustomImageSizeModelFuture(baseImageUrl);
//        Glide.with(this)
//                .load(customImageRequest)
//                .into(targetImageView);

        //-----16、动态使用Model Loader(从AndroidManifest.xml中删除你的Glide Module，可为单个请求设置Glide Module，使用.using()方法)--------------
//        String baseImageUrl = "http://i.imgur.com/rT5vXE1.jpg";
//        CustomImageSizeModelFuture customImageRequest = new CustomImageSizeModelFuture(baseImageUrl);
//        Glide.with(this)
//                .using(new CustomImageSizeUrlLoader(this))
//                .load(customImageRequest)
//                .into(targetImageView);

        //-----17、如何旋转图片--------------
//        loadImageOriginal();
        loadImageRotated();

    }

    private void loadImageRotated() {
        Glide.with(this)
                .load("http://i.imgur.com/rT5vXE1.jpg")
                .animate(R.anim.anim_zoom_in)
                .transform(new RotateTransformation(this, 90f))
                .into(targetImageView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_image_list:
                jump2Act(ImageListActivity.class);
                break;
            case R.id.action_load_progress:
                jump2Act(LoadProgressActivity.class);
                break;
        }
        return true;
    }


    private void jump2Act(Class<? extends Activity> clazz) {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(this, clazz));
        startActivity(intent);
    }

}
