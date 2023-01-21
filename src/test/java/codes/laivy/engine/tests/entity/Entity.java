package codes.laivy.engine.tests.entity;

import codes.laivy.engine.coordinates.Location;
import codes.laivy.engine.coordinates.dimension.Dimension;
import codes.laivy.engine.graphics.components.shape.RectangleComponent;
import codes.laivy.engine.graphics.layout.responsive.ResponsiveLayout;
import codes.laivy.engine.graphics.layout.responsive.disposition.ResponsiveDisposition;
import codes.laivy.engine.tests.TestGame;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.awt.*;
import java.util.Objects;

public abstract class Entity {

    private final @NotNull RectangleComponent component;
    private @Range(from = 0, to = 20) int speed = 10;

    public Entity(@NotNull Location location, @NotNull Color color) {
        this.component = new RectangleComponent(TestGame.instance(), true, location, new Dimension(35, 35));
        this.component.setColor(color);
        this.component.getBackground().setColor(color);
        this.component.setDisposition(new ResponsiveDisposition.Rectangle(this.component, (ResponsiveLayout) Objects.requireNonNull(TestGame.instance().getWindow().getLayout())));
        getComponent().getGame().getGraphics().runWindowThreadLater(component::add);
    }

    public int getSpeed() {
        return speed;
    }
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    protected @NotNull RectangleComponent getComponent() {
        return component;
    }

    public @NotNull Location getLocation() {
        return getComponent().getLocation();
    }
    public void setLocation(@NotNull Location location) {
        getComponent().setLocation(location);
    }

}
