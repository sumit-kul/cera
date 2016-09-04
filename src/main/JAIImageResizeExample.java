package main;


import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import javax.media.jai.Interpolation;
import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;
import javax.media.jai.RenderedOp;
import javax.media.jai.operator.ScaleDescriptor;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

/**
 * JAI Bicubic Image Resize Example
 */
public class JAIImageResizeExample {
    
    public static void main(String[] args) {
        try {
            PlanarImage image = JAI.create("fileload", "C:\\Users\\IBM_ADMIN\\Pictures\\test.png");
            int destWidth = 1000;
            float xScale = (float) destWidth / image.getWidth();
            float yScale = 0f;
            if (args.length == 3) {
                int destHeight = Integer.parseInt(args[2]);
                yScale = (float)destHeight / (float)image.getHeight();
            } else {
                yScale = xScale;
            }
            RenderedOp renderedOp = ScaleDescriptor.create(image, new Float(xScale), new Float(yScale),
                    new Float(0.0f), new Float(0.0f), Interpolation.getInstance(Interpolation.INTERP_BICUBIC), null);
            writeJPEG(renderedOp.getAsBufferedImage(), "C:\\output.jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeJPEG(BufferedImage input, String name) throws IOException {
        Iterator iter =
                ImageIO.getImageWritersByFormatName("JPG");
        if (iter.hasNext()) {
            ImageWriter writer = (ImageWriter) iter.next();
            ImageWriteParam iwp =
                    writer.getDefaultWriteParam();
            iwp.setCompressionMode(
                    ImageWriteParam.MODE_EXPLICIT);
            iwp.setCompressionQuality(0.95f);
            File outFile = new File(name);
            FileImageOutputStream output =
                    new FileImageOutputStream(outFile);
            writer.setOutput(output);
            IIOImage image =
                    new IIOImage(input, null, null);
            writer.write(null, image, iwp);
            output.close();
        }
    }
}


