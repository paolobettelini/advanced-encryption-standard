package ch.bettelini.aes;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PaddingTest {

	@Test
	public void TestPKCS_7 () {
		/* Arrange */

		int initialLength = 5;
		int totalLength = 16;
		byte[] data = new byte[initialLength];

		/* Act */

		byte[] data1 = Padding.PKCS_7.addPadding(data, totalLength); // data + padding
		byte[] data2 = Padding.PKCS_7.removePadding(data1); // (data + padding) - padding
		
		/* Assert */

		assertTrue(data1.length == totalLength);
		assertTrue(data2.length == initialLength);
	}

	@Test
	public void TestISO_IEC_7816_4 () {
		/* Arrange */

		int initialLength = 5;
		int totalLength = 16;
		byte[] data = new byte[initialLength];

		/* Act */

		byte[] data1 = Padding.ISO_IEC_7816_4.addPadding(data, totalLength); // data + padding
		byte[] data2 = Padding.ISO_IEC_7816_4.removePadding(data1); // (data + padding) - padding
		
		/* Assert */

		assertTrue(data1.length == totalLength);
		assertTrue(data2.length == initialLength);
	}

	@Test
	public void TestISO_10126 () {
		/* Arrange */

		int initialLength = 5;
		int totalLength = 16;
		byte[] data = new byte[initialLength];

		/* Act */

		byte[] data1 = Padding.ISO_10126.addPadding(data, totalLength); // data + padding
		byte[] data2 = Padding.ISO_10126.removePadding(data1); // (data + padding) - padding
		
		/* Assert */

		assertTrue(data1.length == totalLength);
		assertTrue(data2.length == initialLength);
	}

	@Test
	public void TestANSI_X9_23 () {
		/* Arrange */

		int initialLength = 5;
		int totalLength = 16;
		byte[] data = new byte[initialLength];

		/* Act */

		byte[] data1 = Padding.ANSI_X9_23.addPadding(data, totalLength); // data + padding
		byte[] data2 = Padding.ANSI_X9_23.removePadding(data1); // (data + padding) - padding
		
		/* Assert */

		assertTrue(data1.length == totalLength);
		assertTrue(data2.length == initialLength);
	}

}