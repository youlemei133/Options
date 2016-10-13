package com.chiigu.testyaotia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by hudawei on 2016/10/13.
 */
public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void click(View view){
        switch (view.getId()){
            case R.id.multiplyFormatLayout:
                startActivity(new Intent(MainActivity.this,MutiplyFormatLayoutActivity.class));
                break;
            case R.id.optionCircleView:
                startActivity(new Intent(MainActivity.this,OptionCircleViewActivity.class));
                break;
            case R.id.gesture:
                Intent intent=new Intent(MainActivity.this,GestureActivity.class);
                intent.putExtra("imageUri","http://pic.4j4j.cn/upload/pic/20130815/31e652fe2d.jpg");
                startActivity(intent);
                break;
            case R.id.optionCardView:
                startActivity(new Intent(MainActivity.this,OptionCardViewActivity.class));
                break;
            case R.id.optionsCardViewGroup:
                startActivity(new Intent(MainActivity.this,OptionsCardViewGroupActivity.class));
                break;
        }
    }
}
