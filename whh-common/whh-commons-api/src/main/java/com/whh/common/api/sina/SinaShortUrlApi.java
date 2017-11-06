package com.whh.common.api.sina;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.whh.common.api.interfaces.ShortUrlApi;
import com.whh.common.utils.util.AbsResponse;
import com.whh.common.utils.util.WebUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by huahui.wu on 2017/11/6.
 */
public class SinaShortUrlApi implements ShortUrlApi {
    private final static String appKey = "";
    private final static String apiUrl = "";


    @Override
    public AbsResponse<String> generateShortUrl(String longUrl) throws Exception {
        String response = callApi(longUrl);
        return parsing(response);
    }

    private AbsResponse<String> parsing(String response) {
        AbsResponse<String> absResponse = new AbsResponse<>();
        JSONObject jsonObject = JSON.parseArray(response).getJSONObject(0);
        String data = jsonObject.getString("url_short");
        absResponse.setData(data);
        return absResponse;
    }

    protected String callApi(String longUrl) throws Exception {
        String responseString = null;
        Map<String, String> params = new HashMap<>();
        try {
            params.put("source", appKey);
            params.put("url_long", longUrl);
            responseString = WebUtils.doPost(apiUrl, params);
        } catch (Exception e) {
            responseString = e.getMessage();
            throw e;
        } finally {
//            appendReqAResp("generateShortUrl", reqDate, null, params, responseString);
        }
        return responseString;
    }
}
