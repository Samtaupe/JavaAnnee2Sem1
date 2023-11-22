package traitementImageTp2;

import java.io.*;
import java.util.Arrays;

import traitementImageTp1.GreyImage;

class Histogram {
	
	private short[] data;
	private String minValue;
	
	Histogram(GreyImage im)
	{
		System.arraycopy(im.getData(), 0, this.data, 0, im.getSize());
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
