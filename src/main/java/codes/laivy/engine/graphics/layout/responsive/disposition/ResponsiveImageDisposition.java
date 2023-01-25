package codes.laivy.engine.graphics.layout.responsive.disposition;

import codes.laivy.engine.coordinates.Location;
import codes.laivy.engine.graphics.components.GameComponent;
import codes.laivy.engine.graphics.components.ImageComponent;
import codes.laivy.engine.graphics.layout.GameLayout;
import codes.laivy.engine.graphics.layout.responsive.ResponsiveLayout;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class ResponsiveImageDisposition extends ResponsiveDisposition {
    public ResponsiveImageDisposition(@NotNull ImageComponent component, @NotNull ResponsiveLayout layout) {
        super(component, layout);
    }

    @Override
    @Contract(pure = true)
    public @NotNull ImageComponent getComponent() {
        return (ImageComponent) super.getComponent();
    }

    @Override
    public void draw(@NotNull Graphics2D graphics, @NotNull Location location, @NotNull codes.laivy.engine.coordinates.dimension.Dimension dimension) {
        graphics.drawImage(getComponent().getAsset().toBuffered(), location.getX(), location.getY(), dimension.getWidth(), dimension.getHeight(), getLayout().getWindow().getPanel());
    }

    @Override
    public void alignment(@NotNull Graphics2D renderingGraphics, @NotNull GameComponent.Alignment alignment, @NotNull GameLayout.LayoutCoordinates coords) {
        codes.laivy.engine.coordinates.dimension.Dimension dimension = getComponent().getDimension();

        renderingGraphics.transform(alignment.getTransform());
        if (alignment == GameComponent.Alignment.FLIPPED_HORIZONTALLY) {
            coords.getClientLocation().setX(-coords.getScreenLocation().getX() - calculateWidthOffset(dimension.getWidth()));
        } else if (alignment == GameComponent.Alignment.FLIPPED_VERTICALLY) {
            coords.getClientLocation().setY(-coords.getScreenLocation().getY() - calculateWidthOffset(dimension.getWidth()));
        } else if (alignment == GameComponent.Alignment.FLIPPED_VERTICALLY_HORIZONTALLY) {
            coords.getClientLocation().setX(-coords.getScreenLocation().getX() - calculateWidthOffset(dimension.getWidth()));
            coords.getClientLocation().setY(-coords.getScreenLocation().getY() - calculateWidthOffset(dimension.getWidth()));
        }
    }

    @Override
    public void renderBackground(@NotNull Graphics2D backgroundGraphics, GameLayout.@NotNull LayoutCoordinates coordinates) {
        codes.laivy.engine.coordinates.dimension.Dimension temp = coordinates.getClientDimension().clone();

        Location location = coordinates.getClientLocation().clone();
        location.setY((location.getY() - temp.getHeight()) + temp.getHeight());

        coordinates.setScreenLocation(location);
        coordinates.setScreenDimension(temp);

        Color color = component.getBackground().getFinalColor();
        if (color != null) {
            backgroundGraphics.setColor(color);
            backgroundGraphics.fill(new Rectangle(location.toPoint(), temp.toSwing()));
        }
    }
}
