package com.whh.common.api.AliyunApi;

import com.alibaba.fastjson.JSONObject;
import com.whh.common.api.BaseApi;
import com.whh.common.api.Constants;
import com.whh.common.utils.util.HttpUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

/**
 * Created by huahui.wu on 2017/9/14.
 */
public abstract class AliyunApi extends BaseApi {
    protected static Logger log = LogManager.getLogger(AliyunApi.class);

    protected static final String appCode = Constants.ALIYUN_APPKEY;

}
