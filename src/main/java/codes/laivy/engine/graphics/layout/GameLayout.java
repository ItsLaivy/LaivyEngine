package codes.laivy.engine.graphics.layout;

import codes.laivy.engine.annotations.WindowThread;
import codes.laivy.engine.coordinates.Location;
import codes.laivy.engine.exceptions.LaivyEngineException;
import codes.laivy.engine.exceptions.UnsupportedThreadException;
import codes.laivy.engine.graphics.components.GameComponent;
import codes.laivy.engine.graphics.window.swing.GamePanel;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import codes.laivy.engine.coordinates.dimension.Dimension;

import java.awt.*;

public abstract class GameLayout {

    private final @NotNull GamePanel panel;

    public GameLayout(@NotNull GamePanel panel) {
        this.panel = panel;
    }

    @Contract(pure = true)
    public final @NotNull GamePanel getPanel() {
        return panel;
    }

    @WindowThread
    public synchronized void callLayout(@NotNull Graphics2D graphics, @NotNull GameLayoutBounds bounds) {
        if (!getPanel().getWindow().getGame().getGraphics().isWindowThread()) {
            throw new UnsupportedThreadException("GameWindow");
        }

        try {
            render(graphics, bounds);
        } catch (Exception e) {
            throw new LaivyEngineException(e, "Layout organization");
        }
    }

    /**
     * It's called when the layout needs to be reconfigured. Commonly called a lot of times per second.
     */
    @WindowThread
    protected abstract void render(@NotNull Graphics2D graphics, @NotNull GameLayoutBounds bounds);

    /**
     * On a layout rendering, the screen location will be the {@link GameComponent#getScreenLocation()} and the screen dimensions will be the {@link GameComponent#getScreenDimension()}
     * The client location and dimension will be the location/dimension where will be rendered on the screen of the client
     */
    public static class LayoutCoordinates {

        private @NotNull Location clientLayoutLocation;
        private @NotNull Location screenLayoutLocation;

        private @NotNull Dimension clientDimension;
        private @NotNull Dimension screenDimension;

        public LayoutCoordinates(@NotNull Location location, @NotNull Dimension dimension) {
            this(location, location, dimension, dimension);
        }
        public LayoutCoordinates(@NotNull Location clientLayoutLocation, @NotNull Location screenLayoutLocation, @NotNull Dimension clientDimension, @NotNull Dimension screenDimension) {
            this.clientLayoutLocation = clientLayoutLocation;
            this.screenLayoutLocation = screenLayoutLocation;
            this.clientDimension = clientDimension;
            this.screenDimension = screenDimension;
        }

        public void setLocation(@NotNull Location layoutLocation) {
            this.clientLayoutLocation = layoutLocation;
            this.screenLayoutLocation = layoutLocation;
        }
        public void setDimension(@NotNull Dimension dimension) {
            this.clientDimension = dimension;
            this.screenDimension = dimension;
        }

        public @NotNull Location getClientLocation() {
            return clientLayoutLocation;
        }

        public void setClientLocation(@NotNull Location clientLayoutLocation) {
            this.clientLayoutLocation = clientLayoutLocation;
        }

        public @NotNull Location getScreenLocation() {
            return screenLayoutLocation;
        }

        public void setScreenLocation(@NotNull Location screenLayoutLocation) {
            this.screenLayoutLocation = screenLayoutLocation;
        }

        public @NotNull Dimension getClientDimension() {
            return clientDimension;
        }

        public void setClientDimension(@NotNull Dimension clientDimension) {
            this.clientDimension = clientDimension;
        }

        public @NotNull Dimension getScreenDimension() {
            return screenDimension;
        }

        public void setScreenDimension(@NotNull Dimension screenDimension) {
            this.screenDimension = screenDimension;
        }
    }

}
