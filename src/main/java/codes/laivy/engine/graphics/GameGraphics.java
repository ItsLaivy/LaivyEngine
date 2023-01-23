package codes.laivy.engine.graphics;

import codes.laivy.engine.Game;
import codes.laivy.engine.graphics.window.GameWindow;
import codes.laivy.engine.threads.GameRunnable;
import codes.laivy.engine.threads.GraphicsThread;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import javax.swing.*;

public class GameGraphics {

    private final @NotNull Game game;
    protected final @NotNull GameWindow window;

    private @Range(from = 1, to = 1000) int framesPerSecond = 60;

    protected @NotNull GraphicsThread graphicsThread;

    public GameGraphics(@NotNull Game game, @NotNull GameWindow window) {
        this.game = game;
        this.window = window;

        graphicsThread = new GraphicsThread(this);
        graphicsThread.start();
    }

    public boolean isWindowThread() {
        return SwingUtilities.isEventDispatchThread();
    }
    public void runWindowThreadLater(@NotNull GameRunnable runnable) {
        SwingUtilities.invokeLater(runnable);
    }

    @NotNull
    @Contract(pure = true)
    public Game getGame() {
        return game;
    }
    @NotNull
    @Contract(pure = true)
    public GameWindow getWindow() {
        return window;
    }

    public @Range(from = 1, to = 1000) int getFramesPerSecond() {
        return framesPerSecond;
    }

    public void setFramesPerSecond(@Range(from = 1, to = 1000) int framesPerSecond) {
        this.framesPerSecond = framesPerSecond;
    }

}
