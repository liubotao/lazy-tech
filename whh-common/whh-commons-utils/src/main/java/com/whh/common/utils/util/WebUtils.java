package com.whh.common.utils.util;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.ser.BeanDeserializerFactory;
import org.apache.axis.encoding.ser.BeanSerializerFactory;
import org.apache.axis.message.SOAPHeaderElement;

import javax.net.ssl.*;
import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.ServiceException;
import javax.xml.soap.SOAPException;
import java.io.*;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.rmi.RemoteException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.zip.GZIPInputStream;

/**
 * 网络工具类。
 *
 * @since 1.0, 2016-03-09 13:26:00
 */
public abstract class WebUtils {

    private static final String DEFAULT_CHARSET = "UTF-8";
    //	private static final Set<String> aliDomains = new HashSet<String>();
    private static final String METHOD_POST = "POST";
    private static final String METHOD_GET = "GET";
    private static final String METHOD_DELETE = "DELETE";
//	private static final Certificate verisign; // G3根证书

    private static boolean ignoreSSLCheck; // 忽略SSL检查
    private static boolean ignoreHostCheck; // 忽略HOST检查
    public static final String CHARSET_UTF8 = DEFAULT_CHARSET;
    public static final String CHARSET_GBK = "GBK";
    public static final String FORMAT_JSON = "json";
    public static final String FORMAT_XML = "xml";
    public static final String SIGN_METHOD_MD5 = "md5";
    public static final String SIGN_METHOD_HMAC = "hmac";
    public static final String ACCEPT_ENCODING = "Accept-Encoding";
    public static final String CONTENT_ENCODING = "Content-Encoding";
    public static final String CONTENT_ENCODING_GZIP = "gzip";
    public static final String ERROR_RESPONSE = "error_response";
    public static final String ERROR_CODE = "code";
    public static final String ERROR_MSG = "msg";
    private static int connectionTimeOut = 25000;
    private static int readTimeout = 25000;
//	static {
//		aliDomains.add("*.iscs.com.cn");
//
//		InputStream input = null;
//		try {
//			CertificateFactory cf = CertificateFactory.getInstance("X.509");
//			input = WebUtils.class.getResourceAsStream("/verisign.crt");
//			verisign = cf.generateCertificate(input);
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		} finally {
//			if (input != null) {
//				try {
//					input.close();
//				} catch (IOException e) {
//				}
//			}
//		}
//	}

//	public static class VerisignTrustManager implements X509TrustManager {
//		public X509Certificate[] getAcceptedIssuers() {
//			return null;
//		}
//
//		public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
//		}
//
//		public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
//			X509Certificate aliCert = null;
//			for (X509Certificate cert : chain) {
//				cert.checkValidity(); // 验证证书是否已经过期
//				try {
//					String dn = cert.getSubjectX500Principal().getName();
//					LdapName ldapDN = new LdapName(dn);
//					for (Rdn rdn : ldapDN.getRdns()) {
//						if ("CN".equals(rdn.getType()) && aliDomains.contains(rdn.getValue())) {
//							aliCert = cert; // 查询是否存在阿里系网站的证书
//							break;
//						}
//					}
//				} catch (Exception e) {
//					throw new CertificateException(e);
//				}
//			}
//			if (aliCert != null) {
//				try {
//					aliCert.verify(verisign.getPublicKey()); // 验证证书是否是权威机构颁发的
//				} catch (Exception e) {
//					throw new CertificateException(e);
//				}
//			} else {
//				throw new CertificateException("Access to the non Alibaba Group's HTTPS services are not allowed!");
//			}
//		}
//	}
//
//	public static class TrustAllTrustManager implements X509TrustManager {
//		public X509Certificate[] getAcceptedIssuers() {
//			return null;
//		}
//
//		public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
//		}
//
//		public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
//		}
//	}


    /**
     * 将url参数转换成map
     *
     * @param param aa=11&bb=22&cc=33
     * @return
     */
    public static Map<String, String> getUrlParams(String param) {
        Map<String, String> map = new HashMap<String, String>();
        if (StringUtils.isEmpty(param)) {
            return map;
        }
        String[] params = param.split("&");
        for (int i = 0; i < params.length; i++) {
            String[] p = params[i].split("=");
            if (p.length == 2) {
                map.put(p[0], p[1]);
            }
        }
        return map;
    }

    /**
     * 将map转换成url
     *
     * @param map
     * @return
     */

    public static String getUrlParamsByMap(Map<String, Object> map) {
        if (map == null) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for (Entry<String, Object> entry : map.entrySet()) {
            sb.append(entry.getKey() + "=" + entry.getValue());
            sb.append("&");
        }
        String s = sb.toString();
        if (s.endsWith("&")) {
            s = org.apache.commons.lang.StringUtils.substringBeforeLast(s, "&");
        }
        return s;
    }

    /**
     * 将map转换成url
     *
     * @param map
     * @return
     */

    public static String getUrlParamsAppendToString(Map<String, String> map) {
        if (map == null) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for (Entry<String, String> entry : map.entrySet()) {
            sb.append(entry.getKey() + "=" + entry.getValue());
            sb.append("&");
        }
        String s = sb.toString();
        if (s.endsWith("&")) {
            s = org.apache.commons.lang.StringUtils.substringBeforeLast(s, "&");
        }
        return s;
    }

    private WebUtils() {
    }

    public static void setIgnoreSSLCheck(boolean ignoreSSLCheck) {
        WebUtils.ignoreSSLCheck = ignoreSSLCheck;
    }

    public static void setIgnoreHostCheck(boolean ignoreHostCheck) {
        WebUtils.ignoreHostCheck = ignoreHostCheck;
    }

    /**
     * 执行HTTP POST请求。
     *
     * @param url    请求地址
     * @param params 请求参数
     * @return 响应字符串
     */
    public static String doPost(String url, Map<String, String> params, int connectTimeout, int readTimeout) throws IOException {
        return doPost(url, params, DEFAULT_CHARSET, connectTimeout, readTimeout);
    }

    /**
     * 执行HTTP POST请求。
     *
     * @param url    请求地址
     * @param params 请求参数
     * @return 响应字符串
     */
    public static String doPost(String url, Map<String, String> params) throws IOException {
        return doPost(url, params, DEFAULT_CHARSET, connectionTimeOut, readTimeout);
    }

    /**
     * 执行HTTP POST请求。
     */
    public static String doIscsPost(String url, Map<String, String> headerParams, String requestBody) throws IOException {
        String ctype = "application/json";
        return _doPost(url, ctype, requestBody.getBytes(DEFAULT_CHARSET), connectionTimeOut, readTimeout, headerParams);
    }

    /**
     * 执行HTTP POST请求。
     *
     * @param url     请求地址
     * @param params  请求参数
     * @param charset 字符集，如UTF-8, GBK, GB2312
     * @return 响应字符串
     */
    public static String doPost(String url, Map<String, String> params, String charset,
                                int connectTimeout, int readTimeout) throws IOException {
        return doPost(url, params, charset, connectTimeout, readTimeout, null);
    }

    public static String doPost(String url, Map<String, String> params, String charset,
                                int connectTimeout, int readTimeout, Map<String, String> headerMap) throws IOException {
        String ctype = "application/x-www-form-urlencoded;charset=" + charset;
        String query = buildQuery(params, charset);
        byte[] content = {};
        if (query != null) {
            content = query.getBytes(charset);
        }
        return _doPost(url, ctype, content, connectTimeout, readTimeout, headerMap);
    }

    public static String doPost(String url, Map<String, String> params, String contentType, String charset,
                                int connectTimeout, int readTimeout, Map<String, String> headerMap) throws IOException {
        String query = buildQuery(params, charset);
        byte[] content = {};
        if (query != null) {
            content = query.getBytes(charset);
        }
        return _doPost(url, contentType, content, connectTimeout, readTimeout, headerMap);
    }

    public static String doPost(String url, String apiBody, String charset, int connectTimeout,
                                int readTimeout, Map<String, String> headerMap) throws IOException {
        String ctype = "text/plain;charset=" + charset;
        byte[] content = apiBody.getBytes(charset);
        return _doPost(url, ctype, content, connectTimeout, readTimeout, headerMap);
    }

    /**
     * 执行HTTP POST请求。
     *
     * @param url     请求地址
     * @param ctype   请求类型
     * @param content 请求字节数组
     * @return 响应字符串
     */
    public static String doPost(String url, String ctype, byte[] content, int connectTimeout, int readTimeout) throws IOException {
        return _doPost(url, ctype, content, connectTimeout, readTimeout, null);
    }

    private static String _doPost(String url, String ctype, byte[] content, int connectTimeout, int readTimeout, Map<String, String> headerMap) throws IOException {
        HttpURLConnection conn = null;
        OutputStream out = null;
        String rsp = null;
        try {
            conn = getConnection(new URL(url), METHOD_POST, ctype, headerMap);
            conn.setConnectTimeout(connectTimeout);
            conn.setReadTimeout(readTimeout);
            out = conn.getOutputStream();
            out.write(content);
            rsp = getResponseAsString(conn);
        } finally {
            if (out != null) {
                out.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }

        return rsp;
    }

    /**
     * 执行带文件上传的HTTP POST请求。
     *
     * @param url        请求地址
     * @param params 文本请求参数
     * @param fileParams 文件请求参数
     * @return 响应字符串
     */
    public static String doPost(String url, Map<String, String> params, Map<String, FileItem> fileParams,
                                int connectTimeout, int readTimeout) throws IOException {
        if (fileParams == null || fileParams.isEmpty()) {
            return doPost(url, params, DEFAULT_CHARSET, connectTimeout, readTimeout);
        } else {
            return doPost(url, params, fileParams, DEFAULT_CHARSET, connectTimeout, readTimeout);
        }
    }

    public static String doPost(String url, Map<String, String> params, Map<String, FileItem> fileParams,
                                String charset, int connectTimeout, int readTimeout) throws IOException {
        return doPost(url, params, fileParams, charset, connectTimeout, readTimeout, null);
    }

    /**
     * 执行带文件上传的HTTP POST请求。
     *
     * @param url        请求地址
     * @param params 文本请求参数
     * @param fileParams 文件请求参数
     * @param charset    字符集，如UTF-8, GBK, GB2312
     * @param headerMap  需要传递的header头，可以为空
     * @return 响应字符串
     */
    public static String doPost(String url, Map<String, String> params, Map<String, FileItem> fileParams, String charset,
                                int connectTimeout, int readTimeout, Map<String, String> headerMap) throws IOException {
        if (fileParams == null || fileParams.isEmpty()) {
            return doPost(url, params, charset, connectTimeout, readTimeout, headerMap);
        } else {
            return _doPostWithFile(url, params, fileParams, charset, connectTimeout, readTimeout, headerMap);
        }
    }

    private static String _doPostWithFile(String url, Map<String, String> params, Map<String, FileItem> fileParams,
                                          String charset, int connectTimeout, int readTimeout, Map<String, String> headerMap) throws IOException {
        String boundary = String.valueOf(System.nanoTime()); // 随机分隔线
        HttpURLConnection conn = null;
        OutputStream out = null;
        String rsp = null;
        try {
            String ctype = "multipart/form-data;charset=" + charset + ";boundary=" + boundary;
            conn = getConnection(new URL(url), METHOD_POST, ctype, headerMap);
            conn.setConnectTimeout(connectTimeout);
            conn.setReadTimeout(readTimeout);
            out = conn.getOutputStream();
            byte[] entryBoundaryBytes = ("\r\n--" + boundary + "\r\n").getBytes(charset);

            // 组装文本请求参数
            Set<Entry<String, String>> textEntrySet = params.entrySet();
            for (Entry<String, String> textEntry : textEntrySet) {
                byte[] textBytes = getTextEntry(textEntry.getKey(), textEntry.getValue(), charset);
                out.write(entryBoundaryBytes);
                out.write(textBytes);
            }

            // 组装文件请求参数
            Set<Entry<String, FileItem>> fileEntrySet = fileParams.entrySet();
            for (Entry<String, FileItem> fileEntry : fileEntrySet) {
                FileItem fileItem = fileEntry.getValue();
                if (fileItem.getContent() == null) {
                    continue;
                }
                byte[] fileBytes = getFileEntry(fileEntry.getKey(), fileItem.getFileName(), fileItem.getMimeType(), charset);
                out.write(entryBoundaryBytes);
                out.write(fileBytes);
                out.write(fileItem.getContent());
            }

            // 添加请求结束标志
            byte[] endBoundaryBytes = ("\r\n--" + boundary + "--\r\n").getBytes(charset);
            out.write(endBoundaryBytes);
            rsp = getResponseAsString(conn);
        } finally {
            if (out != null) {
                out.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }

        return rsp;
    }

    private static byte[] getTextEntry(String fieldName, String fieldValue, String charset) throws IOException {
        StringBuilder entry = new StringBuilder();
        entry.append("Content-Disposition:form-data;name=\"");
        entry.append(fieldName);
        entry.append("\"\r\nContent-Type:text/plain\r\n\r\n");
        entry.append(fieldValue);
        return entry.toString().getBytes(charset);
    }

    private static byte[] getFileEntry(String fieldName, String fileName, String mimeType, String charset) throws IOException {
        StringBuilder entry = new StringBuilder();
        entry.append("Content-Disposition:form-data;name=\"");
        entry.append(fieldName);
        entry.append("\";filename=\"");
        entry.append(fileName);
        entry.append("\"\r\nContent-Type:");
        entry.append(mimeType);
        entry.append("\r\n\r\n");
        return entry.toString().getBytes(charset);
    }

    /**
     * 执行HTTP GET请求。
     *
     * @param url    请求地址
     * @param params 请求参数
     * @return 响应字符串
     */
    public static String doGet(String url, Map<String, String> params) throws IOException {
        return doGet(url, params, DEFAULT_CHARSET);
    }

    /**
     * 执行HTTP GET请求。
     *
     * @param url     请求地址
     * @param params  请求参数
     * @param charset 字符集，如UTF-8, GBK, GB2312
     * @return 响应字符串
     */
    public static String doGet(String url, Map<String, String> params, String charset) throws IOException {
        return doGet(url, params, charset, null);
    }

    /**
     * 执行HTTP GET请求。
     *
     * @param url       请求地址
     * @param params    请求参数
     * @param charset   字符集，如UTF-8, GBK, GB2312
     * @param headerMap header
     * @return 响应字符串
     */
    public static String doGet(String url, Map<String, String> params, String charset, Map<String, String> headerMap) throws IOException {
        HttpURLConnection conn = null;
        String rsp = null;

        try {
            String ctype = "application/x-www-form-urlencoded;charset=" + charset;
            String query = buildQuery(params, charset);
            conn = getConnection(buildGetUrl(url, query), METHOD_GET, ctype, headerMap);
            rsp = getResponseAsString(conn);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        return rsp;
    }


    public static HttpURLConnection getConnection(URL url, String method, String ctype, Map<String, String> headerMap) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        if (conn instanceof HttpsURLConnection) {
            HttpsURLConnection connHttps = (HttpsURLConnection) conn;
            if (ignoreSSLCheck) {
                try {
                    SSLContext ctx = SSLContext.getInstance("TLS");
                    ctx.init(null, new TrustManager[]{new TrustAnyTrustManager()}, new SecureRandom());
                    connHttps.setSSLSocketFactory(ctx.getSocketFactory());
                    connHttps.setHostnameVerifier(new HostnameVerifier() {
                        public boolean verify(String hostname, SSLSession session) {
                            return true;
                        }
                    });
                } catch (Exception e) {
                    throw new IOException(e);
                }
            } else {
                try {
                    SSLContext ctx = SSLContext.getInstance("TLS");
                    ctx.init(null, new TrustManager[]{new TrustAnyTrustManager()}, new SecureRandom());
                    connHttps.setSSLSocketFactory(ctx.getSocketFactory());
                    if (ignoreHostCheck) {
                        connHttps.setHostnameVerifier(new HostnameVerifier() {
                            public boolean verify(String hostname, SSLSession session) {
                                return true;
                            }
                        });
                    }
                } catch (Exception e) {
                    throw new IOException(e);
                }
            }
            conn = connHttps;
        }

        conn.setRequestMethod(method);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setRequestProperty("Host", url.getHost());
//		conn.setRequestProperty("Accept", "text/xml,text/javascript");
        conn.setRequestProperty("User-Agent", "Iscs-Http-Client");
        conn.setRequestProperty("Content-Type", ctype);
        if (headerMap != null) {
            for (Entry<String, String> entry : headerMap.entrySet()) {
                conn.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }
        return conn;
    }

    /**
     * 执行HTTP DELETE请求。
     *
     * @param url    请求地址
     * @param params 请求参数
     * @return 响应字符串
     */
    public static String doDelete(String url, Map<String, String> params) throws IOException {
        return doDelete(url, params, DEFAULT_CHARSET);
    }

    /**
     * 执行HTTP DELETE请求。
     *
     * @param url     请求地址
     * @param params  请求参数
     * @param charset 字符集，如UTF-8, GBK, GB2312
     * @return 响应字符串
     */
    public static String doDelete(String url, Map<String, String> params, String charset) throws IOException {
        return doDelete(url, params, charset, null);
    }

    /**
     * 执行HTTP DELETE请求。
     *
     * @param url       请求地址
     * @param params    请求参数
     * @param charset   字符集，如UTF-8, GBK, GB2312
     * @param headerMap header
     * @return 响应字符串
     */
    public static String doDelete(String url, Map<String, String> params,
                                  String charset, Map<String, String> headerMap) throws IOException {
        HttpURLConnection conn = null;
        String rsp = null;

        try {
            String ctype = "application/x-www-form-urlencoded;charset=" + charset;
            String query = buildQuery(params, charset);
            conn = getConnection(buildGetUrl(url, query), METHOD_DELETE, ctype, headerMap);
            rsp = getResponseAsString(conn);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        return rsp;
    }

    private static URL buildGetUrl(String strUrl, String query) throws IOException {
        URL url = new URL(strUrl);
        if (StringUtils.isEmpty(query)) {
            return url;
        }

        if (StringUtils.isEmpty(url.getQuery())) {
            if (strUrl.endsWith("?")) {
                strUrl += query;
            } else {
                strUrl += "?" + query;
            }
        } else {
            if (strUrl.endsWith("&")) {
                strUrl += query;
            } else {
                strUrl += "&" + query;
            }
        }

        return new URL(strUrl);
    }

    public static String buildQuery(Map<String, String> params, String charset) {
        if (params == null || params.isEmpty()) {
            return null;
        }

        StringBuilder query = new StringBuilder();
        Set<Entry<String, String>> entries = params.entrySet();
        boolean hasParam = false;

        for (Entry<String, String> entry : entries) {
            String name = entry.getKey();
            String value = entry.getValue();
            // 忽略参数名或参数值为空的参数
            if (StringUtils.areNotEmpty(name, value)) {
                if (hasParam) {
                    query.append("&");
                } else {
                    hasParam = true;
                }

                query.append(name).append("=").append(CodecUtils.urlEncode(value, charset));
            }
        }

        return query.toString();
    }

    public static String buildQuery(Map<String, String> params, String charset, boolean doEncode) {
        if (params == null || params.isEmpty()) {
            return null;
        }

        StringBuilder query = new StringBuilder();
        Set<Entry<String, String>> entries = params.entrySet();
        boolean hasParam = false;

        for (Entry<String, String> entry : entries) {
            String name = entry.getKey();
            String value = entry.getValue();
            // 忽略参数名或参数值为空的参数
            if (StringUtils.areNotEmpty(name, value)) {
                if (hasParam) {
                    query.append("&");
                } else {
                    hasParam = true;
                }
                if (doEncode) {
                    value = CodecUtils.urlDecode(value, charset);
                }

                query.append(name).append("=").append(value);
            }
        }

        return query.toString();
    }

    /**
     * @param url
     * @param queries
     * @return
     */
    public static String combineQueries(StringBuilder url, String... queries) {
        if (queries == null || queries.length == 0) {
            return url.toString();
        }

        boolean hasQuery = url.indexOf("?") != -1;
        for (String query : queries) {
            if (!StringUtils.isEmpty(query)) {
                char lastChar = url.charAt(url.length() - 1);
                if (lastChar != '?' && lastChar != '&') {
                    if (hasQuery) {
                        url.append("&");
                    } else {
                        url.append("?");
                        hasQuery = true;
                    }
                }
                url.append(query);
            }
        }
        return url.toString();
    }

    public static String combineQueries(StringBuilder url, Map<String, String> params, String charset) {
        if (params == null || params.isEmpty()) {
            return url.toString();
        }

        String paramString = buildQuery(params, charset);
        return combineQueries(url, paramString);
    }

    public static String combineQueries(StringBuilder url, Map<String, String> params) {
        if (params == null || params.isEmpty()) {
            return url.toString();
        }

        String paramString = buildQuery(params, CHARSET_UTF8);
        return combineQueries(url, paramString);
    }

    public static String combineQueries(String url, Map<String, String> params) {
        if (params == null || params.isEmpty()) {
            return url;
        }

        StringBuilder strBuilder = new StringBuilder(url);
        return combineQueries(strBuilder, params, CHARSET_UTF8);
    }

    public static String combineQueries(String url, Map<String, String> params, String charset) {
        if (params == null || params.isEmpty()) {
            return url;
        }

        StringBuilder strBuilder = new StringBuilder(url);
        return combineQueries(strBuilder, params, charset);
    }

    protected static String getResponseAsString(HttpURLConnection conn) throws IOException {
        String charset = getResponseCharset(conn.getContentType());
        if (conn.getResponseCode() < 400) {
            String contentEncoding = conn.getContentEncoding();
            if (WebUtils.CONTENT_ENCODING_GZIP.equalsIgnoreCase(contentEncoding)) {
                return getStreamAsString(new GZIPInputStream(conn.getInputStream()), charset);
            } else {
                return getStreamAsString(conn.getInputStream(), charset);
            }
        } else {// Client Error 4xx and Server Error 5xx
            throw new IOException(conn.getResponseCode() + " " + conn.getResponseMessage());
        }
    }

    public static String getStreamAsString(InputStream stream, String charset) throws IOException {
        try {
            Reader reader = new InputStreamReader(stream, charset);
            StringBuilder response = new StringBuilder();

            final char[] buff = new char[1024];
            int read = 0;
            while ((read = reader.read(buff)) > 0) {
                response.append(buff, 0, read);
            }

            return response.toString();
        } finally {
            if (stream != null) {
                stream.close();
            }
        }
    }

    public static String getResponseCharset(String ctype) {
        String charset = DEFAULT_CHARSET;

        if (!StringUtils.isEmpty(ctype)) {
            String[] params = ctype.split(";");
            for (String param : params) {
                param = param.trim();
                if (param.startsWith("charset")) {
                    String[] pair = param.split("=", 2);
                    if (pair.length == 2) {
                        if (!StringUtils.isEmpty(pair[1])) {
                            charset = pair[1].trim();
                        }
                    }
                    break;
                }
            }
        }

        return charset;
    }

    /**
     * 调用Web Service服务
     *
     * @param targetAddress eg:http://sws2.vjia.com/swsms/GetOrderService.asmx?WSDL
     * @param namespaceURI  targetNamespace中定义的值，eg:targetNamespace="http://swsms.vjia.org/"，则值为http://swsms.vjia.org/
     * @param localPart     eg:GetFormCodeInfo
     * @param params
     * @param headerName
     * @param headerParams
     * @param timeOut
     * @param returnType
     * @return
     * @throws Exception
     */
    public static Object callService(String targetAddress, String namespaceURI,
                                     String localPart, Map<String, Object[]> params, String headerName,
                                     Map<String, String> headerParams, int timeOut, QName returnType)
            throws ServiceException, SOAPException, RemoteException {
        Map<String, Map<String, String>> hParams = null;
        if (StringUtils.isNotEmpty(headerName) && headerParams != null
                && !headerParams.isEmpty()) {
            hParams = new HashMap<>();
            hParams.put(headerName, headerParams);
        }

        return callService(targetAddress, namespaceURI, localPart, params,
                hParams, timeOut, returnType);
    }

    /**
     * 调用Web Service服务
     *
     * @param targetAddress eg:http://sws2.vjia.com/swsms/GetOrderService.asmx?WSDL
     * @param namespaceURI  targetNamespace中定义的值 ,eg:targetNamespace="http://swsms.vjia.org/"，则值为http://swsms.vjia.org/
     * @param localPart     eg:GetFormCodeInfo
     * @param params
     * @param headerParams  请求头信息
     * @param timeOut
     * @param returnType
     * @return
     * @throws Exception
     */
    public static Object callService(String targetAddress, String namespaceURI,
                                     String localPart, Map<String, Object[]> params,
                                     Map<String, Map<String, String>> headerParams, int timeOut, QName returnType)
            throws ServiceException, SOAPException, RemoteException {
        return callService(targetAddress, namespaceURI, localPart, params,
                headerParams, timeOut, returnType, null);
    }

    public static Object callService(String targetAddress, String namespaceURI,
                                     String localPart, Map<String, Object[]> params,
                                     Map<String, Map<String, String>> headerParams, int timeOut,
                                     QName returnType, Class<?> returnClazz) throws ServiceException,
            SOAPException, RemoteException {
        Call call = (Call) new Service().createCall();
        call.setTargetEndpointAddress(targetAddress);
        call.setOperationName(new QName(namespaceURI, localPart));
        call.setTimeout(timeOut);

        //设置输入参数
        Object[] wsParams = new Object[params.keySet().size()];
        int i = 0;
        for (String key : params.keySet()) {
            Object[] objs = (Object[]) params.get(key);
            QName type = (QName) objs[0];
            call.addParameter(key, type, ParameterMode.IN);
            wsParams[i] = objs[1];
            i++;
        }

        //设置返回值类型
        if (returnClazz != null) {
            call.registerTypeMapping(returnClazz, returnType,
                    new BeanSerializerFactory(returnClazz, returnType),//序列化
                    new BeanDeserializerFactory(returnClazz, returnType));
            call.setReturnClass(Array.newInstance(returnClazz, 100).getClass());
        } else {
            call.setReturnType(returnType);
        }

        //设置头信息
        if (headerParams != null && !headerParams.isEmpty()) {
            for (String childName : headerParams.keySet()) {
                Map<String, String> map = headerParams.get(childName);

                SOAPHeaderElement soapHeader = new SOAPHeaderElement(namespaceURI, childName);
                soapHeader.setNamespaceURI(namespaceURI);
                for (String key : map.keySet()) {
                    soapHeader.addChildElement(key).setValue(map.get(key));
                }
                call.addHeader(soapHeader);
            }
        }

        return call.invoke(wsParams);
    }

    public static void closeQuietly(OutputStream output) {
        try {
            if (output != null) {
                output.close();
            }
        } catch (IOException ioe) {
        }
    }

    public static void closeQuietly(InputStream input) {
        try {
            if (input != null) {
                input.close();
            }
        } catch (IOException ioe) {
        }
    }

    /**
     * 从URL中提取所有的参数。
     *
     * @param query URL地址
     * @return 参数映射
     */
    public static Map<String, String> splitUrlQuery(String query) {
        Map<String, String> result = new HashMap<String, String>();

        String[] pairs = query.split("&");
        if (pairs != null && pairs.length > 0) {
            for (String pair : pairs) {
                String[] param = pair.split("=", 2);
                if (param != null && param.length == 2) {
                    result.put(param[0], param[1]);
                }
            }
        }

        return result;
    }

    /**
     * 拼装异常栈中的信息
     *
     * @param e
     * @return
     */
    public static String getStackTrace(Throwable e) {
        if (e == null) {
            return "";
        }
        StringBuilder trace = new StringBuilder();
        if (e.getMessage() != null) {
            trace.append(e.getMessage());
        }
        if (e.getStackTrace() != null) {
            for (StackTraceElement el : e.getStackTrace()) {
                trace.append("\n").append(el.toString());
            }
        }
        return trace.toString();
    }


}

class TrustAnyHostnameVerifier implements HostnameVerifier {
    public boolean verify(String hostname, SSLSession session) {
        return true;
    }
}

class TrustAnyTrustManager implements X509TrustManager {
    public void checkClientTrusted(X509Certificate[] chain, String authType)
            throws CertificateException {
    }

    public void checkServerTrusted(X509Certificate[] chain, String authType)
            throws CertificateException {
    }

    public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[0];
    }

}