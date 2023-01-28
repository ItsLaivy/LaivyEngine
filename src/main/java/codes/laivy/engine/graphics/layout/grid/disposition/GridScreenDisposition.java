package codes.laivy.engine.graphics.layout.grid.disposition;

import codes.laivy.engine.coordinates.Location;
import codes.laivy.engine.coordinates.dimension.Dimension;
import codes.laivy.engine.graphics.components.ScreenComponent;
import codes.laivy.engine.graphics.layout.GameLayout;
import codes.laivy.engine.graphics.layout.grid.columns.GridColumn;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Objects;

public class GridScreenDisposition extends GridDisposition {
    public GridScreenDisposition(@NotNull ScreenComponent component, @NotNull GridColumn column) {
        super(component, column.getRow().getLayout(), column);

        if (!Objects.equals(getComponent().getGamePanel().getLayout(), getLayout())) {
            throw new IllegalArgumentException("This game component's panel layout isn't the same as the disposition layout!");
        }
    }

    @Override
    public void drawObject(@NotNull Graphics2D renderingGraphics, @NotNull Location location, @NotNull Dimension dimension, @NotNull GameLayout.Bounds bounds) {
        if (getComponent().getPanel().getLayout() != null) {
            getComponent().getPanel().getLayout().callLayout(renderingGraphics, new GameLayout.Bounds(location.clone(), dimension.clone(), dimension.clone()));
        }
    }

    @Override
    public @NotNull ScreenComponent getComponent() {
        return (ScreenComponent) super.getComponent();
    }
}
