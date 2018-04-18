package com.nelson.glidetest.transformation;

import android.content.Context;
import android.graphics.Bitmap;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

/**
 * 用Renderscript来模糊图片
 *
 * Created by Nelson on 16/12/19.
 */
public class BlurTransformation extends BitmapTransformation {

    private final RenderScript rs;

    public BlurTransformation(Context context) {
        super(context);

        rs = RenderScript.create(context);
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        Bitmap blurredBitmap = toTransform.copy(Bitmap.Config.ARGB_8888, true);

        //Allocate memory for Renderscript to work with
        Allocation input = Allocation.createFromBitmap(
                rs,
                blurredBitmap,
                Allocation.MipmapControl.MIPMAP_FULL,
                Allocation.USAGE_SHARED);

        Allocation output = Allocation.createTyped(rs, input.getType());

        //Load up an instance of the specific script that we want to use.
        ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        script.setInput(input);

        //Set the blur radius
        script.setRadius(10);

        //Start the ScriptIntrinisicBlur
        script.forEach(output);

        //Copy the output to the blurred bitmap
        output.copyTo(blurredBitmap);

        toTransform.recycle();

        return blurredBitmap;
    }

    @Override
    public String getId() {
        // 描述了这个转换的唯一标识符，Glide使用该键作为缓存系统的一部分，为了避免意外的问题，你需要确保它是唯一的！！！
        return "blur";
    }
}
