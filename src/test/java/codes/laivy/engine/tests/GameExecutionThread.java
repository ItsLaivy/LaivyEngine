package codes.laivy.engine.tests;

import codes.laivy.engine.Game;
import codes.laivy.engine.coordinates.MouseLocation;
import codes.laivy.engine.tests.entity.Player;
import codes.laivy.engine.threads.GameThread;
import org.jetbrains.annotations.NotNull;

import java.awt.event.KeyEvent;

public class GameExecutionThread extends GameThread {
    public GameExecutionThread(@NotNull Game game) {
        super(game, "Game Execution WindowThread");
    }

    @Override
    public void run() {
        while (true) {
            Player player = TestGame.instance().player;

            if (getGame().getWindow().getKeyManager().isKeyTyping(KeyEvent.VK_UP)) {
                player.setLocation(player.getLocation().add(0, -player.getSpeed()));
            }
            if (getGame().getWindow().getKeyManager().isKeyTyping(KeyEvent.VK_DOWN)) {
                player.setLocation(player.getLocation().add(0, player.getSpeed()));
            }
            if (getGame().getWindow().getKeyManager().isKeyTyping(KeyEvent.VK_LEFT)) {
                player.setLocation(player.getLocation().add(-player.getSpeed(), 0));
            }
            if (getGame().getWindow().getKeyManager().isKeyTyping(KeyEvent.VK_RIGHT)) {
                player.setLocation(player.getLocation().add(player.getSpeed(), 0));
            }

            getGame().getGraphics().runWindowThreadLater(() -> {
                @NotNull MouseLocation mouse = getGame().getWindow().getMouseLocation();
                if (mouse.getLocation() != null) {
                    System.out.println(mouse.getLocation() + " - '" + mouse.getLocation().getComponents(getGame().getWindow()) + "'");
                }
            });

            try {
                //noinspection BusyWait
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
