package codes.laivy.engine.coordinates;

import codes.laivy.engine.annotations.WindowThread;
import codes.laivy.engine.graphics.components.GameComponent;
import codes.laivy.engine.graphics.window.GameWindow;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Objects;
import java.util.Set;

/**
 * @author ItsLaivy
 * @since 1.0 build 0 (~01/07/2022)
 */
public class Location implements Cloneable {

    protected int x, y;

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public Location(@NotNull Point point) {
        this.x = point.x;
        this.y = point.y;
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

    @NotNull
    @Contract("_ -> this")
    public Location subtract(@NotNull Location location) {
        return this.subtract(location.getX(), location.getY());
    }
    @NotNull
    @Contract("_, _ -> this")
    public Location subtract(int x, int y) {
        setX(getX() - x);
        setY(getY() - y);
        return this;
    }

    @NotNull
    @Contract("_ -> this")
    public Location add(@NotNull Location location) {
        return this.add(location.getX(), location.getY());
    }
    @NotNull
    @Contract("_, _ -> this")
    public Location add(int x, int y) {
        setX(getX() + x);
        setY(getY() + y);
        return this;
    }

    @NotNull
    @Contract("_ -> this")
    public Location multiply(int value) {
        setX(getX() * value);
        setY(getY() * value);
        return this;
    }

    @WindowThread
    public @NotNull Set<@NotNull GameComponent> getComponents(@NotNull GameWindow window) {
        return window.getPanel().getComponents(this);
    }

    @NotNull
    @Contract(value = "-> new")
    public Point toPoint() {
        return new Point(x, y);
    }

    @Override
    public String toString() {
        return "Location[x:" + x + ",y:" + y + "]";
    }

    @Override
    @Contract(value = "null -> false")
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return x == location.x && y == location.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    @Contract("-> new")
    public Location clone() {
        try {
            return (Location) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

}
