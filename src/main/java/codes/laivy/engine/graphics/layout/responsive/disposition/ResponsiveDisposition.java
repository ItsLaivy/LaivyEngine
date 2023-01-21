package codes.laivy.engine.graphics.layout.responsive.disposition;

import codes.laivy.engine.coordinates.Location;
import codes.laivy.engine.coordinates.dimension.Dimension;
import codes.laivy.engine.graphics.components.GameComponent;
import codes.laivy.engine.graphics.components.shape.RectangleComponent;
import codes.laivy.engine.graphics.components.shape.ShapeComponent;
import codes.laivy.engine.graphics.layout.ComponentDisposition;
import codes.laivy.engine.graphics.layout.responsive.ResponsiveLayout;
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
}
