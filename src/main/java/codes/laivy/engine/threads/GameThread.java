package codes.laivy.engine.threads;

import codes.laivy.engine.Game;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class GameThread extends Thread {

    private final @NotNull Game game;

    private final long creationTime;

    private long startTime = 0;
    private long interruptTime = 0;
    private long endTime = 0;

    public GameThread(@NotNull Game game, @NotNull String name) {
        this(game, name, null);
    }
    public GameThread(@NotNull Game game, @NotNull String name, @Nullable Runnable target) {
        super(target, name);
        this.game = game;
        this.game.getThreads().add(this);
        this.creationTime = System.currentTimeMillis();
    }

    @Override
    public synchronized void start() {
        super.start();
        startTime = System.currentTimeMillis();
    }

    @Override
    public abstract void run();

    @SuppressWarnings("deprecation")
    @Override
    public void destroy() {
        throw new UnsupportedOperationException();
    }

    @SuppressWarnings("deprecation")
    @Override
    public int countStackFrames() {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void finalize() throws Throwable {
        if (isAlive()) {
            super.finalize();
            endTime = System.currentTimeMillis();
        } else {
            throw new IllegalStateException("This thread isn't running");
        }
    }

    @Override
    public void interrupt() {
        super.interrupt();
        interruptTime = System.currentTimeMillis();
    }

    public long getCreationTime() {
        return creationTime;
    }

    public long getStartTime() {
        return startTime;
    }
    public long getEndTime() {
        return endTime;
    }
    public long getInterruptTime() {
        return interruptTime;
    }

    public @NotNull Game getGame() {
        return game;
    }

}
