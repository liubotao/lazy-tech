package com.whh.common.api.tencent;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.meta.FileAuthority;
import com.qcloud.cos.meta.InsertOnly;
import com.qcloud.cos.meta.OverWrite;
import com.qcloud.cos.request.*;
import com.qcloud.cos.sign.Credentials;
import com.whh.common.api.model.CosKey;
import com.whh.common.api.model.HttpContent;

/**
 * 腾讯 对象存储Api
 * Created by huahui.wu on 2017/9/18.
 */
public class CosApi {

    private COSClient cosClient;

    /**
     * 初始化 CosClient
     *
     * @param appId     bucket id
     * @param secretId  bucket secretId
     * @param secretKey bucket secretKey
     * @param region    bucket 所在区域 比如广州(gz), 天津(tj), 上海(sh)
     */
    public CosApi(Long appId, String secretId, String secretKey, String region) {
        // 初始化客户端配置
        ClientConfig clientConfig = new ClientConfig();
        // 设置bucket所在的区域，比如广州(gz), 天津(tj)
        clientConfig.setRegion(region);
        // 初始化秘钥信息
        Credentials cred = new Credentials(appId, secretId, secretKey);
        // 初始化cosClient
        cosClient = new COSClient(clientConfig, cred);
    }

    /**
     * 初始化 CosClient
     *
     * @param cosKey cos配置
     */
    public CosApi(CosKey cosKey) {
        new CosApi(cosKey.getAppId(), cosKey.getSecretId(), cosKey.getSecretKey(), cosKey.getRegion());
    }

    public CosApi() {

    }

    /*******************************************文件操作************************************************/

    /**
     * 本地文件上传（默认开启断点上传）
     *
     * @param bucketName      bucket名称
     * @param cosPath         cos路径, 必须从bucket下的根/开始，目录路径必须以/结尾, 例如 /mytest/dir/
     * @param localPath       通过磁盘文件上传的本地绝对路径
     * @param bizAttr         文件的备注，主要用于对该文件用途的描述
     * @param insertOnly      是否直插入不覆盖已存在的文件。 NO_OVER_WRITE表示只直插入不覆盖，当文件存在时会返回错误； OVER_WRITE表示允许覆盖，当文件存在时覆盖原有文件，覆盖不会产生错误。
     * @param enableShaDigest 是否计算sha摘要，如果开启sha，并且bucket下有相同内容文件，则会触发秒传。sha计算会耗费一定的CPU和时间，建议大文件不开启。
     * @return
     */
    public String uploadFile(String bucketName, String cosPath, String localPath, String bizAttr,
                             InsertOnly insertOnly, Boolean enableShaDigest) {
        UploadFileRequest uploadFileRequest = new UploadFileRequest(bucketName, cosPath, localPath, bizAttr);
        uploadFileRequest.setInsertOnly(insertOnly);
        uploadFileRequest.setEnableShaDigest(enableShaDigest);
        String uploadFileRet = cosClient.uploadFile(uploadFileRequest);
        return uploadFileRet;
    }

    /**
     * 内存文件上传（默认开启断点上传）t
     *
     * @param bucketName      bucket名称
     * @param cosPath         cos路径, 必须从bucket下的根/开始，目录路径必须以/结尾, 例如 /mytest/dir/
     * @param contentBuffer   通过内存上传的buffer内容
     * @param bizAttr         文件的备注，主要用于对该文件用途的描述
     * @param insertOnly      是否直插入不覆盖已存在的文件。 NO_OVER_WRITE表示只直插入不覆盖，当文件存在时会返回错误； OVER_WRITE表示允许覆盖，当文件存在时覆盖原有文件，覆盖不会产生错误。
     * @param enableShaDigest 是否计算sha摘要，如果开启sha，并且bucket下有相同内容文件，则会触发秒传。sha计算会耗费一定的CPU和时间，建议大文件不开启。
     * @return
     */
    public String uploadFile(String bucketName, String cosPath, byte[] contentBuffer, String bizAttr,
                             InsertOnly insertOnly, Boolean enableShaDigest) {
        UploadFileRequest uploadFileRequest = new UploadFileRequest(bucketName, cosPath, contentBuffer);
        uploadFileRequest.setBizAttr(bizAttr);
        uploadFileRequest.setInsertOnly(insertOnly);
        uploadFileRequest.setEnableShaDigest(enableShaDigest);
        String uploadFileRet = cosClient.uploadFile(uploadFileRequest);
        return uploadFileRet;
    }

