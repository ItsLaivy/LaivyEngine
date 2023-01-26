package codes.laivy.engine.graphics.layout.grid;

import codes.laivy.engine.coordinates.Location;
import codes.laivy.engine.coordinates.dimension.Dimension;
import codes.laivy.engine.graphics.layout.GameLayout;
import codes.laivy.engine.graphics.layout.grid.columns.GridColumn;
import codes.laivy.engine.graphics.layout.grid.disposition.GridDisposition;
import codes.laivy.engine.graphics.window.GameWindow;
import codes.laivy.engine.utils.MathUtils;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

/**
 * @since 1.0 22/01/2023
 * @author ItsLaivy
 */
public class GridLayout extends GameLayout {

    private final @NotNull List<@NotNull GridRow> rows = new LinkedList<>();

    public GridLayout(@NotNull GameWindow window) {
        super(window);
    }

    @Contract(pure = true)
    public @NotNull List<@NotNull GridRow> getRows() {
        return rows;
    }

    public final @NotNull GridSize getSize() {
        Dimension screen = getWindow().getSize();
        for (GridSize size : GridSize.values()) {
            if (screen.getWidth() >= size.getMinimumWidth() && screen.getWidth() <= size.getMaximumWidth()) {
                return size;
            }
        }
        throw new IllegalArgumentException("Couldn't get grid size for width: '" + screen.getWidth() + "'");
    }

    @Override
    protected void render(@NotNull Graphics2D graphics) {
        int screenWidth = getWindow().getSize().getWidth();
        int screenHeight = getWindow().getAvailableSize().getHeight();


        int totalRows = getRows().size();
        for (GridRow row : getRows()) {
            int rowIndex = getRows().indexOf(row);

            Dimension rowDim = new Dimension(screenWidth, (screenHeight / totalRows));
            Location rowLoc = new Location(0, (screenHeight / totalRows) * rowIndex);

            GridColumn[][] matriz = row.getColumnsWithBreakpoint(getSize());

            int breakpoint = 0;
            for (GridColumn[] columns : matriz) {
                int walkedSpaces = 0;
                for (GridColumn column : columns) {
                    // Component
                    GridDisposition disposition = column.getDisposition();
                    //

                    // Coordinates
                    int columnSpace = column.getBreakpoints().getSpacing(getSize());
                    int columnWidth, columnHeight, columnX, columnY;

                    columnWidth = (rowDim.getWidth() / row.getMaxColumns()) * columnSpace;
                    columnHeight = (rowDim.getHeight() / matriz.length);
                    columnX = rowLoc.getX() + ((screenWidth - rowLoc.getX()) / row.getMaxColumns()) * walkedSpaces;
                    columnY = rowLoc.getY() + (rowDim.getHeight() / matriz.length) * breakpoint;

                    walkedSpaces += columnSpace;
                    //

                    if (disposition != null) {
                        // Rendering Graphics
                        Graphics2D graphics2D = (Graphics2D) graphics.create();
                        disposition.render(graphics2D, new LayoutCoordinates(new Location(columnX + calculateWidthOffset(disposition.getComponent().getOffsetX()), columnY + calculateHeightOffset(disposition.getComponent().getOffsetY())), new Location(columnX, columnY), new Dimension(columnWidth, columnHeight), new Dimension(columnWidth, columnHeight)));
                        graphics2D.dispose();
                        //
                    }
                }
                breakpoint++;
            }
        }
    }

    // ---/-/--- //
    // Utilities //
    // ---/-/--- //
    public final int calculateWidthOffset(int offsetX) {
        if (offsetX != 0) {
            offsetX = (int) MathUtils.rthree(new codes.laivy.engine.coordinates.dimension.Dimension(800, 600).getWidth() + new codes.laivy.engine.coordinates.dimension.Dimension(800, 600).getHeight(), offsetX, getWindow().getAvailableSize().getWidth() + getWindow().getAvailableSize().getHeight());
        }
        return offsetX;
    }
    public final int calculateHeightOffset(int offsetY) {
        if (offsetY != 0) {
            offsetY = (int) MathUtils.rthree(new codes.laivy.engine.coordinates.dimension.Dimension(800, 600).getWidth() + new codes.laivy.engine.coordinates.dimension.Dimension(800, 600).getHeight(), offsetY, getWindow().getAvailableSize().getWidth() + getWindow().getAvailableSize().getHeight());
        }
        return offsetY;
    }
    // ---/-/--- //
    // Utilities //
    // ---/-/--- //
}
