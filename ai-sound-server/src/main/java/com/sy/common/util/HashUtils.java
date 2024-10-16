package com.sy.common.util;

/**
 * @author zys
 * @description TODO
 * @date 2024/7/16
 */



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


}
