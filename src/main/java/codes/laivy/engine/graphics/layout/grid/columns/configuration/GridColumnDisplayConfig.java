package codes.laivy.engine.graphics.layout.grid.columns.configuration;

import org.jetbrains.annotations.NotNull;

public final class GridColumnDisplayConfig extends GridColumnConfig<GridColumnDisplayConfig.Display> {
    public GridColumnDisplayConfig(@NotNull Display value) {
        super(value);
    }

    public enum Display {
        /**
         * This value cancels any display limitation. It will make the object display normally.
         */
        NONE,

        /**
         * This value blocks the display, with that, the object will not display.
         */
        BLOCK
    }

}
