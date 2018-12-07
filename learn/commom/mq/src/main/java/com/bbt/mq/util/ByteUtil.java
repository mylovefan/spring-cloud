package com.bbt.mq.util;

import java.io.*;

/**
 * byte转换工具
 *
 * @author zhangrc
 * @since 2018/11/30
 */
public class ByteUtil {

    /**
     * 将byte数组转换成object
     *
     * @param bytes
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Object byteToObject(byte[] bytes) throws IOException, ClassNotFoundException {
        Object object;
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(byteArrayInputStream);
            object = objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw e;
        } finally {
            if (objectInputStream != null) {
                try {
                    objectInputStream.close();
                } catch (IOException e) {

                }
            }
            if (byteArrayInputStream != null) {
                try {
                    byteArrayInputStream.close();
                } catch (IOException e) {

                }
            }
        }

        return object;
    }

    /**
     * 将object转换成byte数组
     *
     * @param obj
     * @return
     * @throws IOException
     */
    public static byte[] objectToByte(Object obj) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = null;
        try {
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(obj);
            objectOutputStream.flush();
        } catch (IOException e) {
            throw e;
        } finally {
            if (objectOutputStream != null) {
                try {
                    objectOutputStream.close();
                } catch (IOException e) {

                }
            }
            if (byteArrayOutputStream != null) {
                try {
                    byteArrayOutputStream.close();
                } catch (IOException e) {

                }
            }
        }

        byte[] bytes = byteArrayOutputStream.toByteArray();

        return bytes;
    }


}
