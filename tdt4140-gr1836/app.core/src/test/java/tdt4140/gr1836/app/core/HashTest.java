package tdt4140.gr1836.app.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.security.NoSuchAlgorithmException;

import org.junit.Before;
import org.junit.Test;

import tdt4140.gr1836.app.db.Hash;

public class HashTest {
	private String password;
	private String validP;
	private String invalidP;
	
	private byte[] salt;
	private String saltString;
	@Before
	public void setUp() {
		password="passord";
		
		validP="passord";
		invalidP="feil";
		try {
			salt = Hash.getSalt();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		saltString=Hash.convertSalt(salt);
	}
	
	@Test public void testDecodeConvertSalt() {
		byte[] decodedSalt=Hash.decodeSalt(saltString);
		String convertedBackSalt=Hash.convertSalt(decodedSalt);
		assertEquals(convertedBackSalt,saltString);
	}
	@Test public void testHashedPassword() {
		String hashedPassword = Hash.hash(password, salt);
		String hashedValidP= Hash.hash(validP, salt);
		String hashedInvalidP= Hash.hash(invalidP, salt);
		
		assertEquals(hashedPassword,hashedValidP);	
		assertNotEquals(hashedPassword,hashedInvalidP);
	}
}
