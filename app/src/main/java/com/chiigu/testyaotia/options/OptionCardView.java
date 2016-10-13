package com.chiigu.testyaotia.options;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.chiigu.testyaotia.R;
import com.chiigu.testyaotia.multiformatviews.MutiplyFormatLayout;

/**
 * Created by hudawei on 2016/10/12.
 *
 */
public class OptionCardView extends RelativeLayout{
    private OptionCircleView optionCircleView;
    private MutiplyFormatLayout mutiplyFormatLayout;

    private String source;
    private int index;
    private int status;

    public OptionCardView(Context context) {
        this(context,null);
    }

    public OptionCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view=LayoutInflater.from(context).inflate(R.layout.view_optioncard,null);
        optionCircleView = (OptionCircleView)view.findViewById(R.id.optionCircleView);
        mutiplyFormatLayout = (MutiplyFormatLayout)view.findViewById(R.id.mutiplyFormatLayout);
        this.addView(view);
    }

    public void setIndex(int index) {
        this.index = index;
        optionCircleView.setOptionText(OptionsCardViewGroup.positionToOption(index));
    }

    public void setStatus(int status) {
        this.status = status;
        optionCircleView.setOptionStatus(status);
    }

    public void setSource(String source) {
        this.source = source;
        mutiplyFormatLayout.setSource(source);
    }

    public void setImageLoader(MutiplyFormatLayout.ImageLoader imageLoader){
        Log.e("imageLoader","---OptionCardView----"+imageLoader);
        mutiplyFormatLayout.setImageLoader(imageLoader);
    }

    public int getIndex() {
        return index;
    }

}
