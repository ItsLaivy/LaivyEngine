package codes.laivy.engine.assets;

import codes.laivy.engine.exceptions.LaivyEngineException;
import codes.laivy.engine.exceptions.ResourceNotFoundException;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Objects;

public class ImageAsset extends Asset {

    public ImageAsset(@NotNull InputStream stream) throws IOException {
        super(stream);
        if (super.toBuffered() == null) {
            throw new LaivyEngineException(new ResourceNotFoundException("This path file isn't an image"), "ImageAsset's constructor");
        }
    }
    public ImageAsset(@NotNull ResourceFile file) throws IOException {
        super(file);
        if (super.toBuffered() == null) {
            throw new LaivyEngineException(new ResourceNotFoundException("This path file isn't an image"), "ImageAsset's constructor");
        }
    }
    public ImageAsset(@NotNull BufferedImage image) {
        super(image);
        if (super.toBuffered() == null) {
            throw new LaivyEngineException(new ResourceNotFoundException("This path file isn't an image"), "ImageAsset's constructor");
        }
    }

    @Override
    public @NotNull BufferedImage toBuffered() {
        return Objects.requireNonNull(super.toBuffered());
    }

    @ApiStatus.Experimental
    public static @NotNull ImageAsset getImage(@NotNull String name) {
        Asset asset = get(name);
        if (asset instanceof ImageAsset) {
            return (ImageAsset) asset;
        }
        throw new LaivyEngineException(new IllegalArgumentException("This asset isn't a ImageAsset"), "ImageAsset's retrieve");
    }

}
