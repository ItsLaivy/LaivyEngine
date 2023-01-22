package codes.laivy.engine.tests.first.entity;

import codes.laivy.engine.assets.ImageAsset;
import codes.laivy.engine.coordinates.Location;
import codes.laivy.engine.coordinates.dimension.Dimension;
import codes.laivy.engine.graphics.components.ImageComponent;
import codes.laivy.engine.graphics.layout.responsive.ResponsiveLayout;
import codes.laivy.engine.graphics.layout.responsive.disposition.ResponsiveDisposition;
import codes.laivy.engine.tests.first.TestGame;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Objects;

public class ImagePlayer extends Player {

    private final @NotNull ImageComponent image;

    public ImagePlayer(@NotNull String name, @NotNull ImageAsset asset, @NotNull Location location) {
        super(name, location);

        this.image = new ImageComponent(TestGame.instance().getWindow().getPanel(), getLocation().clone(), new Dimension(35, 35), 0, 0, asset);
        this.image.setDisposition(new ResponsiveDisposition.Image(this.image, (ResponsiveLayout) Objects.requireNonNull(TestGame.instance().getWindow().getLayout())));
        this.image.getBackground().setColor(Color.RED);
        this.image.getBackground().setOpacity(20);

        getSquare().getPanel().getWindow().getGame().getGraphics().runWindowThreadLater(this.image::add);
    }

    @Override
    public void setLocation(@NotNull Location location) {
        super.setLocation(location);
        this.image.setLocation(getLocation());
    }

}
