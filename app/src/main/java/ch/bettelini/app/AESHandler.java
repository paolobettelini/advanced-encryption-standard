package ch.bettelini.app;

import ch.bettelini.aes.AES;
import ch.bettelini.app.utils.Converter;

public class AESHandler {

	private int keyEncoding, inputEncoding, outputEncoding;
	
	private String key, in, out;

	private AES cipher;

	public AESHandler() {
		this.keyEncoding = this.inputEncoding = this.outputEncoding = 0;
		this.cipher = new AES(new byte[16]);
	}

	public void setKey(String key) {
		this.key = key;

		try {
			byte[] bytes = null;

			switch (keyEncoding) {
				case 0: // PlainText
					bytes = key.getBytes();
					break;
				case 1: // Base64
					try {
						bytes = Converter.frombase64(key);
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

	private void update() {
		byte[] bytes = null;

		switch (inputEncoding) {
			case 0: // PlainText
				bytes = in.getBytes();
				
				break;
			case 1: // Base64
				try {
					bytes = Converter.frombase64(in);
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

		byte[] result = cipher.encrypt(bytes);

		switch (outputEncoding) {
			case 0: // Base 64
				out = Converter.toBase64(result);

				break;
			case 1: // Hex
				out = Converter.toHex(result);
				
				break;
		}
	}

}