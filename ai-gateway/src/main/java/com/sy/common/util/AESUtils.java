package com.sy.common.util;


import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * @author zys
 * @description 加密工具类  使用 AES/GCM 模式进行加密和解密操作。AES/GCM 提供了认证加密，能够保证数据的完整性和机密性
 * @date 2024/7/2
 */
public class AESUtils {

    private static final String AES = "AES";
    private static final String AES_GCM = "AES/GCM/NoPadding";
    private static final int GCM_IV_LENGTH = 12;
    // 128-bit
    private static final int GCM_TAG_LENGTH = 128;

    public static void main(String[] args) {
        try {
            // 密钥从配置项中获取
            String base64Key = "Tga/BC5ykb/kfXJPl1MTBt4P6vHit71iRbJh8vlHhGk=";
//            String base64Key = generateBase64Key();
            System.out.println("Generated Key: " + base64Key);
            String plainText = "{\n" +
                    "    \"taskId\":\"\",\n" +
                    "    \"taskName\":\"Turntable\"\n" +
                    "}";
            String encryptedResponse = encrypt(plainText, base64Key);
            System.out.println("Encrypted: " + encryptedResponse);
            encryptedResponse = "wCAfgD25bO6g2vG6gHKgnYpphWBdCB3lkTYQVrbos5eK+Wyj60T6nvIvYyDq4UboCt/vLr740I+vEMK7QiHwxwV/pWjSMPUyKmpFWV34";
            String decryptedResponse = decrypt(encryptedResponse, base64Key);
            System.out.println("Decrypted: " + decryptedResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // 创建随机密钥，用于加密解密数据 AES-256
    public static String generateBase64Key() throws Exception {
        SecureRandom secureRandom = new SecureRandom();
        // 256-bit key
        byte[] key = new byte[32];
        secureRandom.nextBytes(key);
        return Base64.getEncoder().encodeToString(key);
    }

    public static String encrypt(String encryptText, String base64Key) throws Exception {
        byte[] encryptedBytes;
        SecretKey key = new SecretKeySpec(Base64.getDecoder().decode(base64Key), AES);
        Cipher cipher = null;

        cipher = Cipher.getInstance(AES_GCM);
        byte[] iv = new byte[GCM_IV_LENGTH];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(iv);

        GCMParameterSpec parameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
        cipher.init(Cipher.ENCRYPT_MODE, key, parameterSpec);

        byte[] cipherText = cipher.doFinal(encryptText.getBytes(StandardCharsets.UTF_8));

        encryptedBytes = new byte[GCM_IV_LENGTH + cipherText.length];
        System.arraycopy(iv, 0, encryptedBytes, 0, GCM_IV_LENGTH);
        System.arraycopy(cipherText, 0, encryptedBytes, GCM_IV_LENGTH, cipherText.length);
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String decrypt(String encryptedText, String base64Key) throws Exception {
        byte[] plainText;
        SecretKey key = new SecretKeySpec(Base64.getDecoder().decode(base64Key), AES);
        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedText);

        Cipher cipher = Cipher.getInstance(AES_GCM);

        GCMParameterSpec parameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH, encryptedBytes, 0, GCM_IV_LENGTH);
        cipher.init(Cipher.DECRYPT_MODE, key, parameterSpec);

        plainText = cipher.doFinal(encryptedBytes, GCM_IV_LENGTH, encryptedBytes.length - GCM_IV_LENGTH);
        return new String(plainText, StandardCharsets.UTF_8);
    }
}
