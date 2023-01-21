package codes.laivy.engine.assets;

import codes.laivy.engine.Game;
import codes.laivy.engine.exceptions.LaivyEngineException;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Asset {

    // Name + Extension, Asset - Respectively
    public static final @NotNull Map<@NotNull String, @NotNull Asset> ASSETS = new LinkedHashMap<>();

    protected @Nullable ResourceFile file;
    protected @Nullable BufferedImage bufferedImage;

    public Asset(@NotNull URI uri) throws IOException {
        this(new ResourceFile(uri));
    }
    public Asset(@NotNull ResourceFile file) throws IOException {
        this.file = file;
        bufferedImage = ImageIO.read(file);

        if (ASSETS.containsKey(file.getName())) {
            throw new LaivyEngineException(new IllegalStateException("An asset with that name already is loaded! (Name: " + file.getName() + ", Total assets loaded: \"" + ASSETS.size() + "\")"), "Asset's constructor");
        }
        ASSETS.put(file.getName(), this);
    }

    /**
     * Creating assets with that constructor will not save the asset into the RAM and will not be able to be got using {@link #get(String)}
     */
    public Asset(@NotNull BufferedImage image) {
        this.file = null;
        this.bufferedImage = image;
    }

    /**
     * Unloads the asset from RAM
     *
     * @since 1.0 build 0 (02/07/2022)
     * @author ItsLaivy
     */
    public void unload() {
        BufferedImage buff = toBuffered();
        if (buff != null) buff.flush();

        ASSETS.values().remove(this);
    }

    /**
     * @since 1.0 build 0 (02/07/2022)
     * @author ItsLaivy
     * @return The BufferedImage of the asset or null if the asset isn't an image
     */
    public @Nullable BufferedImage toBuffered() {
        return bufferedImage;
    }

    public @Nullable ResourceFile getFile() {
        return file;
    }

    /**
     * Gets a loaded asset by it name
     *
     * @since 1.0 build 0 (02/07/2022)
     * @author ItsLaivy
     * @param name Name of the loaded asset
     * @return Returns the asset with that name
     */
    public static @NotNull Asset get(@NotNull String name) {
        if (!ASSETS.containsKey(name)) {
            throw new LaivyEngineException(new NullPointerException("Couldn't find a asset with that name"), "Asset#get(String) method");
        }
        return ASSETS.get(name);
    }

}
