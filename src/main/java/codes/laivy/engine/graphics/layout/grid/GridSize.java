package codes.laivy.engine.graphics.layout.grid;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

public enum GridSize {

    XS(0, 575),
    SM(576, 767),
    MD(768, 991),
    LG(992, 1199),
    XL(1200, 1399),
    XXL(1400, Integer.MAX_VALUE),
    ;

    private final @Range(from = 0, to = Integer.MAX_VALUE) int minimumWidth;
    private final @Range(from = 0, to = Integer.MAX_VALUE) int maximumWidth;

    GridSize(@Range(from = 0, to = Integer.MAX_VALUE) int minimumWidth, @Range(from = 0, to = Integer.MAX_VALUE) int maximumWidth) {
        this.minimumWidth = minimumWidth;
        this.maximumWidth = maximumWidth;
    }

    @Contract(pure = true)
    public @Range(from = 0, to = Integer.MAX_VALUE) int getMinimumWidth() {
        return minimumWidth;
    }

    @Contract(pure = true)
    public @Range(from = 1, to = Integer.MAX_VALUE) int getMaximumWidth() {
        return maximumWidth;
    }

    @Contract(pure = true)
    public @Nullable GridSize getPrevious() {
        int ordinal = this.ordinal();
        return ordinal > 0 ? GridSize.values()[ordinal - 1] : null;
    }

    @Contract(pure = true)
    public @Nullable GridSize getNext() {
        int ordinal = this.ordinal();
        GridSize[] values = GridSize.values();
        return ordinal < values.length - 1 ? values[ordinal + 1] : null;
    }

    public static @NotNull GridSize getLowerGridSize() {
        return XS;
    }
    public static @NotNull GridSize getHigherGridSize() {
        return XXL;
    }

}
