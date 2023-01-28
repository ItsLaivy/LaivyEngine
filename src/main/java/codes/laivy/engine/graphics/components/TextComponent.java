package codes.laivy.engine.graphics.components;

import codes.laivy.engine.coordinates.Location;
import codes.laivy.engine.coordinates.dimension.Dimension;
import codes.laivy.engine.graphics.window.swing.GamePanel;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

/**
 * A {@link TextComponent TextComponent} is used to write something on the screen.
 */
public class TextComponent extends GameComponent {

    private @NotNull String text;
    private @NotNull Font font;

    public TextComponent(@NotNull GamePanel panel, @NotNull Location location, @NotNull String text, @NotNull Font font, @Nullable Color color) {
        this(panel, location, text, font, color, 0, 0, 100);
    }
    public TextComponent(@NotNull GamePanel panel, @NotNull Location location, @NotNull String text, @NotNull Font font, @Nullable Color color, int offsetX, int offsetY, int opacity) {
        super(panel, location, offsetX, offsetY, opacity);

        this.text = text;
        this.font = font;
        setColor(color);

        // Default dimension value
        Rectangle2D rectangle = font.getStringBounds(text, new FontRenderContext(getAlign().getTransform(), isAntiAliasing(), true));
        this.dimension = new Dimension(rectangle.getBounds().width, rectangle.getBounds().height);
        //
    }

    public @NotNull Dimension getDimension(@NotNull AffineTransform transform) {
        Rectangle2D textSize = font.getStringBounds(text, new FontRenderContext(transform, isAntiAliasing(), true));
        int textWidth = (int) textSize.getWidth();
        int textHeight = (int) textSize.getHeight();

        return new Dimension(textWidth, textHeight);
    }

    @Override
    public void setAlign(@NotNull Alignment align) {
        super.setAlign(align);
    }

    public void setSize(float size) {
        setFont(getFont().deriveFont(size));
    }
    public int getSize() {
        return getFont().getSize();
    }

    public @NotNull String getText() {
        return text;
    }
    public void setText(@NotNull String text) {
        this.text = text;
    }

    public @NotNull Font getFont() {
        return font;
    }
    public void setFont(@NotNull Font font) {
        this.font = font;

        // Default dimension value
        Rectangle2D rectangle = font.getStringBounds(text, new FontRenderContext(getAlign().getTransform(), isAntiAliasing(), true));
        this.dimension = new Dimension(rectangle.getBounds().width, rectangle.getBounds().height);
        //
    }

    @Override
    @Contract("-> new")
    public @NotNull TextComponent clone() {
        return (TextComponent) super.clone();
    }
}
