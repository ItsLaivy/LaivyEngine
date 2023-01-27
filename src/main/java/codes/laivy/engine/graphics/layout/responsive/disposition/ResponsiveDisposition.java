package codes.laivy.engine.graphics.layout.responsive.disposition;

import codes.laivy.engine.coordinates.Location;
import codes.laivy.engine.coordinates.dimension.Dimension;
import codes.laivy.engine.graphics.components.GameComponent;
import codes.laivy.engine.graphics.components.GameComponent.Alignment;
import codes.laivy.engine.graphics.layout.ComponentDisposition;
import codes.laivy.engine.graphics.layout.GameLayout;
import codes.laivy.engine.graphics.layout.GameLayoutBounds;
import codes.laivy.engine.graphics.layout.responsive.ResponsiveLayout;
import codes.laivy.engine.utils.MathUtils;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

@ApiStatus.Experimental
public abstract class ResponsiveDisposition extends ComponentDisposition<ResponsiveLayout> {

    public ResponsiveDisposition(@NotNull GameComponent component, @NotNull ResponsiveLayout layout) {
        super(component, layout);
    }

    public @NotNull Dimension getReferenceSize(@NotNull GameLayoutBounds bounds) {
        return new Dimension(getLayout().getReferenceSize().getWidth(), getLayout().getReferenceSize().getHeight());
    }

    public void render(@NotNull Graphics2D graphics, @NotNull GameLayoutBounds bounds) {
        if (getComponent().isAntiAliasing()) {
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }
        graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);

        Graphics2D renderingGraphics = (Graphics2D) graphics.create();
        Graphics2D backgroundGraphics = (Graphics2D) graphics.create();

        // Coordinates
        GameLayout.LayoutCoordinates coordinates = resolutionFix(bounds);
        //

        // Post
        postResolutionFix(renderingGraphics, backgroundGraphics, coordinates, bounds);
        //

        // Background
        drawBackground(backgroundGraphics, coordinates, bounds);
        //

        // Component alignment
        alignment(renderingGraphics, getComponent().getAlign(), coordinates, bounds);
        //

        // Component rendering
        renderingGraphics.setColor(getComponent().getColor());

