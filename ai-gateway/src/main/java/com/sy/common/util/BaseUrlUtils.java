package com.sy.common.util;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

import static com.sy.common.constants.Constants.BASE_URL_KEY;

/**
 * @author zys
 * @description 地址加密工具
 * @date 2024/8/22
 */
@Slf4j
public class BaseUrlUtils {

    public static void main(String[] args) {
        System.out.println(encrypt("refreshTask", 8));
    }

    private static String encrypt(String content, Integer len) {
        String encryptedText = null;
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(BASE_URL_KEY.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

            byte[] encryptedData = cipher.doFinal(content.getBytes());
            encryptedText = Base64.getEncoder().encodeToString(encryptedData);
            if (null != len) {
                return StringUtils.substring(encryptedText, 0, len);
            }
        } catch (Exception e) {
            log.error("BaseUrlUtils encrypt error", e.getMessage());
        }
        return encryptedText;
    }

}
