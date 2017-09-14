package com.whh.common.api;

import com.alibaba.fastjson.JSONObject;
import com.whh.common.utils.util.HttpUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.util.Map;

/**
 * Created by huahui.wu on 2017/9/14.
 */
public abstract class BaseApi {
    /**
     * 请求方式
     */
    protected static final String methodGet = "GET";
    protected static final String methodPost = "POST";

    protected static final String charset = "UTF-8";

    /**
     * http 请求
     * @param host 请求地址
     * @param path 请求地址路径
     * @param methodGet 请求方式
     * @param headers 请求头
     * @param querys 请求参数
     * @return JSONObject
     */
    protected static JSONObject request(String host, String path, String methodGet, Map<String, String> headers, Map<String, String> querys) {
        try {
            HttpResponse response = HttpUtils.doGet(host, path, methodGet, headers, querys);

            //获取response的body
            return JSONObject.parseObject(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONObject();
        }
    }


}