    /**
     * 下载文件(默认没有开启CDN加速)
     *
     * @param bucketName    bucket名称
     * @param cosFilePath   cos路径, 必须从bucket下的根/开始，目录路径必须以/结尾, 例如 /mytest/dir/
     * @param localPathDown 要下载到的本地路径
     * @return
     */
    public String getFileLocal(String bucketName, String cosFilePath, String localPathDown) {
        GetFileLocalRequest getFileLocalRequest = new GetFileLocalRequest(bucketName, cosFilePath, localPathDown);
        getFileLocalRequest.setUseCDN(false);
        getFileLocalRequest.setReferer("*.myweb.cn");
        String getFileResult = cosClient.getFileLocal(getFileLocalRequest);
        return getFileResult;
    }

    /**
     * 移动文件
     *
     * @param bucketName     bucket名称
     * @param cosFilePath    cos路径, 必须从bucket下的根/开始，文件路径不能以/结尾, 例如 /mytest/demo.txt
     * @param dstCosFilePath 移动文件的目标地址，必须从bucket下的根/开始，文件路径不能以/结尾，例如/mytest/demo.txt.move
     * @param overWrite      在移动的目标文件存在时，选择不覆盖还是覆盖，默认不覆盖
     * @return
     */
    public String moveFile(String bucketName, String cosFilePath, String dstCosFilePath, OverWrite overWrite) {
        MoveFileRequest moveRequest = new MoveFileRequest(bucketName, cosFilePath, dstCosFilePath);
        moveRequest.setOverWrite(overWrite);
        String moveFileResult = cosClient.moveFile(moveRequest);
        return moveFileResult;
    }

    /**
     * 获取文件属性
     *
     * @param bucketName  bucket名称
     * @param cosFilePath cos路径, 必须从bucket下的根/开始，文件路径不能以/结尾, 例如 /mytest/demo.txt
     * @return
     */
    public String statFile(String bucketName, String cosFilePath) {
        StatFileRequest statFileRequest = new StatFileRequest(bucketName, cosFilePath);
        String statFileRet = cosClient.statFile(statFileRequest);
        return statFileRet;
    }

    /**
     * 更新属性  tips:可以选择其中的某几个，对于HTTP头部cache_control，content_type, content_disposition和x-cos-meta-, 如果本次只更新其中的某几个，其他的都会被抹掉，即这4个属性是整体更新。
     *
     * @param bucketName  bucket名称
     * @param cosFilePath cos路径, 必须从bucket下的根/开始，文件路径不能以/结尾, 例如 /mytest/demo.txt
     * @param bizAttr     目录备注
     * @param httpContent http请求头
     * @return
     */
    public String updateFile(String bucketName, String cosFilePath, String bizAttr, HttpContent httpContent) {
        UpdateFileRequest updateFileRequest = new UpdateFileRequest(bucketName, cosFilePath);

        updateFileRequest.setBizAttr(bizAttr);
        updateFileRequest.setAuthority(FileAuthority.WPRIVATE);
        updateFileRequest.setCacheControl(httpContent.getCacheControl());
        updateFileRequest.setContentDisposition(httpContent.getContentDisposition());
        updateFileRequest.setContentLanguage(httpContent.getContentLanguage());
        updateFileRequest.setContentType(httpContent.getContentType());
        for (String cosMeta : httpContent.getxCosMeta()) {
            updateFileRequest.setXCosMeta("x-cos-meta-" + cosMeta, cosMeta);
        }
        String updateFileRet = cosClient.updateFile(updateFileRequest);

        return updateFileRet;
    }

