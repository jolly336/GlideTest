package com.nelson.glidetest.glidemodule;

/**
 * Created by Nelson on 16/12/19.
 */
public class CustomImageSizeModelFuture implements CustomImageSizeModel {

    String baseImageUrl;

    public CustomImageSizeModelFuture(String baseImageUrl) {
        this.baseImageUrl = baseImageUrl;
    }


    @Override
    public String requestCustomSizeUrl(int width, int height) {
        return baseImageUrl + "?w=" + width + "&h=" + height;
    }
}
