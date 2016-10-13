package com.chiigu.testyaotia;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;

import com.chiigu.testyaotia.multiformatviews.LoaderImageView;
import com.chiigu.testyaotia.multiformatviews.MutiplyFormatLayout;
import com.chiigu.testyaotia.options.OptionCardView;
import com.chiigu.testyaotia.options.OptionCircleView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

/**
 * Created by hudawei on 2016/10/13.
 *
 */
public class OptionCardViewActivity extends Activity {
    private OptionCardView optionCardView;
    private OptionCircleView optionCircleView;
    private MutiplyFormatLayout mutiplyFormatLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option_card_view);
        optionCardView=(OptionCardView)findViewById(R.id.optionCardView);
        optionCardView.setImageLoader(createImageLoader());
        optionCardView.setStatus(OptionCircleView.OPTION_BASE);
        optionCardView.setSource("图片是指由图形、图像等构成的平面媒体。<img src='http://pic.4j4j.cn/upload/pic/20130815/31e652fe2d.jpg'/>图片的格式很多，但总体上可以分为点阵图和矢量图两大类，我们常用BMP、JPG等格式都是点阵图形，而SWF、CDR、AI等格式的图形属于矢量图形。<img src='http://pic.4j4j.cn/upload/pic/20130815/31e652fe2d.jpg'/>有形式的事物，我们看到的，是图画、照片、拓片等的统称。");
//        optionCardView.setIndex(1);
//        optionCircleView=(OptionCircleView)findViewById(R.id.optionCircleView);
//        optionCircleView.setOptionText("B");
//        mutiplyFormatLayout=(MutiplyFormatLayout)findViewById(R.id.mutiplyFormatLayout);
//        mutiplyFormatLayout.setImageLoader(createImageLoader());
//        mutiplyFormatLayout.setSource("图片是指由图形、图像等构成的平面媒体。<img src='http://pic.4j4j.cn/upload/pic/20130815/31e652fe2d.jpg'/>图片的格式很多，但总体上可以分为点阵图和矢量图两大类，我们常用BMP、JPG等格式都是点阵图形，而SWF、CDR、AI等格式的图形属于矢量图形。<img src='http://pic.4j4j.cn/upload/pic/20130815/31e652fe2d.jpg'/>有形式的事物，我们看到的，是图画、照片、拓片等的统称。");
    }

    private MutiplyFormatLayout.ImageLoader createImageLoader(){
        return new MutiplyFormatLayout.ImageLoader() {
            @Override
            public void disPlayImage(String uri, LoaderImageView imageView) {
                disPlayImageView(uri,imageView);
            }
        };
    }

    private void disPlayImageView(String uri, final LoaderImageView imageView){
        ImageLoader.getInstance().displayImage(uri, imageView.getImageView(), new SimpleImageLoadingListener(){
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                super.onLoadingStarted(imageUri, view);
                imageView.setStatus(LoaderImageView.STATUS_LOADING);

            }
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                super.onLoadingComplete(imageUri, view, loadedImage);
                imageView.setStatus(LoaderImageView.STATUS_SUC);
                imageView.setOnClickListener(new LoaderImageViewClickListener(LoaderImageView.STATUS_SUC,imageUri));
            }
            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                super.onLoadingFailed(imageUri, view, failReason);
                imageView.setStatus(LoaderImageView.STATUS_FAILED);
                imageView.setOnClickListener(new LoaderImageViewClickListener(LoaderImageView.STATUS_FAILED,imageUri));
            }
        });
    }



    class LoaderImageViewClickListener implements View.OnClickListener{
        private int status;
        private String uri;
        public LoaderImageViewClickListener(int status,String uri) {
            this.status = status;
            this.uri=uri;
        }

        @Override
        public void onClick(View v) {
            LoaderImageView view=(LoaderImageView) v;
            if(status==LoaderImageView.STATUS_FAILED){
                disPlayImageView(uri,view);
            }else if(status==LoaderImageView.STATUS_SUC){
                Intent intent=new Intent(OptionCardViewActivity.this,GestureActivity.class);
                intent.putExtra("imageUri",uri);
                startActivity(intent);
            }
        }
    }
}
