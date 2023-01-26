package codes.laivy.engine.graphics.layout.grid;

import codes.laivy.engine.graphics.layout.grid.columns.GridColumn;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * @since 1.0 22/01/2023
 * @author ItsLaivy
 */
public class GridRow {

    private final @NotNull GridLayout layout;
    private final @NotNull Set<@NotNull GridColumn> columns = new LinkedHashSet<>();

    public GridRow(@NotNull GridLayout layout) {
        this.layout = layout;
        getLayout().getRows().add(this);
    }

    public @Range(from = 1, to = Integer.MAX_VALUE) int getMaxColumns() {
        return 12;
    }

    @Contract(value = "-> new", pure = true)
    public @NotNull Set<@NotNull GridColumn> getColumns() {
        return new LinkedHashSet<>(columns);
    }
    public void addColumn(@NotNull GridColumn column) {
        for (Integer point : column.getBreakpoints().getPoints().values()) {
            if (point > getMaxColumns()) {
                throw new IllegalArgumentException("This column contains breakpoints higher than the row maximum columns value: '" + getMaxColumns() + "', illegal value: '" + point + "'");
            }
        }

        if (getColumns().size() >= 12) {
            throw new IllegalStateException("The row couldn't have more than 12 columns.");
        }

        columns.add(column);
    }
    public void removeColumn(@NotNull GridColumn column) {
        columns.remove(column);
    }

    @Contract(pure = true)
    public @NotNull GridLayout getLayout() {
        return layout;
    }

    // Utilities
    public int getTotalSpaces(@NotNull GridSize size) {
        int spaces = 0;
        for (GridColumn column : getColumns()) {
            spaces += column.getBreakpoints().getSpacing(size);
        }
        return spaces;
    }
    public @NotNull GridColumn@NotNull[]@NotNull[] getColumnsWithBreakpoint(@NotNull GridSize size) {
        int count = getMaxColumns();
        int breakpoint = 0;

        Map<Integer, Set<GridColumn>> map = new LinkedHashMap<>();
        for (GridColumn column : getColumns()) {
            count -= column.getBreakpoints().getSpacing(size);
            if (count < 0) {
                count = getMaxColumns() - Math.abs(count);
                breakpoint++;
            }

            map.putIfAbsent(breakpoint, new LinkedHashSet<>());
            map.get(breakpoint).add(column);
        }

        GridColumn[][] array = new GridColumn[map.size()][];
        breakpoint = 0;
        for (Map.Entry<Integer, Set<GridColumn>> entry : map.entrySet()) {
            array[entry.getKey()] = new GridColumn[entry.getValue().size()];

            int columnIndex = 0;
            for (GridColumn column : entry.getValue()) {
                array[entry.getKey()][columnIndex] = column;
                columnIndex++;
            }

            breakpoint++;
        }
        return array;
    }
    //

}
