package com.whh.common.api.push.baidu.sample;

import java.util.List;

import com.baidu.yun.core.log.YunLogEvent;
import com.baidu.yun.core.log.YunLogHandler;
import com.baidu.yun.push.auth.PushKeyPair;
import com.baidu.yun.push.client.BaiduPushClient;
import com.baidu.yun.push.constants.BaiduPushConstants;
import com.baidu.yun.push.exception.PushClientException;
import com.baidu.yun.push.exception.PushServerException;
import com.baidu.yun.push.model.QueryTimerListRequest;
import com.baidu.yun.push.model.QueryTimerListResponse;
import com.baidu.yun.push.model.TimerResultInfo;

public class QueryTimerList {
    public static void main(String[] args)
            throws PushClientException,PushServerException {
        // 1. get apiKey and secretKey from developer console
        String apiKey = "C7IbFY6p0sTRT14IGKDcQbWL";
        String secretKey = "NgZ1SIfhvvO5QKiM8cRMaGlRn6tbIokG";
        PushKeyPair pair = new PushKeyPair(apiKey, secretKey);

        // 2. build a BaidupushClient object to access released interfaces
        BaiduPushClient pushClient = new BaiduPushClient(pair,
                BaiduPushConstants.CHANNEL_REST_URL);

        // 3. register a YunLogHandler to get detail interacting information
        // in this request.
        pushClient.setChannelLogHandler(new YunLogHandler() {
            @Override
            public void onHandle(YunLogEvent event) {
                System.out.println(event.getMessage());
            }
        });

        try {
            // 4. specify request arguments
            QueryTimerListRequest request = new QueryTimerListRequest().
                    // addTimerId("xxxxxxxxxxxx"). //设置查询一个定时任务
                            addStart(0).addLimit(6).addDeviceType(3);
            // 5. http request
            QueryTimerListResponse response = pushClient
                    .queryTimerList(request);
            // Http请求返回值解析
            System.out.println("totalNum: " + response.getTotalNum() + "\n"
                    + "result:");
            if (null != response) {
                List<?> list = response.getTimerResultInfos();
                for (int i = 0; i < list.size(); i++) {
                    Object object = list.get(i);
                    StringBuilder strBuilder = new StringBuilder();
                    if (object instanceof TimerResultInfo) {
                        TimerResultInfo timerResult = (TimerResultInfo) object;
                        strBuilder.append("List[" + i + "]: " + "timerId= "
                                + timerResult.getTimerId() + ",sendTime= "
                                + timerResult.getSendTime() + ",= "
                                + timerResult.getMsgType() + ",msgType= "
                                + timerResult.getMsgType() + ",rangeType= "
                                + timerResult.getRangeType() + "\n");
                    }
                    System.out.println(strBuilder.toString());
                }
            }
        } catch (PushClientException e) {
            if (BaiduPushConstants.ERROROPTTYPE) {
                throw e;
            } else {
                e.printStackTrace();
            }
        } catch (PushServerException e) {
            if (BaiduPushConstants.ERROROPTTYPE) {
                throw e;
            } else {
                System.out.println(String.format(
                        "requestId: %d, errorCode: %d, errorMsg: %s",
                        e.getRequestId(), e.getErrorCode(), e.getErrorMsg()));
            }
        }
    }
}