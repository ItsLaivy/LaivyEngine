package codes.laivy.engine.graphics.components.shape;

import codes.laivy.engine.coordinates.Location;
import codes.laivy.engine.coordinates.dimension.Dimension;
import codes.laivy.engine.graphics.window.swing.GamePanel;
import org.jetbrains.annotations.NotNull;

public class CircleComponent extends EllipseComponent {

    public CircleComponent(@NotNull GamePanel panel, boolean filled, @NotNull Location location, int diameter) {
        super(panel, filled, location, new Dimension(diameter, diameter));
    }

    @Override
    public @NotNull CircleComponent clone() {
        return (CircleComponent) super.clone();
    }

}
