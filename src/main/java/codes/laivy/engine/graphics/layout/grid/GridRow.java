package codes.laivy.engine.graphics.layout.grid;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @since 1.0 22/01/2023
 * @author ItsLaivy
 */
public class GridRow {

    private @Range(from = 1, to = Integer.MAX_VALUE) int maxColumns = 12;

    private final @NotNull GridLayout layout;
    private final @NotNull Set<@NotNull GridColumn> columns = new LinkedHashSet<>();

    public GridRow(@NotNull GridLayout layout) {
        this.layout = layout;
        getLayout().getRows().add(this);
    }

    public @Range(from = 1, to = Integer.MAX_VALUE) int getMaxColumns() {
        return maxColumns;
    }
    public void setMaxColumns(@Range(from = 1, to = Integer.MAX_VALUE) int maxColumns) {
        this.maxColumns = maxColumns;
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
