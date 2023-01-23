package codes.laivy.engine.graphics.layout;

import codes.laivy.engine.graphics.components.GameComponent;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public abstract class ComponentDisposition {

    protected final @NotNull GameComponent component;

    public ComponentDisposition(@NotNull GameComponent component) {
        this.component = component;
    }

    @Contract(pure = true)
    public @NotNull GameComponent getComponent() {
        return component;
    }

}
