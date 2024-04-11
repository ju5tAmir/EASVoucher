package dk.easvoucher.bll;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import net.glxn.qrgen.javase.QRCode;
import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;
import net.sourceforge.barbecue.output.OutputException;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class TicketViewLogic {

    // TODO: Implement Facade design pattern for Barcode, Qrcode, also use a single library

    public Image createQRCode(String text){
        // Creates QRCode for the provided UUID
        ByteArrayOutputStream out = QRCode.from(text).withSize(500, 500).stream();
        Image image = new Image(new ByteArrayInputStream(out.toByteArray()));
        // The default created image has white background, so we need to remove white pixels
        return makeImageTransparent(image);
    }

    public Image createBarCode(String text) throws BarcodeException, OutputException {
        // Creates QRCode for the provided UUID
        Barcode barcode = BarcodeFactory.createCode128(text);
        barcode.setDrawingText(false); // Remove text from barcode
        barcode.setBarHeight(100); // Adjust barcode height if needed
        // Generate barcode image
        ByteArrayOutputStream bb = new ByteArrayOutputStream();
        BarcodeImageHandler.writePNG(barcode, bb);

        Image image = new Image(new ByteArrayInputStream(bb.toByteArray()));
        // The default created image has white background, so we need to remove white pixels
        return makeImageTransparent(image);
    }

    private Image makeImageTransparent(Image originalImage){
        // Create a writable image of the same size
        WritableImage writableImage = new WritableImage(
                (int) originalImage.getWidth(),
                (int) originalImage.getHeight());

        // Get the PixelReader and PixelWriter
        PixelReader pixelReader = originalImage.getPixelReader();
        PixelWriter pixelWriter = writableImage.getPixelWriter();

        // Iterate through all pixels and make white pixels transparent
        for (int y = 0; y < originalImage.getHeight(); y++) {
            for (int x = 0; x < originalImage.getWidth(); x++) {
                // Get the color of the pixel at position (x, y)
                int argb = pixelReader.getArgb(x, y);

                // Extract red, green, blue, and alpha components
                int alpha = (argb >> 24) & 0xFF;
                int red = (argb >> 16) & 0xFF;
                int green = (argb >> 8) & 0xFF;
                int blue = argb & 0xFF;

                // Check if the pixel is white
                if (red == 255 && green == 255 && blue == 255) {
                    // If white, set alpha to 0 (transparent)
                    alpha = 0;
                }

                // Combine the components into a single pixel value
                int newArgb = (alpha << 24) | (red << 16) | (green << 8) | blue;

                // Write the new pixel to the writable image
                pixelWriter.setArgb(x, y, newArgb);

            }
        }
        return writableImage;
    }

}
