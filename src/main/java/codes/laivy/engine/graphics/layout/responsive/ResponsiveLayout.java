package codes.laivy.engine.graphics.layout.responsive;

import codes.laivy.engine.coordinates.dimension.Dimension;
import codes.laivy.engine.graphics.components.GameComponent;
import codes.laivy.engine.graphics.layout.GameLayout;
import codes.laivy.engine.graphics.layout.responsive.disposition.ResponsiveDisposition;
import codes.laivy.engine.graphics.window.swing.GamePanel;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

@ApiStatus.Experimental
public class ResponsiveLayout extends GameLayout {

    private final @NotNull Dimension referenceSize;

    private boolean cubicResizing = true;
    private boolean autoMove = true;

    public ResponsiveLayout(@NotNull GamePanel panel) {
        this(panel, new Dimension(800, 600));
    }
    public ResponsiveLayout(@NotNull GamePanel panel, @NotNull Dimension referenceSize) {
        super(panel);
        this.referenceSize = referenceSize;
    }

    /**
     * The reference size is the reference value to change the component's size with the difference between Current Frame GridSize vs Reference GridSize
     * @return the refence size
     */
    @Contract(pure = true)
    public @NotNull Dimension getReferenceSize() {
        return referenceSize;
    }

    /**
     * The cubic resizing means that the components will maintain their cubic dimensions on every dimension
     * <br><br>
     * <storng>Note:</storng> The cubic resizing mode isn't recommended for screens that contains components with a high location/dimension precision needed
     *
     * @return true if the cubic resizing is active
     */
    public boolean isCubicResizing() {
        return cubicResizing;
    }
    public void setCubicResizing(boolean cubicResizing) {
        this.cubicResizing = cubicResizing;
    }

    /**
     * The auto move means that the components will be moved to the new location between the refence size vs the current size
     * @return true if the auto move is active
     */
    public boolean isAutoMove() {
        return autoMove;
    }
    public void setAutoMove(boolean autoMove) {
        this.autoMove = autoMove;
    }

    @Override
    protected void render(@NotNull Graphics2D graphics, @NotNull GameLayout.Bounds bounds) {
        for (GameComponent component : getPanel().getComponents()) {
            if (!(component.getDisposition() instanceof ResponsiveDisposition)) {
                throw new IllegalStateException("The component '" + component + "' doesn't have the ResponsiveDisposition. All the components needs the ResponsiveDisposition when the ResponsiveLayout is active.");
            }
            ResponsiveDisposition disposition = (ResponsiveDisposition) component.getDisposition();
            disposition.render(graphics, bounds);
        }
    }

}
