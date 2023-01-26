package codes.laivy.engine.coordinates.dimension;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.util.Objects;

public class Dimension implements Cloneable, IDimension {

    private int width, height;

    public Dimension(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public int getWidth() {
        return width;
    }
    @Override
    public void setWidth(@Range(from = 0, to = Integer.MAX_VALUE) int width) {
        this.width = width;
    }
    @Override
    public int getHeight() {
        return height;
    }
    @Override
    public void setHeight(@Range(from = 0, to = Integer.MAX_VALUE) int height) {
        this.height = height;
    }

    @Contract(value = "_ -> this")
    public @NotNull Dimension subtract(@NotNull Dimension dimension) {
        setHeight(getHeight() - dimension.getHeight());
        setWidth(getWidth() - dimension.getWidth());
        return this;
    }
    @Contract(value = "_ -> this")
    public @NotNull Dimension add(@NotNull Dimension dimension) {
        setHeight(getHeight() + dimension.getHeight());
        setWidth(getWidth() + dimension.getWidth());
        return this;
    }

    @Contract(value = "_, _ -> this")
    public @NotNull Dimension multiply(float width, float height) {
        setHeight((int) (getHeight() * height));
        setWidth((int) (getWidth() * width));
        return this;
    }
    @Contract(value = "_ -> this")
    public @NotNull Dimension multiply(float value) {
        return this.multiply(value, value);
    }

    @Contract(value = "_ -> this")
    public @NotNull Dimension divide(float value) {
        setHeight((int) (getHeight() / value));
        setWidth((int) (getWidth() / value));
        return this;
    }

    @Override
    public String toString() {
        return "Dimension[width:" + getWidth() + ",height:" + getHeight() + "]";
    }

    @Override
    @Contract("null -> false")
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dimension dimension = (Dimension) o;
        return getWidth() == dimension.getWidth() && getHeight() == dimension.getHeight();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getWidth(), getHeight());
    }

    @Override
    @Contract("-> new")
    public Dimension clone() {
        try {
            return (Dimension) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
