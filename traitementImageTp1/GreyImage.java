package traitementImageTp1;

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
}
