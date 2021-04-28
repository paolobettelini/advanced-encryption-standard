package ch.bettelini.app;

import ch.bettelini.aes.AES;
import ch.bettelini.aes.CipherMode;
import ch.bettelini.aes.Padding;
import ch.bettelini.app.utils.Converter;

public class AESHandler {

	private AES cipher;

	private int keyEncoding, inputEncoding, outputEncoding, ivEncoding;
	
	private String key, in, out, iv;

	private boolean encryption;

	public AESHandler() {
		this.cipher = new AES(new byte[16]);
		this.keyEncoding = this.inputEncoding = ivEncoding = 0;
		this.outputEncoding = 1;
		this.encryption = true;
	}

	public void setPadding(String padding) {
		switch (padding) {
			case "PKCS#7":
				cipher.setPadding(Padding.PKCS_7);
				break;
			case "ANSI X9.23":
				cipher.setPadding(Padding.ANSI_X9_23);
				break;
			case "ISO 10126":
				cipher.setPadding(Padding.ISO_10126);
				break;
			case "ISO/IEC 7816-4":
				cipher.setPadding(Padding.ISO_IEC_7816_4);
				break;
		}

		update();
	}

	public void setMode(String mode) {
		switch (mode) {
			case "ECB":
				cipher.setMode(CipherMode.ECB);
				break;
			case "CBC":
				cipher.setMode(CipherMode.CBC);
				break;
		}

		update();
	}

	public void setIV(String iv) {
		if (iv == null) {
			return;
		}

		this.iv = iv;

		byte[] bytes = null;
		
		switch (ivEncoding) {
			case 0: // PlainText
				bytes = iv.getBytes();
				break;
			case 1: // Base64
				try {
					bytes = Converter.fromBase64(iv);
				} catch (IllegalArgumentException e) {
					out = "Invalid Base64 IV";
					return;
				}
				break;
			case 2: // Hex
				try {
					bytes = Converter.fromHex(iv);
				} catch (IllegalArgumentException e) {
					out = "Invalid Hex IV";
					return;
				}
				break;
		}

		try {
			cipher.setIV(bytes);
			update();
		} catch (IllegalArgumentException e) {
			out = e.getMessage();
		}
	}

	public void setKey(String key) {
		if (key == null) {
			return;
		}

		this.key = key;

		byte[] bytes = null;

		switch (keyEncoding) {
			case 0: // PlainText
				bytes = key.getBytes();
				break;
			case 1: // Base64
				try {
					bytes = Converter.fromBase64(key);
				} catch (IllegalArgumentException e) {
					out = "Invalid Base64 Key";
					return;
				}
				break;
			case 2: // Hex
				try {
					bytes = Converter.fromHex(key);
				} catch (IllegalArgumentException e) {
					out = "Invalid Hex Key";
					return;
				}
				break;
		}

		try {
			cipher.setKey(bytes);
			update();
		} catch (IllegalArgumentException e) {
			out = e.getMessage();
		}
	}

	public void setInput(String in) {
		this.in = in;

		update();
	}

	public String getOutput() {
		return out;
	}

	public void setKeyEncoding(int encoding) {
		this.keyEncoding = encoding;

		setKey(key);
	}

	public void setInputEncoding(int encoding) {
		this.inputEncoding = encoding;

		setInput(in);
	}

	public void setOutputEncoding(int encoding) {
		this.outputEncoding = encoding;

		update();
	}

	public void setIvEncoding(int encoding) {
		this.ivEncoding = encoding;

		setIV(iv);
	}

	public void setEncryption() {
		encryption = true;

		update();
	}

	public void setDecryption() {
		encryption = false;

		update();
	}

	private void update() {
		if (in == null || in.isEmpty()) {
			out = "Invalid input";
			return;
		}

		byte[] bytes = null;

		switch (inputEncoding) {
			case 0: // PlainText
				bytes = in.getBytes();
				
				break;
			case 1: // Base64
				try {
					bytes = Converter.fromBase64(in);
				} catch (IllegalArgumentException e) {
					out = "Invalid Base64 Input";
					return;
				}

				break;
			case 2: // Hex
				try {
					bytes = Converter.fromHex(in);
				} catch (IllegalArgumentException e) {
					out = "Invalid Hex Input";
					return;
				}

				break;
		}

		byte[] result = null;

		if (encryption) {
			result = cipher.encrypt(bytes);
		} else {
			try {
				result = cipher.decrypt(bytes);
			} catch (IllegalArgumentException e) {
				out = e.getMessage();
				return;
			}
		}

		switch (outputEncoding) {
			case 0: // Plain Text
				out = new String(result);

			break;
			case 1: // Base 64
				out = Converter.toBase64(result);
			
			break;
			case 2: // Hex		
				out = Converter.toHex(result);
				break;
		}
	}

}