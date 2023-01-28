package codes.laivy.engine.graphics.components.shape.ellipse;

import codes.laivy.engine.coordinates.Location;
import codes.laivy.engine.coordinates.dimension.Dimension;
import codes.laivy.engine.graphics.window.swing.GamePanel;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * @author ItsLaivy
 * @since 1.0 build 0 (22/01/2023)
 */
public class CircleComponent extends EllipseComponent {

    public CircleComponent(@NotNull GamePanel panel, boolean filled, @NotNull Location location, int diameter) {
        this(panel, filled, location, diameter, 0, 0, 100);
    }
    public CircleComponent(@NotNull GamePanel panel, boolean filled, @NotNull Location location, int diameter, int offsetX, int offsetY, int opacity) {
        super(panel, filled, location, new Dimension(diameter, diameter), offsetX, offsetY, opacity);
    }

    @Override
    @Contract("-> new")
    public @NotNull CircleComponent clone() {
        return (CircleComponent) super.clone();
    }

}
