package com.chiigu.testyaotia.options;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.chiigu.testyaotia.multiformatviews.MutiplyFormatLayout;

import java.util.List;

/**
 * Created by hudawei on 2016/10/12.
 *
 */
public class OptionsCardViewGroup extends LinearLayout implements View.OnClickListener{



    /** 练习模式 */
    public final static int MODE_EXERSIZE=0;
    /** 解析模式 */
    public final static int MODE_PARSE=1;

    private int mode;
    /** 当前点击选项的位置 */
    private int curCheckedPosition=-1;
    /** 我的选择 */
    private String myOption;
    /** 正确选择 */
    private String rightOpion;
    /** 我的选择index */
    private int myOptionIndex=-1;
    /** 正确选择index */
    private int rightOpionIndex=-1;
    /** 练习模式下监听选中答案操作 */
    private OptionCheckedListener listener;
    /** 图片加载器 */
    private MutiplyFormatLayout.ImageLoader imageLoader;
    /** 选项数据 */
    private List<String> datas;
    private Context context;
    public OptionsCardViewGroup(Context context) {
        this(context,null);
    }

    public OptionsCardViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
    }

    public OptionsCardViewGroup mode(int mode){
        this.mode=mode;
        return this;
    }

    public OptionsCardViewGroup datas(List<String> datas){
        this.datas=datas;
        return this;
    }

    public OptionsCardViewGroup rightOpion(String rightOpion){
        this.rightOpion=rightOpion;
        this.rightOpionIndex=optionToPosition(rightOpion);
        return this;
    }
    public OptionsCardViewGroup imageLoader(MutiplyFormatLayout.ImageLoader imageLoader){
        this.imageLoader=imageLoader;
        return this;
    }
    public OptionsCardViewGroup checkedListener(OptionCheckedListener listener){
        this.listener=listener;
        return this;
    }
    public OptionsCardViewGroup myOption(String myOption){
        this.myOption=myOption;
        this.myOptionIndex=optionToPosition(myOption);
        return this;
    }

    public void show(){
        if(datas!=null&&datas.size()!=0){
            for(int i=0;i<datas.size();i++){
                OptionCardView optionCardView=new OptionCardView(context);
                optionCardView.setIndex(i);
                optionCardView.setImageLoader(imageLoader);
                optionCardView.setSource(datas.get(i));
                if(mode==MODE_EXERSIZE) {//练习模式
                    optionCardView.setStatus(OptionCircleView.OPTION_BASE);
                    optionCardView.setOnClickListener(this);
                }else if(mode==MODE_PARSE){//解析模式
                    Log.e("myOptionIndex","myOptionIndex="+myOptionIndex+"\trightOpionIndex="+rightOpionIndex);
                    if(i==myOptionIndex){//当前选项为我的选项
                        if(i==rightOpionIndex){//当前选项为正确选项
                            optionCardView.setStatus(OptionCircleView.OPTION_RIGHT);
                        }else{//当前选项非正确选项
                            optionCardView.setStatus(OptionCircleView.OPTION_WRONG);
                        }
                    }else{//当前选项非我的选项
                        if(i==rightOpionIndex){//当前选项为正确选项
                            optionCardView.setStatus(OptionCircleView.OPTION_RIGHT);
                        }else{//当前选项非正确选项
                            optionCardView.setStatus(OptionCircleView.OPTION_BASE);
                        }
                    }
                }
                this.addView(optionCardView);
            }
        }
    }

    public String getMyOption(){
        return myOption;
    }

    public int getCheckedItem(){
        return curCheckedPosition;
    }

    /**
     * 答案的位置转成字母形式
     * @param position 答案在集合中的索引
     * @return 字母形式的顺序 0A 1B 2C 3D 4E...,如果集合长度超过了24那么显示“”
     */
    public static String positionToOption(int position){
        if(position+'A'>='A'&&position+'A'<='Z'){
            return Character.toString((char) (position+'A'));
        }
        return "";
    }

    /**
     * 字母转索引
     * @param option
     * @return
     */
    public static int optionToPosition(String option){
        if(option!=null){
            char[] chars=option.toCharArray();
            if(chars!=null&&chars.length==1){
                char cOption=chars[0];
                if(cOption>='A'&&cOption<='Z'){
                    return cOption-'A';
                }
            }
        }
        return -1;
    }

    public interface OptionCheckedListener{
        void onOptionChecked(int index,String myAnswer);
    }

    @Override
    public void onClick(View v) {
        if(v instanceof OptionCardView){
            OptionCardView cardView=(OptionCardView) v;
            if(curCheckedPosition==-1){
                cardView.setStatus(OptionCircleView.OPTION_SELECTED);
                curCheckedPosition=cardView.getIndex();
                myOption=positionToOption(curCheckedPosition);
                if(listener!=null){
                    listener.onOptionChecked(curCheckedPosition,myOption);
                }
            }
        }
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
