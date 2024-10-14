package com.hx.common.util;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author zys
 * @description TODO
 * @date 2024/7/8
 */
@Slf4j
public class RSAUtil {

    private static String chartsetName = "utf-8";

    private static String RSA = "RSA";

    private static String MD5withRSA = "MD5withRSA";

    private static String SHA1withRSA = "SHA1withRSA";

    private static String SHA256withRSA = "SHA256withRSA";

    //生成密钥对
    public static KeyPair getKeyPair() throws Exception {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance(RSA);
        //可以理解为：加密后的密文长度，实际原文要小些 越大 加密解密越慢
        keyGen.initialize(1024);
        KeyPair keyPair = keyGen.generateKeyPair();
        return keyPair;
    }

    /**
     * 转换为公钥对象
     *
     * @param publicKeyStr
     * @return
     * @throws Exception
     */
    public static RSAPublicKey loadPublicKeyByStr(String publicKeyStr)
            throws Exception {
        try {
            byte[] buffer = Base64.decode(publicKeyStr);
            KeyFactory keyFactory = KeyFactory.getInstance(RSA);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (NullPointerException e) {
            throw new Exception("公钥数据为空");
        }
    }

    /**
     * 转换为私钥对象
     *
     * @param privateKeyStr
     * @return
     * @throws Exception
     */
    public static RSAPrivateKey loadPrivateKeyByStr(String privateKeyStr)
            throws Exception {
        try {
            byte[] buffer = Base64.decode(privateKeyStr);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
            KeyFactory keyFactory = KeyFactory.getInstance(RSA);
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (NullPointerException e) {
            throw new Exception("私钥数据为空");
        }
    }

    /**
     * 使用MD5withRSA进行签名
     *
     * @param content
     * @param privateKeyStr
     * @return
     * @throws Exception
     */
    public static String signMD5withRSA(String content, String privateKeyStr) throws Exception {
        byte[] contentBytes = content.getBytes(chartsetName);
        Signature signature = Signature.getInstance(MD5withRSA);
        signature.initSign(loadPrivateKeyByStr(privateKeyStr));
        signature.update(contentBytes);
        byte[] signs = signature.sign();
        return Base64.toBase64String(signs);
    }

    /**
     * 使用MD5withRSA进行签名
     */
    public static String signMD5withRSA(Map<String, Object> map, String privateKeyStr) throws Exception {
        String md5 = makeSignCommon(map);
        byte[] contentBytes = md5.getBytes();
        Signature signature = Signature.getInstance(MD5withRSA);
        signature.initSign(loadPrivateKeyByStr(privateKeyStr));
        signature.update(contentBytes);
        byte[] signs = signature.sign();
        return Base64.toBase64String(signs);
    }


    /**
     * 使用MD5withRSA进行签名
     */
    public static String signMD5withRSA(String content, PrivateKey privateKey) throws Exception {
        byte[] contentBytes = content.getBytes(chartsetName);
        Signature signature = Signature.getInstance(MD5withRSA);
        signature.initSign(privateKey);
        signature.update(contentBytes);
        byte[] signs = signature.sign();
        return Base64.toBase64String(signs);
    }


    /**
     * 使用Sha256withRSA进行加密
     *
     * @param content       明文
     * @param privateKeyStr 私钥
     * @return
     * @throws Exception
     */
    public static String signSHA256withRSA(String content, String privateKeyStr) throws Exception {
        byte[] contentBytes = content.getBytes(chartsetName);
        Signature signature = Signature.getInstance(SHA256withRSA);
        signature.initSign(loadPrivateKeyByStr(privateKeyStr));
        signature.update(contentBytes);
        byte[] signs = signature.sign();
        return Base64.toBase64String(signs);
    }

    /**
     * ASCII 排序后进行MD5
     *
     * @param params
     * @param params
     * @return
     */
    public static String makeSignCommon(Map<String, Object> params) {
        TreeMap<String, Object> treeMap = new TreeMap<>();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            treeMap.put(entry.getKey(), entry.getValue());
        }
        String pSeparator = "&";
        StringBuilder s = new StringBuilder();
        for (String key : treeMap.keySet()) {
            if (ObjectUtils.isEmpty(treeMap.get(key))) {
                continue;
            }
            if (s.length() > 0) {
                s.append(pSeparator);
            }
            if (treeMap.get(key) instanceof List<?>) {
                s.append(key).append("=").append(JSON.toJSONString(treeMap.get(key)));
            } else {
                s.append(key).append("=").append(treeMap.get(key));
            }
        }
        return Md5Util.encodeString(s.toString(), chartsetName);
    }

    /**
     * ASCII 排序后进行MD5
     *
     * @param params
     * @param pSeparator
     * @return
     */
    public static String makeSignCommon(Map<String, String> params, String pSeparator) {
        TreeMap<String, String> treeMap = new TreeMap<>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            treeMap.put(entry.getKey(), entry.getValue());
        }
        StringBuilder s = new StringBuilder();
        for (String key : treeMap.keySet()) {
            if (!StringUtils.hasText(treeMap.get(key))) {
                continue;
            }
            if (s.length() > 0) {
                s.append(pSeparator);
            }
            s.append(key).append("=").append(treeMap.get(key));
        }
        return Md5Util.encodeString(s.toString(), chartsetName);
    }

