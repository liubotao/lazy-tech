package com.whh.common.api.interfaces.instancer.impl;

import com.whh.common.api.baidu.BaiduMapApi;
import com.whh.common.api.interfaces.IMapApi;
import com.whh.common.api.interfaces.instancer.ApiInstancer;
import com.whh.common.api.model.AppKey;

/**
 * Created by huahui.wu on 2017/9/15.
 */
public class MapApiInstancer {
    public static class IbsMapInstancer extends ApiInstancer {
        @Override
        public IMapApi getMapApi(AppKey appKey) {
            return new BaiduMapApi(appKey);
        }
    }
}
