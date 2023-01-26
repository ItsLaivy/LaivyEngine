package codes.laivy.engine.graphics.layout.grid.disposition;

import codes.laivy.engine.graphics.components.shape.CircleComponent;
import codes.laivy.engine.graphics.layout.grid.GridLayout;
import codes.laivy.engine.graphics.layout.grid.columns.GridColumn;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class GridCircleDisposition extends GridEllipseDisposition {
    public GridCircleDisposition(@NotNull CircleComponent component, @NotNull GridLayout layout, @NotNull GridColumn column) {
        super(component, layout, column);
    }

    @Override
    @Contract(pure = true)
    public @NotNull CircleComponent getComponent() {
        return (CircleComponent) super.getComponent();
    }
}
