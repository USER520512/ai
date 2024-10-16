package com.sy.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class OrderNumberGenerator {

    public static String generateOrderNumber() {
        // 生成一个随机的 UUID
        UUID uuid = UUID.randomUUID();
        // 将 UUID 转换为字符串
        String uuidString = uuid.toString().replace("-", "");
        
        // 使用 SHA-256 哈希算法生成订单号
        return hashString(uuidString);
    }

    private static String hashString(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(input.getBytes());
            
            // 将字节数组转换为十六进制字符串
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            // 取前 32 个字符
            return hexString.toString().substring(0, 32);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        // 测试生成订单号
        String orderNumber = generateOrderNumber();
        System.out.println("生成的订单号: " + orderNumber);
    }
}
