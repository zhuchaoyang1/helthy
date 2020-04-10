package com.zcy.cn.enums;

/**
 * 日志来源
 */
public enum PlatformsEnum {

    WECHAT("WECHAT"),   // 微信小程序
    WEB("WEB");         // Web PC

    private String platforms;

    PlatformsEnum(String platforms) {
        this.platforms = platforms;
    }

    public String getPlatforms() {
        return platforms;
    }
}
