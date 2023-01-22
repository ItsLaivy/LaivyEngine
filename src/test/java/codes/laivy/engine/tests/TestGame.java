package codes.laivy.engine.tests;

import codes.laivy.engine.Game;
import codes.laivy.engine.coordinates.Location;
import codes.laivy.engine.graphics.layout.responsive.ResponsiveLayout;
import codes.laivy.engine.tests.entity.Entity;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class TestGame extends Game {

    public static void main(String[] args) {
        new TestGame();
    }

    public static @NotNull TestGame instance() {
        return (TestGame) getGame("Nome Teste");
    }

    Entity player;

    @Override
    public void init() {
        new GameExecutionThread(this).start();

        getGraphics().setFramesPerSecond(60);

        getGraphics().getWindow().setLayout(new ResponsiveLayout(getGraphics().getWindow(), getWindow().getSize()));

        //player = new ImagePlayer("ItsLaivy", Game.LAIVY_ENGINE_LOGO, new Location(getWindow().getSize().getWidth() / 2, getWindow().getSize().getHeight() / 2));
        player = new Entity(new Location(getWindow().getSize().getWidth() / 2, getWindow().getSize().getHeight() / 2), Color.RED) {
        };
    }

    @Override
    public void render() {
    }

    @Override
    public boolean exception(@NotNull Thread thread, @NotNull Throwable e) {
        return true;
    }

    @Override
    public @NotNull String getName() {
        return "Nome Teste";
    }
}
