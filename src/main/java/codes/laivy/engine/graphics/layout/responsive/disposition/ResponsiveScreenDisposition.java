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

@ApiStatus.Experimental
public class ResponsiveScreenDisposition extends ResponsiveDisposition {
    public ResponsiveScreenDisposition(@NotNull ScreenComponent component, @NotNull ResponsiveLayout layout) {
        super(component, layout);
    }

    @Override
    public void drawObject(@NotNull Graphics2D renderingGraphics, @NotNull Location location, @NotNull Dimension dimension, @NotNull GameLayoutBounds bounds) {
        if (getComponent().getPanel().getEngineLayout() != null) {
            getComponent().getPanel().getEngineLayout().callLayout(renderingGraphics, new GameLayoutBounds(location.clone(), dimension.clone(), dimension.clone()));
        }
    }

    @Override
    public @NotNull ScreenComponent getComponent() {
        return (ScreenComponent) super.getComponent();
    }
}
