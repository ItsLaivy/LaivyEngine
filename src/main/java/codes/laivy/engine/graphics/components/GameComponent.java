package codes.laivy.engine.graphics.components;

import codes.laivy.engine.annotations.WindowThread;
import codes.laivy.engine.coordinates.Location;
import codes.laivy.engine.coordinates.dimension.Dimension;
import codes.laivy.engine.coordinates.dimension.Rectangle;
import codes.laivy.engine.exceptions.LaivyEngineException;
import codes.laivy.engine.exceptions.UnsupportedThreadException;
import codes.laivy.engine.graphics.layout.ComponentDisposition;
import codes.laivy.engine.graphics.window.swing.GamePanel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Objects;

public abstract class GameComponent implements Cloneable {

    private final @NotNull GamePanel panel;

    private @NotNull Location location;

    protected @Nullable Location screenLocation;
    protected @Nullable Dimension screenDimension;
    protected @NotNull Dimension dimension;

    private int offsetX;
    private int offsetY;

    private boolean visible = true;

    private @NotNull GameComponent.Alignment align;

    private @Nullable Color color;
    private @Range(from = 0, to = 100) int opacity;

    protected @NotNull Background background;

    private @Nullable ComponentDisposition disposition;

    public GameComponent(@NotNull GamePanel panel, @NotNull Location location) {
        this(panel, location, 0,0, 100);
    }
    public GameComponent(@NotNull GamePanel panel, @NotNull Location location, int offsetX, int offsetY, @Range(from = 0, to = 100) int opacity) {
        this.location = location;
        this.opacity = opacity;

        this.offsetX = offsetX;
        this.offsetY = offsetY;

        background = new Background(null, 100);

        this.panel = panel;
        this.align = Alignment.NORMAL;

        dimension = new Dimension(0, 0);
    }

    /**
     * @param location the location
     * @return returns true if the location collides with the component
     */
    @WindowThread
    public boolean collides(@NotNull Location location) {
        if (isAtScreen()) {
            return getHitBox().contains(location);
        }
        return false;
    }

    @WindowThread
    public boolean isAdded() {
        if (!getPanel().getWindow().getGame().getGraphics().isWindowThread()) {
            throw new UnsupportedThreadException("GameWindow");
        }

        return getPanel().getEngineComponents().contains(this);
    }
    @WindowThread
    public void add() {
        if (!getPanel().getWindow().getGame().getGraphics().isWindowThread()) {
            throw new UnsupportedThreadException("GameWindow");
        }

        getPanel().getEngineComponents().add(this);
    }
    @WindowThread
    public void remove() {
        if (!getPanel().getWindow().getGame().getGraphics().isWindowThread()) {
            throw new UnsupportedThreadException("GameWindow");
        }

        getPanel().getEngineComponents().remove(this);
    }

    /**
     * The {@link ComponentDisposition} of a component is the configuration of the layout. Some layouts needs extra information to render some components.
     * @return The disposition of that component
     */
    public @Nullable ComponentDisposition getDisposition() {
        return disposition;
    }
    public void setDisposition(@Nullable ComponentDisposition disposition) {
        this.disposition = disposition;
    }

    public @NotNull Background getBackground() {
        return background;
    }

    public boolean isVisible() {
        return visible;
    }
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public @Nullable Color getColor() {
        return color;
    }
    public void setColor(@Nullable Color color) {
        this.color = color;
    }

    public @NotNull GameComponent.Alignment getAlign() {
        return align;
    }
    public void setAlign(@NotNull GameComponent.Alignment align) {
        this.align = align;
    }

    public int getOffsetX() {
        return offsetX;
    }
    public void setOffsetX(int offsetX) {
        this.offsetX = offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }
    public void setOffsetY(int offsetY) {
        this.offsetY = offsetY;
    }

    public @NotNull GamePanel getPanel() {
        return panel;
    }

    public @Range(from = 0, to = 100) int getOpacity() {
        return opacity;
    }
    public void setOpacity(@Range(from = 0, to = 100) int opacity) {
        this.opacity = opacity;
    }

    public @NotNull Location getLocation() {
        return location;
    }
    public void setLocation(@NotNull Location location) {
        this.location = location;
    }

