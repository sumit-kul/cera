package com.era.community.base;

import java.util.Random;

public class RandomString {
	private static final char[] symbols;

	static {
		StringBuilder tmp = new StringBuilder();
		for (char ch = '0'; ch <= '9'; ++ch)
			tmp.append(ch);
		for (char ch = 'a'; ch <= 'z'; ++ch)
			tmp.append(ch);
		for (char ch = 'A'; ch <= 'Z'; ++ch)
			tmp.append(ch);
		symbols = tmp.toString().toCharArray();
	}   

	private static final Random random = new Random();

	private static final char[] buf = new char[48];
	private static final char[] shortBuf = new char[8];

	public static String nextString() {
		for (int idx = 0; idx < buf.length; ++idx) 
			buf[idx] = symbols[random.nextInt(symbols.length)];
		return new String(buf);
	}
	
	public static String nextShortString() {
		for (int idx = 0; idx < shortBuf.length; ++idx) 
			shortBuf[idx] = symbols[random.nextInt(symbols.length)];
		return new String(shortBuf);
	}
}
