package view;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageLoader {

    /**
     * @param path to image
     * @return BufferedImage
     */
    public static BufferedImage loadImage(String path) {
    	System.out.println(path);
        try {
            return ImageIO.read(new File(path));

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }
}