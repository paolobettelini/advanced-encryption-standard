package ch.bettelini.aes;

/**
 * AES
 * This object represents an encoder/decoder.
 * 
 * Supported key sizes: 128, 192, 256 bit
 * Supported block cipher modes: ECB, CBC
 * Supported paddings: ANSI X9.23, ISO 10126, PKCS#7, ISO/IEC 7816-4
 * 
 * @version 26.04.2021
 * @author Paolo Bettelini
 */
public class AES {
	
	/**
	 * The Substitution-Box.
	 */
	private static final int[] sbox = {
		0x63, 0x7C, 0x77, 0x7B, 0xF2, 0x6B, 0x6F, 0xC5, 0x30, 0x01, 0x67, 0x2B, 0xFE, 0xD7, 0xAB, 0x76,
		0xCA, 0x82, 0xC9, 0x7D, 0xFA, 0x59, 0x47, 0xF0, 0xAD, 0xD4, 0xA2, 0xAF, 0x9C, 0xA4, 0x72, 0xC0,
		0xB7, 0xFD, 0x93, 0x26, 0x36, 0x3F, 0xF7, 0xCC, 0x34, 0xA5, 0xE5, 0xF1, 0x71, 0xD8, 0x31, 0x15,
		0x04, 0xC7, 0x23, 0xC3, 0x18, 0x96, 0x05, 0x9A, 0x07, 0x12, 0x80, 0xE2, 0xEB, 0x27, 0xB2, 0x75,
		0x09, 0x83, 0x2C, 0x1A, 0x1B, 0x6E, 0x5A, 0xA0, 0x52, 0x3B, 0xD6, 0xB3, 0x29, 0xE3, 0x2F, 0x84,
		0x53, 0xD1, 0x00, 0xED, 0x20, 0xFC, 0xB1, 0x5B, 0x6A, 0xCB, 0xBE, 0x39, 0x4A, 0x4C, 0x58, 0xCF,
		0xD0, 0xEF, 0xAA, 0xFB, 0x43, 0x4D, 0x33, 0x85, 0x45, 0xF9, 0x02, 0x7F, 0x50, 0x3C, 0x9F, 0xA8,
		0x51, 0xA3, 0x40, 0x8F, 0x92, 0x9D, 0x38, 0xF5, 0xBC, 0xB6, 0xDA, 0x21, 0x10, 0xFF, 0xF3, 0xD2,
		0xCD, 0x0C, 0x13, 0xEC, 0x5F, 0x97, 0x44, 0x17, 0xC4, 0xA7, 0x7E, 0x3D, 0x64, 0x5D, 0x19, 0x73,
		0x60, 0x81, 0x4F, 0xDC, 0x22, 0x2A, 0x90, 0x88, 0x46, 0xEE, 0xB8, 0x14, 0xDE, 0x5E, 0x0B, 0xDB,
		0xE0, 0x32, 0x3A, 0x0A, 0x49, 0x06, 0x24, 0x5C, 0xC2, 0xD3, 0xAC, 0x62, 0x91, 0x95, 0xE4, 0x79,
		0xE7, 0xC8, 0x37, 0x6D, 0x8D, 0xD5, 0x4E, 0xA9, 0x6C, 0x56, 0xF4, 0xEA, 0x65, 0x7A, 0xAE, 0x08,
		0xBA, 0x78, 0x25, 0x2E, 0x1C, 0xA6, 0xB4, 0xC6, 0xE8, 0xDD, 0x74, 0x1F, 0x4B, 0xBD, 0x8B, 0x8A,
		0x70, 0x3E, 0xB5, 0x66, 0x48, 0x03, 0xF6, 0x0E, 0x61, 0x35, 0x57, 0xB9, 0x86, 0xC1, 0x1D, 0x9E,
		0xE1, 0xF8, 0x98, 0x11, 0x69, 0xD9, 0x8E, 0x94, 0x9B, 0x1E, 0x87, 0xE9, 0xCE, 0x55, 0x28, 0xDF,
		0x8C, 0xA1, 0x89, 0x0D, 0xBF, 0xE6, 0x42, 0x68, 0x41, 0x99, 0x2D, 0x0F, 0xB0, 0x54, 0xBB, 0x16
	};

