package codes.laivy.engine.graphics.components;

import codes.laivy.engine.assets.ImageAsset;
import codes.laivy.engine.coordinates.Location;
import codes.laivy.engine.coordinates.dimension.Dimension;
import codes.laivy.engine.graphics.window.swing.GamePanel;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ImageComponent extends GameComponent {

    private @NotNull ImageAsset asset;

    public ImageComponent(@NotNull GamePanel panel, @NotNull Location location, @NotNull ImageAsset asset) {
        this(panel, location, asset, null, 0, 0, 100);
    }
    public ImageComponent(@NotNull GamePanel panel, @NotNull Location location, @NotNull ImageAsset asset, @Nullable Dimension defaultDimension, int offsetX, int offsetY, int opacity) {
        super(panel, location, offsetX, offsetY, opacity);

        this.asset = asset;

        // Define o valor da dimensão padrão
        Dimension dimension = defaultDimension;
        if (dimension == null) {
            dimension = new Dimension(asset.getBufferedImage().getWidth(null), asset.getBufferedImage().getHeight(null));
        }
        this.dimension = dimension;
        // Define o valor da dimensão padrão
    }

    public @NotNull ImageAsset getAsset() {
        return asset;
    }
    public void setAsset(@NotNull ImageAsset asset) {
        this.asset = asset;
    }

    @Override
    @Contract("-> new")
    public @NotNull ImageComponent clone() {
        return (ImageComponent) super.clone();
    }

}
