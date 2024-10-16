package com.sy.common.util;

/**
 * @author zys
 * @description TODO
 * @date 2024/7/16
 */

import com.hx.common.constants.CacheConstants;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class HashUtils {

    public static void main(String[] args) {
        String fieldName = "width";
        String salt = "Nzc1NjExZTQtOTY0ZS00YTEzLThiODkt";
        String hashedFieldName = hashFieldName(fieldName, salt);
        System.out.println(hashedFieldName);
    }

    public static String hashFieldName(String fieldName, String salt) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String toHash = fieldName + salt;
            byte[] hash = digest.digest(toHash.getBytes(StandardCharsets.UTF_8));

            // 使用Base64编码将哈希值转换为字符串
            return Base64.getUrlEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error occurred while hashing the field name", e);
        }
    }

    /**
     * 带盐的加密处理 缓存
     *
     * @param fieldName 原始字符串
     * @return 带盐的MD5哈希值
     */
    public static String hashFieldNameCache(String fieldName, String salt) {
        //        将盐添加到输入数据的前面 返回盐和哈希值的组合,设置缓存并返回
        Map<String, String> innerMap = CacheConstants.MD5_FIELD_MAP.computeIfAbsent(salt, k -> new HashMap<>());
        String md = innerMap.putIfAbsent(fieldName, hashFieldName(fieldName, salt).substring(0, 10));
        //判断md是否为空串
        if (StringUtils.hasText(md)) {
            return md;
        }
        return innerMap.get(fieldName);
    }
}