	/**
	 * The inverse Substitution-Box.
	 */
	private static int[] sbox_inv = {
		0x52, 0x09, 0x6A, 0xD5, 0x30, 0x36, 0xA5, 0x38, 0xBF, 0x40, 0xA3, 0x9E, 0x81, 0xF3, 0xD7, 0xFB,
		0x7C, 0xE3, 0x39, 0x82, 0x9B, 0x2F, 0xFF, 0x87, 0x34, 0x8E, 0x43, 0x44, 0xC4, 0xDE, 0xE9, 0xCB,
		0x54, 0x7B, 0x94, 0x32, 0xA6, 0xC2, 0x23, 0x3D, 0xEE, 0x4C, 0x95, 0x0B, 0x42, 0xFA, 0xC3, 0x4E,
		0x08, 0x2E, 0xA1, 0x66, 0x28, 0xD9, 0x24, 0xB2, 0x76, 0x5B, 0xA2, 0x49, 0x6D, 0x8B, 0xD1, 0x25,
		0x72, 0xF8, 0xF6, 0x64, 0x86, 0x68, 0x98, 0x16, 0xD4, 0xA4, 0x5C, 0xCC, 0x5D, 0x65, 0xB6, 0x92,
		0x6C, 0x70, 0x48, 0x50, 0xFD, 0xED, 0xB9, 0xDA, 0x5E, 0x15, 0x46, 0x57, 0xA7, 0x8D, 0x9D, 0x84,
		0x90, 0xD8, 0xAB, 0x00, 0x8C, 0xBC, 0xD3, 0x0A, 0xF7, 0xE4, 0x58, 0x05, 0xB8, 0xB3, 0x45, 0x06,
		0xD0, 0x2C, 0x1E, 0x8F, 0xCA, 0x3F, 0x0F, 0x02, 0xC1, 0xAF, 0xBD, 0x03, 0x01, 0x13, 0x8A, 0x6B,
		0x3A, 0x91, 0x11, 0x41, 0x4F, 0x67, 0xDC, 0xEA, 0x97, 0xF2, 0xCF, 0xCE, 0xF0, 0xB4, 0xE6, 0x73,
		0x96, 0xAC, 0x74, 0x22, 0xE7, 0xAD, 0x35, 0x85, 0xE2, 0xF9, 0x37, 0xE8, 0x1C, 0x75, 0xDF, 0x6E,
		0x47, 0xF1, 0x1A, 0x71, 0x1D, 0x29, 0xC5, 0x89, 0x6F, 0xB7, 0x62, 0x0E, 0xAA, 0x18, 0xBE, 0x1B,
		0xFC, 0x56, 0x3E, 0x4B, 0xC6, 0xD2, 0x79, 0x20, 0x9A, 0xDB, 0xC0, 0xFE, 0x78, 0xCD, 0x5A, 0xF4,
		0x1F, 0xDD, 0xA8, 0x33, 0x88, 0x07, 0xC7, 0x31, 0xB1, 0x12, 0x10, 0x59, 0x27, 0x80, 0xEC, 0x5F,
		0x60, 0x51, 0x7F, 0xA9, 0x19, 0xB5, 0x4A, 0x0D, 0x2D, 0xE5, 0x7A, 0x9F, 0x93, 0xC9, 0x9C, 0xEF,
		0xA0, 0xE0, 0x3B, 0x4D, 0xAE, 0x2A, 0xF5, 0xB0, 0xC8, 0xEB, 0xBB, 0x3C, 0x83, 0x53, 0x99, 0x61,
		0x17, 0x2B, 0x04, 0x7E, 0xBA, 0x77, 0xD6, 0x26, 0xE1, 0x69, 0x14, 0x63, 0x55, 0x21, 0x0C, 0x7D
	};

