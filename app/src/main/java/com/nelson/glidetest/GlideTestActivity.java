package com.nelson.glidetest;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.nelson.glidetest.activity.ImageListActivity;
import com.nelson.glidetest.transformation.RotateTransformation;

/**
 * Created by Nelson on 2018/3/15.
 */

public class GlideTestActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide_test);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        jump2Act(LoadProgressActivity.class);
        return true;
    }


    private void jump2Act(Class<? extends Activity> clazz) {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(this, clazz));
        startActivity(intent);
    }

}
