package com.sswprod.tlcms;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author genovama
 */
public class TokenUtils {
    
    /**
     * 
     * @param byteObject
     * @return
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    public static Object convertFromByteArray(byte[] byteObject) throws IOException,
            ClassNotFoundException {
        Object o = null;

        if (byteObject != null && byteObject.length > 0) {
            ByteArrayInputStream bais = new ByteArrayInputStream(byteObject);

            try (ObjectInputStream in = new ObjectInputStream(bais)) {
                o = in.readObject();
            }
        }
        
        return o;
    }
    
    /**
     * 
     * @param complexObject
     * @return
     * @throws IOException 
     */
    public static byte[] convertToByteArray(Object complexObject) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        
        try (ObjectOutputStream out = new ObjectOutputStream(baos)) {
            out.writeObject(complexObject);
        }

        return baos.toByteArray();
    }
}
