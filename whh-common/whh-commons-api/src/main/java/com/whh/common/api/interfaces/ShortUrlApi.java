package com.whh.common.api.interfaces;

import com.whh.common.utils.util.AbsResponse;

/**
 * Created by huahui.wu on 2017/9/14.
 */
public interface ShortUrlApi {
    /**
     * 获取 短网址
     * @param longUrl
     * @return
     * @throws Exception
     */
    AbsResponse<String> generateShortUrl(String longUrl) throws Exception;
}
