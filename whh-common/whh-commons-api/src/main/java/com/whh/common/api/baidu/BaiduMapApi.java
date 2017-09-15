package com.whh.common.api.baidu;

import com.whh.common.api.interfaces.IMapApi;
import com.whh.common.api.sys.AppKey;

/**
 * Created by huahui.wu on 2017/9/14.
 */
public class BaiduMapApi implements IMapApi {

    private String appCode;

    public BaiduMapApi(AppKey appKey) {
        this.appCode = appKey.getPlatformCode();
    }
}
