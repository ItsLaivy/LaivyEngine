package codes.laivy.engine.tests.entity;

import codes.laivy.engine.coordinates.Location;
import codes.laivy.engine.graphics.components.TextComponent;
import codes.laivy.engine.graphics.layout.responsive.ResponsiveLayout;
import codes.laivy.engine.graphics.layout.responsive.disposition.ResponsiveDisposition;
import codes.laivy.engine.tests.TestGame;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Objects;
import java.util.Random;

public class Player extends Entity {

    private final @NotNull TextComponent name;

    public Player(@NotNull String name, @NotNull Location location) {
        super(location, new Color(new Random().nextInt(255), new Random().nextInt(255), new Random().nextInt(255)));

        this.name = new TextComponent(TestGame.instance(), getLocation().clone(), -17, -15, 100, name, new Font("ARIAL", Font.PLAIN, 20), Color.WHITE);
        this.name.setDisposition(new ResponsiveDisposition.Text(this.name, (ResponsiveLayout) Objects.requireNonNull(TestGame.instance().getWindow().getLayout())));
        getSquare().getGame().getGraphics().runWindowThreadLater(this.name::add);
    }

    @Override
    public void setLocation(@NotNull Location location) {
        super.setLocation(location);
        this.name.setLocation(getLocation());
    }

}
