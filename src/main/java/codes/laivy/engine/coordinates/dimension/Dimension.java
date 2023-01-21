package codes.laivy.engine.coordinates.dimension;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Dimension implements Cloneable, IDimension {

    private int width, height;

    public Dimension(int width, int height) {
        this.width = width;
        this.height = height;
    }
    public Dimension(@NotNull java.awt.Dimension jDimension) {
        this(jDimension.width, jDimension.height);
    }

    @Override
    public int getWidth() {
        return width;
    }
    @Override
    public void setWidth(int width) {
        if (height < 0) {
            throw new IllegalArgumentException("A dimensão não pode ser menor que zero");
        }
        this.width = width;
    }
    @Override
    public int getHeight() {
        return height;
    }
    @Override
    public void setHeight(int height) {
        if (height < 0) {
            throw new IllegalArgumentException("A dimensão não pode ser menor que zero");
        }
        this.height = height;
    }

    @Override
    public String toString() {
        return "Dimension[width:" + getWidth() + ",height:" + getHeight() + "]";
    }

    @Override
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
    public Dimension clone() {
        try {
            return (Dimension) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
