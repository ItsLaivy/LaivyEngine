package codes.laivy.engine.graphics.layout.grid.columns.configuration;

import org.jetbrains.annotations.NotNull;

public abstract class GridColumnConfig<T> {

    private @NotNull T value;

    public GridColumnConfig(@NotNull T value) {
        this.value = value;
    }

    public @NotNull T get() {
        return value;
    }

    public void set(@NotNull T value) {
        this.value = value;
    }

}
