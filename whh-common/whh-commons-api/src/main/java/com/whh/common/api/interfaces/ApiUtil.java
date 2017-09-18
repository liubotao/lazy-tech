package com.whh.common.api.interfaces;

import com.whh.common.api.interfaces.enums.MapPlatform;
import com.whh.common.api.interfaces.instancer.ApiInstancer;
import com.whh.common.api.model.AppKey;

/**
 * Created by huahui.wu on 2017/9/15.
 */
public class ApiUtil {
    private ApiUtil() {

    }

    public static IMapApi getMapApi(AppKey appKey) {
        MapPlatform platform = MapPlatform.getPlatform(appKey.getPlatformId());

        ApiInstancer apiInstancer = platform.getApiInstancer();
        if (apiInstancer != null) {
            return apiInstancer.getMapApi(appKey);
        } else {
            return null;
        }
    }
}
