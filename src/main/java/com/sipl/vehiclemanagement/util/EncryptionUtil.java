package com.sipl.vehiclemanagement.util;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import org.springframework.stereotype.Component;

@Component
public class EncryptionUtil {

	public EncryptionUtil() {

	}
	
	public static SecretKey generateKeyFromPassword(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
		 PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray());
	    SecretKeyFactory kf = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
	  return kf.generateSecret(keySpec);
    }
	

	public static String encrypt( String input, SecretKey key) throws NoSuchPaddingException, NoSuchAlgorithmException,
		    InvalidAlgorithmParameterException, InvalidKeyException,
		    BadPaddingException, IllegalBlockSizeException {
		    byte[] salt = "salt1234".getBytes();
		    PBEParameterSpec paramSpec = new PBEParameterSpec(salt, 20);
		    Cipher cipher = Cipher.getInstance("PBEWithMD5AndDES");
		    cipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
		    byte[] cipherText = cipher.doFinal(input.getBytes());                
		    return Base64.getEncoder()
		        .encodeToString(cipherText);
		}
	
	
	public static String decrypt( String cipherText, SecretKey key) throws NoSuchPaddingException, NoSuchAlgorithmException,
		    InvalidAlgorithmParameterException, InvalidKeyException,
		    BadPaddingException, IllegalBlockSizeException {
		     byte[] salt = "salt1234".getBytes();
		    PBEParameterSpec paramSpec = new PBEParameterSpec(salt, 20);
		    Cipher cipher = Cipher.getInstance("PBEWithMD5AndDES");
		    cipher.init(Cipher.DECRYPT_MODE, key,paramSpec);
		    byte[] plainText = cipher.doFinal(Base64.getDecoder()
		        .decode(cipherText));
		    return new String(plainText);
		}

}
