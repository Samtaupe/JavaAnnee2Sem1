package traitementImageTp1;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

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
    
    public void setData(short[] data) {
		this.data = data;
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
    
    public GreyImage addRandomNoise(double p) {
    	if(p > 1 || p < 0) {
    		throw new IllegalArgumentException("p doit être compris entre 0 et 1");
    	}
    	
    	GreyImage img = new GreyImage(getSizeX(), getSizeY(), getData());    	
    	Random r = new Random();
    	
    	for(int i = 0; i < getSizeData(); i ++) {
    		double x = r.nextDouble();
    		
    		if(x < p) {
    			double y = r.nextDouble();
    			setPixel(i, (short)(y>0.5?255:0));
    		}
    	}
    	
    	return img;
    }
    
    public GreyImage addGaussianNoise(double mean, double std) {
    	GreyImage img = new GreyImage(getSizeX(), getSizeY(), getData());
    	Random r = new Random();
    	for(int i = 0; i < getSizeX(); i++) {
    		for(int j = 0; j < getSizeY(); j++) {
    			double x = r.nextGaussian();
    			short pixel = (short)(mean + std * x + getPixel(i, j));
    			img.setPixel(i,j, pixel);
    		}
    	}
    	return img;
    }
    
    public GreyImage medianFilter(int maskSize) {
        if (maskSize % 2 == 0) {
            throw new IllegalArgumentException("La taille du mask doit être impaire.");
        }

        GreyImage result = new GreyImage(getSizeX(), getSizeY());

        for (int x = 0; x < getSizeX(); x++) {
            for (int y = 0; y < getSizeY(); y++) {
                int[] neighborhood = getNeighborhood(x, y, maskSize);
                int median = calculateMedian(neighborhood);
                result.setPixel(x, y, (short) median);
            }
        }

        return result;
    }

    private int[] getNeighborhood(int x, int y, int maskSize) {
        int[] neighborhood = new int[maskSize * maskSize];
        int index = 0;

        for (int i = -maskSize / 2; i <= maskSize / 2; i++) {
            for (int j = -maskSize / 2; j <= maskSize / 2; j++) {
                int newX = x + i;
                int newY = y + j;

                if (isPosValid(newX, newY)) {
                    neighborhood[index++] = getPixel(newX, newY);
                }
            }
        }

        return Arrays.copyOf(neighborhood, index);
    }

    private int calculateMedian(int[] neighborhood) {
        Arrays.sort(neighborhood);
        int middle = neighborhood.length / 2;

        if (neighborhood.length % 2 == 0) {
            return (neighborhood[middle - 1] + neighborhood[middle]) / 2;
        } else {
            return neighborhood[middle];
        }
    }

    public GreyImage gradient(GreyImage Ix, GreyImage Iy) {
        GreyImage result = new GreyImage(getSizeX(), getSizeY());

        for (int x = 0; x < getSizeX(); x++) {
            for (int y = 0; y < getSizeY(); y++) {
                int gradient = (int) Math.sqrt(Math.pow(Ix.getPixel(x, y), 2) + Math.pow(Iy.getPixel(x, y), 2));
                result.setPixel(x, y, (short) gradient);
            }
        }

        return result;
    }

    public double computeNMSE(GreyImage im) {
        if (getSizeX() != im.getSizeX() || getSizeY() != im.getSizeY()) {
            throw new IllegalArgumentException("Les images doivent avoir les mêmes dimensions");
        }

        double sumSquaredDiff = 0;
        double sumSquaredOriginal = 0;

        for (int x = 0; x < getSizeX(); x++) {
            for (int y = 0; y < getSizeY(); y++) {
                double diff = getPixel(x, y) - im.getPixel(x, y);
                sumSquaredDiff += Math.pow(diff, 2);
                sumSquaredOriginal += Math.pow(getPixel(x, y), 2);
            }
        }

        return sumSquaredDiff / sumSquaredOriginal;
    }

    
    
    public GreyImage erode(Mask B) {
        int p = B.getSizeX() / 2;
        GreyImage img = new GreyImage(getSizeX(), getSizeY());

        for (int i = 0; i < getSizeX(); i++) {
            for (int j = 0; j < getSizeY(); j++) {
            	
                boolean isEroded = true;

                for (int k = 0; k < B.getSizeX(); k++) {
                    for (int l = 0; l < B.getSizeY(); l++) {
                    	
                    	int x = i+k-p;
                    	int y = j+l-p;

                        if (isPosValid(x, y)) {
                            if (B.getPixel(k, l) == 1 && getPixel(x,y) != 255) {
                                isEroded = false;
                                break;
                            }
                        } else {
                            if (B.getPixel(k, l) == 1) {
                                isEroded = false;
                                break;
                            }
                        }
                    }

                    if (!isEroded) {
                        break;
                    }
                }

                img.setPixel(i, j, isEroded ? (short) 255 : 0);
            }
        }
        return img;
    }


    public GreyImage dilate(Mask B) {
        int p = B.getSizeX() / 2;
        GreyImage img = new GreyImage(getSizeX(), getSizeY());

        for (int i = 0; i < getSizeX(); i++) {
            for (int j = 0; j < getSizeY(); j++) {
                boolean isDilated = false;

                for (int k = 0; k < B.getSizeX(); k++) {
                    for (int l = 0; l < B.getSizeY(); l++) {
                        int x = i + k - p;
                        int y = j + l - p;

                        if (isPosValid(x, y)) {
                            if (B.getPixel(k, l) == 1 && getPixel(x, y) == 255) {
                                isDilated = true;
                                break;
                            }
                        }
                    }

                    if (isDilated) {
                        break;
                    }
                }

                img.setPixel(i, j, isDilated ? (short) 255 : 0);
            }
        }

        return img;
    }
    
    public GreyImage open(Mask B) {
    	GreyImage img = this.erode(B).dilate(B);
    	return img;
    	
    }
    public GreyImage close(Mask B) {
    	GreyImage img = this.dilate(B).erode(B);
    	return img;
    	
    }
    
    public GreyImage morphologicalGradient(GreyImage erode, GreyImage dilate) {   	
    	GreyImage img = new GreyImage(getSizeX(), getSizeY());
    	for (int i = 0; i < getSizeX(); i++) {
            for (int j = 0; j < getSizeY(); j++) {
                int diff = dilate.getPixel(i, j) - erode.getPixel(i, j);
                img.setPixel(i, j, (short)diff);
            }
        }
    	return img;
    	
    	
    }

}
