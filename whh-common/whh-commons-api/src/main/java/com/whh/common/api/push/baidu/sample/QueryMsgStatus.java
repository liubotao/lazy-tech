package com.whh.common.api.push.baidu.sample;

import com.baidu.yun.core.log.YunLogEvent;
import com.baidu.yun.core.log.YunLogHandler;
import com.baidu.yun.push.auth.PushKeyPair;
import com.baidu.yun.push.client.BaiduPushClient;
import com.baidu.yun.push.constants.BaiduPushConstants;
import com.baidu.yun.push.exception.PushClientException;
import com.baidu.yun.push.exception.PushServerException;
import com.baidu.yun.push.model.MsgSendInfo;
import com.baidu.yun.push.model.QueryMsgStatusRequest;
import com.baidu.yun.push.model.QueryMsgStatusResponse;

import java.util.List;

public class QueryMsgStatus {
    public static void main(String[] args)
            throws PushClientException, PushServerException {
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
            String[] msgIds = {"4581768166976512771"};
            QueryMsgStatusRequest request = new QueryMsgStatusRequest()
                    .addMsgIds(msgIds)
                    .addDeviceType(3);
            // 5. http request
            QueryMsgStatusResponse response = pushClient
                    .queryMsgStatus(request);
            // Http请求返回值解析
            System.out.println("totalNum: " + response.getTotalNum() + "\n"
                    + "result:");
            if (null != response) {
                List<?> list = response.getMsgSendInfos();
                for (int i = 0; i < list.size(); i++) {
                    Object object = list.get(i);
                    if (object instanceof MsgSendInfo) {
                        MsgSendInfo msgSendInfo = (MsgSendInfo) object;
                        StringBuilder strBuilder = new StringBuilder();
                        strBuilder.append("List[" + i + "]: {" + "msgId = "
                                + msgSendInfo.getMsgId() + ",status = "
                                + msgSendInfo.getMsgStatus() + ",sendTime = "
                                + msgSendInfo.getSendTime() + ",successCount = "
                                + msgSendInfo.getSuccessCount());
                        strBuilder.append("}\n");
                        System.out.println(strBuilder.toString());
                    }
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