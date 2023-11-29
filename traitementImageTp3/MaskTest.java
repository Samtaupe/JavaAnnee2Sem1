package traitementImageTp3;

import java.io.IOException;

import traitementImageTp1.GreyImage;

public class MaskTest {
	public static void main(String[] args) {
		short[] dataM1 = {1, 1, 1, 1, 1, 1, 1, 1, 1};
		short[] dataM2 = {-1, 0, 1, -2, 0, 2, -1, 0, 1};
		short[] dataM3 = {-1, -2, -1, 0, 0, 0, 1, 2, 1};
		short[] dataM4 = {-1, -1, -1, -1, 16, -1, -1, -1, -1};
		

		try {
			GreyImage greyImage = GreyImage.loadPGM("C:/Users/thoma/git/JavaAnnee2Sem1/traitementImageTp1/imgs/barbara.pgm");
			Mask mask1 = new Mask(dataM1,3);
			
			GreyImage convolveResult1 = greyImage.convolve(mask1);
			convolveResult1.truncate((short)(0), (short)(255));
			convolveResult1.savePGM("outputM1.pgm");
			
			Mask mask2 = new Mask(dataM2,3);
			
			GreyImage convolveResult2 = greyImage.convolve(mask2);
			convolveResult2.truncate((short)(0), (short)(255));
			convolveResult2.savePGM("outputM2.pgm");
			
			Mask mask3 = new Mask(dataM3,3);
			
			GreyImage convolveResult3 = greyImage.convolve(mask3);
			convolveResult3.truncate((short)(0), (short)(255));
			convolveResult3.savePGM("outputM3.pgm");
			
			Mask mask4 = new Mask(dataM4,3);
			
			GreyImage convolveResult4 = greyImage.convolve(mask4);
			convolveResult4.truncate((short)(0), (short)(255));
			convolveResult4.savePGM("outputM4.pgm");
			
			GreyImage blurImage = greyImage.addRandomNoise(0.3);
			blurImage.savePGM("outputBlur.pgm");
			
			GreyImage gaussImage = greyImage.addGaussianNoise(0.3, 0.3);
			gaussImage.savePGM("outputGauss.pgm");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		

	}
}

