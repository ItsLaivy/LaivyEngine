package codes.laivy.engine.graphics.layout.responsive.disposition;

import codes.laivy.engine.coordinates.Location;
import codes.laivy.engine.coordinates.dimension.Dimension;
import codes.laivy.engine.graphics.components.GameComponent;
import codes.laivy.engine.graphics.components.TextComponent;
import codes.laivy.engine.graphics.components.shape.RectangleComponent;
import codes.laivy.engine.graphics.components.shape.ShapeComponent;
import codes.laivy.engine.graphics.layout.ComponentDisposition;
import codes.laivy.engine.graphics.layout.responsive.ResponsiveLayout;
import codes.laivy.engine.utils.MathUtils;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public abstract class ResponsiveDisposition extends ComponentDisposition {

    private final @NotNull ResponsiveLayout layout;

    public ResponsiveDisposition(@NotNull GameComponent component, @NotNull ResponsiveLayout layout) {
        super(component);
        this.layout = layout;
    }

    public @NotNull ResponsiveLayout getLayout() {
        return layout;
    }

    /**
     * That's a copy of the original graphics method, used to render the component on the screen
     * @param graphics the graphics
     * @param location the sreen location of the component
     * @param dimension the screen dimension of the component
     */
    public abstract void render(@NotNull Graphics2D graphics, @NotNull Location location, @NotNull Dimension dimension);

    public void renderBackground(@NotNull Graphics2D graphics, @NotNull Location location, @NotNull Dimension dimension) {
        Color color = component.getBackground().getFinalColor();
        if (color != null) {
            graphics.setColor(color);
            graphics.fill(new java.awt.Rectangle(location.toPoint(), dimension.toSwing()));
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
        public void render(@NotNull Graphics2D graphics, @NotNull Location location, @NotNull Dimension dimension) {
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
        public void render(@NotNull Graphics2D graphics, @NotNull Location location, @NotNull Dimension dimension) {
            Font font = getComponent().getFont().deriveFont(MathUtils.rthree(getLayout().getReferenceSize().getWidth() + getLayout().getReferenceSize().getHeight(), getComponent().getFont().getSize(), getLayout().getWindow().getAvailableSize().getWidth() + getLayout().getWindow().getAvailableSize().getHeight()));

            graphics.setFont(font);
            graphics.drawString(getComponent().getText(), location.getX(), location.getY());
        }
    }
}
