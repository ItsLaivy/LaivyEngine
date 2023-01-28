package codes.laivy.engine.assets;

import org.jetbrains.annotations.NotNull;

import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * The main assets class
 *
 * @author ItsLaivy
 * @since 1.0 build 0 (28/01/2023), Originally written on 02/07/2022.
 */
public abstract class Asset {

    // Name + Format, Asset - Respectively
    private static final @NotNull List<@NotNull Asset> assets = new LinkedList<>();

    /**
     * Gets the original list of all the loaded assets
     * @return the loaded assets list
     *
     * @author ItsLaivy
     * @since 1.0 build 0 (28/01/2023)
     */
    public static @NotNull List<@NotNull Asset> getAssets() {
        return assets;
    }

    // ---/-/--- //

    protected Asset() {
    }

    /**
     * Unloads the asset from RAM
     *
     * @since 1.0 build 0 (02/07/2022)
     * @author ItsLaivy
     */
    public void dispose() {
        assets.remove(this);
    }

}
