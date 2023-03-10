package codes.laivy.engine.graphics.components;

import codes.laivy.engine.coordinates.Location;
import codes.laivy.engine.coordinates.MouseLocation;
import codes.laivy.engine.coordinates.dimension.Dimension;
import codes.laivy.engine.graphics.window.GameWindow;
import codes.laivy.engine.graphics.window.swing.GamePanel;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Set;

/**
 * A {@link ScreenComponent ScreenComponent} is used to print a GamePanel on an existent GamePanel.
 *
 * @author ItsLaivy
 * @since 1.0 build 0 (26/01/2023)
 */
public class ScreenComponent extends GameComponent {

    private @NotNull GamePanel panel;

    public ScreenComponent(@NotNull GamePanel gamePanel, @NotNull Location location, @NotNull GamePanel panel, @NotNull Dimension dimension) {
        this(gamePanel, location, panel, dimension, 0, 0, 100);
    }
    public ScreenComponent(@NotNull GamePanel gamePanel, @NotNull Location location, @NotNull GamePanel panel, @NotNull Dimension dimension, int offsetX, int offsetY, int opacity) {
        super(gamePanel, location, offsetX, offsetY, opacity);

        if (gamePanel.equals(panel)) {
            throw new IllegalArgumentException("The 'panel' couldn't be the same as 'gamePanel'!");
        }

        this.panel = panel;
        this.dimension = dimension;
    }

    /**
     * This method converts the {@link GameWindow}'s mouse location to the {@link ScreenComponent}'s mouse location.
     * @return the converted location from {@link GameWindow}
     */
    public @NotNull MouseLocation convertLocation(@NotNull MouseLocation gameWindowLocation) {
        if (!isAtScreen() || gameWindowLocation.getLocation() == null || getScreenCoordinates() == null) {
            throw new IllegalStateException("The component needs to be active on screen to perform that method.");
        }

        return new MouseLocation(
                getPanel(),
                new Location(
                        gameWindowLocation.getLocation().getX() - getScreenCoordinates().getLocation().getX(),
                        gameWindowLocation.getLocation().getY() - getScreenCoordinates().getLocation().getY()
                )
        );
    }

    @Override
    public @NotNull Set<@NotNull GameComponent> getComponent(@NotNull Location location) {
        return getPanel().getComponents(Objects.requireNonNull(location));
    }

    public @NotNull GamePanel getPanel() {
        return panel;
    }
    public void setPanel(@NotNull GamePanel panel) {
        this.panel = panel;
    }

    @Override
    @Contract("-> new")
    public @NotNull ScreenComponent clone() {
        return (ScreenComponent) super.clone();
    }
}
