package codes.laivy.engine.tests.first;

import codes.laivy.engine.Game;
import codes.laivy.engine.coordinates.MouseLocation;
import codes.laivy.engine.graphics.components.GameComponent;
import codes.laivy.engine.tests.first.entity.Entity;
import codes.laivy.engine.threads.GameThread;
import org.jetbrains.annotations.NotNull;

import java.awt.event.KeyEvent;
import java.util.Set;

public class GameExecutionThread extends GameThread {
    public GameExecutionThread(@NotNull Game game) {
        super(game, "Game Execution WindowThread");
    }

    @Override
    public void run() {
        while (true) {
            Entity player = TestGame.instance().player;

            if (getGame().getWindow().getPanel().getKeyManager().isKeyTyping(KeyEvent.VK_UP)) {
                player.setLocation(player.getLocation().add(0, -player.getSpeed()));
            }
            if (getGame().getWindow().getPanel().getKeyManager().isKeyTyping(KeyEvent.VK_DOWN)) {
                player.setLocation(player.getLocation().add(0, player.getSpeed()));
            }
            if (getGame().getWindow().getPanel().getKeyManager().isKeyTyping(KeyEvent.VK_LEFT)) {
                player.setLocation(player.getLocation().add(-player.getSpeed(), 0));
            }
            if (getGame().getWindow().getPanel().getKeyManager().isKeyTyping(KeyEvent.VK_RIGHT)) {
                player.setLocation(player.getLocation().add(player.getSpeed(), 0));
            }

            getGame().getGraphics().runWindowThreadLater(() -> {
                @NotNull MouseLocation mouse = getGame().getWindow().getMouseLocation();
                if (mouse.getLocation() != null) {
                    Set<GameComponent> components = mouse.getLocation().getComponents(getGame().getWindow());
                    if (!components.isEmpty()) {
                        System.out.println("Hovering: '" + components + "'");
                    }
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
