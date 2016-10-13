package com.chiigu.testyaotia.options;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.chiigu.testyaotia.R;

/**
 * Created by hudawei on 2016/10/12.
 *
 */
public class OptionCircleView extends View {
    public final static int OPTION_BASE=0;
    public final static int OPTION_SELECTED=1;
    public final static int OPTION_RIGHT=2;
    public final static int OPTION_WRONG=3;
    private Context context;
    /**选项状态*/
    private int optionStatus;
    /**选项文本，例如A B C D*/
    private String text;
    /**选项文本颜色 */
    private int textColor;
    /** 选项文本字体大小 */
    private float textSize;//
    /** 圆半径 */
    private float circleRadius;
    /** 外圆颜色 */
    private int outCircleColor;
    /** 正确选项背景色 */
    private int rightOptionBackgroundColor;
    /** 正确答案图标 */
    private Drawable rightOptionImageResource;
    /** 错误答案背景色 */
    private int wrongOptionBackgroundColor;
    /** 错误答案图标 */
    private Drawable wrongOptionImageResource;
    /** 选中状态的背景色 */
    private int selectedOptionBackgroundColor;
    /** 选中文本颜色 */
    private int selectedOptionTextColor;
    private Paint circlePaint;
    private Paint textPaint;
    private Paint.FontMetrics fm;
    private float baselineY;

    public OptionCircleView(Context context) {
        this(context,null);
    }

    public OptionCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context,attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        Log.e("OptionCircleView","initView");
        //获取属性值
        this.context=context;
        TypedArray ta=context.obtainStyledAttributes(attrs, R.styleable.OptionCircleView);
        text=ta.getString(R.styleable.OptionCircleView_text);
        textColor=ta.getColor(R.styleable.OptionCircleView_mTextColor, Color.parseColor("#383838"));
        textSize=ta.getDimension(R.styleable.OptionCircleView_mTextSize,15);
        circleRadius=ta.getDimension(R.styleable.OptionCircleView_circleRadius,dp2px(15,context));
        outCircleColor=ta.getColor(R.styleable.OptionCircleView_outCircleColor,Color.parseColor("#dddddd"));
        rightOptionBackgroundColor=ta.getColor(R.styleable.OptionCircleView_rightOptionBackgroundColor,Color.parseColor("#1fbba6"));
        rightOptionImageResource=ta.getDrawable(R.styleable.OptionCircleView_rightOptionImageResource);
        if(rightOptionImageResource==null)
            rightOptionImageResource=ContextCompat.getDrawable(context,R.mipmap.icon_right);
        if(wrongOptionImageResource==null)
            wrongOptionImageResource=ContextCompat.getDrawable(context,R.mipmap.icon_wrong);
        wrongOptionBackgroundColor=ta.getColor(R.styleable.OptionCircleView_wrongOptionBackgroundColor,Color.parseColor("#ff6766"));
        wrongOptionImageResource=ta.getDrawable(R.styleable.OptionCircleView_wrongOptionImageResource);
        selectedOptionBackgroundColor=ta.getColor(R.styleable.OptionCircleView_selectedOptionBackgroundColor,Color.parseColor("#1fbba6"));
        selectedOptionTextColor=ta.getColor(R.styleable.OptionCircleView_selectedOptionTextColor,Color.WHITE);
        optionStatus=ta.getInt(R.styleable.OptionCircleView_optionStatus,OPTION_BASE);
        ta.recycle();

        circlePaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setColor(outCircleColor);
        circlePaint.setStrokeWidth(dp2px(2,context));

        textPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(textColor);
        textPaint.setTextSize(textSize);
        textPaint.setTextAlign(Paint.Align.CENTER);
        fm=textPaint.getFontMetrics();
        baselineY = circleRadius - fm.descent + (fm.bottom - fm.top) / 2;
    }

    public void setOptionStatus(int optionStatus){
        if(this.optionStatus==optionStatus)
            return;
        this.optionStatus=optionStatus;
        postInvalidate();
    }

    public int getOptionStatus(){
        return optionStatus;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        switch (optionStatus){
            case OPTION_BASE:
                circlePaint.setStyle(Paint.Style.STROKE);
                circlePaint.setColor(outCircleColor);
                canvas.drawCircle(circleRadius,circleRadius,circleRadius-dp2px(1,context),circlePaint);
                textPaint.setColor(textColor);
                canvas.drawText(text+"",circleRadius,baselineY,textPaint);
                break;
            case OPTION_SELECTED:
                circlePaint.setStyle(Paint.Style.FILL);
                circlePaint.setColor(selectedOptionBackgroundColor);
                canvas.drawCircle(circleRadius,circleRadius,circleRadius-dp2px(1,context),circlePaint);
                textPaint.setColor(selectedOptionTextColor);
                canvas.drawText(text+"",circleRadius,baselineY,textPaint);
                break;
            case OPTION_RIGHT:
                circlePaint.setStyle(Paint.Style.FILL);
                circlePaint.setColor(rightOptionBackgroundColor);
                canvas.drawCircle(circleRadius,circleRadius,circleRadius-dp2px(1,context),circlePaint);
                int drWidth=rightOptionImageResource.getIntrinsicWidth();
                int drHeight=rightOptionImageResource.getIntrinsicHeight();
                rightOptionImageResource.setBounds((int)((2*circleRadius-drWidth)/2),(int)((2*circleRadius-drHeight)/2),(int)(circleRadius+drWidth/2),(int)(circleRadius+drHeight/2));
                rightOptionImageResource.draw(canvas);
                break;
            case OPTION_WRONG:
                circlePaint.setStyle(Paint.Style.FILL);
                circlePaint.setColor(wrongOptionBackgroundColor);
                canvas.drawCircle(circleRadius,circleRadius,circleRadius,circlePaint);
                int dWidth=wrongOptionImageResource.getIntrinsicWidth();
                int dHeight=wrongOptionImageResource.getIntrinsicHeight();
                wrongOptionImageResource.setBounds((int)((2*circleRadius-dWidth)/2),(int)((2*circleRadius-dHeight)/2),(int)(circleRadius+dWidth/2),(int)(circleRadius+dHeight/2));
                wrongOptionImageResource.draw(canvas);
                break;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension((int)(2*circleRadius),(int)(2*circleRadius));
    }

    public void setOptionText(String text){
        this.text=text;
    }
    public void setOptionTextColor(int color){
        this.textColor=color;
    }
    public void setOptionTextSize(int size){
        this.textSize=size;
    }

    public void setCirlceRadius(float radius){
        this.circleRadius=radius;
    }

    public void setOutCircleColor(int color){
        this.outCircleColor=color;
    }

    public void setRightOptionBackgroundColor(int color){
        this.rightOptionBackgroundColor=color;
    }

    public void setRightOptionImageResource(int rightResource){
        this.rightOptionImageResource= ContextCompat.getDrawable(context,rightResource);
    }

    public void setWrongOptionBackgroundColor(int color){
        this.wrongOptionBackgroundColor=color;
    }

    public void setWrongOptionImageResource(int wrongResource){
        this.wrongOptionImageResource=ContextCompat.getDrawable(context,wrongResource);
    }

    public void setSelectedOptionTextColor(int color){
        this.selectedOptionTextColor=color;
    }

    public void setSelectedOptionBackgroundColor(int color){
        this.selectedOptionBackgroundColor=color;
    }

    private int dp2px(float dpValue,Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * density + 0.5f);
    }
    public  int px2dp(float pxValue,Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / density + 0.5f);
    }
}
