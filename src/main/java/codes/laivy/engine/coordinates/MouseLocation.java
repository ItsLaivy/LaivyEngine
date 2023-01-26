package codes.laivy.engine.coordinates;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

public final class MouseLocation {

    private final @Nullable Location location;

    public MouseLocation(@Nullable Location location) {
        this.location = location;
    }

    /**
     * Returns the mouse location
     * @return The mouse location or null if the mouse isn't on the screen
     */
    @Contract(pure = true)
    public @Nullable Location getLocation() {
        return location;
    }

    /**
     * Returns if the mouse is on the screen or not
     * @return true if the mouse pointer is on the screen
     */
    public boolean isOnScreen() {
        return location != null;
    }

    @Override
    public String toString() {
        if (getLocation() != null) {
            return "MouseLocation[x:" + getLocation().getY() + ",y:" + getLocation().getX() + "]";
        } else {
            return super.toString();
        }
    }
}
