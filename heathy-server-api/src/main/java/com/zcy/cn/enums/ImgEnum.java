package com.zcy.cn.enums;

public enum ImgEnum {
    WECHATNODATAIMG(1, "小程序无素材"),
    WECHATDEGREE(2, "小程序周报顶端图片");

    private Integer indexs;

    private String names;

    ImgEnum(Integer indexs, String names) {
        this.indexs = indexs;
        this.names = names;
    }

    public Integer getIndexs() {
        return indexs;
    }

    public void setIndexs(Integer indexs) {
        this.indexs = indexs;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }
}
