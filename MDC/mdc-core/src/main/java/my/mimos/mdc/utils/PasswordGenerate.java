package my.mimos.mdc.utils;

import java.security.SecureRandom;
import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class PasswordGenerate {

	private static final Random RANDOM = new SecureRandom();
	public static final int PASSWORD_LENGTH = 8;

	public String generateRandomPassword() {
		String letters = "abcdefghjkmnpqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ23456789";
		String password = "";
		for (int i = 0; i < PASSWORD_LENGTH; i++) {
			int index = (int) (RANDOM.nextDouble() * letters.length());
			password += letters.substring(index, index + 1);
		}
		return password;
	}

}
