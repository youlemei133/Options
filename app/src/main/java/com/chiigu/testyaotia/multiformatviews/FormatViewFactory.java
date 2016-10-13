package com.chiigu.testyaotia.multiformatviews;

import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by hudawei on 2016/10/12.
 *
 */
public class FormatViewFactory {
    public static LoaderImageView imgCreator(FormatType formatType, Context context){
        if(formatType.getType()!=FormatType.IMG)
            return null;
        LoaderImageView imageView=new LoaderImageView(context,null);
        imageView.setBackgroundColor(Color.parseColor("#F5F5F5"));
        return imageView;
    }

    public static TextView textCreator(FormatType formatType, Context context){
        JustifyTextView textView=new JustifyTextView(context,null);
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(-1,-2);
        textView.setLayoutParams(params);
        textView.setText(formatType.getContent());
        return textView;
    }

}
