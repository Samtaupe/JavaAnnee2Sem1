package traitementImageTp2;

import java.io.*;

import traitementImageTp1.GreyImage;

class Histogram {
	
	private int[] data;
	private short minValue;
	
	Histogram(GreyImage im)
	{
		this.minValue = im.getMin();
		this.data = new int[im.getSizeData()];
	}
	
	
	
	public int[] getData() {
		return data;
	}



	public short getMinValue() {
		return minValue;
	}



	int getValue(short v) {
		if(v >= minValue) { return data[v-minValue]; }
		return data[v];
	}
	
	short getPeak() {
		short peak = minValue;
		for(int i = 0; i < data.length; i ++) {
			if(getValue(peak) > getValue((short) (i))) {
				peak = (short) (i + minValue);
			}
		}
		return peak;
	}

	void saveHisto(String filename) throws FileNotFoundException, IOException
	{
		FileOutputStream fileout=new FileOutputStream(filename);
		for(int i=0; i<data.length; i++)
		{
			String tmp=minValue+i + " " + data[i]+"\n";
			fileout.write(tmp.getBytes());
		}
		
		fileout.close();
	}

}
