package codes.laivy.engine.assets;

import codes.laivy.engine.exceptions.LaivyEngineException;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author ItsLaivy
 * @since 1.0 build 0 (~03/07/2023)
 */
public class SpriteSheetAsset extends Asset {

    private final @NotNull ImageAsset asset;
    private final @NotNull ImageAsset[][] sprites;

    public SpriteSheetAsset(
            @NotNull ImageAsset asset,

            @Range(from = 1, to = Integer.MAX_VALUE) int rows,
            @Range(from = 1, to = Integer.MAX_VALUE) int columns,
            @Range(from = 1, to = Integer.MAX_VALUE) int width,
            @Range(from = 1, to = Integer.MAX_VALUE) int height
    ) {
        try {
            this.asset = asset;

            sprites = new ImageAsset[rows][columns];
            BufferedImage image = asset.getBufferedImage();

            for (int row = 0; row < rows; row++) {
                for (int column = 0; column < columns; column++) {
                    sprites[row][column] = new ImageAsset(image.getSubimage(column * width, row * height, width, height));
                }
            }
        } catch (Exception e) {
            throw new LaivyEngineException(e, "SpriteSheetAsset's constructor");
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        getAsset().dispose();
        for (ImageAsset sprite : getTotalSprites()) {
            sprite.dispose();
        }
    }

    @Contract(pure = true)
    public @NotNull ImageAsset getSprite(@Range(from = 0, to = Integer.MAX_VALUE) int row, @Range(from = 0, to = Integer.MAX_VALUE) int column) {
        return sprites[row][column];
    }

    /**
     * This gives the matriz of sprites
     * @return the sprites matriz
     *
     * @author ItsLaivy
     * @since 1.0 build 0 (28/01/2023)
     */
    public @NotNull ImageAsset[][] getSprites() {
        return sprites;
    }

    /**
     * This gives a single array of all the sprites (in order)
     * @return all sprites
     *
     * @author ItsLaivy
     * @since 1.0 build 0 (28/01/2023)
     */
    public @NotNull ImageAsset[] getTotalSprites() {
        Set<ImageAsset> images = new LinkedHashSet<>();

        for (@NotNull ImageAsset[] assets : getSprites()) {
            images.addAll(Arrays.asList(assets));
        }

        return images.toArray(new ImageAsset[0]);
    }

    @Contract(pure = true)
    public @NotNull ImageAsset getAsset() {
        return asset;
    }
}
