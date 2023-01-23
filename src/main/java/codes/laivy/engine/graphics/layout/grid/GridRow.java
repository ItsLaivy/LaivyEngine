package codes.laivy.engine.graphics.layout.grid;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * A row have 12 columns spaces, a column can have any amount of space (between 1 and 12) it want.
 * @since 1.0 22/01/2023
 * @author ItsLaivy
 */
public class GridRow {

    private final @NotNull GridLayout layout;
    private final @NotNull Set<@NotNull GridColumn> columns = new LinkedHashSet<>();

    public GridRow(@NotNull GridLayout layout) {
        this.layout = layout;
    }

    @Contract(pure = true)
    public @NotNull Set<@NotNull GridColumn> getColumns() {
        return columns;
    }

    @Contract(pure = true)
    public @NotNull GridLayout getLayout() {
        return layout;
    }

}
