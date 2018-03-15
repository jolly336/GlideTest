package com.nelson.glidetest;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Zihuatanejo on 16/12/15.
 */
public class CustomView extends FrameLayout {

    private TextView tv;
    private ImageView iv;

    public CustomView(Context context) {
        this(context, null);
    }

    public CustomView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    private void initialize() {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.view_custom_target, this);
        tv = (TextView) rootView.findViewById(R.id.tv_text);
        iv = (ImageView) rootView.findViewById(R.id.iv_img);
    }

    public void setImage(Drawable drawable) {
        iv.setImageDrawable(drawable);
    }

}
