package codes.laivy.engine.graphics.layout;

import codes.laivy.engine.graphics.components.GameComponent;
import org.jetbrains.annotations.NotNull;

public abstract class ComponentDisposition {

    protected final @NotNull GameComponent component;

    public ComponentDisposition(@NotNull GameComponent component) {
        this.component = component;
    }

    public @NotNull GameComponent getComponent() {
        return component;
    }
}