    /**
     * 验签
     *
     * @param map
     * @param sign
     * @param publicKeyStr
     * @return
     */
    public static boolean verifyMD5withRSA(Map<String, Object> map, String sign, String publicKeyStr) {
        try {
            PublicKey publicKey = loadPublicKeyByStr(publicKeyStr);
            byte[] contentBytes = makeSignCommon(map).getBytes();
            Signature signature = Signature.getInstance(MD5withRSA);
            signature.initVerify(publicKey);
            signature.update(contentBytes);
            return signature.verify(Base64.decode(sign));
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    /**
     * 验签
     *
     * @param content
     * @param sign
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static boolean verifyMD5withRSA(String content, String sign, String publicKey) throws Exception {
        byte[] contentBytes = content.getBytes(chartsetName);
        Signature signature = Signature.getInstance(MD5withRSA);
        signature.initVerify(loadPublicKeyByStr(publicKey));
        signature.update(contentBytes);
        return signature.verify(Base64.decode(sign));
    }


    /**
     * 验证sha256加密
     *
     * @param content   明文
     * @param sign      密文
     * @param publicKey 公钥
     * @return
     * @throws Exception
     */
    public static boolean verifySHA256withRSA(String content, String sign, String publicKey) throws Exception {
        byte[] contentBytes = content.getBytes(chartsetName);
        Signature signature = Signature.getInstance(SHA256withRSA);
        signature.initVerify(loadPublicKeyByStr(publicKey));
        signature.update(contentBytes);
        return signature.verify(Base64.decode(sign));
    }

    /**
     * 用sha1生成内容摘要，再用RSA的私钥加密，进而生成数字签名
     */
    public static String getSha1Sign(String content, PrivateKey privateKey) throws Exception {
        byte[] contentBytes = content.getBytes(chartsetName);
        Signature signature = Signature.getInstance(SHA1withRSA);
        signature.initSign(privateKey);
        signature.update(contentBytes);
        byte[] signs = signature.sign();
        return Base64.toBase64String(signs);
    }

    /**
     * 对用md5和RSA私钥生成的数字签名进行验证
     */
    public static boolean verifyWhenSha1Sign(String content, String sign, PublicKey publicKey) throws Exception {
        byte[] contentBytes = content.getBytes(chartsetName);
        Signature signature = Signature.getInstance(SHA1withRSA);
        signature.initVerify(publicKey);
        signature.update(contentBytes);
        return signature.verify(Base64.decode(sign));
    }

    public static void main(String[] args) throws Exception {
        String privateKeys =
                // "-----BEGIN PRIVATE KEY-----\n" +
                "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMMRQOevHKZo7dIM\n" +
                        "zB0oKEtijePYOOgI1Z7lrdOMJumjJt7CCeByS40AvdjcQe2PskgkIQclfiIempQo\n" +
                        "KoYMIW5UsrzNSf9PB61sPEyTvVZzRC8xARIbuPemFiZX6ywtHFUrVwnuRqLsMPZm\n" +
                        "s/+yyyXbB+WNc7Y0OUWkmUWc1QDHAgMBAAECgYAhhtWg/HfwIhi+AXUTjdNfIZFB\n" +
                        "l+gv+VS9+rvloDEP9vq3TqJj8UEK+xWmMDUkn44E2DDVCZykQJ5Q2JZ2c59LCjjh\n" +
                        "VPr2Krek0aFkGD1DsXU4NDkGVQyBxW1R9O2o//4OS1gwjIG57Q0n+VNscObCR5cv\n" +
                        "n/zXHtYPkfodDD0gAQJBAPFDALgqebsWBtkJ5+8Of+ClA0+R3QsXYigz1PH73ar9\n" +
                        "OoXd+TuVnYA/AtwR/Jze0dVpjVu8kh6tysbO8MgnKCMCQQDO+9XzBoqKLO8Xa1qb\n" +
                        "MpElsSI2Ij147if+RLITCM7s3seGDYWuo8i44ATZSG0rThL38uhlEYZdPsn0187e\n" +
                        "RB0NAkBHVhp2WgjYarDnp+guZUkmcWRDOMv1JZrebEUAsAphLrMJNhMlrR1++CKu\n" +
                        "U5sv/ypoQeeMQnuqGpUkp7fGVt2lAkEAy064j1bse965FoLfY7QeuCwuU4f8Y61i\n" +
                        "YTIuy92KC0akKvtbRPghr95zRM4MVU4B+cSCGsxE85A6JSJZUx8KfQJBAJ7mNB+a\n" +
                        "nm7+EsajuoIY6L7SyQM3or/2qgl+R/W7l/xA7/3mJAYIYLoz9il5FBdLhhNUPyHc\n" +
                        "Ek9DmeRjKOLRUu4="
                // +
                // "-----END PRIVATE KEY-----"
                //
                ;
        String publicKeys = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDEUDnrxymaO3SDMwdKChLYo3j2DjoCNWe5a3TjCbpoybewgngckuNAL3Y3EHtj7JIJCEHJX4iHpqUKCqGDCFuVLK8zUn/TwetbDxMk71Wc0QvMQESG7j3phYmV+ssLRxVK1cJ7kai7DD2ZrP/sssl2wfljXO2NDlFpJlFnNUAxwIDAQAB";
        testGet(publicKeys, privateKeys);
        testPost(publicKeys, privateKeys);
    }

    private static void testPost(String publicKeys, String privateKeys) throws Exception {
        System.out.println("=== Test Post ====");
        Map<String, Object> params = new HashMap<>();
        params.put("client_id", "hsdhklsad002");
        params.put("ga_id", "hsdhklsadhk001");
        params.put("ad_type", "1");
        baseTest(params, publicKeys, privateKeys);
    }

    private static void testGet(String publicKeys, String privateKeys) throws Exception {
        System.out.println("=== Test Get ====");
        String pa = "deviceCode=00-E1-8C-B8-14-4C&deviceSoftVersion=2023070418&loginType=0&password=14e1b600b1fd579f47433b88e8d85291&platform=3&platformModel=1.0&playerAccount=1357081120&timestamp=1689042765568";
        Map<String, Object> map = new TreeMap<>();
        for (String arr : pa.split("&")) {
            String[] pair = arr.split("=");
            map.put(pair[0], pair[1]);
        }
        baseTest(map, publicKeys, privateKeys);
    }

    private static void baseTest(Map<String, Object> content, String publicKey, String privateKey) throws Exception {
        System.out.println("私钥=" + privateKey);
        //用私钥签名
        String sign = signMD5withRSA(content, privateKey);
        System.out.println("签名值：" + sign);

        //签名
        boolean isPass = verifyMD5withRSA(content, sign, publicKey);
        System.out.println("验签结果：" + isPass);
    }

}
