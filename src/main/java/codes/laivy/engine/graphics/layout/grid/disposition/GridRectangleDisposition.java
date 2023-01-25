package codes.laivy.engine.graphics.layout.grid.disposition;

import codes.laivy.engine.graphics.components.GameComponent;
import codes.laivy.engine.graphics.components.shape.RectangleComponent;
import codes.laivy.engine.graphics.layout.grid.GridColumn;
import codes.laivy.engine.graphics.layout.grid.GridLayout;
import org.jetbrains.annotations.NotNull;

public class GridRectangleDisposition extends GridDisposition {
    public GridRectangleDisposition(@NotNull GameComponent component, @NotNull GridLayout layout, @NotNull GridColumn column) {
        super(component, layout, column);

        if (column.getDisposition() != null) {
            throw new IllegalArgumentException("This column already have a disposition!");
        }
        column.setDisposition(this);
    }

    @Override
    public @NotNull RectangleComponent getComponent() {
        return (RectangleComponent) super.getComponent();
    }
}
