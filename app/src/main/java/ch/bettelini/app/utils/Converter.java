package ch.bettelini.app.utils;

import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

/**
 * Converter class
 * Base64 and Hex conversion utils
 * 
 * @author Paolo Bettelini
 * @version 25.04.2021
 */
public class Converter {
	
	/**
	 * Base64 encoder instance
	 */
	private static Encoder encoder;
	
	/**
	 * Bas64 decoder instance
	 */
	private static Decoder decoder;

	static {
		encoder = Base64.getEncoder();
		decoder = Base64.getDecoder();
	}

	/**
	 * Converts a binary stream to a <code>String</code> representation 
	 * where each byte is represented by a hex value.
	 * 
	 * @param data the data to convert
	 * @return the result
	 * @see {@link #fromHex(String)} the inverse function
	 */
	public static String toHex(byte[] data) {
		StringBuilder builder = new StringBuilder();

		for (byte b : data) {
			int value = b & 0xFF;
			builder.append((value < 16 ? "0" : "") + Integer.toHexString(value));
		}

		return builder.toString();
	}

	/**
	 * Converts a byte stream to a Base64 <code>String</code> representation.
	 * 
	 * @param data the data to convert
	 * @return the result
	 * @see {@link #frombase64(String)} the inverse function
	 */
	public static String toBase64(byte[] data) {
		return encoder.encodeToString(data);
	}

	/**
	 * Converts a <code>String</code> where each pair of chars
	 * represents a hex value to a byte stream.
	 * 
	 * @param data the data to convert
	 * @return the result
	 * @throws IllegalArgumentException if the data is not a valid representation
	 * @see {@link #toHex(byte[])} the inverse function
	 */
	public static byte[] fromHex(String data)
			throws IllegalArgumentException {
		if ((data.length() & 1) == 1) {
			throw new IllegalArgumentException("Invalid hex string");
		}

		byte[] result = new byte[data.length() >> 1];
		
		try {
			for (int i = 0; i < data.length(); i += 2) {
				result[i >> 1] = (byte) Integer.parseInt(String.valueOf(data.charAt(i)) + String.valueOf(data.charAt(i + 1)), 16);
			}
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Invalid hex string");
		}

		return result;
	}

	/**
	 * Converts a <code>String</code> Base64 representation
	 * into a byte stream.
	 * 
	 * @param data the data to convert
	 * @return the result
	 * @throws IllegalArgumentException if the data is not a valid representation
	 * @see {@link #toBase64(byte[])} the inverse function
	 */
	public static byte[] frombase64(String data)
			throws IllegalArgumentException {
		return decoder.decode(data);
	}

}
