/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sswprod;

import com.sswprod.tlcms.BaseTokenGenerator;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 *
 * @author genovama
 */
public class NewEmptyTestNGTest {
    
    public NewEmptyTestNGTest() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}

    @org.testng.annotations.BeforeClass
    public static void setUpClass() throws Exception {
    }

    @org.testng.annotations.AfterClass
    public static void tearDownClass() throws Exception {
    }

    @org.testng.annotations.BeforeMethod
    public void setUpMethod() throws Exception {
    }

    @org.testng.annotations.AfterMethod
    public void tearDownMethod() throws Exception {
    }

    @Test
    public void defineEncoder() {
        try {           
            byte[] keyValue = new byte[] { 'T', 'h', 'i', 's', 'I', 's', 'A', 'S', 'e', 'c', 'r', 'e', 't', 'K', 'e', 'y' };
            byte[] ivValue = new byte[] { 'T', 'h', 'i', 's', 'I', 's', 'A', 'S'};
            
            BaseTokenGenerator baseTokenGenerator = new BaseTokenGenerator(keyValue, ivValue);
            String secretMessage = "001TEST123456789";
            byte[] encrypt = baseTokenGenerator.encrypt(secretMessage);
            String token = baseTokenGenerator.base64Encode(encrypt);
            
            System.out.println("====Token====");
            System.out.println(token);
            System.out.println("====End Token====");
            
            byte[] base64Decode = baseTokenGenerator.base64Dencode(token);
            Object decrypt = baseTokenGenerator.decrypt(base64Decode);
            System.out.println(decrypt);
            
            assertEquals(decrypt,secretMessage);
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException | ClassNotFoundException | InvalidKeySpecException ex) {
            Logger.getLogger(NewEmptyTestNGTest.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.getMessage(), ex);
        } catch (InvalidKeyException | ShortBufferException | IllegalBlockSizeException | BadPaddingException | InvalidAlgorithmParameterException | IOException ex) {
            Logger.getLogger(NewEmptyTestNGTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
