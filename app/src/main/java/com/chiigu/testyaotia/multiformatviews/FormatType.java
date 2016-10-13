package com.chiigu.testyaotia.multiformatviews;

/**
 * Created by hudawei on 2016/10/11.
 *
 */
public class FormatType {
    /**文本类型*/
    public final static int TEXT=0;
    /**图片类型*/
    public final static int IMG=1;

    /**当前类型*/
    private int type;
    /**该类型的内容部分*/
    private String content;

    public FormatType(int type, String content) {
        this.type = type;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "FormatType{" +
                "type=" + type +
                ", content='" + content + '\'' +
                '}';
    }
}
