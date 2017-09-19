package com.whh.common.api.cloud;

import com.aliyun.oss.model.StorageClass;
import com.whh.common.utils.qrcode.QrcodeUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.File;


/**
 * Created by huahui.wu on 2017/9/18.
 */
public class OssClientTest {

    private OssClient ossClient = null;
    private String bucketName = "oss-yushang-test";
    private String key = "90UNuo5QjBJuNWCdIM1DAyzgZ0IKV9";

    @Before
    public void initDate() {
        String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
        // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建
        String accessKeyId = "LTAIa5VBM2ckaDe9";
        String accessKeySecret = "90UNuo5QjBJuNWCdIM1DAyzgZ0IKV9";
        ossClient = new OssClient(endpoint, accessKeyId, accessKeySecret);
    }

    @Test
    public void createBucket() throws Exception {
        ossClient.createBucket(bucketName, null, StorageClass.IA);
    }

    @Test
    public void deleteBucket() throws Exception {
        ossClient.deleteBucket(bucketName);
    }

    @Test
    public void uploadFile() throws Exception {

        ossClient.uploadFile(bucketName, key, "D:\\用户目录\\下载\\PanDownload_v1.4.3.zip");
    }

    @Test
    public void uploadFileNew() throws Exception {

        ossClient.uploadFileNew(bucketName, key, "D:\\用户目录\\下载\\question.jpg");
    }

    @Test
    public void test() throws Exception {
        File tempFile = new File("/temp.jpg");
        QrcodeUtils.gen(key, tempFile);
        ossClient.uploadFileNew(bucketName, key, tempFile.getPath());
        tempFile.deleteOnExit();

    }

    @Test
    public void putFolder() throws Exception {
        ossClient.createFolder(bucketName, "parent_directory/");
    }

}