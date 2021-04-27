package ch.bettelini.aes;

import java.util.Random;

public enum Padding {
    PKCS_7 {
        @Override
        byte[] addPadding(byte[] data, int n) {
            int length = n - data.length % n; // padding length	

            byte[] result = new byte[data.length + length];

            for (int i = 0; i < data.length; i++) {
            	result[i] = data[i];
            }

            byte value = (byte) (length);

            for (int i = data.length; i < result.length; i++) {
            	result[i] = value;
            }

            return result;
        };
		
        @Override
        byte[] removePadding(byte[] data) throws IllegalArgumentException {
			if (data.length == 0) {
				throw new IllegalArgumentException("Invalid data size");
			}

	    	int length = data[data.length - 1] & 0xFF;

			if (data.length < length) {
				throw new IllegalArgumentException("Invalid data size");
			}

    		for (int i = data.length - 1; i > data.length - length - 1; i--) {
    			if ((data[i] & 0xFF) != length) {
    				throw new IllegalArgumentException("Invalid Padding");
    			}
    		}

			byte[] result = new byte[data.length - length];

			for (int i = 0; i < result.length; i++) {
				result[i] = data[i];
			}

			return result;
        }
    },
    ISO_IEC_7816_4 {
        @Override
        byte[] addPadding(byte[] data, int n) {
       		int totalLength = n - data.length % n;
			
			byte[] result = new byte[data.length + totalLength];

			// clone data
			for (int i = 0; i < data.length; i++) {
				result[i] = data[i];
			}

			result[data.length] = (byte) 0x80;

			for (int i = data.length + 1; i < result.length; i++) {
				result[i] = 0;
			}

			return result;
        };
		
        @Override
        byte[] removePadding(byte[] data) throws IllegalArgumentException {
    		int totalLength = data.length;

			for (int i = totalLength - 1; i >= 0; i--) {
				totalLength--;

				if (data[i] == (byte)0x80) { // start of padding
					break;
				}

				if (data[i] != 0) { // no valid padding
					throw new IllegalArgumentException("Invalid padding");
				}
			}

			byte[] result = new byte[totalLength];

			for (int i = 0; i < result.length; i++) {
				result[i] = data[i];
			}

			return result;
        }
    },
    ISO_10126 {
    	@Override
    	byte[] addPadding(byte[] data, int n) {
    		int length = n - data.length % n;

    		byte[] result = new byte[data.length + length];

    		for (int i = 0; i < data.length; i++) {
    			result[i] = data[i];
    		}

    		Random random = new Random();
    		byte[] randomBytes = new byte[length - 1];
    		random.nextBytes(randomBytes);

    		for (int i = 0; i < randomBytes.length; i++) {
    			result[data.length + i] = randomBytes[i];
    		}

    		result[result.length - 1] = (byte) length;

    		return result;
    	}
	    
    	@Override
	    byte[] removePadding(byte[] data) throws IllegalArgumentException {
			if (data.length == 0) {
				throw new IllegalArgumentException("Invalid data size");
			}

	    	int length = data[data.length - 1] & 0xFF;

			if (data.length < length) {
				throw new IllegalArgumentException("Invalid data size");
			}

	    	byte[] result = new byte[data.length - length];

	    	for (int i = 0; i < result.length; i++) {
	    		result[i] = data[i];
	    	}


	    	return result;
	    }
    },
    ANSI_X9_23 {
    	@Override
    	byte[] addPadding(byte[] data, int n) {
    		int length = n - data.length % n;

    		byte[] result = new byte[data.length + length];

    		for (int i = 0; i < data.length; i++) {
    			result[i] = data[i];
    		}

    		for (int i = data.length; i < result.length - 1; i++) {
    			result[i] = 0;
    		}

    		result[result.length - 1] = (byte) length;

    		return result;
    	}
	    
    	@Override
	    byte[] removePadding(byte[] data) throws IllegalArgumentException {
			if (data.length == 0) {
				throw new IllegalArgumentException("Invalid data size");
			}

	    	int length = data[data.length - 1] & 0xFF;

			if (data.length < length) {
				throw new IllegalArgumentException("Invalid data size");
			}

	    	for (int i = data.length - 2; i > data.length - length - 1; i--) {
	    		if (data[i] != 0) {
	    			throw new IllegalArgumentException("Invalid padding");
	    		}
	    	}

	    	byte[] result = new byte[data.length - length];

	    	for (int i = 0; i < result.length; i++) {
	    		result[i] = data[i];
	    	}


	    	return result;
	    }
    };

    abstract byte[] addPadding(byte[] data, int n);
    abstract byte[] removePadding(byte[] data) throws IllegalArgumentException;
}