package com.whh.common.api.AliyunApi;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by huahui.wu on 2017/9/14.
 */
public class AliSendVerificationCodeApi extends AliyunApi {
    /**
     * 发送短信验证码接口
     *
     * @param SignName     签名名称
     * @param TemplateCode 模板CODE
     * @param phoneNum     目标手机号,多条记录可以英文逗号分隔
     * @param param 模板定义参数
     * @return
     */
    public static int sendVerificationCode(String SignName, String TemplateCode, String phoneNum, JSONObject param) {
        final String host = "http://sms.market.alicloudapi.com";
        final String path = "/singleSendSms";
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", "APPCODE " + appCode);

        Map<String, String> map = new HashMap<String, String>();
        map.put("ParamString", param.toJSONString());
        map.put("RecNum", phoneNum);
        map.put("SignName", SignName);
        map.put("TemplateCode", TemplateCode);

        request(host, path, methodGet, headers, map);
        return 0;
    }
}
