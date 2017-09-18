package com.whh.common.api.model;

/**
 * Created by huahui.wu on 2017/9/18.
 */
public class CosKey {

    // 这些属性可以通过cos控制台获取(https://console.qcloud.com/cos)

    private Long appId; //bucket id

    private String secretId; //bucket secretId

    private String secretKey; //secretId secretKey

    private String bucketName; //secretKey bucketName

    private String region; //bucket所在的区域，比如广州(gz), 天津(tj), 上海(sh)

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public String getSecretId() {
        return secretId;
    }

    public void setSecretId(String secretId) {
        this.secretId = secretId;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
