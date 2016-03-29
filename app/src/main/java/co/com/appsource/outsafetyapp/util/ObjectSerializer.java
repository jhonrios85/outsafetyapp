package co.com.appsource.outsafetyapp.util;

import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by JANUS on 17/12/2015.
 */
public class ObjectSerializer {

    public static String serialize(Serializable obj) throws IOException {
        String strResult = "";
        if (obj == null) return "";
        try {
            ByteArrayOutputStream serialObj = new ByteArrayOutputStream();
            ObjectOutputStream objStream = new ObjectOutputStream(serialObj);
            objStream.writeObject(obj);
            objStream.close();
            strResult = encodeBytes(serialObj.toByteArray());
        } catch (Exception e) {
            //throw WrappedIOException.wrap("Serialization error: " + e.getMessage(), e);
        } finally {
            return strResult;
        }
    }

    public static Object deserialize(String str) throws IOException {
        Object objObject = null;
        if (str == null || str.length() == 0) return null;
        try {
            ByteArrayInputStream serialObj = new ByteArrayInputStream(decodeBytes(str));
            ObjectInputStream objStream = new ObjectInputStream(serialObj);
            objObject = objStream.readObject();
        } catch (Exception e) {
            //throw WrappedIOException.wrap("Deserialization error: " + e.getMessage(), e);
        } finally {
            return objObject;
        }
    }

    public static String encodeBytes(byte[] bytes) {
        StringBuffer strBuf = new StringBuffer();

        for (int i = 0; i < bytes.length; i++) {
            strBuf.append((char) (((bytes[i] >> 4) & 0xF) + ((int) 'a')));
            strBuf.append((char) (((bytes[i]) & 0xF) + ((int) 'a')));
        }

        return strBuf.toString();
    }

    public static byte[] decodeBytes(String str) {
        byte[] bytes = new byte[str.length() / 2];
        for (int i = 0; i < str.length(); i += 2) {
            char c = str.charAt(i);
            bytes[i / 2] = (byte) ((c - 'a') << 4);
            c = str.charAt(i + 1);
            bytes[i / 2] += (c - 'a');
        }
        return bytes;
    }
}
