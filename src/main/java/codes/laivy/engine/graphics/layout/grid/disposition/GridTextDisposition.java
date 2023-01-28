package codes.laivy.engine.graphics.layout.grid.disposition;

import codes.laivy.engine.coordinates.Location;
import codes.laivy.engine.coordinates.dimension.Dimension;
import codes.laivy.engine.graphics.components.GameComponent;
import codes.laivy.engine.graphics.components.TextComponent;
import codes.laivy.engine.graphics.layout.GameLayout;
import codes.laivy.engine.graphics.layout.grid.columns.GridColumn;
import codes.laivy.engine.utils.MathUtils;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Objects;

public class GridTextDisposition extends GridDisposition {
    public GridTextDisposition(@NotNull TextComponent component, @NotNull GridColumn column) {
        super(component, column.getRow().getLayout(), column);

        if (!Objects.equals(getComponent().getGamePanel().getEngineLayout(), getLayout())) {
            throw new IllegalArgumentException("This game component's panel layout isn't the same as the disposition layout!");
        }
    }

    @Override
    @Contract(pure = true)
    public @NotNull TextComponent getComponent() {
        return (TextComponent) super.getComponent();
    }

    @Override
    public void drawBackground(@NotNull Graphics2D backgroundGraphics, GameLayout.@NotNull Coordinates coordinates, @NotNull GameLayout.Bounds bounds) {
        Dimension temp = coordinates.getClientDimension().clone();

        coordinates.setScreenLocation(coordinates.getClientLocation().clone());
        coordinates.setScreenDimension(temp);

        Color color = component.getBackground().getFinalColor();
        if (color != null) {
            backgroundGraphics.setColor(color);
            backgroundGraphics.fill(new Rectangle(coordinates.getScreenLocation().toPoint(), temp.toSwing()));
        }
    }

    @Override
    public void drawObject(@NotNull Graphics2D graphics, @NotNull Location location, @NotNull Dimension dimension, @NotNull GameLayout.Bounds bounds) {
        Font font = getComponent().getFont().deriveFont((float) MathUtils.rthree(getComponent().getDimension().getWidth() * 8F, getComponent().getSize(), bounds.getAvailable().getWidth()));

        graphics.setFont(font);
        graphics.drawString(getComponent().getText(), location.getX(), location.getY() + dimension.getHeight());
    }

    @Override
    public void alignment(@NotNull Graphics2D renderingGraphics, @NotNull GameComponent.Alignment alignment, @NotNull GameLayout.Coordinates coords, @NotNull GameLayout.Bounds bounds) {
        Dimension dimension = getComponent().getDimension();

        renderingGraphics.transform(alignment.getTransform());
        if (alignment == GameComponent.Alignment.FLIPPED_HORIZONTALLY) {
            coords.getClientLocation().setX(-coords.getClientLocation().getX() - coords.getScreenDimension().getWidth());
        } else if (alignment == GameComponent.Alignment.FLIPPED_VERTICALLY) {
            coords.getClientLocation().setY(-coords.getClientLocation().getY() - coords.getScreenDimension().getHeight());
        } else if (alignment == GameComponent.Alignment.FLIPPED_VERTICALLY_HORIZONTALLY) {
            coords.getClientLocation().setX(-coords.getClientLocation().getX() - coords.getScreenDimension().getWidth());
            coords.getClientLocation().setY(-coords.getClientLocation().getY() - coords.getScreenDimension().getHeight());
        }
    }
}
