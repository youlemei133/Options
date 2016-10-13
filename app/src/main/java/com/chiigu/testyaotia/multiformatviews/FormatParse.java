package com.chiigu.testyaotia.multiformatviews;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hudawei on 2016/10/11.
 * <img src="" >fdaf</img>
 * <img src="" ></img>
 * <img src="" />
 * <img src="" >
 * <img >
 */
public class FormatParse {
    private static String imgReg="<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>";
    public static List<FormatType> parseFormat(String source){
        Pattern p = Pattern.compile(imgReg,0);
        Matcher m = p.matcher(source);
        List<FormatType> formatTypes=new ArrayList<>();
        int mStart=0;
        while(m.find()){
            int start=m.start();
            int end=m.end();
            String src=m.group(1);
            Log.e("tag","start="+start+"\tend="+end+"\tgroup="+m.group(1));
            if(start!=0){
                String text=source.substring(mStart,start);
                mStart=end;
                formatTypes.add(new FormatType(FormatType.TEXT,text));
            }

            formatTypes.add(new FormatType(FormatType.IMG,src));
        }
        if(mStart!=source.length()){
            String text=source.substring(mStart,source.length());
            formatTypes.add(new FormatType(FormatType.TEXT,text));
        }
        return formatTypes;
    }

}
