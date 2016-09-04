package support.community.util;

import java.security.SecureRandom;

/**
 * Generates a random String using a cryptographically 
 * secure random number generator.
 */
public class PasswordGenerator {

	/**
	 * Random number generator.
	 */
	protected SecureRandom rand;

	public PasswordGenerator() 
	{
		this(new SecureRandom());
	}

	public PasswordGenerator(SecureRandom rand) 
	{
		this.rand = rand;
	}
	
	/**
        Generates a random string based on an alphanumeric pattern. The
        pattern can be any length with 'A' meaning the character at this position
        is to be an upper case letter, and '0' meaning the character at this 
        position is a digit.
	 */
	public String generatePassword(String pattern) 
	{
		char[] password = new char[pattern.length()];

		for (int i = 0; i < pattern.length(); i++) {
			if (pattern.charAt(i) == 'A')
				password[i] = (char) (Math.abs(rand.nextInt() % 26) + (int) 'A');
			else
				password[i] = (char) (Math.abs(rand.nextInt() % 10) + (int) '0');
		}
		return (new String(password));
	}
}