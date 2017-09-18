package com.whh.common.api.tencent;

import com.whh.common.api.model.CosKey;
import org.junit.Test;

/**
 * Created by huahui.wu on 2017/9/18.
 */
public class CosApiTest {
    @Test
    public void uploadFile() throws Exception {
        CosKey cosKey = new CosKey();
        cosKey.setAppId(10064421L);
        cosKey.setSecretId("AKIDy5Qb2m96V4qqSd8JiKm8svtwsetep3o5");
        cosKey.setSecretKey("7shVFdkkySHqmWIK74eFXEF38X9CsE7L");
        cosKey.setRegion("sh");
        CosApi cosApi = new CosApi(cosKey);
        String request = cosApi.createFolder("yushang", "/blogs/test/");


    }

    @Test
    public void uploadFile1() throws Exception {
    }

    @Test
    public void getFileLocal() throws Exception {
    }

    @Test
    public void moveFile() throws Exception {
    }

    @Test
    public void statFile() throws Exception {
    }

    @Test
    public void updateFile() throws Exception {
    }

    @Test
    public void delFile() throws Exception {
    }

    @Test
    public void createFolder() throws Exception {
    }

    @Test
    public void createFolder1() throws Exception {
    }

    @Test
    public void statFolder() throws Exception {
    }

    @Test
    public void updateFolder() throws Exception {
    }

    @Test
    public void listFolder() throws Exception {
    }

}