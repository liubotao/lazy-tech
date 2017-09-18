package com.whh.common.api.cloud;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.UUID;

/**
 * 阿里云对象存储
 * doc: https://help.aliyun.com/document_detail/32008.html?spm=5176.doc32010.6.659.NnswRF
 * Created by huahui.wu on 2017/9/18.
 */
public class OssClient {
    private static final Logger _log = LoggerFactory.getLogger(OssClient.class);

    private OSSClient ossClient;

    /**
     * 初始化OSS
     *
     * @param endpoint        endpoint以杭州为例，其它region请按实际情况填写 "http://oss-cn-hangzhou.aliyuncs.com"
     * @param accessKeyId     accessKeyId
     * @param accessKeySecret accessKeySecret
     */
    public OssClient(String endpoint, String accessKeyId, String accessKeySecret) {
        ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);

    }

    /***********************************************Bucket 管理*********************************************************/

    /**
     * 创建 Bucket
     *
     * @param bucketName Bucket 名字（只能包括小写字母、数字和短横线（-）。必须以小写字母或者数字开头和结尾。长度必须在3-63字节之间。）
     * @return
     */
    public Bucket createBucket(String bucketName) {
        return this.createBucket(bucketName, null, null);
    }


    /**
     * 创建 Bucket
     *
     * @param bucketName   Bucket 名字（只能包括小写字母、数字和短横线（-）。必须以小写字母或者数字开头和结尾。长度必须在3-63字节之间。）
     * @param cannedACL    权限 (CannedAccessControlList) 默认：私有读写
     * @param storageClass 存储类型 (StorageClass) 默认：标准类型
     * @return
     */
    public Bucket createBucket(String bucketName, CannedAccessControlList cannedACL, StorageClass storageClass) {
        CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName);
        // 设置bucket权限为公共读，默认是私有读写
        createBucketRequest.setCannedACL(cannedACL == null ? CannedAccessControlList.PublicRead : cannedACL);
        // 设置bucket存储类型为低频访问类型，默认是标准类型
        createBucketRequest.setStorageClass(storageClass == null ? StorageClass.IA : storageClass);
        Bucket bucket = ossClient.createBucket(createBucketRequest);
        if (_log.isInfoEnabled()) {
            _log.info("创建Bucket：" + bucket);
        }
        return bucket;
    }

    /**
     * 列举Bucket
     *
     * @return List<Bucket>
     */
    public List<Bucket> listBuckets() {
        List<Bucket> buckets = ossClient.listBuckets();
        return buckets;
    }

    /**
     * 列举Bucket
     *
     * @param prefix  限定返回的Bucket name必须以prefix作为前缀，可以不设定，不设定时不过滤前缀信息
     * @param marker  设定结果从marker之后按字母排序的第一个开始返回，可以不设定，不设定时从头开始返回
     * @param maxKeys 限定此次返回Bucket的最大数，如果不设定，默认为100，max-keys取值不能大于1000
     * @return BucketList
     */
    public BucketList listBuckets(String prefix, String marker, Integer maxKeys) {
        ListBucketsRequest listBucketsRequest = new ListBucketsRequest();
        if (prefix != null) {
            listBucketsRequest.setPrefix(prefix);
        }
        if (marker != null) {
            listBucketsRequest.setMarker(marker);
        }
        if (marker != null) {
            listBucketsRequest.setMaxKeys(maxKeys);
        }
        BucketList buckets = ossClient.listBuckets(listBucketsRequest);
        return buckets;
    }

    /**
     * 删除bucket
     *
     * @param bucketName bucket 名字
     */
    public void deleteBucket(String bucketName) {
        // 删除bucket
        ossClient.deleteBucket(bucketName);
        if (_log.isInfoEnabled()) {
            _log.info("删除Bucket：" + bucketName);
        }
    }

    /**
     * 判断Bucket是否存在
     *
     * @param bucketName 容器名字
     * @return true & false
     */
    public Boolean doesBucketExist(String bucketName) {
        boolean exists = ossClient.doesBucketExist(bucketName);
        if (exists) {
            _log.warn(bucketName + "容器已存在");
        }
        return exists;
    }


    /*************************************************文件 管理*********************************************************/

    /**
     * 上本地传文件，保留原有文件名
     *
     * @param bucketName bucket 名字
     * @param key        key
     * @param filePath   文件路径
     * @return MD5
     */
    public String uploadFile(String bucketName, String key, String filePath) {
        UploadFileRequest upload = new UploadFileRequest(bucketName, key);
        upload.setUploadFile(filePath);
        upload.setKey(new File(filePath).getName());
        UploadFileResult uploadFileResult = null;
        try {
            uploadFileResult = ossClient.uploadFile(upload);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        assert uploadFileResult != null;
        return uploadFileResult.getMultipartUploadResult().getETag();

    }

    /**
     * 上本地传文件,生成新的文件名
     *
     * @param bucketName bucket 名字
     * @param key        key
     * @param filePath   文件路径
     * @return 文件名
     */
    public String uploadFileNew(String bucketName, String key, String filePath) {
        UploadFileRequest upload = new UploadFileRequest(bucketName, key);
        upload.setUploadFile(filePath);
        upload.setKey(UUID.randomUUID().toString() + filePath.substring(filePath.lastIndexOf("."), filePath.length()));
        try {
            UploadFileResult uploadFileResult = ossClient.uploadFile(upload);
            System.out.println(uploadFileResult.getMultipartUploadResult().getLocation());
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return upload.getKey();

    }

    /**
     * 上传网络流
     *
     * @param bucketName bucket 名字
     * @param key        key
     * @param url        Url地址
     */
    public String putObjectUrl(String bucketName, String key, String url) {
        // 上传
        InputStream inputStream = null;
        try {
            inputStream = new URL(url).openStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, inputStream);
        putObjectRequest.setKey(UUID.randomUUID().toString() + url.substring(url.lastIndexOf("."), url.length()));
        PutObjectResult result = ossClient.putObject(putObjectRequest);
        return putObjectRequest.getKey();
    }

    /**
     * 创建模拟文件夹
     *
     * @param bucketName         bucket 名字
     * @param keySuffixWithSlash 文件夹路径 "parent_directory/"
     */
    public void createFolder(String bucketName, String keySuffixWithSlash) {
        ossClient.putObject(bucketName, keySuffixWithSlash, new ByteArrayInputStream(new byte[0]));
    }


}
