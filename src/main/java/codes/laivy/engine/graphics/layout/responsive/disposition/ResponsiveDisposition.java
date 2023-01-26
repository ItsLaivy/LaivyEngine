package codes.laivy.engine.graphics.layout.responsive.disposition;

import codes.laivy.engine.coordinates.Location;
import codes.laivy.engine.coordinates.dimension.Dimension;
import codes.laivy.engine.graphics.components.GameComponent;
import codes.laivy.engine.graphics.components.GameComponent.Alignment;
import codes.laivy.engine.graphics.layout.ComponentDisposition;
import codes.laivy.engine.graphics.layout.GameLayout;
import codes.laivy.engine.graphics.layout.responsive.ResponsiveLayout;
import codes.laivy.engine.graphics.window.GameWindow;
import codes.laivy.engine.utils.MathUtils;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

@ApiStatus.Experimental
public abstract class ResponsiveDisposition extends ComponentDisposition<ResponsiveLayout> {

    public ResponsiveDisposition(@NotNull GameComponent component, @NotNull ResponsiveLayout layout) {
        super(component, layout);
    }

    public void render(@NotNull Graphics2D graphics) {
        Graphics2D renderingGraphics = (Graphics2D) graphics.create();
        Graphics2D backgroundGraphics = (Graphics2D) graphics.create();

        // Coordinates
        GameLayout.LayoutCoordinates coordinates = resolutionFix();
        //

        if (getComponent().isAntiAliasing()) {
            renderingGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }

        // Background
        renderBackground(backgroundGraphics, coordinates);
        backgroundGraphics.dispose();
        //

        // Component alignment
        alignment(renderingGraphics, getComponent().getAlign(), coordinates);
        //

        // Component rendering
        renderingGraphics.setColor(getComponent().getColor());

        if (getComponent().getStroke() != null) {
            renderingGraphics.setStroke(getComponent().getStroke());
        }

        if (getComponent().getOpacity() != 100) {
            renderingGraphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, getComponent().getOpacity() / 100F));
        }

        draw(renderingGraphics, coordinates.getClientLocation(), coordinates.getClientDimension());

        renderingGraphics.dispose();
        //

        // Hitbox defining
        getComponent().setScreenLocation(coordinates.getScreenLocation());
        getComponent().setScreenDimension(coordinates.getScreenDimension());
        //
    }

    /**
     * This method is responsible to generate the layout coordinates
     * @return the layout coordinates
     */
    public @NotNull GameLayout.LayoutCoordinates resolutionFix() {
        GameWindow window = getLayout().getWindow();
        Dimension defaultDim = component.getDimension().clone();
        Location defaultLoc = component.getLocation().clone();

        GameLayout.LayoutCoordinates coordinates = new GameLayout.LayoutCoordinates(
                defaultLoc,
                defaultDim.clone()
        );

        int offsetX = calculateWidthOffset(component.getOffsetX());
        int offsetY = calculateHeightOffset(component.getOffsetY());

        if ((window.getAvailableSize().getWidth() != getLayout().getReferenceSize().getWidth() || window.getAvailableSize().getHeight() != getLayout().getReferenceSize().getHeight())) {
            if (getLayout().isCubicResizing()) {
                int ratio = defaultDim.getWidth() / defaultDim.getHeight();

                int width = (int) MathUtils.rthree(getLayout().getReferenceSize().getWidth(), defaultDim.getWidth(), window.getAvailableSize().getWidth());
                int height = (width / ratio);

                coordinates.setDimension(new Dimension(
                        width,
                        height
                ));
            } else {
                coordinates.setDimension(new Dimension(
                        (int) MathUtils.rthree(getLayout().getReferenceSize().getWidth(), defaultDim.getWidth(), window.getAvailableSize().getWidth()),
                        (int) MathUtils.rthree(getLayout().getReferenceSize().getHeight(), defaultDim.getHeight(), window.getAvailableSize().getHeight())
                ));
            }
        }

        if (getLayout().isAutoMove()) {
            Location location;
            if (getLayout().isCubicResizing()) {
                int x = (int) MathUtils.rthree(getLayout().getReferenceSize().getWidth(), defaultLoc.getX(), window.getAvailableSize().getWidth());
                int y = (int) MathUtils.rthree(getLayout().getReferenceSize().getHeight(), defaultLoc.getY(), window.getAvailableSize().getHeight());

                location = new Location(
                        x,
                        (int) (y - coordinates.getClientDimension().getHeight() / 2.1D)
                );
            } else {
                location = new Location(
                        (int) MathUtils.rthree(getLayout().getReferenceSize().getWidth(), defaultLoc.getX(), window.getAvailableSize().getWidth()),
                        (int) MathUtils.rthree(getLayout().getReferenceSize().getHeight(), defaultLoc.getY(), window.getAvailableSize().getHeight())
                );
            }
            coordinates.setLocation(new Location(offsetX + location.getX(), offsetY + location.getY()));
        }

        return coordinates;
    }

    /**
     * Use that method to draw the component on the screen using the graphics.
     *
     * @param renderingGraphics the rendering graphics of that component
     * @param location the sreen location of the component
     * @param dimension the screen dimension of the component
     */
    public abstract void draw(@NotNull Graphics2D renderingGraphics, @NotNull Location location, @NotNull Dimension dimension);

    /**
     * That's the alignment method for components
     * <strong>Note:</strong> The location and dimension parameter needs to be changed to get results.
     *
     * @param renderingGraphics the rendering graphics of that component
     * @param alignment The alignment position
     * @param coords The coordinates of the component
     */
    public void alignment(@NotNull Graphics2D renderingGraphics, @NotNull Alignment alignment, @NotNull GameLayout.LayoutCoordinates coords) {
        renderingGraphics.transform(alignment.getTransform());
        if (alignment == Alignment.FLIPPED_HORIZONTALLY) {
            coords.getClientLocation().setX(coords.getScreenLocation().getX());
        } else if (alignment == Alignment.FLIPPED_VERTICALLY) {
            coords.getClientLocation().setY(coords.getScreenLocation().getY());
        } else if (alignment == Alignment.FLIPPED_VERTICALLY_HORIZONTALLY) {
            coords.getClientLocation().setX(coords.getScreenLocation().getX());
            coords.getClientLocation().setY(coords.getScreenLocation().getY());
        }
    }

    /**
     * @param backgroundGraphics A copy of the original graphics, separated from the rendering graphics.
     * @param coordinates the coordinates
     */
    public void renderBackground(@NotNull Graphics2D backgroundGraphics, @NotNull GameLayout.LayoutCoordinates coordinates) {
        Color color = getComponent().getBackground().getFinalColor();
        if (color != null) {
            backgroundGraphics.setColor(color);
            backgroundGraphics.fill(new java.awt.Rectangle(coordinates.getClientLocation().toPoint(), coordinates.getClientDimension().toSwing()));
        }
    }

    // ---/-/--- //
    // Utilities //
    // ---/-/--- //
    protected final int calculateWidthOffset(int offsetX) {
        if (getLayout().isCubicResizing()) {
            if (offsetX != 0) {
                offsetX = (int) MathUtils.rthree(getLayout().getReferenceSize().getWidth() + getLayout().getReferenceSize().getHeight(), offsetX, getLayout().getWindow().getAvailableSize().getWidth() + getLayout().getWindow().getAvailableSize().getHeight());
            }
        } else {
            if (offsetX != 0) {
                offsetX = (int) MathUtils.rthree(getLayout().getReferenceSize().getWidth(), offsetX, getLayout().getWindow().getAvailableSize().getWidth());
            }
        }
        return offsetX;
    }
    protected final int calculateHeightOffset(int offsetY) {
        if (getLayout().isCubicResizing()) {
            if (offsetY != 0) {
                offsetY = (int) MathUtils.rthree(getLayout().getReferenceSize().getWidth() + getLayout().getReferenceSize().getHeight(), offsetY, getLayout().getWindow().getAvailableSize().getWidth() + getLayout().getWindow().getAvailableSize().getHeight());
            }
        } else {
            if (offsetY != 0) {
                offsetY = (int) MathUtils.rthree(getLayout().getReferenceSize().getHeight(), offsetY, getLayout().getWindow().getAvailableSize().getHeight());
            }
        }
        return offsetY;
    }
    // ---/-/--- //
    // Utilities //
    // ---/-/--- //

}
