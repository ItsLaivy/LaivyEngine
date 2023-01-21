package codes.laivy.engine.graphics.window.swing;

import codes.laivy.engine.coordinates.Location;
import codes.laivy.engine.graphics.window.GameWindow;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GameFrame extends JFrame {

    private final @NotNull GameWindow window;

    public GameFrame(@NotNull GameWindow window) {
        this.window = window;
    }

    public @NotNull GameWindow getWindow() {
        return window;
    }

}
