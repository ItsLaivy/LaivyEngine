package codes.laivy.engine.graphics.layout.responsive;

import codes.laivy.engine.coordinates.Location;
import codes.laivy.engine.coordinates.dimension.Dimension;
import codes.laivy.engine.graphics.components.GameComponent;
import codes.laivy.engine.graphics.layout.GameLayout;
import codes.laivy.engine.graphics.layout.responsive.disposition.ResponsiveDisposition;
import codes.laivy.engine.graphics.window.GameWindow;
import codes.laivy.engine.utils.MathUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;

public class ResponsiveLayout extends GameLayout {

    private final @NotNull Dimension referenceSize;

    private boolean cubicResizing = true;
    private boolean autoMove = true;

    public ResponsiveLayout(@NotNull GameWindow window, @NotNull Dimension referenceSize) {
        super(window);
        this.referenceSize = referenceSize;
    }

    /**
     * The reference size is the reference value to change the component's size with the difference between Current Frame Size vs Reference Size
     * @return the refence size
     */
    public @NotNull Dimension getReferenceSize() {
        return referenceSize;
    }

    /**
     * The cubic resizing means that the components will maintain their cubic dimensions on every dimension
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
    protected void render(@NotNull Graphics2D graphics) {
        for (GameComponent component : getWindow().getComponents()) {
            if (!(component.getDisposition() instanceof ResponsiveDisposition)) {
                throw new IllegalStateException("The component '" + component + "' doesn't have the ResponsiveDisposition. All the components needs the ResponsiveDisposition when the ResponsiveLayout is active.");
            }
            ResponsiveDisposition disposition = (ResponsiveDisposition) component.getDisposition();

            Graphics2D gCopy = (Graphics2D) graphics.create();

            // Coordinates
            Dimension defaultCompDimension = component.getDimension(null);

            Dimension dimension = component.getDimension(getWindow()).clone();
            Location location = component.getLocation().clone();
            int offsetX = component.getOffsetX();
            int offsetY = component.getOffsetY();

            if ((getWindow().getAvailableSize().getWidth() != getReferenceSize().getWidth() || getWindow().getAvailableSize().getHeight() != getReferenceSize().getHeight())) {
                if (isCubicResizing()) {
                    dimension = new Dimension(
                            (int) MathUtils.rthree(getReferenceSize().getWidth() + getReferenceSize().getHeight(), defaultCompDimension.getWidth(), getWindow().getAvailableSize().getWidth() + getWindow().getAvailableSize().getHeight()),
                            (int) MathUtils.rthree(getReferenceSize().getWidth() + getReferenceSize().getHeight(), defaultCompDimension.getHeight(), getWindow().getAvailableSize().getWidth() + getWindow().getAvailableSize().getHeight())
                    );

                    if (offsetX != 0) {
                        offsetX = (int) MathUtils.rthree(getReferenceSize().getWidth() + getReferenceSize().getHeight(), offsetX, getWindow().getAvailableSize().getWidth() + getWindow().getAvailableSize().getHeight());
                    }
                    if (offsetY != 0) {
                        offsetY = (int) MathUtils.rthree(getReferenceSize().getHeight() + getReferenceSize().getHeight(), offsetY, getWindow().getAvailableSize().getHeight() + getWindow().getAvailableSize().getHeight());
                    }
                } else {
                    dimension = new Dimension(
                            (int) MathUtils.rthree(getReferenceSize().getWidth(), defaultCompDimension.getWidth(), getWindow().getAvailableSize().getWidth()),
                            (int) MathUtils.rthree(getReferenceSize().getHeight(), defaultCompDimension.getHeight(), getWindow().getAvailableSize().getHeight())
                    );

                    if (offsetX != 0) {
                        offsetX = (int) MathUtils.rthree(getReferenceSize().getWidth(), offsetX, getWindow().getAvailableSize().getWidth());
                    } if (offsetY != 0) {
                        offsetY = (int) MathUtils.rthree(getReferenceSize().getHeight(), offsetY, getWindow().getAvailableSize().getHeight());
                    }
                }
            }

            if (isAutoMove()) {
                location = new Location(
                        (int) MathUtils.rthree(getReferenceSize().getWidth(), component.getLocation().getX(), getWindow().getAvailableSize().getWidth()),
                        (int) MathUtils.rthree(getReferenceSize().getHeight(), component.getLocation().getY(), getWindow().getAvailableSize().getHeight())
                );
                location = new Location(offsetX + location.getX(), offsetY + location.getY());
            }
            //

            // Hitbox defining
            component.getScreensLocation().put(getWindow(), location);
            component.getScreensDimension().put(getWindow(), dimension);
            //

            // Background
            Color color = getComponentBackgroundColor(component);
            if (color != null) {
                gCopy.setColor(color);
                gCopy.fill(new Rectangle(location.toPoint(), dimension.toSwing()));
            }
            //

            gCopy.setColor(component.getColor());

            disposition.render(gCopy, location, dimension);

            gCopy.dispose();
        }
    }

    protected static @Nullable Color getComponentBackgroundColor(@NotNull GameComponent component) {
        GameComponent.Background background = component.getBackground();

        if (background.getColor() != null) {
            return new Color(background.getColor().getRed() / 255F, background.getColor().getGreen() / 255F, background.getColor().getBlue() / 255F, background.getOpacity() / 100F);
        } else {
            return null;
        }
    }

}
