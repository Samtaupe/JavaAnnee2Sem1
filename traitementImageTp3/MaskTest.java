package traitementImageTp3;

import java.io.IOException;

import traitementImageTp1.GreyImage;

public class MaskTest {
	public static void main(String[] args) {
		short[] dataM1 = {-1, -1, -1, -1, 16, -1, -1, -1, -1};

		try {
			GreyImage greyImage = GreyImage.loadPGM("C:/Users/thoma/git/JavaAnnee2Sem1/traitementImageTp1/imgs/barbara.pgm");
			Mask mask = new Mask(dataM1,3);
			
			GreyImage convolveResult = greyImage.convolve(mask);
			//convolveResult.truncate((short)(0), (short)(255));
			convolveResult.savePGM("output.pgm");
		} catch (IOException e) {
			e.printStackTrace();
		}
		

	}
}

