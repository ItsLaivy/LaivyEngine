package codes.laivy.engine.graphics.layout.responsive.disposition;

import codes.laivy.engine.coordinates.Location;
import codes.laivy.engine.coordinates.dimension.Dimension;
import codes.laivy.engine.graphics.components.GameComponent;
import codes.laivy.engine.graphics.components.ScreenComponent;
import codes.laivy.engine.graphics.layout.GameLayout;
import codes.laivy.engine.graphics.layout.GameLayoutBounds;
import codes.laivy.engine.graphics.layout.responsive.ResponsiveLayout;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Objects;

@ApiStatus.Experimental
public class ResponsiveScreenDisposition extends ResponsiveDisposition {
    public ResponsiveScreenDisposition(@NotNull ScreenComponent component, @NotNull ResponsiveLayout layout) {
        super(component, layout);

        if (layout.getPanel().equals(getComponent().getPanel())) {
            throw new IllegalArgumentException("The ScreenComponent's panel cannot be the same as it panel!");
        }
    }

    @Override
    public void drawObject(@NotNull Graphics2D renderingGraphics, @NotNull Location location, @NotNull Dimension dimension, @NotNull GameLayoutBounds bounds) {
        if (getComponent().getPanel().getEngineLayout() != null) {
            getComponent().getPanel().getEngineLayout().callLayout(renderingGraphics, new GameLayoutBounds(location.clone(), dimension.clone(), dimension.clone()));
        }
    }

    @Override
    public void alignment(@NotNull Graphics2D renderingGraphics, @NotNull GameComponent.Alignment alignment, @NotNull GameLayout.LayoutCoordinates coords, @NotNull GameLayoutBounds bounds) {
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

    @Override
    public @NotNull ScreenComponent getComponent() {
        return (ScreenComponent) super.getComponent();
    }
}
