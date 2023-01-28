package codes.laivy.engine.coordinates.dimension;

import codes.laivy.engine.coordinates.Location;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * @author ItsLaivy
 * @since 1.0 build 0 (~01/07/2022)
 */
public interface IDimension {
    
    int getWidth();
    void setWidth(int width);

    int getHeight();
    void setHeight(int height);

    @NotNull
    @Contract("-> new")
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
        }
        return this.getTopRight(thisComponent).getX() >= dimension.getBottomLeft(otherComponent).getX() && this.getBottomLeft(thisComponent).getX() <= dimension.getTopRight(otherComponent).getX();
    }

    @NotNull
    @Contract(pure = true)
    default Location getTopLeft(@NotNull Location location) {
        return location;
    }
    @NotNull
    @Contract(value = "_ -> new")
    default Location getTopRight(@NotNull Location location) {
        location = location.clone();
        location.setX(location.getX() + getWidth());
        return location;
    }
    @NotNull
    @Contract(value = "_ -> new")
    default Location getBottomLeft(@NotNull Location location) {
        location = location.clone();
        location.setY(location.getY() + getHeight());
        return location;
    }
    @NotNull
    @Contract(value = "_ -> new")
    default Location getBottomRight(@NotNull Location location) {
        location = location.clone();
        location.setX(location.getX() + getWidth());
        location.setY(location.getY() + getHeight());
        return location;
    }

    @NotNull
    @Contract(value = "_ -> this")
    default IDimension subtract(@NotNull IDimension dimension) {
        setHeight(getHeight() - dimension.getHeight());
        setWidth(getWidth() - dimension.getWidth());
        return this;
    }
    @NotNull
    @Contract(value = "_ -> this")
    default IDimension add(@NotNull IDimension dimension) {
        setHeight(getHeight() + dimension.getHeight());
        setWidth(getWidth() + dimension.getWidth());
        return this;
    }

    @Contract(value = "_, _ -> this")
    default @NotNull IDimension multiply(int width, int height) {
        setHeight(getHeight() * height);
        setWidth(getWidth() * width);
        return this;
    }
    @Contract(value = "_ -> this")
    default @NotNull IDimension multiply(int value) {
        return this.multiply(value, value);
    }
    
}
