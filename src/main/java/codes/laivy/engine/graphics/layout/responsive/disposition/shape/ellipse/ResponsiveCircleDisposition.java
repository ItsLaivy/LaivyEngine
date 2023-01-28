package codes.laivy.engine.graphics.layout.responsive.disposition.shape.ellipse;

import codes.laivy.engine.graphics.components.shape.ellipse.CircleComponent;
import codes.laivy.engine.graphics.layout.responsive.ResponsiveLayout;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class ResponsiveCircleDisposition extends ResponsiveEllipseDisposition {
    public ResponsiveCircleDisposition(@NotNull CircleComponent component, @NotNull ResponsiveLayout layout) {
        super(component, layout);
    }

    @Override
    @Contract(pure = true)
    public @NotNull CircleComponent getComponent() {
        return (CircleComponent) super.getComponent();
    }
}
