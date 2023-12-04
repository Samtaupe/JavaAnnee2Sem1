package traitementImageTp3;

import java.io.IOException;

import traitementImageTp1.GreyImage;
import traitementImageTp1.GreyImageTest;
import traitementImageTp2.Histogram;

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
			
			GreyImage test = GreyImage.loadPGM("C:/Users/thoma/git/JavaAnnee2Sem1/img_sujet/test1.pgm");
			
			mask4.makeball1();
			GreyImage testErode1 = test.erode(mask4);
			testErode1.savePGM("test1_erode_B1.pgm");
			
			mask4.makeball2();
			GreyImage testErode2 = test.erode(mask4);
			testErode2.savePGM("test1_erode_B2.pgm");
			
			mask4.makeball1();
			GreyImage testDilate1 = test.dilate(mask4);
			testDilate1.savePGM("test1_dilate_B1.pgm");
			
			mask4.makeball2();
			GreyImage testDilate2 = test.dilate(mask4);
			testDilate2.savePGM("test1_dilate_B2.pgm");
			
			mask4.makeball1();
			GreyImage testOpen1 = test.dilate(mask4);
			testOpen1.savePGM("test1_open_B1.pgm");
			
			mask4.makeball2();
			GreyImage testOpen2 = test.dilate(mask4);
			testOpen2.savePGM("test1_open_B2.pgm");
			
			mask4.makeball1();
			GreyImage testClose1 = test.dilate(mask4);
			testClose1.savePGM("test1_close_B1.pgm");
			
			mask4.makeball2();
			GreyImage testClose2 = test.dilate(mask4);
			testClose2.savePGM("test1_close_B2.pgm");
			
			GreyImage testGrad1 = test.morphologicalGradient(testErode1, testDilate1);
			testGrad1.savePGM("test1_grad_B1.pgm");
			
			GreyImage testGrad2 = test.morphologicalGradient(testErode2, testDilate2);
			testGrad2.savePGM("test1_grad_B2.pgm");
			
			GreyImage testCellule = GreyImage.loadPGM("C:/Users/thoma/git/JavaAnnee2Sem1/img_sujet/bloodcells.pgm");
			testCellule.seuiller((short)80);
			testCellule.savePGM("bloodcells_bin.pgm");

			mask4.makeball1();
			GreyImage celluleBackground = testCellule.erode(mask4);
			GreyImage celluleForme = testCellule.dilate(mask4);
			celluleBackground.savePGM("bloodcells_background_B1.pgm");
			celluleForme.savePGM("bloodcells_forme_B1.pgm");
			
			mask4.makeball2();
			GreyImage celluleBackground2 = testCellule.erode(mask4);
			GreyImage celluleForme2 = testCellule.dilate(mask4);
			celluleBackground2.savePGM("bloodcells_background_B2.pgm");
			celluleForme2.savePGM("bloodcells_forme_B2.pgm");
			
			System.out.println(testCellule.computeNMSE(celluleBackground));
			System.out.println(testCellule.computeNMSE(celluleForme));

			GreyImage testCellulePrime = testCellule.addRandomNoise(0.10);
			testCellulePrime.savePGM("bloodcells_poivre_sel.pgm");
			GreyImage testCellulePrimeMedian = testCellulePrime.medianFilter(3);
			testCellulePrimeMedian.savePGM("bloodcells_poivre_sel_median_B1.pgm");
			GreyImage testCellulePrimeMedian2 = testCellulePrime.medianFilter(5);
			testCellulePrimeMedian2.savePGM("bloodcells_poivre_sel_median_B2.pgm");
			
			System.out.println(testCellule.computeNMSE(testCellulePrimeMedian));
			System.out.println(testCellule.computeNMSE(testCellulePrimeMedian2));

			

			
		} catch (IOException e) {
			e.printStackTrace();
		}
		

	}
}

