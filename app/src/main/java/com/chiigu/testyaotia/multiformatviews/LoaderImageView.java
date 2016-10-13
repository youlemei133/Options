package com.chiigu.testyaotia.multiformatviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.chiigu.testyaotia.R;

/**
 * Created by hudawei on 2016/10/12.
 *
 */
public class LoaderImageView extends RelativeLayout{
    public static final int STATUS_LOADING=0;
    public static final int STATUS_SUC=1;
    public static final int STATUS_FAILED=2;
    private int mStatus;
    private View mFailedView;
    private View mLoadingView;
    private ImageView mImageView;

    public LoaderImageView(Context context) {
        this(context,null);
    }

    public LoaderImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray ta=context.obtainStyledAttributes(attrs, R.styleable.LoaderImageView);
        int failed_resource=ta.getResourceId(R.styleable.LoaderImageView_failedView,0);
        int loading_resource=ta.getResourceId(R.styleable.LoaderImageView_loadingView,0);
        ta.recycle();

        LayoutInflater inflater=LayoutInflater.from(context);
        if(failed_resource!=0)
            mFailedView=inflater.inflate(failed_resource,null);
        if(loading_resource!=0)
            mLoadingView=inflater.inflate(loading_resource,null);
        if(mFailedView==null)
            mFailedView=inflater.inflate(R.layout.item_failed,null);
        if(mLoadingView==null)
            mLoadingView=inflater.inflate(R.layout.item_loading,null);

        if(mFailedView!=null)
            addFailedView(mFailedView);
        if(mLoadingView!=null)
            addLoadingView(mLoadingView);

        mImageView=new ImageView(context);
        RelativeLayout.LayoutParams params=new LayoutParams(-1,-2);
        params.addRule(CENTER_IN_PARENT);
        this.addView(mImageView,params);
        setStatus(STATUS_LOADING);
    }

    public void setStatus(int stauts){
        switch (stauts){
            case STATUS_LOADING:
                mLoadingView.setVisibility(VISIBLE);
                mImageView.setVisibility(GONE);
                mFailedView.setVisibility(GONE);
                break;
            case STATUS_SUC:
                mLoadingView.setVisibility(GONE);
                mImageView.setVisibility(VISIBLE);
                mFailedView.setVisibility(GONE);
                break;
            case STATUS_FAILED:
                mLoadingView.setVisibility(GONE);
                mImageView.setVisibility(GONE);
                mFailedView.setVisibility(VISIBLE);
                break;
        }
    }

    public void addLoadingView(View loadingView){
        if(loadingView==null)
            throw new NullPointerException("loadingView 不能为null");
        if(mLoadingView!=null)
            this.removeView(mLoadingView);
        this.mLoadingView=loadingView;
        addCustomView(loadingView);
    }
    public void addFailedView(View failedView){
        if(failedView==null)
            throw new NullPointerException("failedView 不能为null");
        if(failedView!=null)
            this.removeView(failedView);
        this.mFailedView=failedView;
        addCustomView(failedView);
    }

    private void addCustomView(View view){
        RelativeLayout.LayoutParams params= new RelativeLayout.LayoutParams(-2,-2);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        this.addView(view,params);
    }


    public View getLoadingView(){
        return mLoadingView;
    }
    public ImageView getImageView(){
        return mImageView;
    }
    public View getFailedView(){
        return mFailedView;
    }

}
