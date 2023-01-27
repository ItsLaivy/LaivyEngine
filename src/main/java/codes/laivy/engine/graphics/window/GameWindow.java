package codes.laivy.engine.graphics.window;

import codes.laivy.engine.Game;
import codes.laivy.engine.annotations.WindowThread;
import codes.laivy.engine.coordinates.Location;
import codes.laivy.engine.coordinates.MouseLocation;
import codes.laivy.engine.coordinates.dimension.Dimension;
import codes.laivy.engine.exceptions.LaivyEngineException;
import codes.laivy.engine.exceptions.UnsupportedThreadException;
import codes.laivy.engine.graphics.components.GameComponent;
import codes.laivy.engine.graphics.layout.GameLayout;
import codes.laivy.engine.graphics.window.swing.GameFrame;
import codes.laivy.engine.graphics.window.swing.GamePanel;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class GameWindow {

    private final @NotNull Game game;

    protected @NotNull GameFrame frame;
    protected @NotNull GamePanel panel;

    public GameWindow(@NotNull Game game) {
        this.game = game;

        this.frame = new GameFrame(this);
        this.panel = new GamePanel(this);

        getFrame().add(getPanel());
        getFrame().setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setSize(new Dimension(800, 600));
        getFrame().setTitle(null);

        getFrame().setLocationRelativeTo(null);
        getPanel().setBackground(Color.BLACK);
        getFrame().setUndecorated(false);
    }

    @Contract(pure = true)
    public @NotNull Game getGame() {
        return game;
    }

    public @NotNull GameFrame getFrame() {
        return frame;
    }

    public void setFrame(@NotNull GameFrame frame) {
        this.frame = frame;
    }

    public @NotNull GamePanel getPanel() {
        return panel;
    }

    @WindowThread
    public void setPanel(@NotNull GamePanel panel) {
        if (!getPanel().getWindow().getGame().getGraphics().isWindowThread()) {
            throw new UnsupportedThreadException("GameWindow");
        }

        getFrame().remove(getPanel());
        getPanel().clear();
        this.panel = panel;
        getFrame().add(getPanel());
    }

    @Contract("-> new")
    public @NotNull MouseLocation getMouseLocation() {
        Point p = getPanel().getMousePosition();
        Location loc = null;
        if (p != null) {
            loc = new Location(p);
        }

        return new MouseLocation(getPanel(), loc);
    }

    public void setBackground(@NotNull Color color) {
        panel.setBackground(color);
    }
    @NotNull
    public Color getBackground() {
        return panel.getBackground();
    }

    public void setMinimumSize(@NotNull Dimension dimension) {
        getFrame().setMinimumSize(new Dimension(dimension.getWidth(), dimension.getHeight()).toSwing());
    }
    @NotNull
    @Contract("-> new")
    public Dimension getMinimumSize() {
        java.awt.Dimension d = getFrame().getMinimumSize();
        return new Dimension(d.width, d.height);
    }

    public void setMaximumSize(@Nullable Dimension dimension) {
        if (dimension != null) {
            getFrame().setMaximumSize(new Dimension(dimension.getWidth(), dimension.getHeight()).toSwing());
        } else {
            getFrame().setMaximumSize(null);
        }
    }
    @NotNull
    @Contract("-> new")
    public Dimension getMaximumSize() {
        java.awt.Dimension d = getFrame().getMaximumSize();
        return new Dimension(d.width, d.height);
    }

    public void setSize(@NotNull Dimension dimension) {
        getFrame().setSize(new Dimension(dimension.getWidth(), dimension.getHeight()).toSwing());
    }

    @Contract("-> new")
    public @NotNull Dimension getSize() {
        java.awt.Dimension size = getFrame().getSize();
        return new Dimension(size.width, size.height);
    }

    /**
     * This method returns the available panel dimension (without border, close button, ect...)
     * @return The available size, without borders.
     */
    @Contract("-> new")
    public @NotNull Dimension getAvailableSize() {
        if (!getFrame().isVisible()) {
            throw new LaivyEngineException(new IllegalStateException("The window needs to be visible to perform that method"), "GameWindow#getAvailableSize() method");
        }

        return new Dimension(getFrame().getContentPane().getWidth(), getFrame().getContentPane().getHeight());
    }

    public interface Components extends Iterable<GameComponent> {

        @WindowThread
        @NotNull Map<@NotNull Location, @NotNull Set<@NotNull GameComponent>> map();

        @WindowThread
        boolean contains(@NotNull GameComponent component);

        @WindowThread
        void add(@NotNull GameComponent component);

        @WindowThread
        void remove(@NotNull GameComponent component);

        @WindowThread
        int size();

        @WindowThread
        void clear();

    }

}
