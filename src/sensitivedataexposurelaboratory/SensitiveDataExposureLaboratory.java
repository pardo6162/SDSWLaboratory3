/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sensitivedataexposurelaboratory;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
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
         
        try{
            // i  generate a key using keygenerator
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(128);
            SecretKey secretKey = keyGen.generateKey();
        
        
            //ii generate a initialization vector
            final int AES_KEYLENGTH = 128;
            byte[] iv = new byte[AES_KEYLENGTH/8];
            SecureRandom prng = new SecureRandom();
            prng.nextBytes(iv);
    
            //iii instantiate the algorithm of encryption AES
            Cipher aesCipherForEncryption = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        
        
            //iv configure the algorithm of encryption AES
            aesCipherForEncryption.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(iv));
            
            // v encrypt the data 
            String strDataToEncrypt = "Hello World of Encryption using AES";
            byte[] byteDataToEncrypt = strDataToEncrypt.getBytes();
            byte[] byteCipherText= aesCipherForEncryption
                    .doFinal(byteDataToEncrypt);
            
            String strCipherText = new BASE64Encoder().encode(byteCipherText);
            System.out.println("Cipher text generated using AES is"+strCipherText);
            
            //vi decipher the data
            
            Cipher aesCipherForDecryption = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            aesCipherForDecryption.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));
            byte[] byteDecryptedText =  aesCipherForDecryption
                    .doFinal(byteCipherText);
            String strDecryptedText = new String(byteDecryptedText);
            System.out.println("Decrypted Text message is "+ strDecryptedText);  
        }catch(){
            
        }
           
    }
    
    
}
