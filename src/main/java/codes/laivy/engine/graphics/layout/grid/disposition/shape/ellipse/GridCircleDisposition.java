package codes.laivy.engine.graphics.layout.grid.disposition.shape.ellipse;

import codes.laivy.engine.graphics.components.shape.ellipse.CircleComponent;
import codes.laivy.engine.graphics.layout.grid.columns.GridColumn;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class GridCircleDisposition extends GridEllipseDisposition {
    public GridCircleDisposition(@NotNull CircleComponent component, @NotNull GridColumn column) {
        super(component, column);
    }

    @Override
    @Contract(pure = true)
    public @NotNull CircleComponent getComponent() {
        return (CircleComponent) super.getComponent();
    }
}
