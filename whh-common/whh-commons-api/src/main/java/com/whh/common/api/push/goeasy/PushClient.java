package com.whh.common.api.push.goeasy;

import io.goeasy.GoEasy;

/**
 * 消息推送
 * Created by huahui.wu on 2017/9/19.
 */
public class PushClient {

    private static final String REGION_HOST = "rest-hangzhou.goeasy.io";
    private static final String APP_KEY = "BC-0d445167035c4f418f49703af385189c";
    public static void main(String[] args) {
        GoEasy goEasy = new GoEasy(REGION_HOST, APP_KEY);
        goEasy.publish("hello-demo", "hello");
    }
}