	/**
	 * Precalculated rcon table.
	 * Used by the Rijndael key schedule.
	 */
	private static int rcon[] = {
		0x8d, 0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1b, 0x36, 0x6c, 0xd8, 0xab, 0x4d, 0x9a, 
		0x2f, 0x5e, 0xbc, 0x63, 0xc6, 0x97, 0x35, 0x6a, 0xd4, 0xb3, 0x7d, 0xfa, 0xef, 0xc5, 0x91, 0x39, 
		0x72, 0xe4, 0xd3, 0xbd, 0x61, 0xc2, 0x9f, 0x25, 0x4a, 0x94, 0x33, 0x66, 0xcc, 0x83, 0x1d, 0x3a, 
		0x74, 0xe8, 0xcb, 0x8d, 0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1b, 0x36, 0x6c, 0xd8, 
		0xab, 0x4d, 0x9a, 0x2f, 0x5e, 0xbc, 0x63, 0xc6, 0x97, 0x35, 0x6a, 0xd4, 0xb3, 0x7d, 0xfa, 0xef, 
		0xc5, 0x91, 0x39, 0x72, 0xe4, 0xd3, 0xbd, 0x61, 0xc2, 0x9f, 0x25, 0x4a, 0x94, 0x33, 0x66, 0xcc, 
		0x83, 0x1d, 0x3a, 0x74, 0xe8, 0xcb, 0x8d, 0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1b, 
		0x36, 0x6c, 0xd8, 0xab, 0x4d, 0x9a, 0x2f, 0x5e, 0xbc, 0x63, 0xc6, 0x97, 0x35, 0x6a, 0xd4, 0xb3, 
		0x7d, 0xfa, 0xef, 0xc5, 0x91, 0x39, 0x72, 0xe4, 0xd3, 0xbd, 0x61, 0xc2, 0x9f, 0x25, 0x4a, 0x94, 
		0x33, 0x66, 0xcc, 0x83, 0x1d, 0x3a, 0x74, 0xe8, 0xcb, 0x8d, 0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 
		0x40, 0x80, 0x1b, 0x36, 0x6c, 0xd8, 0xab, 0x4d, 0x9a, 0x2f, 0x5e, 0xbc, 0x63, 0xc6, 0x97, 0x35, 
		0x6a, 0xd4, 0xb3, 0x7d, 0xfa, 0xef, 0xc5, 0x91, 0x39, 0x72, 0xe4, 0xd3, 0xbd, 0x61, 0xc2, 0x9f, 
		0x25, 0x4a, 0x94, 0x33, 0x66, 0xcc, 0x83, 0x1d, 0x3a, 0x74, 0xe8, 0xcb, 0x8d, 0x01, 0x02, 0x04, 
		0x08, 0x10, 0x20, 0x40, 0x80, 0x1b, 0x36, 0x6c, 0xd8, 0xab, 0x4d, 0x9a, 0x2f, 0x5e, 0xbc, 0x63, 
		0xc6, 0x97, 0x35, 0x6a, 0xd4, 0xb3, 0x7d, 0xfa, 0xef, 0xc5, 0x91, 0x39, 0x72, 0xe4, 0xd3, 0xbd, 
		0x61, 0xc2, 0x9f, 0x25, 0x4a, 0x94, 0x33, 0x66, 0xcc, 0x83, 0x1d, 0x3a, 0x74, 0xe8, 0xcb
	};

	/**
	 * The number of 32-bit words comprising the Cipher Key.
	 * The number or columns when we represent the key as a matrix.
	 */
	private int keySize;
	
	/**
	 * The number of rounds to perform.
	 */
	private int rounds;

	/**
	 * The keys for each round, generazed by the key schedule.
	 */
	private byte[][] subkeys;

	/**
	 * The type of padding to use
	 */
	private Padding padding;

	/**
	 * The block cipher mode.
	 */
	private CipherMode mode;

	/**
	 * The Initialization Vector (IV)
	 */
	private byte[] iv;

	/**
	 * Default constructor override. 
	 * 
	 * @param key the main key
	 * @throws IllegalArgumentException if the key length isn't 128/192/256 bit
	 */
	public AES(byte[] key)
			throws IllegalArgumentException {
		setKey(key);

		// Default configurations
		
		this.padding = Padding.PKCS_7;
		this.mode = CipherMode.CBC;
		this.iv = new byte[16];
	}

	/**
	 * Changes the type of padding to use.
	 * 
	 * @param padding the type of padding
	 */
	public void setPadding(Padding padding) {
		this.padding = padding;
	}

	/**
	 * Changes the type of cipher block mode.
	 * 
	 * @param mode the block cipher mode
	 */
	public void setMode(CipherMode mode) {
		this.mode = mode;
	}

