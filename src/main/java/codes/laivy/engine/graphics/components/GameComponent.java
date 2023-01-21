package codes.laivy.engine.graphics.components;

import codes.laivy.engine.Game;
import codes.laivy.engine.annotations.WindowThread;
import codes.laivy.engine.coordinates.Location;
import codes.laivy.engine.coordinates.dimension.Dimension;
import codes.laivy.engine.coordinates.dimension.Rectangle;
import codes.laivy.engine.exceptions.LaivyEngineException;
import codes.laivy.engine.exceptions.UnsupportedThreadException;
import codes.laivy.engine.graphics.layout.ComponentDisposition;
import codes.laivy.engine.graphics.window.GameWindow;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@ApiStatus.Experimental
public abstract class GameComponent implements Cloneable {

    private final @NotNull Game game;

    private @NotNull Location location;

    protected final @NotNull Map<@NotNull GameWindow, @NotNull Location> screenLocation = new LinkedHashMap<>();
    protected final @NotNull Map<@NotNull GameWindow, @NotNull Dimension> screenDimension = new LinkedHashMap<>();

    protected @NotNull Map<@Nullable GameWindow, @NotNull Dimension> dimensions = new LinkedHashMap<>();

    private int offsetX;
    private int offsetY;

    private boolean visible = true;

    private @NotNull ComponentAlign align;

    private @Nullable Color color;
    private @Range(from = 0, to = 100) int opacity;

    protected @NotNull Background background;

    private @Nullable ComponentDisposition disposition;

    public GameComponent(@NotNull Game game, @NotNull Location location) {
        this(game, location, 0,0, 100);
    }
    public GameComponent(@NotNull Game game, @NotNull Location location, int offsetX, int offsetY, @Range(from = 0, to = 100) int opacity) {
        this.location = location;
        this.opacity = opacity;

        this.offsetX = offsetX;
        this.offsetY = offsetY;

        background = new Background(null, 100);

        this.game = game;
        this.align = ComponentAlign.NORMAL;

        dimensions.put(null, new Dimension(0, 0));
    }

