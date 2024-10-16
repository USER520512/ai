package com.sy.common.util;

import cn.hutool.core.lang.UUID;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author lwb
 * @description
 * @date 2024/7/17
 */
public class GenerateKeyUtils {

    public static String generateUserId() {
        // 生成一个基于UUID的随机字符串，然后取其一部分并进行Base64编码以提高可读性
        UUID uuid = UUID.randomUUID();
        byte[] bytes = uuid.toString().getBytes(StandardCharsets.UTF_8);
        byte[] encodedBytes = Base64.getUrlEncoder().encode(bytes);
        String base64String = new String(encodedBytes, StandardCharsets.UTF_8).replace("=", "").replace("+", "-").replace("/", "_");
        // 截取前32个字符作为示例的APP Key，根据需要调整长度
        return base64String.substring(0, 16);
    }
}
