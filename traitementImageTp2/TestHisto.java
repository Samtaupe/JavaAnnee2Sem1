package traitementImageTp2;

import traitementImageTp1.GreyImage;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class TestHisto {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the path to the image file: ");
        String imageFilePath = scanner.nextLine();

        GreyImage greyImage = loadImage(imageFilePath);

        if (greyImage == null) {
            System.out.println("Error loading the image.");
            System.exit(1);
        }

        Histogram histogram = new Histogram(greyImage);
        short peakValue = histogram.getPeak();

        System.out.println("Peak value in the histogram: " + peakValue);

        try {
            histogram.saveHisto("histo1.txt");
            System.out.println("Histogram saved to histo1.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        greyImage.negative();
        Histogram negativeHistogram = new Histogram(greyImage);

        try {
            negativeHistogram.saveHisto("histo2.txt");
            System.out.println("Negative histogram saved to histo2.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        scanner.close();
    }

    private static GreyImage loadImage(String filePath) {
        try (Scanner scanner = new Scanner(new File(filePath))) {
            int dimX = scanner.nextInt();
            int dimY = scanner.nextInt();
            short[] data = new short[dimX * dimY];

            for (int i = 0; i < data.length; i++) {
                data[i] = scanner.nextShort();
            }

            return new GreyImage(dimX, dimY, data);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
