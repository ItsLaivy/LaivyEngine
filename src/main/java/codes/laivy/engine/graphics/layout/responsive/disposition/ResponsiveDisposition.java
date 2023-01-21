package codes.laivy.engine.graphics.layout.responsive.disposition;

import codes.laivy.engine.coordinates.Location;
import codes.laivy.engine.coordinates.dimension.Dimension;
import codes.laivy.engine.graphics.components.GameComponent;
import codes.laivy.engine.graphics.components.GameComponent.Alignment;
import codes.laivy.engine.graphics.components.TextComponent;
import codes.laivy.engine.graphics.components.shape.RectangleComponent;
import codes.laivy.engine.graphics.components.shape.ShapeComponent;
import codes.laivy.engine.graphics.layout.ComponentDisposition;
import codes.laivy.engine.graphics.layout.GameLayout;
import codes.laivy.engine.graphics.layout.responsive.ResponsiveLayout;
import codes.laivy.engine.graphics.window.GameWindow;
import codes.laivy.engine.utils.MathUtils;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.geom.AffineTransform;

public abstract class ResponsiveDisposition extends ComponentDisposition {

    private final @NotNull ResponsiveLayout layout;

    public ResponsiveDisposition(@NotNull GameComponent component, @NotNull ResponsiveLayout layout) {
        super(component);
        this.layout = layout;
    }

    public @NotNull ResponsiveLayout getLayout() {
        return layout;
    }

