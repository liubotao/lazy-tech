package com.whh.common.api.interfaces.enums;

import com.whh.common.api.interfaces.instancer.ApiInstancer;
import com.whh.common.api.interfaces.instancer.impl.MapApiInstancer;

/**
 * Created by huahui.wu on 2017/9/15.
 */
public enum MapPlatform {

    BAI_DU_MAP(301L, "百度地图", new MapApiInstancer.IbsMapInstancer());

    private long id;
    private String platformName;
    private String apiCode;
    private ApiInstancer apiInstancer;

    private MapPlatform(long id, String platformName, ApiInstancer apiInstancer) {
        this.apiCode = name();
        this.id = id;
        this.platformName = platformName;
        this.apiInstancer = apiInstancer;
    }

    public boolean equalsByApiCode(String apiCode) {
        return this.apiCode.equals(apiCode);
    }

    public boolean equalsById(Long apiId) {
        return apiId != null && this.id == apiId.longValue();
    }

    public String getPlatformName() {
        return this.platformName;
    }

    public String getApiCode() {
        return this.apiCode;
    }

    public long getId() {
        return this.id;
    }

    public ApiInstancer getApiInstancer() {
        return apiInstancer;
    }

    public boolean equalsByName(String apiName) {
        return this.name().equals(apiName);
    }

    public static MapPlatform getPlatform(String apiName) {
        try {
            return MapPlatform.valueOf(MapPlatform.class, apiName);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public static MapPlatform getPlatform(long id) {
        for (MapPlatform item : values()) {
            if (item.id == id) {
                return item;
            }
        }

        return null;
    }
}
