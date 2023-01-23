package codes.laivy.engine.graphics.layout.grid.disposition;

import codes.laivy.engine.graphics.components.GameComponent;
import codes.laivy.engine.graphics.layout.ComponentDisposition;
import codes.laivy.engine.graphics.layout.grid.GridRow;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class GridDisposition extends ComponentDisposition {

    private final @NotNull GridRow row;

    public GridDisposition(@NotNull GameComponent component, @NotNull GridRow row) {
        super(component);
        this.row = row;
    }

    @Contract(pure = true)
    public @NotNull GridRow getRow() {
        return row;
    }
}
