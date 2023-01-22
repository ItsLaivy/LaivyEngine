package codes.laivy.engine.graphics.window.swing;

import codes.laivy.engine.graphics.window.GameWindow;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    private final @NotNull GameWindow window;

    public GamePanel(@NotNull GameWindow window) {
        this.window = window;
    }

    public @NotNull GameWindow getWindow() {
        return window;
    }

    @Override
    public void paint(Graphics g) {
        getWindow().getGame().render();

        super.paint(g);

        // The layout will do the work :)
        if (getWindow().getLayout() != null && getWindow().getFrame().isVisible()) {
            getWindow().getLayout().callLayout((Graphics2D) g);
        }

        g.dispose();
    }
    
}
