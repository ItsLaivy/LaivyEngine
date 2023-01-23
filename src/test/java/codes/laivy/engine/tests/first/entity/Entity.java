package codes.laivy.engine.tests.first.entity;

import codes.laivy.engine.coordinates.Location;
import codes.laivy.engine.coordinates.dimension.Dimension;
import codes.laivy.engine.graphics.components.shape.*;
import codes.laivy.engine.graphics.layout.responsive.ResponsiveLayout;
import codes.laivy.engine.graphics.layout.responsive.disposition.ResponsiveDisposition;
import codes.laivy.engine.tests.first.TestGame;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.awt.*;
import java.util.Objects;

public abstract class Entity {

    private final @NotNull ShapeComponent component;
    private @Range(from = 0, to = 20) int speed = 10;

    public Entity(@NotNull Location location, @NotNull Color color) {
        this.component = new RoundRectangleComponent(TestGame.instance().getWindow().getPanel(), false, location, new Dimension(150, 150), new Dimension(50, 50));
        this.component.setColor(color);
        this.component.getBackground().setColor(Color.WHITE);
        this.component.getBackground().setOpacity(20);
        //this.component.setStroke(10);
        this.component.setDisposition(new ResponsiveDisposition.RoundRectangle((RoundRectangleComponent) this.component, (ResponsiveLayout) Objects.requireNonNull(TestGame.instance().getWindow().getLayout())));
        //this.component.setAlign(GameComponent.Alignment.FLIPPED_VERTICALLY_HORIZONTALLY);
        getSquare().getPanel().getWindow().getGame().getGraphics().runWindowThreadLater(component::add);
    }

    public int getSpeed() {
        return speed;
    }
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    protected @NotNull ShapeComponent getSquare() {
        return component;
    }

    public @NotNull Location getLocation() {
        return getSquare().getLocation().clone();
    }
    public void setLocation(@NotNull Location location) {
        getSquare().setLocation(location);
    }

}
