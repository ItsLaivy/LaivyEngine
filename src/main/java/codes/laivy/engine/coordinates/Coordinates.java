package codes.laivy.engine.coordinates;

import codes.laivy.engine.coordinates.dimension.Dimension;
import codes.laivy.engine.graphics.window.swing.GamePanel;
import org.jetbrains.annotations.NotNull;

/**
 * A {@link Dimension} contains the location, dimension and the panel of an object.
 *
 * @author ItsLaivy
 * @since 1.0 build 0 (28/01/2023)
 */
public final class Coordinates {

    private final @NotNull GamePanel panel;
    private final @NotNull Location location;
    private final @NotNull Dimension dimension;

    public Coordinates(@NotNull GamePanel panel, @NotNull Location location, @NotNull Dimension dimension) {
        this.panel = panel;
        this.location = location;
        this.dimension = dimension;
    }

    public @NotNull GamePanel getPanel() {
        return panel;
    }

    public @NotNull Location getLocation() {
        return location;
    }

    public @NotNull Dimension getDimension() {
        return dimension;
    }
}
