package codes.laivy.engine.assets;

import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class ImageAsset extends Asset {

    private final @NotNull BufferedImage image;

    public ImageAsset(@NotNull InputStream stream) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(stream);
        if (bufferedImage == null) {
            throw new IllegalArgumentException("This ResourceFile isn't a image!");
        }

        this.image = bufferedImage;
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
