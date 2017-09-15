package com.whh.common.api.interfaces.instancer;

import com.whh.common.api.interfaces.IMapApi;
import com.whh.common.api.sys.AppKey;

/**
 * Created by huahui.wu on 2017/9/15.
 */
public abstract class ApiInstancer {

    /**
     * 创建地图接口实例
     *
     * @param appKey
     * @return IShopApi
     */
    public IMapApi getMapApi(AppKey appKey) {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("类：").append(this.getClass().getName());
        strBuilder.append("未重写[MapApiInstance.getMapApi()]方法");

        throw new RuntimeException(strBuilder.toString());
    }
}
