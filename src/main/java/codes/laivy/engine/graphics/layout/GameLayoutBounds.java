package codes.laivy.engine.graphics.layout;

import codes.laivy.engine.coordinates.Location;
import codes.laivy.engine.coordinates.dimension.Dimension;
import org.jetbrains.annotations.NotNull;

public class GameLayoutBounds {

    private final @NotNull Location location;
    private final @NotNull Dimension total;
    private final @NotNull Dimension available;

    public GameLayoutBounds(@NotNull Location location, @NotNull Dimension total, @NotNull Dimension available) {
        this.location = location;
        this.total = total;
        this.available = available;
    }

    public @NotNull Location getLocation() {
        return location;
    }

    public @NotNull Dimension getTotal() {
        return total;
    }

    public @NotNull Dimension getAvailable() {
        return available;
    }
}
