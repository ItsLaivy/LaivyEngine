package codes.laivy.engine.graphics.layout.grid.columns;

import codes.laivy.engine.graphics.layout.grid.GridRow;
import codes.laivy.engine.graphics.layout.grid.GridSize;
import codes.laivy.engine.graphics.layout.grid.disposition.GridDisposition;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedList;

public class GridColumn {

    private final @NotNull GridRow row;
    private @Nullable GridDisposition disposition;

    private @NotNull ColumnBreakpoints breakpoints;

    public GridColumn(@NotNull GridRow row, @NotNull ColumnBreakpoints breakpoints) {
        this.row = row;
        this.breakpoints = breakpoints;
        getRow().addColumn(this);
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
