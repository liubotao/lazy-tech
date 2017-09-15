package com.whh.common.api.sys;

/**
 * Created by huahui.wu on 2017/9/15.
 */
public class AppKey {

    private Long platformId;
    private String platformName;
    private String  platformCode;

    public Long getPlatformId() {
        return platformId;
    }

    public void setPlatformId(Long platformId) {
        this.platformId = platformId;
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    public String getPlatformCode() {
        return platformCode;
    }

    public void setPlatformCode(String platformCode) {
        this.platformCode = platformCode;
    }
}
