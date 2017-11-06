package com.whh.common.api.baidu;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.whh.common.api.interfaces.ShortUrlApi;
import com.whh.common.utils.util.AbsResponse;
import com.whh.common.utils.util.WebUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 百度短链接
 * @author huahui.wuwu
 * Created by huahui.wu on 2017/11/6.
 */
public class BaiduShortUrlApi implements ShortUrlApi{
    private final static String apiUrl = "http://dwz.cn/create.php";

    public static void main(String[] args) {
        BaiduShortUrlApi api = new BaiduShortUrlApi();
        try {
           String str = api.callApi("http://fanyi.baidu.com/translate?aldtype=16047&query=&keyfrom=baidu&smartresult=dict&lang=auto2zh#zh/en/%E7%88%B6%E7%B1%BB");
           AbsResponse absResponse = api.parsing(str);
            System.out.println(absResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public AbsResponse<String> generateShortUrl(String longUrl) throws Exception {
        AbsResponse<String> absResponse = new AbsResponse<>();
        String response = callApi(longUrl);
        absResponse = parsing(response);
        return absResponse;
    }

    private AbsResponse<String> parsing(String response) {
        AbsResponse<String> absResponse = new AbsResponse<>();
        JSONObject jsonObject = JSON.parseObject(response);
        Integer status = jsonObject.getInteger("status");
        String data;
        if ((status != null) && (status.intValue() == 0)) {
            String tinyurl = jsonObject.get("tinyurl").toString();
            data = tinyurl.replaceAll("(?is)(?<!')(http://[/\\.\\w\\?=&-]+)", tinyurl);
            absResponse.setData(data);
            return absResponse;
        }
        absResponse.setResult(100, jsonObject.get("err_msg").toString(), null);
        return absResponse;
    }

    private String callApi(String longUrl) throws Exception {
        String responseString = null;
        Date reqDate = new Date();
        Map<String, String> params = new HashMap<>();
        try {
            params.put("url", longUrl);
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
