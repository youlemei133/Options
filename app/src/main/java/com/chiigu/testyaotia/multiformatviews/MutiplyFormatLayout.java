package com.chiigu.testyaotia.multiformatviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chiigu.testyaotia.R;

import java.util.List;

/**
 * Created by hudawei on 2016/10/11.
 * //1. 解析文本
 * //2. 显示不同的内容
 *  解析规则 : 以"<"开始 以"/>"结束
 *  类型：TEXT IMG
 *  显示View
 *
 */
public class MutiplyFormatLayout extends LinearLayout {
    private Context context;
    private ImageLoader imageLoader;
    private int textColor;
    private float textSize;
    private float textMultSpace;

    private int imageLoadingViewResource;
    private int imageFailedViewResource;
    private int imageScaleType;
    private float imageMinHeight;


    public MutiplyFormatLayout(Context context) {
        super(context,null);
    }

    public MutiplyFormatLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context,attrs);
        setOrientation(VERTICAL);
    }

    private void initView(Context context, AttributeSet attrs) {
        this.context=context;
        TypedArray ta=context.obtainStyledAttributes(attrs,R.styleable.MutiplyFormatLayout);
        textColor=ta.getColor(R.styleable.MutiplyFormatLayout_mTextColor, Color.BLACK);
        textSize=ta.getDimension(R.styleable.MutiplyFormatLayout_mTextSize,dp2px(12,context));
        textSize=px2dp(textSize,context);
         textMultSpace =ta.getFloat(R.styleable.MutiplyFormatLayout_textMultSpace,1);
         imageLoadingViewResource=ta.getResourceId(R.styleable.MutiplyFormatLayout_imageLoadingView,0);
         imageFailedViewResource=ta.getResourceId(R.styleable.MutiplyFormatLayout_imageFailedView,0);
         imageScaleType=ta.getInt(R.styleable.MutiplyFormatLayout_imageScaleType,0);
        imageMinHeight=ta.getDimension(R.styleable.MutiplyFormatLayout_imageMinHeight,dp2px(100,context));
        ta.recycle();
    }

    public void setSource(String source){
        this.removeAllViews();
        List<FormatType> formatTypes=FormatParse.parseFormat(source);
        for(FormatType formatType:formatTypes){
            if(formatType!=null){
                switch (formatType.getType()){
                    case FormatType.IMG:
                        LoaderImageView imageView = FormatViewFactory.imgCreator(formatType, context);
                        setLoaderImageView(imageView);
                        this.addView(imageView);
                        if(imageLoader!=null){
                            imageLoader.disPlayImage(formatType.getContent(),imageView);
                        }
                        break;
                    case FormatType.TEXT:
                        TextView textView = FormatViewFactory.textCreator(formatType, context);
                        setTextView(textView);
                        this.addView(textView);
                        break;
                }
            }
        }
    }

    public void setImageLoader(ImageLoader loader){
        this.imageLoader=loader;
    }

    public interface ImageLoader{
        void disPlayImage(String uri,LoaderImageView imageView);
    }

    private int dp2px(float dpValue,Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * density + 0.5f);
    }
    public  int px2dp(float pxValue,Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / density + 0.5f);
    }
    private void setTextView(TextView textView){
        textView.setTextSize(textSize);
        textView.setTextColor(textColor);
        textView.setLineSpacing(0,textMultSpace);
    }
    private void setLoaderImageView(LoaderImageView loaderImageView){
        LayoutInflater inflater=LayoutInflater.from(context);
        if(imageLoadingViewResource!=0) {
            View imageLoadingView = inflater.inflate(imageLoadingViewResource, null);
            loaderImageView.addLoadingView(imageLoadingView);
        }
        if(imageFailedViewResource!=0) {
            View imageFaieldView = inflater.inflate(imageFailedViewResource, null);
            loaderImageView.addFailedView(imageFaieldView);
        }
        loaderImageView.setMinimumHeight((int)imageMinHeight);

        ImageView imageView=loaderImageView.getImageView();
        ImageView.ScaleType scaleType=null;
        switch (imageScaleType){
            case 0:
                scaleType=ImageView.ScaleType.MATRIX;
                break;
            case 1:
                scaleType=ImageView.ScaleType.FIT_XY;
                break;
            case 2:
                scaleType=ImageView.ScaleType.FIT_START;
                break;
            case 3:
                scaleType=ImageView.ScaleType.FIT_CENTER;
                break;
            case 4:
                scaleType=ImageView.ScaleType.FIT_END;
                break;
            case 5:
                scaleType=ImageView.ScaleType.CENTER;
                break;
            case 6:
                scaleType=ImageView.ScaleType.CENTER_CROP;
                break;
            case 7:
                scaleType=ImageView.ScaleType.CENTER_INSIDE;
                break;
        }
        imageView.setScaleType(scaleType);
    }
}
