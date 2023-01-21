package codes.laivy.engine.tests;

import codes.laivy.engine.Game;
import codes.laivy.engine.coordinates.Location;
import codes.laivy.engine.graphics.layout.responsive.ResponsiveLayout;
import codes.laivy.engine.tests.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TestGame extends Game {

    public static void main(String[] args) {
        new TestGame();
    }

    public static @NotNull TestGame instance() {
        return (TestGame) getGame("Nome Teste");
    }

    Player player;

    @Override
    public void init() {
        new GameExecutionThread(this).start();

        getGraphics().setFramesPerSecond(60);

        ResponsiveLayout layout = new ResponsiveLayout(getGraphics().getWindow(), getWindow().getSize());
        getGraphics().getWindow().setLayout(layout);

        player = new Player(new Location(getWindow().getSize().getWidth() / 2, getWindow().getSize().getHeight() / 2));
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
