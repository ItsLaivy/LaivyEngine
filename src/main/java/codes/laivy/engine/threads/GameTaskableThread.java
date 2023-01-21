package codes.laivy.engine.threads;

import codes.laivy.engine.Game;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedList;

public class GameTaskableThread extends GameThread {

    private final @NotNull LinkedList<@NotNull GameRunnable> tasks = new LinkedList<>();

    public GameTaskableThread(@NotNull Game game, @NotNull String name) {
        super(game, name);
    }

    public GameTaskableThread(@NotNull Game game, @NotNull String name, @Nullable Runnable target) {
        super(game, name, target);
    }

    public @NotNull LinkedList<GameRunnable> getTasks() {
        return tasks;
    }

    @Override
    public final void run() {
        while (isAlive()) {
            for (GameRunnable task : new LinkedList<>(getTasks())) {
                task.execute();
            }
            getTasks().clear();

            try {
                //noinspection BusyWait
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public final void run(@NotNull GameRunnable runnable) {
        getTasks().add(runnable);
    }

}
