package codes.laivy.engine.graphics.layout.responsive.disposition;

import codes.laivy.engine.coordinates.Location;
import codes.laivy.engine.coordinates.dimension.Dimension;
import codes.laivy.engine.graphics.components.GameComponent;
import codes.laivy.engine.graphics.components.TextComponent;
import codes.laivy.engine.graphics.layout.GameLayout;
import codes.laivy.engine.graphics.layout.responsive.ResponsiveLayout;
import codes.laivy.engine.utils.MathUtils;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.font.GlyphVector;

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
    public void render(@NotNull Graphics2D graphics) {
        Graphics2D renderingGraphics = (Graphics2D) graphics.create();
        Graphics2D backgroundGraphics = (Graphics2D) graphics.create();

        // Coordinates
        GameLayout.LayoutCoordinates coordinates = resolutionFix();
        //

        // Text resolution fix
        float size = (float) MathUtils.rthree(getLayout().getReferenceSize().getWidth(), getComponent().getSize(), getLayout().getWindow().getAvailableSize().getWidth());
        Font font = getComponent().getFont().deriveFont(size);

        renderingGraphics.setRenderingHint(
                RenderingHints.KEY_FRACTIONALMETRICS,
                RenderingHints.VALUE_FRACTIONALMETRICS_ON);


        FontMetrics metrics = renderingGraphics.getFontMetrics(font);

        int width = metrics.stringWidth(getComponent().getText());
        int height = metrics.getHeight();

        coordinates.setDimension(new Dimension(width, height));
        //

        // Background
        renderBackground(backgroundGraphics, coordinates);
        backgroundGraphics.dispose();
        //

        // Component alignment
        alignment(renderingGraphics, getComponent().getAlign(), coordinates);
        //

        // Component rendering
        renderingGraphics.setColor(getComponent().getColor());

        if (getComponent().getStroke() != null) {
            renderingGraphics.setStroke(getComponent().getStroke());
        }

        if (getComponent().getOpacity() != 100) {
            renderingGraphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, getComponent().getOpacity() / 100F));
        }

        draw(renderingGraphics, coordinates.getClientLocation(), coordinates.getClientDimension());

        renderingGraphics.dispose();
        //

        // Hitbox defining
        getComponent().setScreenLocation(coordinates.getScreenLocation());
        getComponent().setScreenDimension(coordinates.getScreenDimension());
        //
    }

    @Override
    public void draw(@NotNull Graphics2D graphics, @NotNull Location location, @NotNull Dimension dimension) {
        Font font = getComponent().getFont().deriveFont((float) MathUtils.rthree(getLayout().getReferenceSize().getWidth(), getComponent().getSize(), getLayout().getWindow().getAvailableSize().getWidth()));

        graphics.setFont(font);
        graphics.drawString(getComponent().getText(), location.getX(), location.getY());
    }

    @Override
    public void alignment(@NotNull Graphics2D renderingGraphics, @NotNull GameComponent.Alignment alignment, @NotNull GameLayout.LayoutCoordinates coords) {
        Dimension dimension = getComponent().getDimension(alignment.getTransform());

        renderingGraphics.transform(alignment.getTransform());
        if (alignment == GameComponent.Alignment.FLIPPED_HORIZONTALLY) {
            coords.getClientLocation().setX(-coords.getScreenLocation().getX() - calculateWidthOffset(dimension.getWidth()));
        } else if (alignment == GameComponent.Alignment.FLIPPED_VERTICALLY) {
            coords.getClientLocation().setY(-coords.getScreenLocation().getY());
        } else if (alignment == GameComponent.Alignment.FLIPPED_VERTICALLY_HORIZONTALLY) {
            coords.getClientLocation().setX(-coords.getScreenLocation().getX() - calculateWidthOffset(dimension.getWidth()));
            coords.getClientLocation().setY(-coords.getScreenLocation().getY());
        }
    }

    @Override
    public void renderBackground(@NotNull Graphics2D backgroundGraphics, GameLayout.@NotNull LayoutCoordinates coordinates) {
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
