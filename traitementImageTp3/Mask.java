package traitementImageTp3;

import java.io.IOException;

import traitementImageTp1.GreyImage;

public class Mask extends GreyImage {

	private GreyImage greyImage;
	
    public Mask(int size) {
        super(size, size);
    }
    
    public Mask(GreyImage img) {
    	super(img);
    }

    public Mask(short[] data, int size) {
        super(size, size, data);
    }
    
    public GreyImage getGreyImage() {
		return greyImage;
	}

	public void setGreyImage(GreyImage greyImage) {
		this.greyImage = greyImage;
	}

	public double getSumWeights() {
        double sum = 0;
        for (int i = 0; i < getSizeData(); i++) {
            sum += getPixel(i);
        }
        return sum;
    }
    
    public void saveImage() {
    	try {
			greyImage.savePGM("output.pgm");
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
