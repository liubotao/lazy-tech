package com.whh.common.utils.qrcode;

import junit.framework.TestCase;

import java.io.File;

/**
 * Created by huahui.wu on 2017/9/15.
 */
public class QrcodeUtilsTest extends TestCase {
    public void testGen() throws Exception {

        QrcodeUtils.gen("123456789", new File("C:\\Users\\Wuhuahui\\Desktop\\qr.jpg"));
    }

}