package com.sunym.encrypt;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5 是将任意长度的数据字符串转化成短小的固定长度的值的单向操作，任意两个字符串不应有相同的散列值。
 * 因此 MD5 经常用于校验字符串或者文件，因为如果文件的 MD5 不一样，说明文件内容也是不一样的，如果发现下载的文件和给定的 MD5 值不一样，就要慎重使用。
 * <p>
 * MD5 主要用做数据一致性验证、数字签名和安全访问认证，而不是用作加密。
 * 比如说用户在某个网站注册账户时，输入的密码一般经过 MD5 编码，更安全的做法还会加一层盐（salt），这样密码就具有不可逆性。
 * 然后把编码后的密码存入数据库，下次登录的时候把密码 MD5 编码，然后和数据库中的作对比，这样就提升了用户账户的安全性。
 * <p>
 * 是一种单向加密算法，只能加密不能解密。
 */
public class MD5Util {

    public final static String BIT_16 = "BIT_16";

    public final static String BIT_32 = "BIT_32";

    public static void main(String[] args) throws Exception {
        String content = "1234567890";
        String encryptMD5 = encryptMD5(content, BIT_32);
        System.out.println("加密前=[" + content + "], 加密后=[" + encryptMD5 + "]");
    }

    public static String encryptMD5(String plainText, String bitType) throws Exception {
        System.out.println("加密方式=[" + bitType + "]");
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes(StandardCharsets.UTF_8));
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }

            if (BIT_16.equals(bitType)) {
                // 16位的加密
                return buf.toString().substring(8, 24);
            } else if (BIT_32.equals(bitType)) {
                //32位加密
                return buf.toString();
            } else {
                return null;
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

}

