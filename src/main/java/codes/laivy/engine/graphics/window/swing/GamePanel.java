package codes.laivy.engine.graphics.window.swing;

import codes.laivy.engine.annotations.WindowThread;
import codes.laivy.engine.coordinates.Location;
import codes.laivy.engine.exceptions.UnsupportedThreadException;
import codes.laivy.engine.graphics.components.GameComponent;
import codes.laivy.engine.graphics.layout.GameLayout;
import codes.laivy.engine.graphics.window.DefaultGameComponents;
import codes.laivy.engine.graphics.window.GameWindow;
import codes.laivy.engine.graphics.window.listeners.GameKeyManager;
import codes.laivy.engine.graphics.window.listeners.GameMouseManager;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class GamePanel extends Component {

    private final @NotNull GameWindow window;

    protected @NotNull GameWindow.Components components;

    private @NotNull GameMouseManager mouseManager;
    private @NotNull GameKeyManager keyManager;

    private @Nullable GameLayout layout;

    public GamePanel(@NotNull GameWindow window) {
        this(window, null);
    }
    public GamePanel(@NotNull GameWindow window, @Nullable GameLayout layout) {
        this.window = window;
        this.components = new DefaultGameComponents(this);

        this.layout = layout;

        mouseManager = new GameMouseManager(this);
        keyManager = new GameKeyManager(this);
    }

    public @Nullable GameLayout getLayout() {
        return layout;
    }
    public void setLayout(@Nullable GameLayout layout) {
        this.layout = layout;
    }

    /**
     * Removes all components from the panel (will be applied only on the next rendering)
     */
    @WindowThread
    public void clear() {
        if (!getWindow().getGame().getGraphics().isWindowThread()) {
            throw new UnsupportedThreadException("GameWindow");
        }

        getComponents().clear();
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
    public @NotNull GameWindow.Components getComponents() {
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

        Map<Location, Set<GameComponent>> components = getComponents().map();
        for (Map.Entry<Location, Set<GameComponent>> map : components.entrySet()) {
            for (GameComponent component : map.getValue()) {
                if (component.isAtScreen() && component.collides(location)) {
                    set.addAll(component.getComponent(location));
                }
            }
        }

        return set;
    }

    @Override
    public void paint(Graphics graphics) {
        getWindow().getGame().render();

        super.paint(graphics);

        // Panel Graphics
        Graphics2D panelGraphics = (Graphics2D) graphics.create();
        panelGraphics.setColor(getBackground());
        panelGraphics.fill(new Rectangle(new Location(0, 0).toPoint(), window.getAvailableSize().toSwing()));
        panelGraphics.dispose();
        // Now it's the layout time :)
        if (getWindow().getPanel().getLayout() != null && getWindow().getFrame().isVisible()) {
            getWindow().getPanel().getLayout().callLayout((Graphics2D) graphics, new GameLayout.Bounds(new Location(0, 0), window.getSize(), window.getAvailableSize()));
        }

        graphics.dispose();
    }
    
}
