package com.hx.common.util;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author zys
 * @description 利用java原生的类实现SHA512加密
 * @date 2024/7/8
 */
@Slf4j
public class SHAUtil {

    /**
     * 利用java原生的类实现SHA512加密
     *
     * @param str 加密后的报文
     * @return
     */
    public static String tosHA512(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] hash = md.digest(str.getBytes());
            StringBuilder result = new StringBuilder();
            for (byte b : hash) {
                result.append(String.format("%02x", b));
            }
            return result.toString();
        } catch (Exception e) {
            log.error("SHAUtil.tosHA512 is error", e);
        }
        return null;
    }


    /**
     * 将字节数组转换为十六进制字符串
     *
     * @param bytes
     * @return
     */
    private static String toHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b & 0xff));
        }
        return sb.toString();
    }


    /**
     * 对给定字符串使用 SHA-512 带密钥进行哈希
     *
     * @param str
     * @param key
     * @return
     */
    public static String sha512HashWithKey(String str, String key) {
        try {
            Mac mac = Mac.getInstance("HmacSHA512");
            SecretKeySpec sks = new SecretKeySpec(key.getBytes(), "HmacSHA512");
            mac.init(sks);
            byte[] hmac = mac.doFinal(str.getBytes());
            return toHexString(hmac);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Error hashing with SHA-512 and key.", e);
        }
    }

    /**
     * sha256加密
     *
     * @param str 要加密的字符串
     * @return 加密后的字符串
     */
    public static String getSha256Str(String str) {
        MessageDigest messageDigest;
        String encodeStr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes(StandardCharsets.UTF_8));
            encodeStr = byte2Hex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return encodeStr;
    }

    /**
     * sha256加密 将byte转为16进制
     *
     * @param bytes 字节码
     * @return 加密后的字符串
     */
    private static String byte2Hex(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder();
        String temp;
        for (byte aByte : bytes) {
            temp = Integer.toHexString(aByte & 0xFF);
            if (temp.length() == 1) {
                //1得到一位的进行补0操作
                stringBuilder.append("0");
            }
            stringBuilder.append(temp);
        }
        return stringBuilder.toString();
    }

    public static void main(String[] args) {

        String strs = "8062084377" + "389743294675" + 80000 + 566;

        String sha512 = sha512HashWithKey(strs, "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDEUDnrxymaO3SDMwdKChLYo3j2DjoCNWe5a3TjCbpoybewgngckuNAL3Y3EHtj7JIJCEHJX4iHpqUKCqGDCFuVLK8zUn/TwetbDxMk71Wc0QvMQESG7j3phYmV+ssLRxVK1cJ7kai7DD2ZrP/sssl2wfljXO2NDlFpJlFnNUAxwIDAQAB");
        System.out.println(sha512);

        String str = "254123451111";
        System.out.println(getSha256Str(str));
    }
}
