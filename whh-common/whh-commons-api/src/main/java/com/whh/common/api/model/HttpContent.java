package com.whh.common.api.model;

import java.util.List;

/**
 * Cos Http请求头设置
 * Created by huahui.wu on 2017/9/18.
 */
public class HttpContent {

    /**
     * 参见HTTP的Cache-Control
     */
    private String cacheControl;

    /**
     * 参见HTTP的Content-Type
     */
    private String contentType;

    /**
     * 参见HTTP的Content-Language
     */
    private String contentLanguage;

    /**
     * 参见HTTP的Content-Disposition
     */
    private String contentDisposition;

    /**
     * 自定义HTTP 头，参数必须以x-cos-meta-开头，值由用户定义，可设置多个
     */
    private List<String> xCosMeta;

    public String getCacheControl() {
        return cacheControl;
    }

    public void setCacheControl(String cacheControl) {
        this.cacheControl = cacheControl;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentLanguage() {
        return contentLanguage;
    }

    public void setContentLanguage(String contentLanguage) {
        this.contentLanguage = contentLanguage;
    }

    public String getContentDisposition() {
        return contentDisposition;
    }

    public void setContentDisposition(String contentDisposition) {
        this.contentDisposition = contentDisposition;
    }

    public List<String> getxCosMeta() {
        return xCosMeta;
    }

    public void setxCosMeta(List<String> xCosMeta) {
        this.xCosMeta = xCosMeta;
    }
}
