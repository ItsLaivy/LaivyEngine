package codes.laivy.engine.threads;

import codes.laivy.engine.graphics.GameGraphics;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class GraphicsThread extends GameThread {

    private final @NotNull GameGraphics graphics;

    public GraphicsThread(@NotNull GameGraphics graphics) {
        super(graphics.getGame(), "Graphics WindowThread");
        this.graphics = graphics;
    }

    @Override
    public void run() {
        while (isAlive()) {
            getGraphics().getWindow().getPanel().repaint();

            try {
                //noinspection BusyWait
                Thread.sleep(1000 / getGraphics().getFramesPerSecond());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Contract(pure = true)
    public @NotNull GameGraphics getGraphics() {
        return graphics;
    }
}