    @WindowThread
    public boolean isAdded() {
        if (!getGame().getGraphics().isWindowThread()) {
            throw new UnsupportedThreadException("GameWindow");
        }

        return getGame().getGraphics().getWindow().getComponents().contains(this);
    }
    @WindowThread
    public void add() {
        if (!getGame().getGraphics().isWindowThread()) {
            throw new UnsupportedThreadException("GameWindow");
        }

        getGame().getGraphics().getWindow().getComponents().add(this);
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

    public @NotNull ComponentAlign getAlign() {
        return align;
    }
    public void setAlign(@NotNull ComponentAlign align) {
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

    public @NotNull Game getGame() {
        return game;
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

    @WindowThread
    public void setLocation(@NotNull Location location) {
        if (!getGame().getGraphics().isWindowThread()) {
            throw new UnsupportedThreadException("GameWindow");
        }

        GameWindow window = game.getGraphics().getWindow();
        boolean contains = window.getComponents().contains(this);

        if (contains) window.getComponents().remove(this);
        this.location = location;
        if (contains) window.getComponents().add(this);
    }

    @ApiStatus.Internal
    @WindowThread
    public @NotNull Map<GameWindow, Location> getScreensLocation() {
        if (!getGame().getGraphics().isWindowThread()) {
            throw new UnsupportedThreadException("GameWindow");
        }

        return screenLocation;
    }

    @WindowThread
    public @NotNull Location getScreenLocation(@NotNull GameWindow window) {
        if (!getGame().getGraphics().isWindowThread()) {
            throw new UnsupportedThreadException("GameWindow");
        }

        if (!getScreensLocation().containsKey(window)) throw new LaivyEngineException(new IllegalStateException("Não foi possível encontrar o local deste componente na tela"), "Pega o local de um componente na tela");
        return getScreensLocation().get(window);
    }

    @ApiStatus.Internal
    @WindowThread
    public @NotNull Map<GameWindow, Dimension> getScreensDimension() {
        if (!getGame().getGraphics().isWindowThread()) {
            throw new UnsupportedThreadException("GameWindow");
        }

        return screenDimension;
    }

    @WindowThread
    public @NotNull Dimension getScreenDimension(@NotNull GameWindow window) {
        if (!getGame().getGraphics().isWindowThread()) {
            throw new UnsupportedThreadException("GameWindow");
        }

        if (!getScreensDimension().containsKey(window)) throw new LaivyEngineException(new IllegalStateException("Não foi possível encontrar a dimensão deste componente na tela"), "Pega a dimensão de um componente na tela");
        return getScreensDimension().get(window);
    }

    @WindowThread
    public boolean isAtScreen(@NotNull GameWindow window) {
        if (!getGame().getGraphics().isWindowThread()) {
            throw new UnsupportedThreadException("GameWindow");
        }

        return getScreensLocation().containsKey(window) && getScreensDimension().containsKey(window);
    }

    @WindowThread
    public @NotNull Rectangle getHitBox(@NotNull GameWindow window) {
        if (!getGame().getGraphics().isWindowThread()) {
            throw new UnsupportedThreadException("GameWindow");
        }

        if (isAtScreen(window)) {
            Location location = getScreenLocation(window);
            Dimension dimension = getScreenDimension(window);
            return new Rectangle(location, dimension);
        }
        throw new LaivyEngineException(new IllegalStateException("The component's hitbox doesn't exists at that screen"), "Gets the hitbox of a component on the screen");
    }

    @WindowThread
    public @NotNull Map<@Nullable GameWindow, @NotNull Dimension> getDimensions() {
        if (!getGame().getGraphics().isWindowThread()) {
            throw new UnsupportedThreadException("GameWindow");
        }

        return dimensions;
    }

    /**
     * Pega as dimensões do componente em uma GameWindow, caso indefinido, será o padrão.
     *
     * @param window janela do jogo, nulo caso queira pegar o valor padrão do asset
     * @return as dimensões do asset naquela tela, ou o padrão caso a tela seja nula ou não esteja definida
     */
    @NotNull
    @WindowThread
    public Dimension getDimension(@Nullable GameWindow window) {
        if (!getGame().getGraphics().isWindowThread()) {
            throw new UnsupportedThreadException("GameWindow");
        }

        if (!getDimensions().containsKey(window)) {
            throw new NullPointerException("Couldn't find a dimension for this screen");
        }

        return getDimensions().get(window);
    }

    /**
     * @param window use null para modificar o valor padrão
     */
    @WindowThread
    public void setDimension(@Nullable GameWindow window, @NotNull Dimension dimension) {
        if (!getGame().getGraphics().isWindowThread()) {
            throw new UnsupportedThreadException("GameWindow");
        }

        if (window != null && !window.getGame().equals(game)) {
            throw new LaivyEngineException(new IllegalArgumentException("Essa janela inserida não pertence ao jogo desse componente"), "Redimensionar componentes");
        }

        boolean contains = window != null && window.getComponents().contains(this);

        if (contains) window.getComponents().remove(this);
        getDimensions().put(window, dimension);
        if (contains) window.getComponents().add(this);
    }

    /**
     * Basicamente a mesma coisa de usar {@link #setDimension(GameWindow, Dimension) setDimension(null, Dimension)}
     *
     * @see #setDimension(GameWindow, Dimension)
     * @param dimension a nova dimensão padrão
     */
    @WindowThread
    public void setDefaultDimension(@NotNull Dimension dimension) {
        if (!getGame().getGraphics().isWindowThread()) {
            throw new UnsupportedThreadException("GameWindow");
        }

        setDimension(null, dimension);
    }

    @Override
    public @NotNull GameComponent clone() {
        try {
            GameComponent clone = (GameComponent) super.clone();
            clone.setLocation(clone.getLocation().clone());
            clone.dimensions = new HashMap<>(clone.dimensions);

            clone.screenLocation.clear();
            clone.screenDimension.clear();

            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public enum ComponentAlign {
        NORMAL(new AffineTransform()),
        FLIPPED_VERTICALLY(AffineTransform.getScaleInstance(1, -1)),
        FLIPPED_HORIZONTALLY(AffineTransform.getScaleInstance(-1, 1)),
        FLIPPED_VERTICALLY_HORIZONTALLY(AffineTransform.getScaleInstance(-1, -1)),
        ;

        private final @NotNull AffineTransform transform;

        ComponentAlign(@NotNull AffineTransform transform) {
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
