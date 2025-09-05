package com.jitesh.pixiverse.utilities;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageResizer {

    // Allowed SDXL dimensions
    private static final int[][] ALLOWED_DIMENSIONS = {
            {1024, 1024}, {1152, 896}, {1216, 832}, {1344, 768},
            {1536, 640}, {640, 1536}, {768, 1344}, {832, 1216}, {896, 1152}
    };


    public static File resizeToNearestAllowed(File inputFile) throws IOException {
        BufferedImage original = ImageIO.read(inputFile);

        // Find nearest allowed dimension
        int[] nearest = findNearestDimension(original.getWidth(), original.getHeight());

        // Resize
        BufferedImage resized = new BufferedImage(nearest[0], nearest[1], BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = resized.createGraphics();
        g.drawImage(original, 0, 0, nearest[0], nearest[1], null);
        g.dispose();

        // Save resized image
        File outputFile = new File("resized_" + inputFile.getName());
        ImageIO.write(resized, "png", outputFile);

        return outputFile;
    }

    // Finds nearest allowed dimension based on Euclidean distance
    private static int[] findNearestDimension(int width, int height) {
        int[] nearest = ALLOWED_DIMENSIONS[0];
        double minDist = distance(width, height, nearest[0], nearest[1]);

        for (int[] dim : ALLOWED_DIMENSIONS) {
            double dist = distance(width, height, dim[0], dim[1]);
            if (dist < minDist) {
                minDist = dist;
                nearest = dim;
            }
        }
        return nearest;
    }

    private static double distance(int w1, int h1, int w2, int h2) {
        return Math.sqrt(Math.pow(w1 - w2, 2) + Math.pow(h1 - h2, 2));
    }
}