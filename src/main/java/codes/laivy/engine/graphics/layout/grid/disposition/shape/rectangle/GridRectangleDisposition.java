package codes.laivy.engine.graphics.layout.grid.disposition.shape.rectangle;

import codes.laivy.engine.coordinates.Location;
import codes.laivy.engine.coordinates.dimension.Dimension;
import codes.laivy.engine.graphics.components.GameComponent;
import codes.laivy.engine.graphics.components.shape.rectangle.RectangleComponent;
import codes.laivy.engine.graphics.layout.GameLayout;
import codes.laivy.engine.graphics.layout.grid.columns.GridColumn;
import codes.laivy.engine.graphics.layout.grid.disposition.shape.GridShapeDisposition;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class GridRectangleDisposition extends GridShapeDisposition {
    public GridRectangleDisposition(@NotNull RectangleComponent component, @NotNull GridColumn column) {
        super(component, column);
    }

    @Override
    public void fill(@NotNull Graphics2D renderingGraphics, @NotNull Location location, @NotNull Dimension dimension, @NotNull GameLayout.Bounds bounds) {
        renderingGraphics.fill(getComponent().getShape(location, dimension));
    }

    @Override
    public void shape(@NotNull Graphics2D graphics, @NotNull Location location, @NotNull Dimension dimension, @NotNull GameLayout.Bounds bounds) {
        graphics.draw(getComponent().getShape(location, dimension));
    }

    @Override
    public void alignment(@NotNull Graphics2D renderingGraphics, GameComponent.@NotNull Alignment alignment, GameLayout.@NotNull Coordinates coords, @NotNull GameLayout.Bounds bounds) {
        renderingGraphics.transform(alignment.getTransform());
        if (alignment == GameComponent.Alignment.FLIPPED_HORIZONTALLY) {
            coords.getClientLocation().setX(-coords.getClientLocation().getX() - coords.getClientDimension().getWidth());
        } else if (alignment == GameComponent.Alignment.FLIPPED_VERTICALLY) {
            coords.getClientLocation().setY(-coords.getClientLocation().getY() - coords.getClientDimension().getHeight());
        } else if (alignment == GameComponent.Alignment.FLIPPED_VERTICALLY_HORIZONTALLY) {
            coords.getClientLocation().setX(-coords.getClientLocation().getX() - coords.getClientDimension().getWidth());
            coords.getClientLocation().setY(-coords.getClientLocation().getY() - coords.getClientDimension().getHeight());
        }
    }

    @Override
    @Contract(pure = true)
    public @NotNull RectangleComponent getComponent() {
        return (RectangleComponent) super.getComponent();
    }
}
