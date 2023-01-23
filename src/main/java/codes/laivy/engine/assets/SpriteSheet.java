package codes.laivy.engine.assets;

import codes.laivy.engine.exceptions.LaivyEngineException;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.awt.image.BufferedImage;
import java.lang.annotation.Documented;

public class SpriteSheet {

    private final @NotNull ImageAsset asset;
    private final @NotNull ImageAsset[][] sprites;

    public SpriteSheet(@NotNull ImageAsset asset, @Range(from = 1, to = Integer.MAX_VALUE) int rows, @Range(from = 1, to = Integer.MAX_VALUE) int columns, @Range(from = 1, to = Integer.MAX_VALUE) int width, @Range(from = 1, to = Integer.MAX_VALUE) int height) {
        try {
            this.asset = asset;

            sprites = new ImageAsset[rows][columns];
            BufferedImage image = asset.toBuffered();

            for (int row = 0; row < rows; row++) {
                for (int column = 0; column < columns; column++) {
                    sprites[row][column] = new ImageAsset(image.getSubimage(column * width, row * height, width, height));
                }
            }
        } catch (Exception e) {
            throw new LaivyEngineException(e, "SpriteSheet's constructor");
        }
    }

    @Contract(pure = true)
    public @NotNull ImageAsset getSprite(@Range(from = 0, to = Integer.MAX_VALUE) int row, @Range(from = 0, to = Integer.MAX_VALUE) int column) {
        return sprites[row][column];
    }

    @Contract(pure = true)
    public @NotNull ImageAsset getAsset() {
        return asset;
    }
}
