package codes.laivy.engine.graphics.layout.grid.columns;

import codes.laivy.engine.coordinates.Coordinates;
import codes.laivy.engine.graphics.layout.grid.GridRow;
import codes.laivy.engine.graphics.layout.grid.GridSize;
import codes.laivy.engine.graphics.layout.grid.columns.configuration.GridColumnConfig;
import codes.laivy.engine.graphics.layout.grid.columns.configuration.GridColumnDisplayConfig;
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

    private @Nullable Coordinates screenCoordinates;

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
        Set<GridColumnConfig<?>> value = configurations.get(size);
        if (value != null) {
            return value.toArray(new GridColumnConfig<?>[0]);
        }

        GridSize next = size.getNext();
        GridSize prev = size.getPrevious();
        while (next != null || prev != null) {
            if (next != null && configurations.containsKey(next)) {
                return configurations.get(next).toArray(new GridColumnConfig<?>[0]);
            }
            if (prev != null && configurations.containsKey(prev)) {
                return configurations.get(prev).toArray(new GridColumnConfig<?>[0]);
            }
            if (next != null) next = next.getNext();
            if (prev != null) prev = prev.getPrevious();
        }

        return new GridColumnConfig<?>[0];
    }

    /**
     * This is the screen coordinates. A column with a {@link GridColumnDisplayConfig.Display#BLOCK} display will never be on the screen, It will never have a screen coordinates then.
     *
     * @return the screen coordinates or null if the column isn't displaying
     * @author ItsLaivy
     * @since 1.0 build 0 (28/01/2023)
     */
    public @Nullable Coordinates getScreenCoordinates() {
        return screenCoordinates;
    }

    /**
     * @author ItsLaivy
     * @since 1.0 build 0 (28/01/2023)
     *
     * @param screenCoordinates The screen coordinates
     */
    public void setScreenCoordinates(@Nullable Coordinates screenCoordinates) {
        this.screenCoordinates = screenCoordinates;
    }

    /**
     * Adds the configuration for all grid sizes
     * @param config the configuration
     */
    public void addConfiguration(@NotNull GridColumnConfig<?> config) {
        this.addConfiguration(GridSize.getHigherGridSize(), config);
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

    /**
     * Checks if the column is able to display
     * @return true if the column is able to display
     */
    public boolean canDisplay(@NotNull GridSize size) {
        for (GridColumnConfig<?> config : getConfigurations(size)) {
            if (config instanceof GridColumnDisplayConfig) {
                return ((GridColumnDisplayConfig) config).get() == GridColumnDisplayConfig.Display.NONE;
            }
        }
        return true;
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
