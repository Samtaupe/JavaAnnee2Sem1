package traitementImageTp2;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import traitementImageTp1.GreyImage;

public class TestHisto {
	@Test
	public void testConstructionHisto() {
		short[] data = {10,20,30,40};
		GreyImage im = new GreyImage(2, 2, data);
		Histogram histo = new Histogram(im);
		assertEquals(0, histo.getValue((short) (10)));
		assertEquals(0, histo.getValue((short) (50)));
	}
	
}
