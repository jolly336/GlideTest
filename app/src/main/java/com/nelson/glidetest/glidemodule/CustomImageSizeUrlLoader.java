package com.nelson.glidetest.glidemodule;

import android.content.Context;
import com.bumptech.glide.load.model.stream.BaseGlideUrlLoader;

/**
 * Created by Nelson on 16/12/19.
 */
public class CustomImageSizeUrlLoader extends BaseGlideUrlLoader<CustomImageSizeModel> {

    public CustomImageSizeUrlLoader(Context context) {
        super(context);
    }

    @Override
    protected String getUrl(CustomImageSizeModel model, int width, int height) {
        // previous way: we directly accessed the images
        // https://futurestud.io/images/logo.png

        // new way, server could handle additional parameter and provide the image in a specific size
        // in this case, the server would serve the image in 400x300 pixel size
        // https://futurestud.io/images/logo.png?w=400&h=300
        return model.requestCustomSizeUrl(width, height);
    }

}
