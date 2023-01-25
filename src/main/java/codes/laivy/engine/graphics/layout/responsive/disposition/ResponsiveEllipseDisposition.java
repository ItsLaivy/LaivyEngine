package codes.laivy.engine.graphics.layout.responsive.disposition;

import codes.laivy.engine.coordinates.Location;
import codes.laivy.engine.coordinates.dimension.Dimension;
import codes.laivy.engine.graphics.components.GameComponent;
import codes.laivy.engine.graphics.components.shape.EllipseComponent;
import codes.laivy.engine.graphics.layout.GameLayout;
import codes.laivy.engine.graphics.layout.responsive.ResponsiveLayout;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class ResponsiveEllipseDisposition extends ResponsiveShapeDisposition {
    public ResponsiveEllipseDisposition(@NotNull EllipseComponent component, @NotNull ResponsiveLayout layout) {
        super(component, layout);
    }

    @Override
    @Contract(pure = true)
    public @NotNull EllipseComponent getComponent() {
        return (EllipseComponent) super.getComponent();
    }

    @Override
    public void fill(@NotNull Graphics2D renderingGraphics, @NotNull Location location, @NotNull codes.laivy.engine.coordinates.dimension.Dimension dimension) {
        renderingGraphics.fill(getComponent().getShape(location, dimension));
    }

    @Override
    public void shape(@NotNull Graphics2D renderingGraphics, @NotNull Location location, @NotNull codes.laivy.engine.coordinates.dimension.Dimension dimension) {
        renderingGraphics.draw(getComponent().getShape(location, dimension));
    }

    @Override
    public void renderBackground(@NotNull Graphics2D backgroundGraphics, GameLayout.@NotNull LayoutCoordinates coordinates) {
        codes.laivy.engine.coordinates.dimension.Dimension temp = coordinates.getClientDimension().clone();
        Location location = coordinates.getClientLocation().clone();

        coordinates.setScreenLocation(location);
        coordinates.setScreenDimension(temp);

        Color color = component.getBackground().getFinalColor();
        if (color != null) {
            backgroundGraphics.setColor(color);
            backgroundGraphics.fill(getComponent().getShape(location, temp));
        }
    }

    @Override
    public void alignment(@NotNull Graphics2D renderingGraphics, @NotNull GameComponent.Alignment alignment, @NotNull GameLayout.LayoutCoordinates coords) {
        Dimension dimension = coords.getScreenDimension();

        renderingGraphics.transform(alignment.getTransform());
        if (alignment == GameComponent.Alignment.FLIPPED_HORIZONTALLY) {
            coords.getClientLocation().setX(-coords.getScreenLocation().getX() - dimension.getWidth());
        } else if (alignment == GameComponent.Alignment.FLIPPED_VERTICALLY) {
            coords.getClientLocation().setY(-coords.getScreenLocation().getY() - dimension.getHeight());
        } else if (alignment == GameComponent.Alignment.FLIPPED_VERTICALLY_HORIZONTALLY) {
            coords.getClientLocation().setX(-coords.getScreenLocation().getX() - dimension.getWidth());
            coords.getClientLocation().setY(-coords.getScreenLocation().getY() - dimension.getHeight());
        }
    }
}
