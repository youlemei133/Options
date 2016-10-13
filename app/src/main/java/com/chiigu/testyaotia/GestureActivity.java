package com.chiigu.testyaotia;

import android.app.Activity;
import android.os.Bundle;

import com.chiigu.testyaotia.multiformatviews.PinchImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by hudawei on 2016/10/12.
 */
public class GestureActivity extends Activity {
    private PinchImageView pinchImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture);
        pinchImageView=(PinchImageView)findViewById(R.id.pinchImageView);
        String imageUri=getIntent().getStringExtra("imageUri");
        ImageLoader.getInstance().displayImage(imageUri,pinchImageView);
    }
}
