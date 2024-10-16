package com.sy.common.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class UrlUtils {

    private static final String ENCODING = "UTF-8";

    // URLEncode 方法
    public static String urlEncode(String value) {
        try {
            return URLEncoder.encode(value, ENCODING);
        } catch (UnsupportedEncodingException e) {
            // 处理异常，返回原始值或其他处理
            e.printStackTrace();
            return value;
        }
    }

    // URLDecode 方法
    public static String urlDecode(String value) {
        try {
            return URLDecoder.decode(value, ENCODING);
        } catch (UnsupportedEncodingException e) {
            // 处理异常，返回原始值或其他处理
            e.printStackTrace();
            return value;
        }
    }
}
