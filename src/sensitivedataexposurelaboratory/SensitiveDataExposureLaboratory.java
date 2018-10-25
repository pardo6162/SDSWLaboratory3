/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sensitivedataexposurelaboratory;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import sun.misc.BASE64Encoder;

/**
 *
 * @author 2115237
 */
public class SensitiveDataExposureLaboratory {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
         
      
        try {
            // i  generate a key using keygenerator
            KeyGenerator keyGen = KeyGenerator.getInstance("DESede");
            keyGen.init(168);
            SecretKey secretKey = keyGen.generateKey();
            
            
            //ii generate a initialization vector
            final int DESede_KEYLENGTH = 168;
            byte[] iv = new byte[DESede_KEYLENGTH/21];
            SecureRandom prng = new SecureRandom();
            prng.nextBytes(iv);
    
            //iii instantiate the algorithm of encryption 3DES
            Cipher aesCipherForEncryption = Cipher.getInstance("DESede/CBC/PKCS5PADDING");
            
            
            //iv configure the algorithm of encryption 3DES
            aesCipherForEncryption.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(iv));
            
            // v encrypt the data 
            String strDataToEncrypt = "I am learning to cypher sensitive data employing JCE in my SDSW course";
            byte[] byteDataToEncrypt = strDataToEncrypt.getBytes();
            byte[] byteCipherText= aesCipherForEncryption
                    .doFinal(byteDataToEncrypt);
            
            String strCipherText = new BASE64Encoder().encode(byteCipherText);
            System.out.println("Cipher text generated using 3DES is"+strCipherText);
            
            
             //calculate sha1
             
            MessageDigest messageDigest= MessageDigest.getInstance("SHA-1");
            byte[] hashCipherText =messageDigest.digest(byteCipherText);            
            System.out.println("The hash of cypher text is :" + new BASE64Encoder().encode(hashCipherText));
            
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException ex) {
            Logger.getLogger(SensitiveDataExposureLaboratory.class.getName()).log(Level.SEVERE, null, ex);
        }
   
    }
    
    
}
