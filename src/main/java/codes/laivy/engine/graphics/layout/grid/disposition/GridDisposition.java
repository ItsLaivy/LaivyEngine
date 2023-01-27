package codes.laivy.engine.graphics.layout.grid.disposition;

import codes.laivy.engine.coordinates.Location;
import codes.laivy.engine.graphics.components.GameComponent;
import codes.laivy.engine.graphics.layout.ComponentDisposition;
import codes.laivy.engine.graphics.layout.GameLayout;
import codes.laivy.engine.graphics.layout.GameLayoutBounds;
import codes.laivy.engine.graphics.layout.grid.columns.GridColumn;
import codes.laivy.engine.graphics.layout.grid.GridLayout;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import codes.laivy.engine.coordinates.dimension.Dimension;

import java.awt.*;

public abstract class GridDisposition extends ComponentDisposition<GridLayout> {

    private final @NotNull GridColumn column;

    public GridDisposition(@NotNull GameComponent component, @NotNull GridLayout layout, @NotNull GridColumn column) {
        super(component, layout);
        this.column = column;

        if (column.getDisposition() != null) {
            throw new IllegalArgumentException("This column already have a disposition!");
        }

        column.setDisposition(this);
    }

    @Contract(pure = true)
    public @NotNull GridColumn getColumn() {
        return column;
    }

    public void postResolutionFix(@NotNull Graphics2D renderingGraphics, @NotNull Graphics2D backgroundGraphics, @NotNull GameLayout.LayoutCoordinates coordinates, @NotNull GameLayoutBounds bounds) {
    }

    public void render(@NotNull Graphics2D graphics, @NotNull GameLayout.LayoutCoordinates coordinates, @NotNull GameLayoutBounds bounds) {
        if (getComponent().isAntiAliasing()) {
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }
        graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);

        Graphics2D renderingGraphics = (Graphics2D) graphics.create();
        Graphics2D backgroundGraphics = (Graphics2D) graphics.create();

        // Background
        drawBackground(backgroundGraphics, coordinates, bounds);
        //

        // Post resolution fix
        postResolutionFix(renderingGraphics, backgroundGraphics, coordinates, bounds);
        //

        // Alignment
        alignment(renderingGraphics, getComponent().getAlign(), coordinates, bounds);
        //

        // Component rendering
        renderingGraphics.setColor(getComponent().getColor());

        if (getComponent().getStroke() != null) {
            renderingGraphics.setStroke(getComponent().getStroke());
        }
        if (getComponent().getOpacity() != 100) {
            renderingGraphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, getComponent().getOpacity() / 100F));
        }

        drawObject(renderingGraphics, coordinates.getClientLocation(), coordinates.getClientDimension(), bounds);
        //

        // Graphics disposing
        backgroundGraphics.dispose();
        renderingGraphics.dispose();
        //

        // Hitbox defining
        getComponent().setScreenLocation(coordinates.getScreenLocation());
        getComponent().setScreenDimension(coordinates.getScreenDimension());
        //
    }

    public void drawBackground(@NotNull Graphics2D backgroundGraphics, @NotNull GameLayout.LayoutCoordinates coordinates, @NotNull GameLayoutBounds bounds) {
        Color color = getComponent().getBackground().getFinalColor();
        if (color != null) {
            backgroundGraphics.setColor(color);
            backgroundGraphics.fill(new java.awt.Rectangle(coordinates.getClientLocation().toPoint(), coordinates.getClientDimension().toSwing()));
        }
    }

    public abstract void drawObject(@NotNull Graphics2D renderingGraphics, @NotNull Location location, @NotNull Dimension dimension, @NotNull GameLayoutBounds bounds);

    public void alignment(@NotNull Graphics2D renderingGraphics, @NotNull GameComponent.Alignment alignment, @NotNull GameLayout.LayoutCoordinates coords, @NotNull GameLayoutBounds bounds) {
        renderingGraphics.transform(alignment.getTransform());
        if (alignment == GameComponent.Alignment.FLIPPED_HORIZONTALLY) {
            coords.getClientLocation().setX(coords.getScreenLocation().getX());
        } else if (alignment == GameComponent.Alignment.FLIPPED_VERTICALLY) {
            coords.getClientLocation().setY(coords.getScreenLocation().getY());
        } else if (alignment == GameComponent.Alignment.FLIPPED_VERTICALLY_HORIZONTALLY) {
            coords.getClientLocation().setX(coords.getScreenLocation().getX());
            coords.getClientLocation().setY(coords.getScreenLocation().getY());
        }
    }

    @Contract(pure = true)
    public @NotNull GridColumn getRow() {
        return column;
    }
}