	public void setIV(byte[] iv) 
			throws IllegalArgumentException {
		if (iv.length != 16) {
			throw new IllegalArgumentException("Invalid IV size");
		}

		this.iv = iv;
	}

	/**
	 * Changed the key of the instance.
	 * 
	 * @param key the main key
	 * @throws IllegalArgumentException if the key length isn't 128/192/256 bit
	 */
	public void setKey(byte[] key) 
			throws IllegalArgumentException {
				
		if (key.length != 16 && key.length != 24 && key.length != 32) {
			throw new IllegalArgumentException("key size must be 128, 192 or 256 bits");
		}

		this.keySize = key.length >> 2;
		this.rounds = keySize + 6;

		subkeys = generateSubkeys(key);
	}

	/**
	 * Encryption algorithm.
	 * 
	 * @param data the data to encrypt
	 * @return the encrypted data
	 */
	public byte[] encrypt(byte[] data) {
		// Size of the encrypted byte stream with padding
		int size = (data.length + 15) >> 4 << 4;

		// A further block of padding is added if the data would have no padding
		if (data.length % 16 == 0) {
			size += 16;
		}

		byte[] result = new byte[size];

		data = padding.addPadding(data, size);

		byte[] lastBlock = null;
		if (mode == CipherMode.CBC) {
			lastBlock = clone(iv);
		}

		for (int i = 0; i < size >> 4; i++) {
			byte[] block = new byte[16];
			for (int j = 0; j < block.length; j++) {
				block[j] = data[(i << 4) + j];
			}

			if (mode == CipherMode.CBC) {
				block = xor(block, lastBlock);
			}

			byte[] encryptedBlock = encryptBlock(block);

			if (mode == CipherMode.CBC) {
				lastBlock = encryptedBlock;
			}

			for (int j = 0; j < 16; j++) {
				result[(i << 4) + j] = encryptedBlock[j];
			}
		}

		return result;
	}

	/**
	 * Decryption algorithm.
	 * 
	 * @param data the data to decrypt
	 * @return the result
	 * @throws IllegalArgumentException if the data length isn't a multiple of 16
	 * 	or it contains invalid padding.
	 */
	public byte[] decrypt(byte[] data)
			throws IllegalArgumentException {
		if (data.length == 0 || data.length % 16 != 0) {
			throw new IllegalArgumentException("Invalid data size");
		}

		int blocks = data.length >> 4;
		byte[] result = new byte[data.length];

		byte[] lastBlock = null;
		if (mode == CipherMode.CBC) {
			lastBlock = clone(iv);
		}

		for (int i = 0; i < blocks; i++) {
			byte[] block = new byte[16];

			for (int j = 0; j < block.length; j++) {
				block[j] = data[(i << 4) + j];
			}

			byte[] decrypted = decryptBlock(block);

			if (mode == CipherMode.CBC) {
				decrypted = xor(decrypted, lastBlock);
				lastBlock = block;
			}

			for (int j = 0; j < decrypted.length; j++) {
				result[(i << 4) + j] = decrypted[j];
			}
		}

		return padding.removePadding(result);
	}

	/**
	 * Decrypts a single 128-bit block.
	 * 
	 * @param block the block to decrypt
	 * @return the decrypted block
	 */
	private byte[] decryptBlock(byte[] block) {
		byte[][] state = generateState(block);

		for (int i = rounds; i > 0; i--) {
			// Apply (inverse) addRoundKey
			// XOR operator is its own inverse function
			addRoundKey(state, subkeys, i);
			
			// Except for the first round
			if (i != rounds) {
				// Apply inverse mixColumns operation
				invMixColumns(state);
			}

			// Apply inverse shiftRows operation
			invShiftRows(state);
		
			// Apply inverse subBytes operation
			invSubBytes(state);
		}

		// Apply (inverse) addRoundKey
		addRoundKey(state, subkeys, 0);

		return unfoldState(state);
	}

