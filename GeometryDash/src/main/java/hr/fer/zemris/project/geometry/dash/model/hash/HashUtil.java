package hr.fer.zemris.project.geometry.dash.model.hash;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Custom byte to hex converter to get the hashed value in hexadecimal
 * @author Andi Škrgat
 *
 */
public class HashUtil {

	/**
	 * Bytes to hex
	 * @param hash
	 * @return
	 */
	private static String bytesToHex(byte[] hash) {
	    StringBuilder hexString = new StringBuilder(2 * hash.length);
	    for (int i = 0; i < hash.length; i++) {
	        String hex = Integer.toHexString(0xff & hash[i]);
	        if(hex.length() == 1) {
	            hexString.append('0');
	        }
	        hexString.append(hex);
	    }
	    return hexString.toString();
	}
	
	/**
	 * Hashes content
	 * @param content content
	 * @return hashed content
	 */
	public static String hashContent(String content) {
		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] encodedhash = digest.digest(
		  content.getBytes(StandardCharsets.UTF_8));
		return bytesToHex(encodedhash);
	}
}
