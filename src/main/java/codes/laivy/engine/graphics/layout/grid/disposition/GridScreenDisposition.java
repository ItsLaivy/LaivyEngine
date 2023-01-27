package codes.laivy.engine.graphics.layout.grid.disposition;

import codes.laivy.engine.coordinates.Location;
import codes.laivy.engine.coordinates.dimension.Dimension;
import codes.laivy.engine.graphics.components.ScreenComponent;
import codes.laivy.engine.graphics.layout.GameLayoutBounds;
import codes.laivy.engine.graphics.layout.grid.columns.GridColumn;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class GridScreenDisposition extends GridDisposition {
    public GridScreenDisposition(@NotNull ScreenComponent component, @NotNull GridColumn column) {
        super(component, column.getRow().getLayout(), column);
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
