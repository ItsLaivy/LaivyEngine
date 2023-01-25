package codes.laivy.engine.graphics.layout.grid.disposition;

import codes.laivy.engine.graphics.components.GameComponent;
import codes.laivy.engine.graphics.layout.ComponentDisposition;
import codes.laivy.engine.graphics.layout.GameLayout;
import codes.laivy.engine.graphics.layout.grid.GridColumn;
import codes.laivy.engine.graphics.layout.grid.GridLayout;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public abstract class GridDisposition extends ComponentDisposition<GridLayout> {

    private final @NotNull GridColumn column;

    public GridDisposition(@NotNull GameComponent component, @NotNull GridLayout layout, @NotNull GridColumn column) {
        super(component, layout);
        this.column = column;
    }

    @Contract(pure = true)
    public @NotNull GridColumn getColumn() {
        return column;
    }

    public void render(@NotNull Graphics2D graphics, @NotNull GameLayout.LayoutCoordinates coordinates) {
        graphics.draw(new Rectangle(coordinates.getClientLocation().toPoint(), coordinates.getClientDimension().toSwing()));
        System.out.println(coordinates.getClientLocation() + " - " + coordinates.getClientDimension());
    }

    @Contract(pure = true)
    public @NotNull GridColumn getRow() {
        return column;
    }
}