    @Override
    public void render(@NotNull Graphics2D graphics) {
        Graphics2D renderingGraphics = (Graphics2D) graphics.create();

        // Coordinates
        GameLayout.LayoutCoordinates coordinates = resolutionFix();
        //

        // Component alignment
        alignment(renderingGraphics, getComponent().getAlign(), coordinates);
        //

        // Background
        Graphics2D backgroundGraphics = (Graphics2D) graphics.create();
        renderBackground(backgroundGraphics, coordinates);
        backgroundGraphics.dispose();
        //

        // Component rendering
        renderingGraphics.setColor(getComponent().getColor());

        if (getComponent().getOpacity() != 100) {
            renderingGraphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, getComponent().getOpacity() / 100F));
        }

        draw(renderingGraphics, coordinates.getClientLocation(), coordinates.getClientDimension());
        renderingGraphics.dispose();
        //

        // Hitbox defining
        getComponent().getScreensLocation().put(getLayout().getWindow(), coordinates.getScreenLocation());
        getComponent().getScreensDimension().put(getLayout().getWindow(), coordinates.getScreenDimension());
        //
    }

    /**
     * This method is responsible to generate the layout coordinates
     * @return the layout coordinates
     */
    public @NotNull GameLayout.LayoutCoordinates resolutionFix() {
        GameWindow window = getLayout().getWindow();
        Dimension defaultCompDimension = component.getDimension(null);

        GameLayout.LayoutCoordinates coordinates = new GameLayout.LayoutCoordinates(
                getComponent().getLocation().clone(),
                defaultCompDimension.clone()
        );

        int offsetX = component.getOffsetX();
        int offsetY = component.getOffsetY();

        if ((window.getAvailableSize().getWidth() != getLayout().getReferenceSize().getWidth() || window.getAvailableSize().getHeight() != getLayout().getReferenceSize().getHeight())) {
            if (getLayout().isCubicResizing()) {
                coordinates.setDimension(new Dimension(
                        (int) MathUtils.rthree(getLayout().getReferenceSize().getWidth() + getLayout().getReferenceSize().getHeight(), defaultCompDimension.getWidth(), window.getAvailableSize().getWidth() + window.getAvailableSize().getHeight()),
                        (int) MathUtils.rthree(getLayout().getReferenceSize().getWidth() + getLayout().getReferenceSize().getHeight(), defaultCompDimension.getHeight(), window.getAvailableSize().getWidth() + window.getAvailableSize().getHeight())
                ));

                if (offsetX != 0) {
                    offsetX = (int) MathUtils.rthree(getLayout().getReferenceSize().getWidth() + getLayout().getReferenceSize().getHeight(), offsetX, window.getAvailableSize().getWidth() + window.getAvailableSize().getHeight());
                }
                if (offsetY != 0) {
                    offsetY = (int) MathUtils.rthree(getLayout().getReferenceSize().getHeight() + getLayout().getReferenceSize().getHeight(), offsetY, window.getAvailableSize().getHeight() + window.getAvailableSize().getHeight());
                }
            } else {
                coordinates.setDimension(new Dimension(
                        (int) MathUtils.rthree(getLayout().getReferenceSize().getWidth(), defaultCompDimension.getWidth(), window.getAvailableSize().getWidth()),
                        (int) MathUtils.rthree(getLayout().getReferenceSize().getHeight(), defaultCompDimension.getHeight(), window.getAvailableSize().getHeight())
                ));

                if (offsetX != 0) {
                    offsetX = (int) MathUtils.rthree(getLayout().getReferenceSize().getWidth(), offsetX, window.getAvailableSize().getWidth());
                } if (offsetY != 0) {
                    offsetY = (int) MathUtils.rthree(getLayout().getReferenceSize().getHeight(), offsetY, window.getAvailableSize().getHeight());
                }
            }
        }

        if (getLayout().isAutoMove()) {
            Location location = new Location(
                    (int) MathUtils.rthree(getLayout().getReferenceSize().getWidth(), getComponent().getLocation().getX(), window.getAvailableSize().getWidth()),
                    (int) MathUtils.rthree(getLayout().getReferenceSize().getHeight(), getComponent().getLocation().getY(), window.getAvailableSize().getHeight())
            );
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
        Dimension dimension = coords.getScreenDimension();
        Location location = coords.getScreenLocation();

        if (alignment == Alignment.FLIPPED_HORIZONTALLY) {
            dimension.setWidth(-dimension.getWidth());
            location.setX(location.getX() - dimension.getWidth());
        } else if (alignment == Alignment.FLIPPED_VERTICALLY) {
            dimension.setHeight(-dimension.getHeight());
            location.setY(location.getY() - dimension.getHeight());
        } else if (alignment == Alignment.FLIPPED_VERTICALLY_HORIZONTALLY) {
            dimension.setWidth(-dimension.getWidth());
            dimension.setHeight(-dimension.getHeight());

            location.setX(location.getX() - dimension.getWidth());
            location.setY(location.getY() - dimension.getHeight());
        }

        if (alignment == Alignment.FLIPPED_HORIZONTALLY) {
            renderingGraphics.setTransform(alignment.getTransform());
            coords.getClientLocation().setX(-(coords.getClientLocation().getX() + getLayout().getWindow().getAvailableSize().getWidth() / getLayout().getWindow().getSize().getWidth()));
            coords.getClientLocation().setX(coords.getClientLocation().getX() - dimension.getWidth());
        }
    }

    /**
     * @param backgroundGraphics A copy of the original graphics, separated from the rendering graphics.
     * @param coordinates the coordinates
     */
    public void renderBackground(@NotNull Graphics2D backgroundGraphics, @NotNull GameLayout.LayoutCoordinates coordinates) {
        Color color = component.getBackground().getFinalColor();
        if (color != null) {
            backgroundGraphics.setColor(color);
            backgroundGraphics.fill(new java.awt.Rectangle(coordinates.getClientLocation().toPoint(), coordinates.getClientDimension().toSwing()));
        }
    }

    // ---/-/--- //
    //   Shape   //
    // ---/-/--- //

    public abstract static class Shape extends ResponsiveDisposition {
        public Shape(@NotNull ShapeComponent component, @NotNull ResponsiveLayout layout) {
            super(component, layout);
        }

        @Override
        public @NotNull ShapeComponent getComponent() {
            return (ShapeComponent) super.getComponent();
        }
    }
    public static class Rectangle extends Shape {
        public Rectangle(@NotNull RectangleComponent component, @NotNull ResponsiveLayout layout) {
            super(component, layout);
        }

        @Override
        public @NotNull RectangleComponent getComponent() {
            return (RectangleComponent) super.getComponent();
        }

        @Override
        public void draw(@NotNull Graphics2D graphics, @NotNull Location location, @NotNull Dimension dimension) {
            graphics.draw(getComponent().getShape(location, dimension));
        }
    }

    // ---/-/--- //
    //   Shape   //
    // ---/-/--- //

    public static class Text extends ResponsiveDisposition {
        public Text(@NotNull TextComponent component, @NotNull ResponsiveLayout layout) {
            super(component, layout);
        }

        @Override
        public @NotNull TextComponent getComponent() {
            return (TextComponent) super.getComponent();
        }

        @Override
        public void draw(@NotNull Graphics2D graphics, @NotNull Location location, @NotNull Dimension dimension) {
            Font font = getComponent().getFont().deriveFont(MathUtils.rthree(getLayout().getReferenceSize().getWidth() + getLayout().getReferenceSize().getHeight(), getComponent().getFont().getSize(), getLayout().getWindow().getAvailableSize().getWidth() + getLayout().getWindow().getAvailableSize().getHeight()));

            graphics.setFont(font);
            graphics.drawString(getComponent().getText(), location.getX(), location.getY());
        }

        @Override
        public void alignment(@NotNull Graphics2D renderingGraphics, @NotNull Alignment alignment, @NotNull GameLayout.LayoutCoordinates coords) {
            if (alignment.getTransform() != null) {
                Dimension textRectangularSize = getComponent().getDimension(alignment.getTransform());

                if (alignment == Alignment.FLIPPED_HORIZONTALLY) {
                    coords.getScreenLocation().setX(coords.getScreenLocation().getX() + textRectangularSize.getWidth());
                } else if (alignment == Alignment.FLIPPED_VERTICALLY) {
                    coords.getScreenLocation().setY(coords.getScreenLocation().getY() - textRectangularSize.getHeight() / 2);
                } else if (alignment == Alignment.FLIPPED_VERTICALLY_HORIZONTALLY) {
                    coords.getScreenLocation().setX(coords.getScreenLocation().getX() + textRectangularSize.getWidth());
                    coords.getScreenLocation().setY(coords.getScreenLocation().getY() - textRectangularSize.getHeight() / 2);
                }

                if (alignment == Alignment.FLIPPED_HORIZONTALLY) {
                    coords.getClientLocation().setX(-(coords.getClientLocation().getX() + getLayout().getWindow().getAvailableSize().getWidth() / getLayout().getWindow().getSize().getWidth()));
                    coords.getClientLocation().setX(coords.getClientLocation().getX() - coords.getScreenDimension().getWidth());
                }
            } else {
                throw new NullPointerException("This alignment doesn't have a AffineTransform. TextComponents alignments needs to have a AffineTransform");
            }
        }

        @Override
        public void renderBackground(@NotNull Graphics2D backgroundGraphics, GameLayout.@NotNull LayoutCoordinates coordinates) {
            AffineTransform transform;
            if (getComponent().getAlign().getTransform() != null) transform = getComponent().getAlign().getTransform();
            else transform = new AffineTransform();

            Dimension temp = coordinates.getScreenDimension().clone();
            temp.setHeight(temp.getHeight());

            Location location = coordinates.getClientLocation().clone();
            location.setY((int) (location.getY() - temp.getHeight() / 1.25f));

            Dimension textRectangularSize = getComponent().getDimension(transform);

            if (getComponent().getAlign() == Alignment.FLIPPED_HORIZONTALLY) {
                location.setX(location.getX() - textRectangularSize.getWidth());
            } else if (getComponent().getAlign() == Alignment.FLIPPED_VERTICALLY) {
                location.setY(location.getY() + textRectangularSize.getHeight() / 2);
            } else if (getComponent().getAlign() == Alignment.FLIPPED_VERTICALLY_HORIZONTALLY) {
                location.setX(location.getX() - textRectangularSize.getWidth());
                location.setY(location.getY() + textRectangularSize.getHeight() / 2);
            }

            coordinates.setScreenLocation(location);
            coordinates.setScreenDimension(temp);

            Color color = component.getBackground().getFinalColor();
            if (color != null) {
                backgroundGraphics.setColor(color);
                backgroundGraphics.fill(new java.awt.Rectangle(location.toPoint(), temp.toSwing()));
            }
        }
    }
}
