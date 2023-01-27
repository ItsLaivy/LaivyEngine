package codes.laivy.engine.graphics.layout.grid;

import codes.laivy.engine.coordinates.Location;
import codes.laivy.engine.coordinates.dimension.Dimension;
import codes.laivy.engine.graphics.layout.GameLayout;
import codes.laivy.engine.graphics.layout.GameLayoutBounds;
import codes.laivy.engine.graphics.layout.grid.columns.GridColumn;
import codes.laivy.engine.graphics.layout.grid.disposition.GridDisposition;
import codes.laivy.engine.graphics.window.swing.GamePanel;
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

    public GridLayout(@NotNull GamePanel panel) {
        super(panel);
    }

    @Contract(pure = true)
    public @NotNull List<@NotNull GridRow> getRows() {
        return rows;
    }

    public final @NotNull GridSize getSize() {
        Dimension screen = getPanel().getWindow().getSize();
        for (GridSize size : GridSize.values()) {
            if (screen.getWidth() >= size.getMinimumWidth() && screen.getWidth() <= size.getMaximumWidth()) {
                return size;
            }
        }
        throw new IllegalArgumentException("Couldn't get grid size for width: '" + screen.getWidth() + "'");
    }

    @Override
    protected void render(@NotNull Graphics2D graphics, @NotNull GameLayoutBounds bounds) {
        int screenWidth = bounds.getTotal().getWidth();
        int screenHeight = bounds.getAvailable().getHeight();

        int totalRows = getRows().size();

        for (GridRow row : getRows()) {
            int rowIndex = getRows().indexOf(row);

            Dimension rowDim = new Dimension(screenWidth, (screenHeight / totalRows));
            Location rowLoc = new Location(bounds.getLocation().getX(), bounds.getLocation().getY() + (screenHeight / totalRows) * rowIndex);

            GridColumn[][] matriz = row.getColumnsWithBreakpoint(getSize());

            int rowWidth = rowDim.getWidth();
            int rowHeight = rowDim.getHeight();

            int breakpoint = 0;
            for (GridColumn[] columns : matriz) {
                int walkedSpaces = 0;
                for (GridColumn column : columns) {
                    // Component
                    GridDisposition disposition = column.getDisposition();

                    int columnSpace = column.getBreakpoints().getSpacing(getSize());
                    walkedSpaces += columnSpace;

                    if (disposition == null) continue;
                    //

                    // Coordinates
                    int columnWidth, columnHeight, columnX, columnY;

                    columnWidth = (int) Math.ceil((double) (rowWidth * columnSpace) / row.getMaxColumns());
                    columnHeight = (rowHeight / matriz.length);
                    columnX = (int) (rowLoc.getX() + Math.ceil((double) screenWidth * (walkedSpaces - columnSpace) / row.getMaxColumns()));
                    columnY = rowLoc.getY() + (rowHeight / matriz.length) * breakpoint;
                    //

                    // Rendering Graphics
                    Graphics2D graphics2D = (Graphics2D) graphics.create();
                    disposition.render(
                            graphics2D,
                            new LayoutCoordinates(
                                    new Location(columnX + calculateWidthOffset(disposition.getComponent().getOffsetX(), bounds), columnY + calculateHeightOffset(disposition.getComponent().getOffsetY(), bounds)),
                                    new Location(columnX + calculateWidthOffset(disposition.getComponent().getOffsetX(), bounds), columnY + calculateHeightOffset(disposition.getComponent().getOffsetY(), bounds)),
                                    new Dimension(columnWidth, columnHeight),
                                    new Dimension(columnWidth, columnHeight)
                            ),
                            bounds
                    );
                    graphics2D.dispose();
                    //
                }
                breakpoint++;
            }
        }
    }

    // ---/-/--- //
    // Utilities //
    // ---/-/--- //
    public final int calculateWidthOffset(int offsetX, @NotNull GameLayoutBounds bounds) {
        if (offsetX != 0) {
            offsetX = (int) MathUtils.rthree(bounds.getAvailable().getWidth() + new codes.laivy.engine.coordinates.dimension.Dimension(800, 600).getHeight(), offsetX, bounds.getAvailable().getWidth() + bounds.getAvailable().getHeight());
        }
        return offsetX;
    }
    public final int calculateHeightOffset(int offsetY, @NotNull GameLayoutBounds bounds) {
        if (offsetY != 0) {
            offsetY = (int) MathUtils.rthree(bounds.getAvailable().getWidth() + new codes.laivy.engine.coordinates.dimension.Dimension(800, 600).getHeight(), offsetY, bounds.getAvailable().getWidth() + bounds.getAvailable().getHeight());
        }
        return offsetY;
    }
    // ---/-/--- //
    // Utilities //
    // ---/-/--- //
}
