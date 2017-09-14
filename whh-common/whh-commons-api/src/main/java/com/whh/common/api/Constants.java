package com.whh.common.api;

import java.util.ResourceBundle;

/**
 * Created by Wuhuahui on 2016/12/13.
 */
public class Constants {
	public static final ResourceBundle rbn = ResourceBundle.getBundle("service-config");

	//email
	public final static String EMAIL_HOST = rbn.getString("email.host");
	public final static String EMAIL_SENDER = rbn.getString("email.sender");
	public final static String EMAIL_USERNAME = rbn.getString("email.username");
	public final static String EMAIL_PASSWORD = rbn.getString("email.password");
	public final static String EMAIL_URL = rbn.getString("email.url");

	//token
	public final  static String TOKEN_KEY = rbn.getString("token.key");

	//faceUrl
	public final static String USER_FACEURL = rbn.getString("user.faceUrl");

	//appkey
	public final static String ALIYUN_APPKEY = rbn.getString("aliyun.key");
	public final static String BAIDU_APPKEY = rbn.getString("baidu.key");


	//test
	public final static String TEST_NAME = rbn.getString("test.name");

//
	public static final String PAGE	= "admin/config/PAGE.txt";			//分页条数配置路径
	public static final String EMAIL = "admin/config/EMAIL.txt";		//邮箱服务器配置路径
	public static final String SMS1 = "admin/config/SMS1.txt";			//短信账户配置路径1
	public static final String SMS2 = "admin/config/SMS2.txt";			//短信账户配置路径2
	public static final String FWATERM = "admin/config/FWATERM.txt";	//文字水印配置路径
	public static final String IWATERM = "admin/config/IWATERM.txt";	//图片水印配置路径
	public static final String WEIXIN	= "admin/config/WEIXIN.txt";	//微信配置路径
	public static final String FILEPATHIMG = "uploadFiles/uploadImgs/";	//图片上传路径
	public static final String FILEPATHFILE = "uploadFiles/file/";		//文件上传路径
	public static final String FILEPATHTWODIMENSIONCODE = "uploadFiles/twoDimensionCode/"; //二维码存放路径
	public static final String NO_INTERCEPTOR_PATH = ".*/((login)|(logout)|(code)|(app)|(weixin)|(static)|(main)|(websocket)).*";	//不对匹配该值的访问路径拦截（正则）

	/**
	 * 异常信息统一头信息<br>
	 * 非常遗憾的通知您,程序发生了异常
	 */
	public static final String Exception_Head = "OH,MY GOD! SOME ERRORS OCCURED! AS FOLLOWS :";
	/** 客户端语言 */
	public static final String USERLANGUAGE = "userLanguage";
	/** 当前用户 */
	public static final String CURRENT_USER = "CURRENT_USER";
}
