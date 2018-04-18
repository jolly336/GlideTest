package com.nelson.glidetest.handler;

import android.content.Context;
import android.content.Intent;
import com.nelson.glidetest.model.RecyclerItem;

/**
 * Created by Nelson on 2018/1/29.
 */

public class Presenter {

    public void onTypeClick(Context context, RecyclerItem recyclerItem) {
        Intent intent = new Intent(recyclerItem.getAction());
        context.startActivity(intent);
    }

}
