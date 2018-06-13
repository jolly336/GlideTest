package com.nelson.glidetest.transformation;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader.TileMode;
import android.support.annotation.NonNull;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import java.nio.charset.Charset;
import java.security.MessageDigest;

/**
 * Created by Nelson on 2018/1/22.
 */

public class CircleCropTransformation extends BitmapTransformation {

    private static final String ID = "com.nelson.glidetest.transformation.CircleCropTransformation";
    private static final byte[] ID_BYTES = ID.getBytes(Charset.forName("UTF-8"));

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        int diameter = Math.min(toTransform.getWidth(), toTransform.getHeight());
        Bitmap toReuse = pool.get(outWidth, outHeight, Config.ARGB_8888);
        Bitmap result;
        if (toReuse != null) {
            result = toReuse;
        } else {
            result = Bitmap.createBitmap(diameter, diameter, Config.ARGB_8888);
        }

        int dx = (toTransform.getWidth() - diameter) / 2;
        int dy = (toTransform.getHeight() - diameter) / 2;
        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        BitmapShader shader = new BitmapShader(toTransform, TileMode.CLAMP, TileMode.CLAMP);
        if (dx != 0 || dy != 0) {
            Matrix matrix = new Matrix();
            matrix.setTranslate(-dx, -dy);
            shader.setLocalMatrix(matrix);
        }

        paint.setShader(shader);
        paint.setAntiAlias(true);
        float radius = diameter / 2f;
        canvas.drawCircle(radius, radius, radius, paint);

        if (!toReuse.equals(toTransform)) {
            toReuse.recycle();
            pool.put(toTransform);
        }

        return result;
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        // 描述了这个转换的唯一标识符，Glide使用该键作为缓存系统的一部分，为了避免意外的问题，你需要确保它是唯一的！！！
        messageDigest.update(ID_BYTES);
    }
}
