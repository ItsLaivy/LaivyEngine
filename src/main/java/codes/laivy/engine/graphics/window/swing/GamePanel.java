package codes.laivy.engine.graphics.window.swing;

import codes.laivy.engine.annotations.WindowThread;
import codes.laivy.engine.coordinates.Location;
import codes.laivy.engine.exceptions.UnsupportedThreadException;
import codes.laivy.engine.graphics.components.GameComponent;
import codes.laivy.engine.graphics.window.DefaultGameComponents;
import codes.laivy.engine.graphics.window.GameWindow;
import codes.laivy.engine.graphics.window.listeners.GameKeyManager;
import codes.laivy.engine.graphics.window.listeners.GameMouseManager;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class GamePanel extends JPanel {

    private final @NotNull GameWindow window;

    protected @NotNull GameWindow.Components components;

    private @NotNull GameMouseManager mouseManager;
    private @NotNull GameKeyManager keyManager;

    public GamePanel(@NotNull GameWindow window) {
        this.window = window;
        this.components = new DefaultGameComponents(this);

        mouseManager = new GameMouseManager(this);
        keyManager = new GameKeyManager(this);
    }

    /**
     * Removes all components from the panel (will be applied only on the next rendering)
     */
    @WindowThread
    public void clear() {
        if (!getWindow().getGame().getGraphics().isWindowThread()) {
            throw new UnsupportedThreadException("GameWindow");
        }

        getEngineComponents().clear();
    }

    @Contract(pure = true)
    public @NotNull GameWindow getWindow() {
        return window;
    }

    public @NotNull GameMouseManager getMouseManager() {
        return mouseManager;
    }

    public void setMouseManager(@NotNull GameMouseManager mouseManager) {
        this.mouseManager = mouseManager;
    }

    public @NotNull GameKeyManager getKeyManager() {
        return keyManager;
    }

    public void setKeyManager(@NotNull GameKeyManager keyManager) {
        this.keyManager = keyManager;
    }

    @WindowThread
    public @NotNull GameWindow.Components getEngineComponents() {
        if (!getWindow().getGame().getGraphics().isWindowThread()) {
            throw new UnsupportedThreadException("GameWindow");
        }

        return components;
    }
    @WindowThread
    public @NotNull Set<@NotNull GameComponent> getComponents(@NotNull Location location) {
        if (!getWindow().getGame().getGraphics().isWindowThread()) {
            throw new UnsupportedThreadException("GameWindow");
        }

        Set<GameComponent> set = new LinkedHashSet<>();

        Map<Location, Set<GameComponent>> components = getEngineComponents().map();
        for (Map.Entry<Location, Set<GameComponent>> map : components.entrySet()) {
            for (GameComponent component : map.getValue()) {
                if (component.isAtScreen() && component.collides(location)) {
                    set.add(component);
                }
            }
        }

        return set;
    }

    @Override
    public void paint(Graphics g) {
        getWindow().getGame().render();

        super.paint(g);

        // The layout will do the work :)
        if (getWindow().getLayout() != null && getWindow().getFrame().isVisible()) {
            getWindow().getLayout().callLayout((Graphics2D) g);
        }

        g.dispose();
    }
    
}
