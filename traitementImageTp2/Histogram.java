package traitementImageTp2;

import java.io.*;

import traitementImageTp1.GreyImage;

public class Histogram {

    private short minValue;
    private int[] data;

    public Histogram(GreyImage im) {
        minValue = im.getMin();
        data = new int[im.getMax() - minValue + 1];
        for (short pixel : im.getData()) {
            data[pixel - minValue]++;
        }
    }

    public int getValue(short v) {
        if (v < minValue || v >= minValue + data.length) {
            throw new IllegalArgumentException("Invalid histogram value: " + v);
        }
        return data[v - minValue];
    }

    public short getPeak() {
        int maxCount = 0;
        short peakValue = minValue;
        for (int i = 0; i < data.length; i++) {
            if (data[i] > maxCount) {
                maxCount = data[i];
                peakValue = (short) (minValue + i);
            }
        }
        return peakValue;
    }

    public void saveHisto(String filename) throws FileNotFoundException, IOException
	{
		FileOutputStream fileout=new FileOutputStream(filename);
		for(int i=0; i<data.length; i++)
		{
			String tmp=minValue+i + ", " + data[i]+"\n";
			fileout.write(tmp.getBytes());
		}
		fileout.close();
	}
    
    public void equalize(int min, int max) {
        int cumulativeSum = 0;
        
        for(int i = min; i < data.length; i ++) {
        	cumulativeSum += data[i];
        }
        
        for(int i = min; i < data.length; i ++) {
        	data[i] = ((max-min)*data[i])/cumulativeSum+min;
        }

    }
}