    /**
     * 删除文件
     *
     * @param bucketName  bucket名称
     * @param cosFilePath cos路径, 必须从bucket下的根/开始，文件路径不能以/结尾, 例如 /mytest/demo.txt
     * @return
     */
    public String delFile(String bucketName, String cosFilePath) {
        DelFileRequest delFileRequest = new DelFileRequest(bucketName, cosFilePath);
        String delFileRet = cosClient.delFile(delFileRequest);
        return delFileRet;
    }

    /*******************************************目录操作************************************************/

    /**
     * 创建目录
     *
     * @param bucketName bucket名称
     * @param cosPath    cos路径, 必须从bucket下的根/开始，目录路径必须以/结尾, 例如 /mytest/dir/
     * @return {'code':\$code, 'message':$mess}, code为0表示成功, message为SUCCESS或者失败原因, 详情请参见返回值模块
     */
    public String createFolder(String bucketName, String cosPath) {
        CreateFolderRequest createFolderRequest = new CreateFolderRequest(bucketName, cosPath);
        String createFolderRet = cosClient.createFolder(createFolderRequest);
        System.out.println(createFolderRet);
        return createFolderRet;
    }

    /**
     * 创建目录
     *
     * @param bucketName bucket名称
     * @param cosPath    cosPath cos路径, 必须从bucket下的根/开始，目录路径必须以/结尾, 例如 /mytest/dir/
     * @param bizAttr    目录的备注，主要用于对目录用途的描述
     * @return {'code':\$code, 'message':$mess}, code为0表示成功, message为SUCCESS或者失败原因, 详情请参见返回值模块
     */
    public String createFolder(String bucketName, String cosPath, String bizAttr) {
        CreateFolderRequest createFolderRequest = new CreateFolderRequest(bucketName, cosPath, bizAttr);
        return cosClient.createFolder(createFolderRequest);
    }

    /**
     * 获取目录属性
     *
     * @param bucketName bucket名称
     * @param cosPath    cos路径, 必须从bucket下的根/开始，目录路径必须以/结尾, 例如 /mytest/dir/
     * @return {'code':\$code, 'message':$mess}, code为0表示成功, message为SUCCESS或者失败原因, 详情请参见返回值模块
     */
    public String statFolder(String bucketName, String cosPath) {
        StatFolderRequest statFolderRequest = new StatFolderRequest(bucketName, cosPath);
        String statFolderRet = cosClient.statFolder(statFolderRequest);
        return statFolderRet;
    }

    /**
     * 更新目录属性
     *
     * @param bucketName bucket名称
     * @param cosPath    cosPath cos路径, 必须从bucket下的根/开始，目录路径必须以/结尾, 例如 /mytest/dir/
     * @param bizAttr    目录的备注，主要用于对目录用途的描述
     * @return {'code':\$code, 'message':$mess}, code为0表示成功, message为SUCCESS或者失败原因, 详情请参见返回值模块
     */
    public String updateFolder(String bucketName, String cosPath, String bizAttr) {
        UpdateFolderRequest updateFolderRequest = new UpdateFolderRequest(bucketName, cosPath);
        updateFolderRequest.setBizAttr(bizAttr);
        String updateFolderRet = cosClient.updateFolder(updateFolderRequest);
        return updateFolderRet;
    }

    /**
     * 获取目录列表
     *
     * @param bucketName bucket名称
     * @param cosPath    cosPath cos路径, 必须从bucket下的根/开始，目录路径必须以/结尾, 例如 /mytest/dir/
     * @param num        获取列表成员的数量，最大为199
     * @param prefix     搜索成员的前缀, 例如prefix为test表示只搜索以test开头的文件或目录
     * @param context    搜索上下文, 由上一次list的结果返回，作为这一次搜索的起点，用于循环获取一个目录下的所有成员
     * @return
     */
    public String listFolder(String bucketName, String cosPath, Integer num, String prefix, String context) {
        ListFolderRequest listFolderRequest = new ListFolderRequest(bucketName, cosPath);
        if (num != null) {
            listFolderRequest.setNum(num);
        }
        if (prefix != null) {
            listFolderRequest.setPrefix(prefix);
        }
        if (context != null) {
            listFolderRequest.setContext(context);
        }
        String listFolderRet = cosClient.listFolder(listFolderRequest);
        return listFolderRet;
    }


}

