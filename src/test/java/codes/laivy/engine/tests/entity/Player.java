package codes.laivy.engine.tests.entity;

import codes.laivy.engine.coordinates.Location;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Random;

public class Player extends Entity {
    public Player(@NotNull Location location) {
        super(location, new Color(new Random().nextInt(255), new Random().nextInt(255), new Random().nextInt(255)));
    }
}
