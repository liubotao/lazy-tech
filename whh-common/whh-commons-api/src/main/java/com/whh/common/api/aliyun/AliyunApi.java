package com.whh.common.api.aliyun;

import com.whh.common.api.sys.BaseApi;
import com.whh.common.api.sys.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by huahui.wu on 2017/9/14.
 */
public abstract class AliyunApi extends BaseApi {
    protected static Logger log = LogManager.getLogger(AliyunApi.class);

    protected static final String appCode = Constants.ALIYUN_APPKEY;

    protected static Map<String, String> headers;

    /**
     * 初始化 header
     */
    static {
        headers = new HashMap<String, String>();
        headers.put("Authorization", "APPCODE " + appCode);
    }

}
