package codes.laivy.engine.graphics.layout.responsive.disposition;

import codes.laivy.engine.coordinates.Location;
import codes.laivy.engine.coordinates.dimension.Dimension;
import codes.laivy.engine.graphics.components.GameComponent;
import codes.laivy.engine.graphics.components.TextComponent;
import codes.laivy.engine.graphics.layout.GameLayout;
import codes.laivy.engine.graphics.layout.GameLayoutBounds;
import codes.laivy.engine.graphics.layout.responsive.ResponsiveLayout;
import codes.laivy.engine.utils.MathUtils;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class ResponsiveTextDisposition extends ResponsiveDisposition {
    public ResponsiveTextDisposition(@NotNull TextComponent component, @NotNull ResponsiveLayout layout) {
        super(component, layout);
    }

    @Override
    @Contract(pure = true)
    public @NotNull TextComponent getComponent() {
        return (TextComponent) super.getComponent();
    }

    @Override
    public void postResolutionFix(@NotNull Graphics2D renderingGraphics, @NotNull Graphics2D backgroundGraphics, GameLayout.@NotNull LayoutCoordinates coordinates, @NotNull GameLayoutBounds bounds) {
        // Text resolution fix
        float size = (float) MathUtils.rthree(getLayout().getReferenceSize().getWidth(), getComponent().getSize(), bounds.getAvailable().getWidth());
        Font font = getComponent().getFont().deriveFont(size);

        FontMetrics metrics = renderingGraphics.getFontMetrics(font);

        int width = metrics.stringWidth(getComponent().getText());
        int height = metrics.getHeight();

        coordinates.setDimension(new Dimension(width, height));
        //
    }

    @Override
    public void drawObject(@NotNull Graphics2D graphics, @NotNull Location location, @NotNull Dimension dimension, @NotNull GameLayoutBounds bounds) {
        Font font = getComponent().getFont().deriveFont((float) MathUtils.rthree(getLayout().getReferenceSize().getWidth(), getComponent().getSize(), bounds.getAvailable().getWidth()));

        graphics.setFont(font);
        graphics.drawString(getComponent().getText(), location.getX(), location.getY());
    }

    @Override
    public void alignment(@NotNull Graphics2D renderingGraphics, @NotNull GameComponent.Alignment alignment, @NotNull GameLayout.LayoutCoordinates coords, @NotNull GameLayoutBounds bounds) {
        Dimension dimension = getComponent().getDimension(alignment.getTransform());

        renderingGraphics.transform(alignment.getTransform());
        if (alignment == GameComponent.Alignment.FLIPPED_HORIZONTALLY) {
            coords.getClientLocation().setX(-coords.getScreenLocation().getX() - calculateWidthOffset(dimension.getWidth(), bounds));
        } else if (alignment == GameComponent.Alignment.FLIPPED_VERTICALLY) {
            coords.getClientLocation().setY(-coords.getScreenLocation().getY());
        } else if (alignment == GameComponent.Alignment.FLIPPED_VERTICALLY_HORIZONTALLY) {
            coords.getClientLocation().setX(-coords.getScreenLocation().getX() - calculateWidthOffset(dimension.getWidth(), bounds));
            coords.getClientLocation().setY(-coords.getScreenLocation().getY());
        }
    }

    @Override
    public void drawBackground(@NotNull Graphics2D backgroundGraphics, GameLayout.@NotNull LayoutCoordinates coordinates, @NotNull GameLayoutBounds bounds) {
        Dimension temp = coordinates.getClientDimension().clone();

        Location location = coordinates.getClientLocation().clone();
        location.setY(location.getY() - temp.getHeight());

        coordinates.setScreenLocation(location);
        coordinates.setScreenDimension(temp);

        Color color = component.getBackground().getFinalColor();
        if (color != null) {
            backgroundGraphics.setColor(color);
            backgroundGraphics.fill(new Rectangle(location.toPoint(), temp.toSwing()));
        }
    }
}
