package codes.laivy.engine.graphics.layout;

import codes.laivy.engine.exceptions.LaivyEngineException;
import codes.laivy.engine.graphics.window.GameWindow;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public abstract class GameLayout {

    private final @NotNull GameWindow window;

    public GameLayout(@NotNull GameWindow window) {
        this.window = window;
    }

    public final @NotNull GameWindow getWindow() {
        return window;
    }

    public void callLayout(@NotNull Graphics2D graphics) {
        try {
            render(graphics);
        } catch (Exception e) {
            throw new LaivyEngineException(e, "Layout organization");
        }
    }

    /**
     * It's called when the layout needs to be reconfigured. Commonly called a lot of times per second.
     */
    protected abstract void render(@NotNull Graphics2D graphics);

}
