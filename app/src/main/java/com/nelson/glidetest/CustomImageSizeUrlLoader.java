package com.nelson.glidetest;

import android.content.Context;
import com.bumptech.glide.load.model.stream.BaseGlideUrlLoader;

/**
 * Created by Zihuatanejo on 16/12/19.
 */
public class CustomImageSizeUrlLoader extends BaseGlideUrlLoader<CustomImageSizeModel> {

    public CustomImageSizeUrlLoader(Context context) {
        super(context);
    }

    @Override
    protected String getUrl(CustomImageSizeModel model, int width, int height) {
        return model.requestCustomSizeUrl(width, height);
    }

}