        if (getComponent().getStroke() != null) {
            renderingGraphics.setStroke(getComponent().getStroke());
        }
        if (getComponent().getOpacity() != 100) {
            renderingGraphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, getComponent().getOpacity() / 100F));
        }

        drawObject(renderingGraphics, coordinates.getClientLocation(), coordinates.getClientDimension(), bounds);
        //

        // Graphics disposing
        backgroundGraphics.dispose();
        renderingGraphics.dispose();
        //

        // Hitbox defining
        getComponent().setScreenLocation(coordinates.getScreenLocation());
        getComponent().setScreenDimension(coordinates.getScreenDimension());
        //
    }

    public void postResolutionFix(@NotNull Graphics2D renderingGraphics, @NotNull Graphics2D backgroundGraphics, @NotNull GameLayout.LayoutCoordinates coordinates, @NotNull GameLayoutBounds bounds) {
    }

    /**
     * This method is responsible to generate the layout coordinates
     * @return the layout coordinates
     */
    public @NotNull GameLayout.LayoutCoordinates resolutionFix(@NotNull GameLayoutBounds bounds) {
        Dimension defaultDim = component.getDimension().clone();
        Location defaultLoc = component.getLocation().clone();

        GameLayout.LayoutCoordinates coordinates = new GameLayout.LayoutCoordinates(
                defaultLoc,
                defaultDim.clone()
        );

        int offsetX = calculateWidthOffset(getComponent().getOffsetX(), bounds);
        int offsetY = calculateHeightOffset(getComponent().getOffsetY(), bounds);

        if ((bounds.getAvailable().getWidth() != getReferenceSize(bounds).getWidth() || bounds.getAvailable().getHeight() != getReferenceSize(bounds).getHeight())) {
            if (getLayout().isCubicResizing()) {
                int ratio = defaultDim.getWidth() / defaultDim.getHeight();

                int width = (int) MathUtils.rthree(getReferenceSize(bounds).getWidth(), defaultDim.getWidth(), bounds.getAvailable().getWidth());
                int height = (width / ratio);

                coordinates.setDimension(new Dimension(
                        width,
                        height
                ));
            } else {
                coordinates.setDimension(new Dimension(
                        (int) MathUtils.rthree(getReferenceSize(bounds).getWidth(), defaultDim.getWidth(), bounds.getAvailable().getWidth()),
                        (int) MathUtils.rthree(getReferenceSize(bounds).getHeight(), defaultDim.getHeight(), bounds.getAvailable().getHeight())
                ));
            }
        }

        if (getLayout().isAutoMove()) {
            Location location;
            if (getLayout().isCubicResizing()) {
                int x = bounds.getLocation().getX() + (int) MathUtils.rthree(getReferenceSize(bounds).getWidth(), defaultLoc.getX(), bounds.getAvailable().getWidth());
                int y = bounds.getLocation().getY() + (int) MathUtils.rthree(getReferenceSize(bounds).getHeight(), defaultLoc.getY(), bounds.getAvailable().getHeight());

                location = new Location(
                        x,
                        (int) (y - coordinates.getClientDimension().getHeight() / 2.1D)
                );
            } else {
                location = new Location(
                        (int) MathUtils.rthree(getReferenceSize(bounds).getWidth(), defaultLoc.getX(), bounds.getAvailable().getWidth()),
                        (int) MathUtils.rthree(getReferenceSize(bounds).getHeight(), defaultLoc.getY(), bounds.getAvailable().getHeight())
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
    public abstract void drawObject(@NotNull Graphics2D renderingGraphics, @NotNull Location location, @NotNull Dimension dimension, @NotNull GameLayoutBounds bounds);

    /**
     * That's the alignment method for components
     * <strong>Note:</strong> The location and dimension parameter needs to be changed to get results.
     *
     * @param renderingGraphics the rendering graphics of that component
     * @param alignment The alignment position
     * @param coords The coordinates of the component
     */
    public void alignment(@NotNull Graphics2D renderingGraphics, @NotNull Alignment alignment, @NotNull GameLayout.LayoutCoordinates coords, @NotNull GameLayoutBounds bounds) {
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
    public void drawBackground(@NotNull Graphics2D backgroundGraphics, @NotNull GameLayout.LayoutCoordinates coordinates, @NotNull GameLayoutBounds bounds) {
        Color color = getComponent().getBackground().getFinalColor();
        if (color != null) {
            backgroundGraphics.setColor(color);
            backgroundGraphics.fill(new Rectangle(coordinates.getClientLocation().toPoint(), coordinates.getClientDimension().toSwing()));
        }
    }

    // ---/-/--- //
    // Utilities //
    // ---/-/--- //
    protected final int calculateWidthOffset(int offsetX, @NotNull GameLayoutBounds bounds) {
        if (offsetX != 0) {
            if (getLayout().isCubicResizing()) {
                offsetX = (int) MathUtils.rthree(getReferenceSize(bounds).getWidth() + getReferenceSize(bounds).getHeight(), offsetX, bounds.getTotal().getWidth() + bounds.getTotal().getHeight());
            } else {
                offsetX = (int) MathUtils.rthree(getReferenceSize(bounds).getWidth(), offsetX, bounds.getAvailable().getWidth());
            }
        }

        return offsetX;
    }
    protected final int calculateHeightOffset(int offsetY, @NotNull GameLayoutBounds bounds) {
        if (offsetY != 0) {
            if (getLayout().isCubicResizing()) {
                offsetY = (int) MathUtils.rthree(getReferenceSize(bounds).getWidth() + getReferenceSize(bounds).getHeight(), offsetY, bounds.getAvailable().getWidth() + bounds.getAvailable().getHeight());
            } else {
                offsetY = (int) MathUtils.rthree(getReferenceSize(bounds).getHeight(), offsetY, bounds.getAvailable().getHeight());
            }
        }

        return offsetY;
    }
    // ---/-/--- //
    // Utilities //
    // ---/-/--- //

}
