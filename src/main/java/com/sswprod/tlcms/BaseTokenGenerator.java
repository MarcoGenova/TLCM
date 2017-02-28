package com.sswprod.tlcms;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author genovama
 */
public class BaseTokenGenerator {

    //AES only supports key sizes of 16, 24 or 32 bytes. You either need to provide exactly that amount or you derive the key from what you type in
    public static final String BASE_ALGORITHM = "AES";

    private final Cipher deCipher;
    private final Cipher enCipher;
    private final SecretKeySpec key;
    private final IvParameterSpec ivSpec;
    private Base64 base64 = null;

    /**
     *
     * @param keyBytes
     * @param ivBytes
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws java.security.InvalidKeyException
     */
    public BaseTokenGenerator(byte[] keyBytes, byte[] ivBytes) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException {
        // wrap key data in Key/IV specs to pass to cipher
        //key = new SecretKeySpec(keyBytes, BASE_ALGORITHM);
        ivSpec = new IvParameterSpec(ivBytes);
        deCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        enCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");

        SecretKeyFactory factory = SecretKeyFactory.getInstance("DES");
        KeySpec spec = new DESKeySpec(keyBytes);
        SecretKey tmp = factory.generateSecret(spec);
        key = new SecretKeySpec(tmp.getEncoded(), "DES");

        base64 = new Base64();
    }

    /**
     *
     * @param obj
     * @return
     * @throws InvalidKeyException
     * @throws ShortBufferException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws InvalidAlgorithmParameterException
     * @throws IOException
     */
    public byte[] encrypt(final Object obj) throws InvalidKeyException, ShortBufferException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, IOException {
        byte[] input = TokenUtils.convertToByteArray(obj);
        enCipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);

        return enCipher.doFinal(input);
    }

    /**
     *
     * @param encrypted
     * @return
     * @throws InvalidKeyException
     * @throws InvalidAlgorithmParameterException
     * @throws IllegalBlockSizeException
     * @throws ShortBufferException
     * @throws BadPaddingException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public Object decrypt(final byte[] encrypted) throws InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, ShortBufferException, BadPaddingException, IOException, ClassNotFoundException {
        deCipher.init(Cipher.DECRYPT_MODE, key, ivSpec);

        return TokenUtils.convertFromByteArray(deCipher.doFinal(encrypted));
    }

    /**
     *
     * @param to64
     * @return
     */
    public String base64Encode(final byte[] to64) {
        return base64.encodeAsString(to64);
    }

    /**
     *
     * @param from64
     * @return
     */
    public byte[] base64Dencode(String from64) {
        return base64.decode(from64);
    }
}
