package codes.laivy.engine.graphics.window;

import codes.laivy.engine.annotations.WindowThread;
import codes.laivy.engine.coordinates.Location;
import codes.laivy.engine.exceptions.UnsupportedThreadException;
import codes.laivy.engine.graphics.components.GameComponent;
import codes.laivy.engine.graphics.window.swing.GamePanel;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.BiFunction;

public class DefaultGameComponents implements GameWindow.Components {

    private final @NotNull Map<@NotNull Location, @NotNull Set<@NotNull GameComponent>> components = new LinkedHashMap<>();

    private final @NotNull GamePanel panel;

    public DefaultGameComponents(@NotNull GamePanel panel) {
        this.panel = panel;
    }

    public @NotNull GamePanel getPanel() {
        return panel;
    }
    public @NotNull GameWindow getWindow() {
        return getPanel().getWindow();
    }

    @Override
    @WindowThread
    public @NotNull Map<@NotNull Location, @NotNull Set<@NotNull GameComponent>> map() {
        if (!getWindow().getGame().getGraphics().isWindowThread()) {
            throw new UnsupportedThreadException("GameWindow");
        }

        return new LinkedHashMap<Location, Set<GameComponent>>(components) {
            @Override
            public Set<GameComponent> put(Location key, Set<GameComponent> value) {
                throw new UnsupportedOperationException("Unsupported operation");
            }
            @Override
            public Set<GameComponent> putIfAbsent(Location key, Set<GameComponent> value) {
                throw new UnsupportedOperationException("Unsupported operation");
            }
            @Override
            public void putAll(Map<? extends Location, ? extends Set<GameComponent>> m) {
                throw new UnsupportedOperationException("Unsupported operation");
            }

            @Override
            public Set<GameComponent> remove(Object key) {
                throw new UnsupportedOperationException("Unsupported operation");
            }
            @Override
            public boolean remove(Object key, Object value) {
                throw new UnsupportedOperationException("Unsupported operation");
            }

            @Override
            public boolean replace(Location key, Set<GameComponent> oldValue, Set<GameComponent> newValue) {
                throw new UnsupportedOperationException("Unsupported operation");
            }
            @Override
            public Set<GameComponent> replace(Location key, Set<GameComponent> value) {
                throw new UnsupportedOperationException("Unsupported operation");
            }
            @Override
            public void replaceAll(BiFunction<? super Location, ? super Set<GameComponent>, ? extends Set<GameComponent>> function) {
                throw new UnsupportedOperationException("Unsupported operation");
            }
        };
    }

    @Override
    @WindowThread
    public boolean contains(@NotNull GameComponent component) {
        if (!getWindow().getGame().getGraphics().isWindowThread()) {
            throw new UnsupportedThreadException("GameWindow");
        }

        Location l = component.getLocation();
        return components.containsKey(l) && components.get(l).contains(component);
    }

    @Override
    @WindowThread
    public void add(@NotNull GameComponent component) {
        if (!component.getPanel().equals(getPanel())) {
            throw new IllegalArgumentException("This component's panel isn't the same as the Components panel");
        }

        Location location = component.getLocation();

        if (!components.containsKey(location)) {
            components.put(location, new LinkedHashSet<>());
        }
        components.get(location).add(component);
    }

    @Override
    @WindowThread
    public void remove(@NotNull GameComponent component) {
        if (!getWindow().getGame().getGraphics().isWindowThread()) {
            throw new UnsupportedThreadException("GameWindow");
        }

        Location location = component.getLocation();

        if (components.containsKey(location)) {
            components.get(location).remove(component);
            if (components.get(location).size() == 0) components.remove(location);
        }

        component.setScreenDimension(null);
        component.setScreenLocation(null);
    }

    @Override
    public int size() {
        if (!getWindow().getGame().getGraphics().isWindowThread()) {
            throw new UnsupportedThreadException("GameWindow");
        }

        return set().size();
    }

    @NotNull
    @Override
    @WindowThread
    public synchronized Iterator<GameComponent> iterator() {
        if (!getWindow().getGame().getGraphics().isWindowThread()) {
            throw new UnsupportedThreadException("GameWindow");
        }

        return set().iterator();
    }

    @Override
    @WindowThread
    public synchronized void clear() {
        if (!getWindow().getGame().getGraphics().isWindowThread()) {
            throw new UnsupportedThreadException("GameWindow");
        }

        for (GameComponent component : this) {
            component.remove();
        }
        components.clear();
    }

    @WindowThread
    private @NotNull Set<@NotNull GameComponent> set() {
        if (!getWindow().getGame().getGraphics().isWindowThread()) {
            throw new UnsupportedThreadException("GameWindow");
        }

        Set<GameComponent> list = new LinkedHashSet<>();
        for (@NotNull Set<@NotNull GameComponent> set : components.values()) {
            list.addAll(set);
        }
        return list;
    }

}
