package traitementImageTp3;


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
    
	public double getSumWeights() {
        double sum = 0;
        for (int i = 0; i < getSizeData(); i++) {
            sum += getPixel(i);
        }
        return sum;
    }
	
	public void makeball1() {
		short[] data = {0,1,0,
						1,1,1,
						0,1,0};
		this.setData(data);
	}
	
	public void makeball2() {
		short[] data = {0,0,1,0,0,
						0,1,1,1,0,
						1,1,1,1,1,
						0,1,1,1,0,
						0,0,1,0,0};
		this.setData(data);
	}
	public void makeball3() {
		short[] data = {0,0,0,0,0,1,0,0,0,0,0,
						0,0,0,0,1,1,1,0,0,0,0,
						0,0,0,1,1,1,1,1,0,0,0,
						0,0,1,1,1,1,1,1,1,0,0,
						0,1,1,1,1,1,1,1,1,1,0,
						1,1,1,1,1,1,1,1,1,1,1,
						0,1,1,1,1,1,1,1,1,1,0,
						0,0,1,1,1,1,1,1,1,0,0,
						0,0,0,1,1,1,1,1,0,0,0,
						0,0,0,0,1,1,1,0,0,0,0,
						0,0,0,0,0,1,0,0,0,0,0};
		this.setData(data);
	}
}
