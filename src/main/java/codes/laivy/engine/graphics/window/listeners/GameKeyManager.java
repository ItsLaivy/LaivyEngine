package codes.laivy.engine.graphics.window.listeners;

import codes.laivy.engine.graphics.window.GameWindow;
import org.jetbrains.annotations.NotNull;

import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class GameKeyManager implements Manager<KeyListener> {

    // KeyCode, Key
    private final @NotNull Map<@NotNull Integer, @NotNull KeyEvent> keys = new HashMap<>();

    protected @NotNull KeyListener listener = new KeyAdapter() {
    };

    private final @NotNull GameWindow window;

    public GameKeyManager(@NotNull GameWindow window) {
        this.window = window;

        getWindow().getFrame().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                getWindow().getKeyManager().getListener().keyTyped(e);
            }

            @Override
            public void keyPressed(KeyEvent e) {
                getWindow().getKeyManager().getKeys().put(e.getKeyCode(), e);
                getWindow().getKeyManager().getListener().keyPressed(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                getWindow().getKeyManager().getKeys().remove(e.getKeyCode());
                getWindow().getKeyManager().getListener().keyReleased(e);
            }
        });
    }

    public @NotNull GameWindow getWindow() {
        return window;
    }

    public @NotNull Map<@NotNull Integer, @NotNull KeyEvent> getKeys() {
        return keys;
    }

    public boolean isKeyTyping(int keyCode) {
        return getKeys().containsKey(keyCode);
    }
    public boolean isKeyTyping(@NotNull KeyEvent keyCode) {
        return getKeys().containsValue(keyCode);
    }

    @Override
    public @NotNull KeyListener getListener() {
        return listener;
    }
    @Override
    public void setListener(@NotNull KeyListener listener) {
        this.listener = listener;
    }

}
