package codes.laivy.engine.graphics.layout.responsive.disposition;

import codes.laivy.engine.coordinates.Location;
import codes.laivy.engine.coordinates.dimension.Dimension;
import codes.laivy.engine.graphics.components.GameComponent;
import codes.laivy.engine.graphics.components.GameComponent.Alignment;
import codes.laivy.engine.graphics.components.ImageComponent;
import codes.laivy.engine.graphics.components.TextComponent;
import codes.laivy.engine.graphics.components.shape.EllipseComponent;
import codes.laivy.engine.graphics.components.shape.RectangleComponent;
import codes.laivy.engine.graphics.components.shape.ShapeComponent;
import codes.laivy.engine.graphics.layout.ComponentDisposition;
import codes.laivy.engine.graphics.layout.GameLayout;
import codes.laivy.engine.graphics.layout.responsive.ResponsiveLayout;
import codes.laivy.engine.graphics.window.GameWindow;
import codes.laivy.engine.utils.MathUtils;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.geom.Ellipse2D;

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
        Graphics2D backgroundGraphics = (Graphics2D) graphics.create();

        // Coordinates
        GameLayout.LayoutCoordinates coordinates = resolutionFix();
        //

        // Background
        renderBackground(backgroundGraphics, coordinates);
        backgroundGraphics.dispose();
        //

        // Component alignment
        alignment(renderingGraphics, getComponent().getAlign(), coordinates);
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

        int offsetX = calculateWidthOffset(component.getOffsetX());
        int offsetY = calculateHeightOffset(component.getOffsetY());

        if ((window.getAvailableSize().getWidth() != getLayout().getReferenceSize().getWidth() || window.getAvailableSize().getHeight() != getLayout().getReferenceSize().getHeight())) {
            if (getLayout().isCubicResizing()) {
                coordinates.setDimension(new Dimension(
                        (int) MathUtils.rthree(getLayout().getReferenceSize().getWidth() + getLayout().getReferenceSize().getHeight(), defaultCompDimension.getWidth(), window.getAvailableSize().getWidth() + window.getAvailableSize().getHeight()),
                        (int) MathUtils.rthree(getLayout().getReferenceSize().getWidth() + getLayout().getReferenceSize().getHeight(), defaultCompDimension.getHeight(), window.getAvailableSize().getWidth() + window.getAvailableSize().getHeight())
                ));
            } else {
                coordinates.setDimension(new Dimension(
                        (int) MathUtils.rthree(getLayout().getReferenceSize().getWidth(), defaultCompDimension.getWidth(), window.getAvailableSize().getWidth()),
                        (int) MathUtils.rthree(getLayout().getReferenceSize().getHeight(), defaultCompDimension.getHeight(), window.getAvailableSize().getHeight())
                ));
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
                offsetY = (int) MathUtils.rthree(getLayout().getReferenceSize().getWidth(), offsetY, getLayout().getWindow().getAvailableSize().getWidth());
            }
        }
        return offsetY;
    }
    // ---/-/--- //
    // Utilities //
    // ---/-/--- //

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
    public static class Ellipse extends Shape {
        public Ellipse(@NotNull EllipseComponent component, @NotNull ResponsiveLayout layout) {
            super(component, layout);
        }

        @Override
        public @NotNull EllipseComponent getComponent() {
            return (EllipseComponent) super.getComponent();
        }

        @Override
        public void draw(@NotNull Graphics2D graphics, @NotNull Location location, @NotNull Dimension dimension) {
            graphics.draw(getComponent().getShape(location, dimension));
        }

        @Override
        public void renderBackground(@NotNull Graphics2D backgroundGraphics, GameLayout.@NotNull LayoutCoordinates coordinates) {
            Dimension temp = coordinates.getClientDimension().clone();
            Location location = coordinates.getClientLocation().clone();

            coordinates.setScreenLocation(location);
            coordinates.setScreenDimension(temp);

            Color color = component.getBackground().getFinalColor();
            if (color != null) {
                backgroundGraphics.setColor(color);
                backgroundGraphics.fill(getComponent().getShape(location, temp));
            }
        }

        @Override
        public void alignment(@NotNull Graphics2D renderingGraphics, @NotNull Alignment alignment, @NotNull GameLayout.LayoutCoordinates coords) {
            Dimension dimension = coords.getScreenDimension();

            renderingGraphics.transform(alignment.getTransform());
            if (alignment == Alignment.FLIPPED_HORIZONTALLY) {
                coords.getClientLocation().setX(-coords.getScreenLocation().getX() - dimension.getWidth());
            } else if (alignment == Alignment.FLIPPED_VERTICALLY) {
                coords.getClientLocation().setY(-coords.getScreenLocation().getY() - dimension.getHeight());
            } else if (alignment == Alignment.FLIPPED_VERTICALLY_HORIZONTALLY) {
                coords.getClientLocation().setX(-coords.getScreenLocation().getX() - dimension.getWidth());
                coords.getClientLocation().setY(-coords.getScreenLocation().getY() - dimension.getHeight());
            }
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
            Font font = getComponent().getFont().deriveFont((float) MathUtils.rthree(getLayout().getReferenceSize().getWidth() + getLayout().getReferenceSize().getHeight(), getComponent().getFont().getSize(), getLayout().getWindow().getAvailableSize().getWidth() + getLayout().getWindow().getAvailableSize().getHeight()));

            graphics.setFont(font);
            graphics.drawString(getComponent().getText(), location.getX(), location.getY());
        }

        @Override
        public void alignment(@NotNull Graphics2D renderingGraphics, @NotNull Alignment alignment, @NotNull GameLayout.LayoutCoordinates coords) {
            Dimension dimension = getComponent().getDimension(alignment.getTransform());

            renderingGraphics.transform(alignment.getTransform());
            if (alignment == Alignment.FLIPPED_HORIZONTALLY) {
                coords.getClientLocation().setX(-coords.getScreenLocation().getX() - calculateWidthOffset(dimension.getWidth()));
            } else if (alignment == Alignment.FLIPPED_VERTICALLY) {
                coords.getClientLocation().setY(-coords.getScreenLocation().getY());
            } else if (alignment == Alignment.FLIPPED_VERTICALLY_HORIZONTALLY) {
                coords.getClientLocation().setX(-coords.getScreenLocation().getX() - calculateWidthOffset(dimension.getWidth()));
                coords.getClientLocation().setY(-coords.getScreenLocation().getY());
            }
        }

        @Override
        public void renderBackground(@NotNull Graphics2D backgroundGraphics, GameLayout.@NotNull LayoutCoordinates coordinates) {
            Dimension temp = coordinates.getClientDimension().clone();

            Location location = coordinates.getClientLocation().clone();
            location.setY(location.getY() - temp.getHeight());

            coordinates.setScreenLocation(location);
            coordinates.setScreenDimension(temp);

            Color color = component.getBackground().getFinalColor();
            if (color != null) {
                backgroundGraphics.setColor(color);
                backgroundGraphics.fill(new java.awt.Rectangle(location.toPoint(), temp.toSwing()));
            }
        }
    }
    public static class Image extends ResponsiveDisposition {
        public Image(@NotNull ImageComponent component, @NotNull ResponsiveLayout layout) {
            super(component, layout);
        }

        @Override
        public @NotNull ImageComponent getComponent() {
            return (ImageComponent) super.getComponent();
        }

        @Override
        public void draw(@NotNull Graphics2D graphics, @NotNull Location location, @NotNull Dimension dimension) {
            graphics.drawImage(getComponent().getAsset().toBuffered(), location.getX(), location.getY(), dimension.getWidth(), dimension.getHeight(), getLayout().getWindow().getPanel());
        }

        @Override
        public void alignment(@NotNull Graphics2D renderingGraphics, @NotNull Alignment alignment, @NotNull GameLayout.LayoutCoordinates coords) {
            Dimension dimension = getComponent().getDimension(null);

            renderingGraphics.transform(alignment.getTransform());
            if (alignment == Alignment.FLIPPED_HORIZONTALLY) {
                coords.getClientLocation().setX(-coords.getScreenLocation().getX() - calculateWidthOffset(dimension.getWidth()));
            } else if (alignment == Alignment.FLIPPED_VERTICALLY) {
                coords.getClientLocation().setY(-coords.getScreenLocation().getY() - calculateWidthOffset(dimension.getWidth()));
            } else if (alignment == Alignment.FLIPPED_VERTICALLY_HORIZONTALLY) {
                coords.getClientLocation().setX(-coords.getScreenLocation().getX() - calculateWidthOffset(dimension.getWidth()));
                coords.getClientLocation().setY(-coords.getScreenLocation().getY() - calculateWidthOffset(dimension.getWidth()));
            }
        }

        @Override
        public void renderBackground(@NotNull Graphics2D backgroundGraphics, GameLayout.@NotNull LayoutCoordinates coordinates) {
            Dimension temp = coordinates.getClientDimension().clone();

            Location location = coordinates.getClientLocation().clone();
            location.setY((location.getY() - temp.getHeight()) + temp.getHeight());

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
