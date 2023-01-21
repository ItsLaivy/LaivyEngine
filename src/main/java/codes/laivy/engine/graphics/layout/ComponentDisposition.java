package codes.laivy.engine.graphics.layout;

import codes.laivy.engine.graphics.components.GameComponent;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public abstract class ComponentDisposition {

    protected final @NotNull GameComponent component;

    public ComponentDisposition(@NotNull GameComponent component) {
        this.component = component;
    }

    /**
     * That's the general render method for the disposition, that will be responsible to insert the component on the screen, configure the screen dimensions/locations, and every other configuration.
     * @param graphics the graphics, is only that you need :)
     */
    public abstract void render(@NotNull Graphics2D graphics);

    public @NotNull GameComponent getComponent() {
        return component;
    }

}
