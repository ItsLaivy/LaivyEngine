package codes.laivy.engine.graphics.layout.grid.disposition;

import codes.laivy.engine.coordinates.Location;
import codes.laivy.engine.coordinates.dimension.Dimension;
import codes.laivy.engine.graphics.components.GameComponent;
import codes.laivy.engine.graphics.components.shape.RoundRectangleComponent;
import codes.laivy.engine.graphics.layout.GameLayout;
import codes.laivy.engine.graphics.layout.grid.GridLayout;
import codes.laivy.engine.graphics.layout.grid.columns.GridColumn;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class GridRoundRectangleDisposition extends GridShapeDisposition {
    public GridRoundRectangleDisposition(@NotNull RoundRectangleComponent component, @NotNull GridLayout layout, @NotNull GridColumn column) {
        super(component, layout, column);
    }

    @Override
    public void fill(@NotNull Graphics2D renderingGraphics, @NotNull Location location, @NotNull Dimension dimension) {
        renderingGraphics.fill(shape(location, dimension));
    }

    @Override
    public void shape(@NotNull Graphics2D graphics, @NotNull Location location, @NotNull Dimension dimension) {
        graphics.draw(shape(location, dimension));
    }

    @Override
    public void alignment(@NotNull Graphics2D renderingGraphics, GameComponent.@NotNull Alignment alignment, GameLayout.@NotNull LayoutCoordinates coords) {
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

    private @NotNull RoundRectangle2D.Float shape(@NotNull Location location, @NotNull codes.laivy.engine.coordinates.dimension.Dimension dimension) {
        RoundRectangle2D.Float shape = getComponent().getShape(location, dimension);
        shape.archeight = getLayout().calculateHeightOffset(getComponent().getArc().getHeight());
        shape.arcwidth = getLayout().calculateWidthOffset(getComponent().getArc().getWidth());

        return shape;
    }

    @Override
    @Contract(pure = true)
    public @NotNull RoundRectangleComponent getComponent() {
        return (RoundRectangleComponent) super.getComponent();
    }
}