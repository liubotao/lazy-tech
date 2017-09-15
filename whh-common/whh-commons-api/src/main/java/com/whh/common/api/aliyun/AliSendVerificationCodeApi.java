package com.whh.common.api.aliyun;

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
     * @param param        模板定义参数
     * @return
     */
    public static Boolean sendVerificationCode(String SignName, String TemplateCode, String phoneNum, JSONObject param) {
        final String host = "http://sms.market.alicloudapi.com";
        final String path = "/singleSendSms";
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", "APPCODE " + appCode);

        Map<String, String> map = new HashMap<String, String>();
        map.put("ParamString", param.toJSONString());
        map.put("RecNum", phoneNum);
        map.put("SignName", SignName);
        map.put("TemplateCode", TemplateCode);

        JSONObject json = request(host, path, methodGet, headers, map);

        Boolean result = json.getBoolean("success");
        if (result) {
            if (log.isInfoEnabled()) {
                log.info("短信发送成功");
            }
        } else {
            log.error("发送短信失败：" + json.getString("message"));
        }
        return result;
    }
}
