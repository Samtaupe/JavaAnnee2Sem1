package traitementImageTp1;

import org.junit.Test;
import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class GreyImageTest {

    @Test
    public void testConstructorWithDimensions() {
        GreyImage image = new GreyImage(1, 1);
        assertEquals(1, image.getSizeX());
        assertEquals(1, image.getSizeY());
        assertEquals(1, image.getSizeData());
        assertEquals(0, image.getPixel(0));
    }

    @Test
    public void testConstructorWithDimensionsAndData() {
        short[] inputData = {10, 20, 30, 40};
        GreyImage image = new GreyImage(2, 2, inputData);
        assertEquals(2, image.getSizeX());
        assertEquals(2, image.getSizeY());
        assertEquals(4, image.getSizeData());
        assertEquals(10, image.getPixel(0, 0));
        assertEquals(40, image.getPixel(1, 1));
    }

    @Test
    public void testCopyConstructor() {
        short[] inputData = {5, 15, 25, 35};
        GreyImage original = new GreyImage(2, 2, inputData);
        GreyImage copy = new GreyImage(original);

        assertEquals(original.getSizeX(), copy.getSizeX());
        assertEquals(original.getSizeY(), copy.getSizeY());
        assertEquals(original.getSizeData(), copy.getSizeData());

        for (int i = 0; i < original.getSizeData(); i++) {
            assertEquals(original.getPixel(i), copy.getPixel(i));
        }
    }

    @Test
    public void testSetAndGetPixel() {
        GreyImage image = new GreyImage(2, 2);
        image.setPixel(1, 0, (short) 50);
        assertEquals(50, image.getPixel(1, 0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidPosition() {
        GreyImage image = new GreyImage(3, 3);
        image.getPixel(5, 2); // This should throw an IllegalArgumentException
    }

    @Test
    public void testNegative() {
        short[] inputData = {10, 20, 30, 40};
        GreyImage image = new GreyImage(2, 2, inputData);
        image.negative();
        assertEquals(30, image.getPixel(0));
        assertEquals(20, image.getPixel(1));
        assertEquals(10, image.getPixel(2));
        assertEquals(0, image.getPixel(3));        

    }

    @Test
    public void testSeuiller() {
        short[] inputData = {10, 20, 30, 40};
        GreyImage image = new GreyImage(2, 2, inputData);
        image.seuiller((short) 25);
        assertEquals(0, image.getPixel(0));
        assertEquals(0, image.getPixel(1));
        assertEquals(255, image.getPixel(2));
        assertEquals(255, image.getPixel(3));
    }

    @Test
    public void testTruncate() {
        short[] inputData = {10, 20, 30, 40};
        GreyImage image = new GreyImage(2, 2, inputData);
        image.truncate((short) 15, (short) 35);
        assertEquals(15, image.getPixel(0));
        assertEquals(20, image.getPixel(1));
        assertEquals(30, image.getPixel(2));
        assertEquals(35, image.getPixel(3));
    }
}
