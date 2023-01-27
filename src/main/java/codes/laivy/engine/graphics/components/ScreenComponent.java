package codes.laivy.engine.graphics.components;

import codes.laivy.engine.coordinates.Location;
import codes.laivy.engine.coordinates.dimension.Dimension;
import codes.laivy.engine.graphics.window.swing.GamePanel;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * A {@link ScreenComponent ScreenComponent} is used to print a GamePanel on an existent GamePanel.
 */
public class ScreenComponent extends GameComponent {

    private @NotNull GamePanel panel;

    public ScreenComponent(@NotNull GamePanel gamePanel, @NotNull Location location, @NotNull GamePanel panel, @NotNull Dimension dimension) {
        super(gamePanel, location);

        if (gamePanel == panel) {
            throw new IllegalArgumentException("The 'panel' couldn't be the same as 'gamePanel'!");
        }

        this.panel = panel;
        this.dimension = dimension;

        this.getGamePanel().getWindow().getFrame().add(getPanel());
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