    @WindowThread
    public @Nullable Location getScreenLocation() {
        if (!getPanel().getWindow().getGame().getGraphics().isWindowThread()) {
            throw new UnsupportedThreadException("GameWindow");
        }

        return screenLocation;
    }

    @WindowThread
    public void setScreenLocation(@Nullable Location screenLocation) {
        if (!getPanel().getWindow().getGame().getGraphics().isWindowThread()) {
            throw new UnsupportedThreadException("GameWindow");
        }

        this.screenLocation = screenLocation;
    }

    @WindowThread
    public @Nullable Dimension getScreenDimension() {
        if (!getPanel().getWindow().getGame().getGraphics().isWindowThread()) {
            throw new UnsupportedThreadException("GameWindow");
        }

        return screenDimension;
    }

    @WindowThread
    public void setScreenDimension(@Nullable Dimension screenDimension) {
        if (!getPanel().getWindow().getGame().getGraphics().isWindowThread()) {
            throw new UnsupportedThreadException("GameWindow");
        }
        
        this.screenDimension = screenDimension;
    }

    @WindowThread
    public boolean isAtScreen() {
        if (!getPanel().getWindow().getGame().getGraphics().isWindowThread()) {
            throw new UnsupportedThreadException("GameWindow");
        }

        return getScreenLocation() != null && getScreenDimension() != null;
    }

    @WindowThread
    public @NotNull Rectangle getHitBox() {
        if (!getPanel().getWindow().getGame().getGraphics().isWindowThread()) {
            throw new UnsupportedThreadException("GameWindow");
        }

        if (isAtScreen()) {
            return new Rectangle(Objects.requireNonNull(getScreenLocation()), Objects.requireNonNull(getScreenDimension()));
        }
        throw new LaivyEngineException(new IllegalStateException("The component's hitbox doesn't exists at that screen"), "Gets the hitbox of a component on the screen");
    }

    @WindowThread
    public @NotNull Dimension getDimension() {
        if (!getPanel().getWindow().getGame().getGraphics().isWindowThread()) {
            throw new UnsupportedThreadException("GameWindow");
        }

        return dimension;
    }
    
    @WindowThread
    public void setDimension(@NotNull Dimension dimension) {
        if (!getPanel().getWindow().getGame().getGraphics().isWindowThread()) {
            throw new UnsupportedThreadException("GameWindow");
        }
        
        this.dimension = dimension;
    }

    @Override
    public @NotNull GameComponent clone() {
        try {
            GameComponent clone = (GameComponent) super.clone();
            clone.setLocation(clone.getLocation().clone());
            clone.dimension = clone.dimension.clone();

            clone.screenLocation = null;
            clone.screenDimension = null;

            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public enum Alignment {
        NORMAL(new AffineTransform()),
        FLIPPED_VERTICALLY(AffineTransform.getScaleInstance(1, -1)),
        FLIPPED_HORIZONTALLY(AffineTransform.getScaleInstance(-1, 1)),
        FLIPPED_VERTICALLY_HORIZONTALLY(AffineTransform.getScaleInstance(-1, -1)),
        ;

        private final @NotNull AffineTransform transform;

        Alignment(@NotNull AffineTransform transform) {
            this.transform = transform;
        }

        public @NotNull AffineTransform getTransform() {
            return transform;
        }
    }

    public static final class Background {

        private @Nullable Color color;
        private @Range(from = 0, to = 100) int opacity;

        public Background(@Nullable Color color, @Range(from = 0, to = 100) int opacity) {
            this.color = color;
            this.opacity = opacity;
        }

        public @Nullable Color getColor() {
            return color;
        }
        public void setColor(@Nullable Color color) {
            this.color = color;
        }

        public @Range(from = 0, to = 100) int getOpacity() {
            return opacity;
        }
        public void setOpacity(@Range(from = 0, to = 100) int opacity) {
            this.opacity = opacity;
        }

        /**
         * @return the final background color with the opacity applied
         */
        public @Nullable Color getFinalColor() {
            if (getColor() != null) {
                return new Color(getColor().getRed() / 255F, getColor().getGreen() / 255F, getColor().getBlue() / 255F, getOpacity() / 100F);
            } else {
                return null;
            }
        }

    }

}