	/**
	 * Encrypts a single 128-bit block.
	 * 
	 * @param block the block to encrypt
	 * @return the encrypted block
	 */
	private byte[] encryptBlock(byte[] block) {
		// Create state matrix
		byte[][] state = generateState(block);

		// Initial addRoundsKey operation
		addRoundKey(state, subkeys, 0);
		
		for (int i = 1; i <= rounds; i++) {
			// Apply subBytes operation
			subBytes(state);
			
			// Apply shiftRows operation
			shiftRows(state);
			
			// Except for the last round
			if (i != rounds) {
				// Apply mixColumns operation
				mixColumns(state);
			}
			
			// Apply addRoundKey
			addRoundKey(state, subkeys, i);
		}

		return unfoldState(state);
	}

	/**
	 * Converts a state matrix to a byte stream.
	 * 
	 * @param state the state to unfold
	 * @return the result
	 */
	private static byte[] unfoldState(byte[][] state) {
		byte[] result = new byte[16];

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				result[(i << 2) + j] = state[j][i];
			}
		}

		return result;
	}

	/**
	 * Generates the sub-keys for each round.
	 * 
	 * @param key the main key
	 * @return the sub-keys
	 */
	private byte[][] generateSubkeys(byte[] key) {
		int totalWords = (rounds + 1) << 2;
		byte[][] result = new byte[totalWords][4];

		for (int i = 0; i < keySize; i++) {
			for (int j = 0; j < 4; j++) {
				result[i][j] = key[(i << 2) + j];
			}
		}

		for (int i = keySize; i < totalWords; i++) {
			byte[] tempWord = new byte[4];

			for(int j = 0; j < 4; j++) {
				tempWord[j] = result[i - 1][j];
			}

			if (i % keySize == 0) {
				rotateWord(tempWord);
				subBytes(tempWord);

				tempWord[0] = (byte) (tempWord[0] ^ (rcon[i / keySize] & 0xFF));
			} else if (keySize > 6 && i % keySize == 4) {
				subBytes(tempWord);
			}

			result[i] = xor(result[i - keySize], tempWord);
		}

		return result;
	}

	/**
	 * Applies an XOR operator to all corresponding bits between two single bytes.
	 * 
	 * @param a the first byte
	 * @param b the second byte
	 * @return the result
	 */
	private static byte[] xor(byte[] a, byte[] b) {
		byte[] result = new byte[a.length];

		for (int i = 0; i < result.length; i++) {
			result[i] = (byte) (a[i] ^ b[i]);
		}

		return result;
	}

	/**
	 * Applies an XOR operator to all corresponding bits between a state and a sub-key. 
	 * 
	 * @param state the state
	 * @param subkeys the sub-keys
	 * @param round the current round
	 */
	private static void addRoundKey(byte[][] state, byte[][] subkeys, int round) {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				state[i][j] = (byte) (state[i][j] ^ subkeys[round * 4 + j][i]);
			}
		}
	}

	/**
	 * Converts a 16-byte block to a 4x4 byte matrix.
	 * 
	 * @param block the byte stream
	 * @return the resulting matrix
	 */
	private static byte[][] generateState(byte[] block) {
		byte[][] state = new byte[4][4];
		
		for (int i = 0; i < block.length; i++) {
			state[i % 4][i / 4] = block[i];
		}

		return state;
	}

	/**
	 * Inverse mix columns operator.
	 * 
	 * @param state the state to operate on
	 * @see {@code #mixColumns(byte[][])} the inverse function
	 */
	private static void invMixColumns(byte[][] state) {
		int[] sp = new int[4];
		byte b02 = (byte)0x0e, b03 = (byte)0x0b, b04 = (byte)0x0d, b05 = (byte)0x09;
		for (int i = 0; i < 4; i++) {
			sp[0] = FFMul(b02, state[0][i]) ^ FFMul(b03, state[1][i]) ^ FFMul(b04, state[2][i])  ^ FFMul(b05, state[3][i]);
			sp[1] = FFMul(b05, state[0][i]) ^ FFMul(b02, state[1][i]) ^ FFMul(b03, state[2][i])  ^ FFMul(b04, state[3][i]);
			sp[2] = FFMul(b04, state[0][i]) ^ FFMul(b05, state[1][i]) ^ FFMul(b02, state[2][i])  ^ FFMul(b03, state[3][i]);
			sp[3] = FFMul(b03, state[0][i]) ^ FFMul(b04, state[1][i]) ^ FFMul(b05, state[2][i])  ^ FFMul(b02, state[3][i]);

			for (int j = 0; j < 4; j++) {
				state[j][i] = (byte)(sp[j]);
			}
		}
	}

	/**
	 * Mix columns operator.
	 * 
	 * @param state the state to operate on
	 * @see {@code #invMixColumns(byte[][])} the inverse function
	 */
	private static void mixColumns(byte[][] state) {
		int[] sp = new int[4];
		byte b02 = (byte)0x02, b03 = (byte)0x03;
		for (int i = 0; i < 4; i++) {
			sp[0] = FFMul(b02, state[0][i]) ^ FFMul(b03, state[1][i]) ^ state[2][i] ^ state[3][i];
			sp[1] = state[0][i] ^ FFMul(b02, state[1][i]) ^ FFMul(b03, state[2][i]) ^ state[3][i];
			sp[2] = state[0][i] ^ state[1][i] ^ FFMul(b02, state[2][i]) ^ FFMul(b03, state[3][i]);
			sp[3] = FFMul(b03, state[0][i]) ^ state[1][i] ^ state[2][i] ^ FFMul(b02, state[3][i]);
			
			for (int j = 0; j < 4; j++) {
				state[j][i] = (byte) (sp[j]);
			}
		}
	}

	/**
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	private static byte FFMul(byte a, byte b) {
		byte aa = a, bb = b, r = 0, t;
		
		while (aa != 0) {
			if ((aa & 1) != 0) {
				r ^= bb;
			}

			t = (byte) (bb & 0x80);
			bb = (byte) (bb << 1);

			if (t != 0) {
				bb = (byte) (bb ^ 0x1B);
			}

			aa = (byte) ((aa & 0xFF) >> 1);
		}

		return r;
	}

	/**
	 * Substitutes each byte of the state to its corresponding byte on the s-box.
	 * 
	 * @param state the state to operate on
	 * @see {@code #sbox}
	 * @see {@code #invSubBytes(byte[][])} the inverse function
	 */
	private static void subBytes(byte[][] state) {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				state[i][j] = (byte) (sbox[state[i][j] & 0xFF] & 0xFF);
			}
		}
	}

	/**
	 * Substitutes each byte of the state to its corresponding byte on the inverse s-box.
	 * 
	 * @param state the state to operate on
	 * @see {@code #sbox_inv}
	 * @see {@code #subBytes(byte[][])} the inverse function
	 */
	private static void invSubBytes(byte[][] state) {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				state[i][j] = (byte) (sbox_inv[state[i][j] & 0xFF] & 0xFF);
			}
		}
	}

	/**
	 * Substitutes each byte of the word to its corresponding byte on the s-box.
	 * 
	 * @param word the 4-byte word to operate on
	 * @see {@code #sbox}
	 */
	private static void subBytes(byte[] word) {
		for (int i = 0; i < 4; i++) {
			word[i] = (byte) (sbox[word[i] & 0xFF] & 0xFF);
		}
	}

	private static void shiftRows(byte[][] state) {
		for (int i = 1; i < 4; i++) { // i == 0 doesn't affect the matrix
			state[i] = shiftRowLeft(state[i], i);
		}
	}

	private static void invShiftRows(byte[][] state) {
		for (int i = 1; i < 4; i++) { // i == 0 doesn't affect the matrix
			state[i] = shiftRowRight(state[i], i);
		}
	}

	private static byte[] shiftRowRight(byte[] row, int times) {
		byte[] result = new byte[row.length];
		
		times %= result.length;

		for (int i = 0; i < result.length; i++) {
			result[i] = row[(i - times + 4) % result.length];
		}

		return result;
	}

	private static byte[] shiftRowLeft(byte[] row, int times) {
		byte[] result = new byte[row.length];

		for (int i = 0; i < result.length; i++) {
			result[i] = row[(i + times) % result.length];
		}

		return result;
	}

	private static void rotateWord(byte[] word) {
		byte q = word[0];

		word[0] = word[1];
		word[1] = word[2];
		word[2] = word[3];
		word[3] = q;
	}

	private static byte[] clone(byte[] data) {
		byte[] result = new byte[data.length];

		for (int i = 0; i < result.length; i++) {
			result[i] = data[i];
		}

		return result;
	}

}
