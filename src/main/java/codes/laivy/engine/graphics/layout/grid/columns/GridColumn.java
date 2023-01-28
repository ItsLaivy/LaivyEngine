package codes.laivy.engine.graphics.layout.grid.columns;

import codes.laivy.engine.graphics.layout.grid.GridRow;
import codes.laivy.engine.graphics.layout.grid.GridSize;
import codes.laivy.engine.graphics.layout.grid.columns.configuration.GridColumnConfig;
import codes.laivy.engine.graphics.layout.grid.disposition.GridDisposition;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class GridColumn {

    private final @NotNull GridRow row;
    private @Nullable GridDisposition disposition;

    private @NotNull ColumnBreakpoints breakpoints;

    protected @NotNull Map<@NotNull GridSize, @NotNull Set<GridColumnConfig<?>>> configurations = new LinkedHashMap<>();

    public GridColumn(@NotNull GridRow row, @NotNull ColumnBreakpoints breakpoints) {
        this.row = row;
        this.breakpoints = breakpoints;
        getRow().addColumn(this);
    }

    /**
     * This follows the same {@linkplain ColumnBreakpoints Column breakpoints hierarchy example}.
     *
     * @param size The grid size
     * @return the configurations for that size
     */
    public final @NotNull GridColumnConfig<?>[] getConfigurations(@NotNull GridSize size) {
        System.out.println("Configurations '" + configurations.size() + "'");

        Set<GridColumnConfig<?>> value = configurations.get(size);
        if (value != null) {
            return value.toArray(new GridColumnConfig<?>[0]);
        }

        GridSize next = size.getNext();
        GridSize prev = size.getPrevious();
        while (next != null || prev != null) {
            if (configurations.containsKey(next)) {
                return configurations.get(next).toArray(new GridColumnConfig<?>[0]);
            }
            if (configurations.containsKey(prev)) {
                return configurations.get(prev).toArray(new GridColumnConfig<?>[0]);
            }
            if (next != null) next = next.getNext();
            if (prev != null) prev = prev.getPrevious();
        }

        return new GridColumnConfig<?>[0];
    }
    public void addConfiguration(@NotNull GridSize size, @NotNull GridColumnConfig<?> config) {
        configurations.putIfAbsent(size, new LinkedHashSet<>());
        configurations.get(size).add(config);
    }
    public void removeConfiguration(@NotNull GridSize size, @NotNull GridColumnConfig<?> config) {
        configurations.get(size).remove(config);
        if (configurations.get(size).isEmpty()) {
            configurations.remove(size);
        }
    }

    public @Nullable GridDisposition getDisposition() {
        return disposition;
    }

    public void setDisposition(@Nullable GridDisposition disposition) {
        this.disposition = disposition;
    }

    public @NotNull ColumnBreakpoints getBreakpoints() {
        return breakpoints;
    }
    public void setBreakpoints(@NotNull ColumnBreakpoints breakpoints) {
        this.breakpoints = breakpoints;
    }

    @Contract(pure = true)
    public @NotNull GridRow getRow() {
        return row;
    }

    // Utilities
    public int getColumnLine(@NotNull GridSize size) {
        int rowTotalSpacesUsed = 0;
        for (GridColumn row : getRow().getColumns()) {
            rowTotalSpacesUsed += row.getBreakpoints().getSpacing(size);
            if (row == this) {
                //noinspection IntegerDivisionInFloatingPointContext
                return (int) Math.floor(rowTotalSpacesUsed / getRow().getMaxColumns());
            }
        }
        throw new IllegalStateException("This row doesn't have this column, have you added it?");
    }
    //

}
