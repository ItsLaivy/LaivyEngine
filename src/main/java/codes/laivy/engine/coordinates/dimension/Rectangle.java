package codes.laivy.engine.coordinates.dimension;

import codes.laivy.engine.coordinates.Location;
import org.jetbrains.annotations.NotNull;

public class Rectangle implements IDimension {

    private int x, y, width, height;

    public Rectangle(@NotNull Location location, @NotNull Dimension dimension) {
        this.width = dimension.getWidth();
        this.height = dimension.getHeight();
        this.x = location.getX();
        this.y = location.getY();
    }

    public boolean contains(@NotNull Location location) {
        return
                location.getX() >= getAsLocation().getX() &&
                location.getY() >= getAsLocation().getY() &&
                location.getX() <= getAsLocation().getX() + getWidth() &&
                location.getY() <= getAsLocation().getY() + getHeight();
    }

    @NotNull
    public Location getAsLocation() {
        return new Location(x, y);
    }
    @NotNull
    public Dimension getAsDimension() {
        return new Dimension(width, height);
    }

    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void setHeight(int height) {
        this.height = height;
    }

}
