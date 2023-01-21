package codes.laivy.engine.coordinates.dimension;

import codes.laivy.engine.coordinates.Location;
import org.jetbrains.annotations.NotNull;

public interface IDimension {
    
    int getWidth();
    void setWidth(int width);

    int getHeight();
    void setHeight(int height);

    @NotNull
    default java.awt.Dimension toSwing() {
        return new java.awt.Dimension(getWidth(), getHeight());
    }
    default boolean contains(@NotNull Location thisComponent, @NotNull Location location) {
        return
                location.getX() >= thisComponent.getX() &&
                location.getY() >= thisComponent.getY() &&
                location.getX() <= thisComponent.getX() + getWidth() &&
                location.getY() <= thisComponent.getY() + getHeight();
    }
    default boolean contains(@NotNull Location thisComponent, @NotNull Location otherComponent, @NotNull IDimension dimension) {
        if (this.getTopRight(thisComponent).getY() < dimension.getBottomLeft(otherComponent).getY() || this.getBottomLeft(thisComponent).getY() > dimension.getTopRight(otherComponent).getY()) {
            return false;
        } if (this.getTopRight(thisComponent).getX() < dimension.getBottomLeft(otherComponent).getX() || this.getBottomLeft(thisComponent).getX() > dimension.getTopRight(otherComponent).getX()) {
            return false;
        }
        return true;
    }

    @NotNull
    default Location getTopLeft(@NotNull Location location) {
        return location;
    }
    @NotNull
    default Location getTopRight(@NotNull Location location) {
        location = location.clone();
        location.setX(location.getX() + getWidth());
        return location;
    }
    @NotNull
    default Location getBottomLeft(@NotNull Location location) {
        location = location.clone();
        location.setY(location.getY() + getHeight());
        return location;
    }
    @NotNull
    default Location getBottomRight(@NotNull Location location) {
        location = location.clone();
        location.setX(location.getX() + getWidth());
        location.setY(location.getY() + getHeight());
        return location;
    }

    @NotNull
    default IDimension subtract(@NotNull IDimension dimension) {
        setHeight(getHeight() - dimension.getHeight());
        setWidth(getWidth() - dimension.getWidth());
        return this;
    }
    @NotNull
    default IDimension add(@NotNull IDimension dimension) {
        setHeight(getHeight() + dimension.getHeight());
        setWidth(getWidth() + dimension.getWidth());
        return this;
    }
    
}
