package traitementImageTp1;

import java.io.FileNotFoundException;
import java.io.IOException;

import traitementImageTp3.Mask;

public class GreyImage {
    private int dimX, dimY, size;
    private short[] data;

    // Constructors
    public GreyImage(int dimX, int dimY) {
        this.dimX = dimX;
        this.dimY = dimY;
        this.size = dimX * dimY;
        this.data = new short[size];
    }

    public GreyImage(int dimX, int dimY, short[] data) {
        this.dimX = dimX;
        this.dimY = dimY;
        this.size = dimX * dimY;

        if (data.length != size) {
            throw new IllegalArgumentException("Le paramètre data n'est pas compatible avec la taille de l'image !!");
        }

        this.data = data;
    }

    public GreyImage(GreyImage other) {
        this.dimX = other.getSizeX();
        this.dimY = other.getSizeY();
        this.size = other.getSizeData();
        this.data = new short[size];

        System.arraycopy(other.data, 0, this.data, 0, size);
    }

    public int getSizeX() {
        return dimX;
    }

    public int getSizeY() {
        return dimY;
    }

    public int getSizeData() {
        return size;
    }

    public int getSize() {
		return size;
	}

	public short[] getData() {
		return data;
	}

	public short getPixel(int x, int y) {
        if (isPosValid(x, y)) {
            return data[y * dimX + x];
        } else {
            throw new IllegalArgumentException("Position invalide : (" + x + "," + y + ")");
        }
    }

    public void setPixel(int x, int y, short value) {
        if (isPosValid(x, y)) {
            data[y * dimX + x] = value;
        } else {
            throw new IllegalArgumentException("Invalid position: (" + x + "," + y + ")");
        }
    }

    public short getPixel(int offset) {
        if (isPosValid(offset)) {
            return data[offset];
        } else {
            throw new IllegalArgumentException("Offset invalid : " + offset);
        }
    }

    public void setPixel(int offset, short value) {
        if (isPosValid(offset)) {
            data[offset] = value;
        } else {
            throw new IllegalArgumentException("Offset invalid : " + offset);
        }
    }

    public boolean isPosValid(int x, int y) {
        return x >= 0 && x < dimX && y >= 0 && y < dimY;
    }

    public boolean isPosValid(int offset) {
        return offset >= 0 && offset < size;
    }

    public short getMin() {
        short min = data[0];
        for (short pixel : data) {
            if (pixel < min) {
                min = pixel;
            }
        }
        return min;
    }

    public short getMax() {
        short max = data[0];
        for (short pixel : data) {
            if (pixel > max) {
                max = pixel;
            }
        }
        return max;
    }

    public void negative() {
        short max = getMax();
        for (int i = 0; i < size; i++) {
            data[i] = (short) (max - data[i]);
        }
    }

    public void seuiller(short seuil) {
        for (int i = 0; i < size; i++) {
            if (data[i] < seuil) {
                data[i] = 0;
            } else {
                data[i] = 255;
            }
        }
    }

    public void truncate(short min, short max) {
        for (int i = 0; i < size; i++) {
            if (data[i] < min) {
                data[i] = min;
            } else if (data[i] > max) {
                data[i] = max;
            }
        }
    }
    
    public static GreyImage loadPGM(String filename) throws FileNotFoundException, IOException {
        PGMFileIO pgmFileIO = new PGMFileIO(filename);
        pgmFileIO.readPGM();

        int sizeX = pgmFileIO.getSizeX();
        int sizeY = pgmFileIO.getSizeY();
        short[] data = pgmFileIO.getData();

        return new GreyImage(sizeX, sizeY, data);
    }
    
    public void savePGM(String filename) throws FileNotFoundException, IOException {
        PGMFileIO pgmFileIO = new PGMFileIO(filename);
        pgmFileIO.writePGM(dimX, dimY, data);
    }
    
    public void adjustContrast(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("Valeurs du contraste invalide, le min ne peut être supérieur au max.");
        }

        short currentMin = getMin();
        short currentMax = getMax();
            for (int i = 0; i < size; i++) {
                data[i] = (short) ((data[i] - currentMin) * (max - min) / (currentMax - currentMin) + min);
            }
    }
    
    public GreyImage convolve(Mask m) {
    	GreyImage img = new GreyImage(dimX, dimY);
    	int p = m.getSizeX()/2;
    	
    	for(int i = 0; i < getSizeX(); i++) {
    		for(int j = 0; j < getSizeY(); j++) {
    			
    			double sum = 0;
    			
    			for(int k = 0; k < m.getSizeX(); k++) {
    				for(int l = 0; l < m.getSizeX(); l++) {
    					
    					if(isPosValid(i+k-p, j+l-p)) {
    						sum+=m.getPixel(k, l)*getPixel(i+k-p, j+l-p);    						
    					}
    					
    				}
    			}
    			if(m.getSumWeights()!=0) {
    				sum/=m.getSumWeights();    				
    			}
                img.setPixel(i, j, (short) sum);
    		}
    	}
    	
    	return img;
    }

}
