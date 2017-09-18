package com.whh.common.api.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by huahui.wu on 2017/9/18.
 */
public class ApiUtils {

    /**
     * 生成文件名加当前时间
     *
     * @param fileName 文件名
     * @return
     */
    public static String getRandomFileName(String fileName) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

        Date date = new Date();

        String str = simpleDateFormat.format(date);

        return fileName.substring(0, fileName.lastIndexOf(".")) + str + fileName.substring(fileName.lastIndexOf("."), fileName.length());// 当前时间
    }
}
