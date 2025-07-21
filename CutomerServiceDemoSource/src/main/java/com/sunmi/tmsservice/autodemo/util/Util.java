package com.sunmi.tmsservice.autodemo.util;

import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Util {

    /**
     * object对象转换成String
     * @param o
     * @return
     */
    public static String object2json(Object o) {
        if (o == null) {
            return null;
        }
        Gson gson = new Gson();
        return gson.toJson(o);
    }

    /**
     * 读取文件转换成byte[]
     * @param filePath
     * @return
     */
    public static byte[] fileToByteArray(String filePath) {
        File file = new File(filePath);
        byte[] buffer = null;
        try (FileInputStream fis = new FileInputStream(file);
             BufferedInputStream bis = new BufferedInputStream(fis)) {
            int length = (int) file.length();
            buffer = new byte[length];
            int offset = 0;
            int numRead;
            while (offset < buffer.length
                    && (numRead = bis.read(buffer, offset, buffer.length - offset)) >= 0) {
                offset += numRead;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    /**
     * 对字符串进行md5加密
     *
     * @param string
     * @return
     */
    public static String md5(String string) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(
                    string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Huh, MD5 should be supported?", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Huh, UTF-8 should be supported?", e);
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10)
                hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }
}
