package com.whh.common.api.aliyun;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by huahui.wu on 2017/9/15.
 */
public class AliIWeatherApiTest {
    @Test
    public void testGetWeather() throws Exception {
        JSONObject jsonObject = AliIWeatherApi.getIdByArea("杭州");
        System.out.println(jsonObject);
    }

    @Test
    public void getWeatherByIp() throws Exception {
        Map<String, String> query = new HashMap<String, String>();
        query.put("ip","115.236.172.146");
        JSONObject jsonObject = AliIWeatherApi.getWeatherByIp(query);
        System.out.println(jsonObject);
    }

}