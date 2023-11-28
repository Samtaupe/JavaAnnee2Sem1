package traitementImageTp2;

import traitementImageTp1.GreyImage;

import java.io.FileNotFoundException;
import java.io.IOException;

public class TestHisto {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        GreyImage greyImage = GreyImage.loadPGM("C:/Users/thoma/git/JavaAnnee2Sem1/traitementImageTp1/imgs_histogramme/badHisto.pgm");

        if (greyImage == null) {
            System.out.println("Erreur au chargement de l'image.");
            System.exit(1);
        }
        
        Histogram histogram = new Histogram(greyImage);
        short peakValue = histogram.getPeak();

        System.out.println("Valeur la plus élevé de l'histograme: " + peakValue);

        try {
            histogram.saveHisto("histo1.txt");
            System.out.println("Histograme sauvegarder  dans le fichier : histo1.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        histogram.equalize(0, 255);
        try {
            histogram.saveHisto("histo2.txt");
            System.out.println("Histograme égalisé sauvegarder dans le fichier : histo2.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
