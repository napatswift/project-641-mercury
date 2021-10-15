package ku.cs.models.utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.*;
import javax.imageio.stream.*;

public class ImageUtil {
    public static void resizeImage(File image, String destinationFilePath) throws IOException{
        BufferedImage bufferedImage = resizeImage(image);
        ImageWriter writer = ImageIO.getImageWritersByFormatName("jpeg").next();
        ImageWriteParam iwp = writer.getDefaultWriteParam();
        iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        iwp.setCompressionQuality(.8f);

        File file = new File(destinationFilePath);
        FileImageOutputStream output = new FileImageOutputStream(file);
        writer.setOutput(output);
        IIOImage outImage = new IIOImage(bufferedImage, null, null);
        writer.write(null, outImage, null);
        writer.dispose();
    }

    private static BufferedImage resizeImage(File imageFile) throws IOException{
        int MAX_SIZE = 1000;
        BufferedImage originalImage = ImageIO.read(imageFile);
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();

        if (width <= MAX_SIZE && height <= MAX_SIZE) {
            return originalImage;
        }

        int imgHeight = MAX_SIZE;
        int imgWidth = MAX_SIZE;

        if (width > height) {
            imgHeight = (int) (MAX_SIZE * ((double) height / width));
        } else if (height > width) {
            imgWidth = (int) (MAX_SIZE * ((double) width / height));
        }

        BufferedImage resizedImage = new BufferedImage(imgWidth, imgHeight, 1);

        Graphics2D resizedImageGraphics = resizedImage.createGraphics();
        resizedImageGraphics.drawImage(originalImage, 0, 0, imgWidth, imgHeight, null);
        resizedImageGraphics.dispose();
        return resizedImage;
    }
}
