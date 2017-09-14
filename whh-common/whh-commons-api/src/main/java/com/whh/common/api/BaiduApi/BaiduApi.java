package com.whh.common.api.BaiduApi;

import com.alibaba.fastjson.JSONObject;
import com.whh.common.api.AliyunApi.AliyunApi;
import com.whh.common.api.BaseApi;
import com.whh.common.api.Constants;
import com.whh.common.utils.util.HttpUtils;
import com.whh.common.utils.util.WebUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by huahui.wu on 2017/9/14.
 */
public class BaiduApi extends BaseApi{

    private static Logger log = LogManager.getLogger(BaiduApi.class);

    private static final String appCode = Constants.BAIDU_APPKEY;


}
