package codes.laivy.engine.graphics.components.shape;

import codes.laivy.engine.Game;
import codes.laivy.engine.coordinates.Location;
import codes.laivy.engine.coordinates.dimension.Dimension;
import org.jetbrains.annotations.NotNull;

import java.awt.geom.Ellipse2D;

public class EllipseComponent extends ShapeComponent {

    public EllipseComponent(@NotNull Game game, boolean filled, @NotNull Location location, @NotNull Dimension dimension) {
        super(game, new Ellipse2D.Float(location.getX(), location.getY(), dimension.getWidth(), dimension.getHeight()), filled, location);
    }

    @Override
    public @NotNull Ellipse2D getShape(@NotNull Location location, @NotNull Dimension dimension) {
        return new Ellipse2D.Float(location.getX(), location.getY(), dimension.getWidth(), dimension.getHeight());
    }
    @Override
    public @NotNull Ellipse2D getShape() {
        return (Ellipse2D) super.getShape();
    }

    @Override
    public boolean collides(@NotNull Location location) {
        Location sLoc = getScreenLocation(getGame().getWindow());
        Dimension sDim = getScreenDimension(getGame().getWindow());

        int centerX = sLoc.getX() + (sDim.getWidth() / 2);
        int centerY = sLoc.getY() + (sDim.getHeight() / 2);

        double a = sDim.getWidth() / 2D; // Semi eixo maior
        double b = sDim.getHeight() / 2D; // Semi eixo menor

        return ((Math.pow(location.getX() - centerX, 2))/(Math.pow(a, 2)) + (Math.pow(location.getY() - centerY, 2))/(Math.pow(b, 2))) <= 1;
    }

    @Override
    public @NotNull EllipseComponent clone() {
        return (EllipseComponent) super.clone();
    }
}
