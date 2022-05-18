package com.sunym.encrypt;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * 高级加密标准（英语：Advanced Encryption Standard，缩写：AES）。
 * 在密码学中又称 Rijndael 加密法，是美国联邦政府采用的一种区块加密标准。
 * 这个标准用来替代原先的 DES，已经被多方分析且广为全世界所使用。
 * 简单说就是 DES 的增强版，比 DES 的加密强度更高。
 * <p>
 * AES 与 DES 一样，一共有四种加密模式：电子密码本模式（ECB）、加密分组链接模式（CBC）、加密反馈模式（CFB）和输出反馈模式（OFB）。
 * <p>
 * aes是基于数据块的加密⽅式，也就是说，每次处理的数据时⼀块（16字节），当数据不是16字节的倍数时填充，这就是所谓的分组密码
 * <p>
 * （区别于基于⽐特位的流密码），16字节是分组长度
 * <p>
 * 分组加密的⼏种模式：
 * <p>
 * ECB：是⼀种基础的加密⽅式，密⽂被分割成分组长度相等的块（不⾜补齐），然后单独⼀个个加密，⼀个个输出组成密⽂。
 * <p>
 * CBC：是⼀种循环模式，前⼀个分组的密⽂和当前分组的明⽂异或或操作后再加密，这样做的⽬的是增强破解难度。
 * <p>
 * CFB/OFB：实际上是⼀种反馈模式，⽬的也是增强破解的难度。
 * <p>
 * CFB和CBC的加密结果是不⼀样的，两者的模式不同，⽽且CBC会在第⼀个密码块运算时加⼊⼀个初始化向量。
 */
public class AESUtil {

    public static final String ALGORITHM = "AES";
    // 加解密算法/工作模式/填充方式 例如：AES/CBC/NOPaddin
    // 使用非ECB模式, 在初始化Cipher对象时, 需要增加参数, 初始化向量IV :
    // IvParameterSpec iv = new IvParameterSpec(key.getBytes());
    // NOPadding: 使用NOPadding模式时, 原文长度必须是8byte的整数倍

    // 工作模式-电子密码本模式（ECB）。不需要IvParameterSpec
    public static final String ENCRYPT_ECB = "ECB";

    // 工作模式-加密分组链接模式（CBC）。
    public static final String ENCRYPT_CBC = "CBC";
    // 工作模式-加密反馈模式（CFB）
    public static final String ENCRYPT_CFB = "CFB";
    // 工作模式-输出反馈模式（OFB）
    public static final String ENCRYPT_OFB = "OFB";

    // 填充方式
    public static final String PADDING_NO = "NOPadding";
    public static final String PADDING_PKCS5 = "PKCS5Padding";

    public static final String KEY = "1234567812345678";

    public static void main(String[] args) throws Exception {
        String content = "1234567890ABCDEF";
        String contentPadding = "1234567890";

        doTest(content, ENCRYPT_CBC, PADDING_NO);
        doTest(contentPadding, ENCRYPT_CBC, PADDING_PKCS5);

        doTest(content, ENCRYPT_ECB, PADDING_NO);
        doTest(contentPadding, ENCRYPT_ECB, PADDING_PKCS5);

        doTest(content, ENCRYPT_CFB, PADDING_NO);
        doTest(contentPadding, ENCRYPT_CFB, PADDING_PKCS5);

        doTest(content, ENCRYPT_OFB, PADDING_NO);
        doTest(contentPadding, ENCRYPT_OFB, PADDING_PKCS5);
    }

    public static void doTest(String content, String model, String padding) throws Exception {
        System.out.println("===============================");
        String encryptAES = encryptAes(content, model, padding);
        System.out.println("加密前=[" + content + "], 加密后=[" + encryptAES + "]");

        System.out.println();

        String decryptAES = decryptAes(encryptAES, model, padding);
        System.out.println("解密前=[" + encryptAES + "], 解密后=[" + decryptAES + "]");
        System.out.println("===============================");
    }

    /***
     * CBC加密
     * 使用CBC模式, 在初始化Cipher对象时, 需要增加参数, 初始化向量IV :
     * @param content 需要加密的参数（注意:必须是16位）
     * @return
     * @throws Exception
     */
    public static String encryptAes(String content, String encryptModel, String paddingModel) throws Exception {
        System.out.println("算法=[" + ALGORITHM + "], 工作模式=[" + encryptModel + "], 填充方式=[" + paddingModel + "]");
        String transformation = ALGORITHM + "/" + encryptModel + "/" + paddingModel;

        // 获取Cipher
        Cipher cipher = Cipher.getInstance(transformation);
        // 生成密钥
        SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(), ALGORITHM);
        // 指定模式(加密)和密钥
        if (!ENCRYPT_ECB.equals(encryptModel)) {
            // 创建初始化向量
            IvParameterSpec iv = new IvParameterSpec(KEY.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv);
        } else {
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        }
        // 加密
        byte[] bytes = cipher.doFinal(content.getBytes());

        return new BASE64Encoder().encode(bytes);
    }

    /**
     * CBC解密
     * 使用CBC模式, 在初始化Cipher对象时, 需要增加参数, 初始化向量IV :
     *
     * @param content 需要解密的参数
     * @return
     * @throws Exception
     */
    public static String decryptAes(String content, String encryptModel, String paddingModel) throws Exception {
        System.out.println("算法=[" + ALGORITHM + "], 工作模式=[" + encryptModel + "], 填充方式=[" + paddingModel + "]");
        String transformation = ALGORITHM + "/" + encryptModel + "/" + paddingModel;

        // 获取Cipher
        Cipher cipher = Cipher.getInstance(transformation);
        // 生成密钥
        SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(), ALGORITHM);
        // 指定模式(解密)和密钥
        if (!ENCRYPT_ECB.equals(encryptModel)) {
            // 创建初始化向量
            IvParameterSpec iv = new IvParameterSpec(KEY.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, keySpec, iv);
        } else {
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
        }

        // 解密
        byte[] bytes = cipher.doFinal(new BASE64Decoder().decodeBuffer(content));

        return new String(bytes);
    }

}

