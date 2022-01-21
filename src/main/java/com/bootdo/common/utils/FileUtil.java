package com.bootdo.common.utils;

import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * @author rory.chen
 * @date 2022/1/19
 */
public class FileUtil {

    /**
     * 获取文件的md5值
     *
     * @param file 输入流
     * @return
     */
    public static String getMd5Hash(InputStream file) {
        try {
            //拿到一个MD5转换器,如果想使用SHA-1或SHA-256，则传入SHA-1,SHA-256
            MessageDigest md = MessageDigest.getInstance("MD5");

            //分多次将一个文件读入，对于大型文件而言，比较推荐这种方式，占用内存比较少。
            byte[] buffer = new byte[1024];
            int length = -1;
            while ((length = file.read(buffer, 0, 1024)) != -1) {
                md.update(buffer, 0, length);
            }
            file.close();
            //转换并返回包含16个元素字节数组,返回数值范围为-128到127
            byte[] md5Bytes  = md.digest();
            //1代表绝对值
            BigInteger bigInt = new BigInteger(1, md5Bytes);
            //转换为16进制
            return bigInt.toString(16);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
