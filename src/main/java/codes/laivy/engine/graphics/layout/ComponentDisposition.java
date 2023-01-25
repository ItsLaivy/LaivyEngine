package codes.laivy.engine.graphics.layout;

import codes.laivy.engine.graphics.components.GameComponent;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Objects;

public abstract class ComponentDisposition<T extends GameLayout> {

    protected final @NotNull GameComponent component;
    private final @NotNull T layout;

    public ComponentDisposition(@NotNull GameComponent component, @NotNull T layout) {
        this.component = component;
        this.layout = layout;

        if (!Objects.equals(this.component.getPanel().getWindow().getLayout(), layout)) {
            throw new IllegalArgumentException("This game component's panel layout isn't the same as the disposition layout!");
        }
    }

    @Contract(pure = true)
    public @NotNull GameComponent getComponent() {
        return component;
    }

    @Contract(pure = true)
    public @NotNull T getLayout() {
        return layout;
    }

}
