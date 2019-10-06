package test;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;

public class twoWayAuthen {
public static void main(String[] args) {
	GoogleAuthenticator gAuth = new GoogleAuthenticator();
	final GoogleAuthenticatorKey key = gAuth.createCredentials();
	String a=key.getKey();
	System.out.println("sssssssssssss"+a);
}
}
