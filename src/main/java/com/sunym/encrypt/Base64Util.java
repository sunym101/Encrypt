package com.sunym.encrypt;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.nio.charset.StandardCharsets;

/**
 * Base64 编码是我们程序开发中经常使用到的编码方法，它用 64 个可打印字符来表示二进制数据。
 * 这 64 个字符是：小写字母 a-z、大写字母 A-Z、数字 0-9、符号"+“、”/“（再加上作为垫字的”="，实际上是 65 个字符），其他所有符号都转换成这个字符集中的字符。
 * Base64 编码通常用作存储、传输一些二进制数据编码方法，所以说它本质上是一种将二进制数据转成文本数据的方案。
 * <p>
 * 通常用作对二进制数据进行加密。
 * 支持加密、解密。
 */
public class Base64Util {

    public static void main(String[] args) throws Exception {
        String content = "1234567890";
        String encryptBASE64 = encryptBASE64(content.getBytes(StandardCharsets.UTF_8));
        String decryBASE64 = new String(decryptBASE64(encryptBASE64));
        System.out.println("加密前=[" + content + "], 加密后=[" + encryptBASE64 + "]");
        System.out.println("解密前=[" + encryptBASE64 + "], 解密后=[" + decryBASE64 + "]");
    }

    /***
     * BASE64解密
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptBASE64(String key) throws Exception {
        return (new BASE64Decoder()).decodeBuffer(key);
    }

    /***
     * BASE64加密
     * @param key
     * @return
     * @throws Exception
     */
    public static String encryptBASE64(byte[] key) throws Exception {
        return (new BASE64Encoder()).encode(key);
    }
}
