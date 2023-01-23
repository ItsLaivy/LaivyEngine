package codes.laivy.engine.graphics.layout.grid;

import codes.laivy.engine.coordinates.dimension.Dimension;
import codes.laivy.engine.graphics.layout.responsive.ResponsiveLayout;
import codes.laivy.engine.graphics.window.GameWindow;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

/**
 * @since 1.0 22/01/2023
 * @author ItsLaivy
 */
public class GridLayout extends ResponsiveLayout {

    private final @NotNull List<@NotNull GridRow> rows = new LinkedList<>();

    public GridLayout(@NotNull GameWindow window, @NotNull Dimension referenceSize) {
        super(window, referenceSize);
    }

    @Contract(pure = true)
    public @NotNull List<@NotNull GridRow> getRows() {
        return rows;
    }

    public final @NotNull GridSize getSize() {
        Dimension screen = getWindow().getSize();
        for (GridSize size : GridSize.values()) {
            if (size.getMinimumWidth() >= screen.getWidth() && size.getMaximumWidth() <= screen.getWidth()) {
                return size;
            }
        }
        throw new IllegalArgumentException("Couldn't get horizontal grid size for width: '" + screen.getWidth() + "'.");
    }

}
