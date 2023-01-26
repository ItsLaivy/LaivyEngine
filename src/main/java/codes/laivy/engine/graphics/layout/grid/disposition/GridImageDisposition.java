package codes.laivy.engine.graphics.layout.grid.disposition;

import codes.laivy.engine.coordinates.Location;
import codes.laivy.engine.coordinates.dimension.Dimension;
import codes.laivy.engine.graphics.components.GameComponent;
import codes.laivy.engine.graphics.components.ImageComponent;
import codes.laivy.engine.graphics.layout.GameLayout;
import codes.laivy.engine.graphics.layout.grid.GridLayout;
import codes.laivy.engine.graphics.layout.grid.columns.GridColumn;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class GridImageDisposition extends GridDisposition {
    public GridImageDisposition(@NotNull ImageComponent component, @NotNull GridLayout layout, @NotNull GridColumn column) {
        super(component, layout, column);
    }

    @Override
    @Contract(pure = true)
    public @NotNull ImageComponent getComponent() {
        return (ImageComponent) super.getComponent();
    }

    @Override
    public void drawObject(@NotNull Graphics2D renderingGraphics, @NotNull Location location, @NotNull Dimension dimension) {
        renderingGraphics.drawImage(getComponent().getAsset().toBuffered(), location.getX(), location.getY(), dimension.getWidth(), dimension.getHeight(), getLayout().getWindow().getPanel());
    }

    @Override
    public void alignment(@NotNull Graphics2D renderingGraphics, @NotNull GameComponent.Alignment alignment, @NotNull GameLayout.LayoutCoordinates coords) {
        Dimension dimension = getComponent().getDimension();

        renderingGraphics.transform(alignment.getTransform());
        if (alignment == GameComponent.Alignment.FLIPPED_HORIZONTALLY) {
            coords.getClientLocation().setX(-coords.getClientLocation().getX() - coords.getScreenDimension().getWidth());
        } else if (alignment == GameComponent.Alignment.FLIPPED_VERTICALLY) {
            coords.getClientLocation().setY(-coords.getClientLocation().getY() - coords.getScreenDimension().getHeight());
        } else if (alignment == GameComponent.Alignment.FLIPPED_VERTICALLY_HORIZONTALLY) {
            coords.getClientLocation().setX(-coords.getClientLocation().getX() - coords.getScreenDimension().getWidth());
            coords.getClientLocation().setY(-coords.getClientLocation().getY() - coords.getScreenDimension().getHeight());
        }
    }
}
