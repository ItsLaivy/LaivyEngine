package codes.laivy.engine.graphics.components;

import codes.laivy.engine.Game;
import codes.laivy.engine.coordinates.Location;
import codes.laivy.engine.coordinates.dimension.Dimension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

/**
 * Um {@link TextComponent TextComponent} é usado para escrever algo na tela do jogo
 */
public class TextComponent extends GameComponent {

    private @NotNull String text;
    private @NotNull Font font;

    public TextComponent(@NotNull Game game, @NotNull Location location, int offsetX, int offsetY, int opacity, @NotNull String text, @NotNull Font font, @Nullable Color color) {
        super(game, location, offsetX, offsetY, opacity);

        this.text = text;
        this.font = font;
        setColor(color);
    }

    public @NotNull Dimension getDimension(AffineTransform transform) {
        Rectangle2D textSize = font.getStringBounds(text, new FontRenderContext(transform, false, false));
        int textWidth = (int) textSize.getWidth();
        int textHeight = (int) textSize.getHeight();

        return new Dimension(textWidth, textHeight);
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
    }

    @Override
    public @NotNull TextComponent clone() {
        TextComponent component = (TextComponent) super.clone();

        component.setText(getText());
        component.setFont(getFont());

        return component;
    }
}
