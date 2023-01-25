package codes.laivy.engine.graphics.layout.grid;

import codes.laivy.engine.coordinates.Location;
import codes.laivy.engine.coordinates.dimension.Dimension;
import codes.laivy.engine.graphics.components.GameComponent;
import codes.laivy.engine.graphics.layout.GameLayout;
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
        int width = getWindow().getAvailableSize().getWidth();
        int height = getWindow().getAvailableSize().getHeight();

        for (GridRow row : getRows()) {
            int rowTotalSpace = row.getMaxColumns();
            for (GridColumn column : row.getColumns()) {
                if (column.getDisposition() != null) {
                    GameComponent component = column.getDisposition().getComponent();
                    int columnWidth, columnHeight, columnX, columnY;

                    int referenceX = 800;
                    int referenceY = 600;

                    float aspectRatio = component.getDimension().getWidth() / component.getDimension().getHeight();

                    columnWidth = (int) MathUtils.rthree(referenceX, component.getDimension().getWidth(), width);
                    columnHeight = (int) (columnWidth / aspectRatio);
                    columnX = (int) MathUtils.rthree(referenceX, component.getLocation().getX(), width);
                    columnY = (int) MathUtils.rthree(referenceY, component.getLocation().getY(), height);

                    Graphics2D renderingGraphics = (Graphics2D) graphics.create();
                    renderingGraphics.setColor(component.getColor());
                    column.getDisposition().render(renderingGraphics, new LayoutCoordinates(new Location(columnX, columnY), new Dimension(columnWidth, columnHeight)));
                    renderingGraphics.dispose();
                } else {
                    throw new NullPointerException("Couldn't find a disposition for that column.");
                }
            }
        }
    }
}
