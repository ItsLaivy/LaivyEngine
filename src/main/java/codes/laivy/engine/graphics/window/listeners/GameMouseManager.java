package codes.laivy.engine.graphics.window.listeners;

import codes.laivy.engine.coordinates.Location;
import codes.laivy.engine.graphics.window.GameWindow;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GameMouseManager implements Manager<MouseListener> {

    protected @Nullable Location clickLocation;

    protected boolean pressing = false;

    protected @NotNull MouseListener listener = new MouseAdapter() {
    };

    private final @NotNull GameWindow window;

    public GameMouseManager(@NotNull GameWindow window) {
        this.window = window;

        getWindow().getFrame().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                getWindow().getMouseManager().getListener().mouseClicked(e);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                getWindow().getMouseManager().setClickLocation(new Location(e.getX(), e.getY()));
                getWindow().getMouseManager().setPressing(true);
                getWindow().getMouseManager().getListener().mousePressed(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                getWindow().getMouseManager().setClickLocation(null);
                getWindow().getMouseManager().setPressing(false);
                getWindow().getMouseManager().getListener().mouseReleased(e);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                getWindow().getMouseManager().getListener().mouseEntered(e);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                getWindow().getMouseManager().getListener().mouseExited(e);
            }
        });
    }

    public @NotNull GameWindow getWindow() {
        return window;
    }

    public @Nullable Location getClickLocation() {
        return clickLocation;
    }
    public void setClickLocation(@Nullable Location clickLocation) {
        this.clickLocation = clickLocation;
    }

    public boolean isPressing() {
        return pressing;
    }
    public void setPressing(boolean pressing) {
        this.pressing = pressing;
    }

    @Override
    public @NotNull MouseListener getListener() {
        return listener;
    }
    @Override
    public void setListener(@NotNull MouseListener listener) {
        this.listener = listener;
    }

}
