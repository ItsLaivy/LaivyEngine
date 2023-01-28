package codes.laivy.engine.assets;

import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;

/**
 * @author ItsLaivy
 * @since 1.0 build 0 (~02/07/2022)
 */
public class ImageAsset extends Asset {

    private final @NotNull BufferedImage image;

    public ImageAsset(@NotNull URL url) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(url);
        if (bufferedImage == null) {
            throw new IllegalArgumentException("This URL isn't a image!");
        }

        this.image = bufferedImage;
    }
    public ImageAsset(@NotNull InputStream stream) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(stream);
        if (bufferedImage == null) {
            throw new IllegalArgumentException("This InputStream isn't a image!");
        }

        this.image = bufferedImage;
        stream.close();
    }

    public ImageAsset(@NotNull ResourceFile file) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(file);
        if (bufferedImage == null) {
            throw new IllegalArgumentException("This ResourceFile isn't a image!");
        }

        this.image = bufferedImage;
    }

    public ImageAsset(@NotNull BufferedImage image) {
        this.image = image;
    }

    @Override
    public void dispose() {
        super.dispose();
        getBufferedImage().flush();
    }

    public @NotNull BufferedImage getBufferedImage() {
        return image;
    }

}
