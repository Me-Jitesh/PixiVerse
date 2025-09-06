package com.jitesh.pixiverse.utilities;

import net.coobird.thumbnailator.Thumbnails;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImagePreprocessor {

    // Stability AI allowed sizes
    private static final int[][] ALLOWED_DIMENSIONS = {
            {1024, 1024}, {1152, 896}, {1216, 832}, {1344, 768},
            {1536, 640}, {640, 1536}, {768, 1344}, {832, 1216}, {896, 1152}
    };

    private static int[] getNearestAllowedDimension(int width, int height) {
        int[] nearest = ALLOWED_DIMENSIONS[0];
        double minDiff = Double.MAX_VALUE;

        for (int[] dim : ALLOWED_DIMENSIONS) {
            double diff = Math.pow(width - dim[0], 2) + Math.pow(height - dim[1], 2);
            if (diff < minDiff) {
                minDiff = diff;
                nearest = dim;
            }
        }
        return nearest;
    }

    public static File prepareImage(File inputFile) throws IOException {
        BufferedImage original = ImageIO.read(inputFile);

        int[] bestFit = getNearestAllowedDimension(original.getWidth(), original.getHeight());
        int targetWidth = bestFit[0];
        int targetHeight = bestFit[1];

        BufferedImage resized = Thumbnails.of(original)
                .size(targetWidth, targetHeight)
                .asBufferedImage();

        BufferedImage output = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = output.createGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, targetWidth, targetHeight);

        int x = (targetWidth - resized.getWidth()) / 2;
        int y = (targetHeight - resized.getHeight()) / 2;
        g.drawImage(resized, x, y, null);
        g.dispose();

        File processedFile = File.createTempFile("processed-", ".png");
        ImageIO.write(output, "png", processedFile);

        return processedFile;
    }
}
