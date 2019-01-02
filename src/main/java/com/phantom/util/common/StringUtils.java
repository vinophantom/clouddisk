package com.phantom.util.common;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.spec.KeySpec;

/**
 * @Author: Jason Xu
 * @Date: 2018/4/1
 * @Description: 此工具需要apache common-codec.jar支持以及spring-security-core.jar
 * @ModifiedBy:
 */
public class StringUtils {
    /**
     * base 64 编码.
     *
     * @param str
     *            原始串.
     * @return 返回编码串.
     */
    public static String encodeBase64(String str) {
        if(str == null) return "";
        return byteArrayToString(Base64.encodeBase64(stringToByteArray(str)));
    }

    /**
     * 字节数组转换为String
     * @param byteArray
     * @return
     */
    public static String byteArrayToString(byte[] byteArray){
        try {
            return new String(byteArray, "UTF8");
        } catch (final UnsupportedEncodingException e) {
            return new String(byteArray);
        }
    }

    /**
     * String 转换为 byte[]
     * @param input
     * @return
     */
    public static byte[] stringToByteArray(String input) {
        try {
            return input.getBytes("UTF-8");
        } catch (UnsupportedEncodingException fallbackToDefault) {
            return input.getBytes();
        }
    }

    /**
     * BASE 64 解码.
     *
     * @param str
     *            编码串.
     * @return 返回原始串.
     */
    public static String decodeBase64(String str) {
        return byteArrayToString(Base64.decodeBase64(stringToByteArray(str)));
    }

    /**
     * DES 加密.
     *
     * @param key
     *            密钥.
     * @param inputStr
     *            原始串.
     * @return 返回加密串.
     */
    public static String encrypt(String key, String inputStr) {
        final byte[] cipherText = cipher(key, stringToByteArray(inputStr), Cipher.ENCRYPT_MODE);
        return byteArrayToString(Base64.encodeBase64(cipherText));
    }


    /**
     * DES 解密.
     *
     * @param key
     *            密钥.
     * @param inputStr
     *            加密串.
     * @return 返回原始串.
     */
    public static String decrypt(String key, String inputStr) {
        final byte[] cipherText = cipher(key, Base64.decodeBase64(stringToByteArray(inputStr)), Cipher.DECRYPT_MODE);
        return byteArrayToString(cipherText);
    }

    public static String reverse(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.reverse();
        return sb.toString();
    }


    private static byte[] cipher(String key, byte[] passedBytes, int cipherMode) {
        try {
            final KeySpec keySpec = new DESedeKeySpec(stringToByteArray(key));
            final SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
            final Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
            final SecretKey secretKey = keyFactory.generateSecret(keySpec);
            cipher.init(cipherMode, secretKey);
            return cipher.doFinal(passedBytes);
        } catch (final Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String trim(String str){
        return str==null ?"":str.trim();
    }

    /**
     * 移除字符串中的换行符.
     *
     * @param str
     *            字符串.
     * @return
     */
    public static String removeLineFeed(String str) {
        try {
            byte[] bytes = str.getBytes("UTF-8");
            ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
            for (byte b : bytes) {
                if (b != 10 && b != 13 && b != 9 && b != 32) {
                    bytestream.write(b);
                }
            }
            byte[] bts = bytestream.toByteArray();
            try {
                bytestream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new String(bts, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static boolean isEmpty(String str) {
        if("".equals(trim(str)))
            return true;
        return false;
    }


}
