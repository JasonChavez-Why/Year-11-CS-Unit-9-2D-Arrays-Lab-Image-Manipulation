package code;
import image.APImage;
import image.Pixel;

public class ImageManipulation {

    /** CHALLENGE 0: Display Image
     *  Write a statement that will display the image in a window
     */
    public static void main(String[] args) {
        String file = "cyberpunk2077.jpg";
        rotateImage(file);
        reflectImage(file);
        edgeDetection(file, 15);
        blackAndWhite(file);
        grayScale(file);
        APImage image = new APImage(file);
        image.draw();
    }

    /** CHALLENGE ONE: Grayscale
     *
     * INPUT: the complete path file name of the image
     * OUTPUT: a grayscale copy of the image
     *
     * To convert a colour image to grayscale, we need to visit every pixel in the image ...
     * Calculate the average of the red, green, and blue components of the pixel.
     * Set the red, green, and blue components to this average value. */
    public static void grayScale(String file) {
        APImage image = new APImage(file);
        APImage grayImage = image.clone();
        for (int x = 0; x < grayImage.getWidth(); x++) {
            for (int y = 0; y < grayImage.getHeight(); y++) {
                Pixel pixel = grayImage.getPixel(x, y);
                int average = getAverageColour(pixel);
                pixel.setRed(average);
                pixel.setGreen(average);
                pixel.setBlue(average);
            }
        }
        grayImage.draw();
    }

    /** A helper method that can be used to assist you in each challenge.
     * This method simply calculates the average of the RGB values of a single pixel.
     * @param pixel
     * @return the average RGB value
     */
    private static int getAverageColour(Pixel pixel) {
        int red = pixel.getRed();
        int green = pixel.getGreen();
        int blue = pixel.getBlue();
        return (red + green + blue) / 3;
    }

    /** CHALLENGE TWO: Black and White
     *
     * INPUT: the complete path file name of the image
     * OUTPUT: a black and white copy of the image
     *
     * To convert a colour image to black and white, we need to visit every pixel in the image ...
     * Calculate the average of the red, green, and blue components of the pixel.
     * If the average is less than 128, set the pixel to black
     * If the average is equal to or greater than 128, set the pixel to white */
    public static void blackAndWhite(String file) {
        APImage image = new APImage(file);
        APImage bwImage = image.clone();
        for (int x = 0; x < bwImage.getWidth(); x++) {
            for (int y = 0; y < bwImage.getHeight(); y++) {
                Pixel pixel = bwImage.getPixel(x, y);
                int average = getAverageColour(pixel);
                if (average < 128) {
                    pixel.setRed(0);
                    pixel.setGreen(0);
                    pixel.setBlue(0);
                } else {
                    pixel.setRed(255);
                    pixel.setGreen(255);
                    pixel.setBlue(255);
                }
                bwImage.setPixel(x, y, pixel);
            }
        }
        bwImage.draw();
    }

    /** CHALLENGE Three: Edge Detection
     *
     * INPUT: the complete path file name of the image
     * OUTPUT: an outline of the image. The amount of information will correspond to the threshold.
     *
     * Edge detection is an image processing technique for finding the boundaries of objects within images.
     * It works by detecting discontinuities in brightness. Edge detection is used for image segmentation
     * and data extraction in areas such as image processing, computer vision, and machine vision.
     *
     * There are many different edge detection algorithms. We will use a basic edge detection technique
     * For each pixel, we will calculate ...
     * 1. The average colour value of the current pixel
     * 2. The average colour value of the pixel to the left of the current pixel
     * 3. The average colour value of the pixel below the current pixel
     * If the difference between 1. and 2. OR if the difference between 1. and 3. is greater than some threshold value,
     * we will set the current pixel to black. This is because an absolute difference that is greater than our threshold
     * value should indicate an edge and thus, we colour the pixel black.
     * Otherwise, we will set the current pixel to white
     * NOTE: We want to be able to apply edge detection using various thresholds
     * For example, we could apply edge detection to an image using a threshold of 20 OR we could apply
     * edge detection to an image using a threshold of 35
     *  */
    public static void edgeDetection(String file, int threshold) {
        APImage image = new APImage(file);
        APImage edgeImage = image.clone();
        for (int x = 0; x < edgeImage.getWidth(); x++) {
            for (int y = 0; y < edgeImage.getHeight(); y++) {
                Pixel currentPixel = edgeImage.getPixel(x, y);
                int currentAvg = getAverageColour(currentPixel);
                int leftAvg;
                if (x > 0) {
                    Pixel leftPixel = edgeImage.getPixel(x - 1, y);
                    leftAvg = getAverageColour(leftPixel);
                } else {
                    leftAvg = currentAvg;
                }
                int belowAvg;
                if (y < edgeImage.getHeight() - 1) {
                    Pixel belowPixel = edgeImage.getPixel(x, y + 1);
                    belowAvg = getAverageColour(belowPixel);
                } else {
                    belowAvg = currentAvg;
                }
                if (Math.abs(currentAvg - leftAvg) < threshold ||
                        Math.abs(currentAvg - belowAvg) < threshold) {
                    currentPixel.setRed(255);
                    currentPixel.setGreen(255);
                    currentPixel.setBlue(255);
                } else {
                    currentPixel.setRed(0);
                    currentPixel.setGreen(0);
                    currentPixel.setBlue(0);
                }
                edgeImage.setPixel(x, y, currentPixel);
            }
        }
        edgeImage.draw();
    }

    /** CHALLENGE Four: Reflect Image
     *
     * INPUT: the complete path file name of the image
     * OUTPUT: the image reflected about the y-axis
     *
     */
    public static void reflectImage(String file) {
        APImage image = new APImage(file);
        APImage reflectedImage = image.clone();
        int width = reflectedImage.getWidth();
        int height = reflectedImage.getHeight();
        for (int x = 0; x < width / 2; x++) {
            for (int y = 0; y < height; y++) {
                Pixel leftPixel = reflectedImage.getPixel(x, y);
                Pixel rightPixel = reflectedImage.getPixel(width - 1 - x, y);
                reflectedImage.setPixel(x, y, rightPixel.clone());
                reflectedImage.setPixel(width - 1 - x, y, leftPixel.clone());
            }
        }
        reflectedImage.draw();
    }

    /** CHALLENGE Five: Rotate Image
     *
     * INPUT: the complete path file name of the image
     * OUTPUT: the image rotated 90 degrees CLOCKWISE
     *
     *  */
    public static void rotateImage(String file) {
        APImage image = new APImage(file);
        int originalWidth = image.getWidth();
        int originalHeight = image.getHeight();
        APImage rotatedImage = new APImage(originalHeight, originalWidth);
        for (int x = 0; x < originalWidth; x++) {
            for (int y = 0; y < originalHeight; y++) {
                Pixel originalPixel = image.getPixel(x, y);
                int newX = originalHeight - 1 - y;
                int newY = x;
                rotatedImage.setPixel(newX, newY, originalPixel.clone());
            }
        }
        rotatedImage.draw();
    }

}
