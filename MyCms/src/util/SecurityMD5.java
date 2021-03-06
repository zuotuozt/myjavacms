/**
 * @author 
 *  创建于2006-7
*/
package util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SecurityMD5 {

	private final static String[] hexDigits = {
	      "0", "1", "2", "3", "4", "5", "6", "7",
	      "8", "9", "a", "b", "c", "d", "e", "f"};

	  private String byteArrayToHexString(byte[] b) {
	    StringBuilder resultSb = new StringBuilder();
	    for (int i = 0; i < b.length; i++) {
	      resultSb.append(byteToHexString(b[i]));
	    }
	    return resultSb.toString();
	  }

	  private String byteToHexString(byte b) {
	    int n = b;
	    if (n < 0)
	      n = 256 + n;
	    int d1 = n / 16;
	    int d2 = n % 16;
	    return hexDigits[d1] + hexDigits[d2];
	  }

	  public String MD5Encode(String password) throws NoSuchAlgorithmException {
	      MessageDigest md = MessageDigest.getInstance("MD5");
	      return byteArrayToHexString(md.digest(password.getBytes()));
	  }

}
